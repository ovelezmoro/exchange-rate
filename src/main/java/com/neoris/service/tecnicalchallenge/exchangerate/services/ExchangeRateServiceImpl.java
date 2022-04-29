package com.neoris.service.tecnicalchallenge.exchangerate.services;

import static com.neoris.service.tecnicalchallenge.util.ExchangeRateUtil.dateToString;
import static com.neoris.service.tecnicalchallenge.util.ExchangeRateUtil.stringToDate;
import static com.neoris.service.tecnicalchallenge.util.ExchangeRateUtil.ENG_FORMAT_DATE;
import static com.neoris.service.tecnicalchallenge.util.ExchangeRateUtil.HRS_FORMAT_DATE;
import static com.neoris.service.tecnicalchallenge.util.ExchangeRateUtil.DB_FORMAT_DATE;

import com.neoris.service.tecnicalchallenge.exchangerate.models.AuditExchangeRate;
import com.neoris.service.tecnicalchallenge.exchangerate.models.ExchangeRateModel;
import com.neoris.service.tecnicalchallenge.exchangerate.repositories.AuditExchangeRateRepository;
import com.neoris.service.tecnicalchallenge.exchangerate.repositories.ExchangeRateRepository;
import com.neoris.service.tecnicalchallenge.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.Date;



@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private AuditExchangeRateRepository auditExchangeRateRepository;

    @Transactional
    @Override
    public Mono<ExchangeRateResponse> create(Mono<ExchangeRateRequest> request) {
        return request
                .flatMap(exchangeRateRequest -> {
                    ExchangeRateModel model = exchangeRateRepository.findOneByBaseCurrencyAndExchangeCurrencyAndDay(
                            exchangeRateRequest.getBaseCurrency(),
                            exchangeRateRequest.getExchangeCurrency(),
                            dateToString(new Date(), DB_FORMAT_DATE));
                    if (model != null) {
                        return Mono.error(new RuntimeException("El registro ya existe"));
                    }
                    return Mono.just(exchangeRateRequest);
                })
                .flatMap(exchangeRateRequest -> Mono.justOrEmpty(exchangeRateRepository.save(ExchangeRateModel
                        .builder()
                        .baseCurrency(exchangeRateRequest.getBaseCurrency())
                        .exchangeCurrency(exchangeRateRequest.getExchangeCurrency())
                        .day(dateToString(new Date(), DB_FORMAT_DATE))
                        .rateAmount(exchangeRateRequest.getRateAmount())
                        .build())))
                .map(model -> new ExchangeRateResponse()
                        .base(model.getBaseCurrency())
                        .result(new ExchangeRate()
                                .currencyCode(model.getExchangeCurrency())
                                .amount(model.getRateAmount()))
                        .updated(dateToString(new Date(), HRS_FORMAT_DATE)));
    }

    @Override
    public Mono<ExchangeRateResponse> update(Mono<ExchangeRateRequest> request) {
        return request.flatMap(exchangeRateRequest -> {
                    ExchangeRateModel model = exchangeRateRepository.findOneByBaseCurrencyAndExchangeCurrencyAndDay(
                            exchangeRateRequest.getBaseCurrency(),
                            exchangeRateRequest.getExchangeCurrency(),
                            dateToString(new Date(), DB_FORMAT_DATE));
                    if (model == null) {
                        return Mono.empty();
                    }
                    model.setRateAmount(exchangeRateRequest.getRateAmount());
                    return Mono.just(model);
                })
                .flatMap(exchangeRateModel -> Mono.just(exchangeRateRepository.save(exchangeRateModel)))
                .map(exchangeRate -> new ExchangeRateResponse()
                        .base(exchangeRate.getBaseCurrency())
                        .result(new ExchangeRate()
                                .currencyCode(exchangeRate.getExchangeCurrency())
                                .amount(exchangeRate.getRateAmount()))
                        .updated(dateToString(new Date(), HRS_FORMAT_DATE)));
    }

    @Override
    public Mono<ExchangeRateResponse> find(String from, String to) {
        return Mono.justOrEmpty(exchangeRateRepository.findOneByBaseCurrencyAndExchangeCurrencyAndDay(from, to, dateToString(new Date(), "ddMMyyyy")))
                .map(exchangeRate -> new ExchangeRateResponse()
                        .base(exchangeRate.getBaseCurrency())
                        .result(new ExchangeRate()
                                .currencyCode(exchangeRate.getExchangeCurrency())
                                .amount(exchangeRate.getRateAmount()))
                        .updated(dateToString(new Date(), HRS_FORMAT_DATE)));
    }

    @Override
    public Mono<HistoryExchangeRateResponse> history(String date, String from) {
        String day = dateToString(stringToDate(date, ENG_FORMAT_DATE), DB_FORMAT_DATE);
        return Flux.fromIterable(exchangeRateRepository.findAllByBaseCurrencyAndDay(from, day))
                .map(exchangeRate -> new ExchangeRate()
                        .currencyCode(exchangeRate.getExchangeCurrency())
                        .amount(exchangeRate.getRateAmount()))
                .collectList()
                .flatMap(exchangeRates -> Mono.just(new HistoryExchangeRateResponse().base(from)
                        .result(exchangeRates)
                        .updated(dateToString(new Date(), HRS_FORMAT_DATE))));
    }

    @Override
    public Mono<ConvertExchangeRateResponse> convert(ConvertExchangeRateRequest request) {
        return Mono.justOrEmpty(exchangeRateRepository.findOneByBaseCurrencyAndExchangeCurrencyAndDay(
                        request.getFrom(),
                        request.getTo(), dateToString(new Date(), "ddMMyyyy")))
                .map(exchangeRateModel -> {
                    UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    auditExchangeRateRepository.save(AuditExchangeRate.builder()
                            .baseCurrency(exchangeRateModel.getBaseCurrency())
                            .rate(exchangeRateModel.getRateAmount())
                            .amount(request.getAmount())
                            .exchangeCurrency(exchangeRateModel.getExchangeCurrency())
                            .user(principal.getUsername())
                            .build());
                    return exchangeRateModel;
                })
                .map(exchangeRate -> new ConvertExchangeRateResponse()
                        .updated(dateToString(new Date(), HRS_FORMAT_DATE))
                        .base(exchangeRate.getBaseCurrency())
                        .amount(request.getAmount())
                        .putResultItem(exchangeRate.getExchangeCurrency(), exchangeRate.getRateAmount() * request.getAmount())
                        .putResultItem("rate", exchangeRate.getRateAmount())
                );

    }
}
