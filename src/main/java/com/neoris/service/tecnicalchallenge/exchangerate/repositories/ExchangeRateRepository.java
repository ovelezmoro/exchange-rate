package com.neoris.service.tecnicalchallenge.exchangerate.repositories;

import com.neoris.service.tecnicalchallenge.exchangerate.models.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {
}
