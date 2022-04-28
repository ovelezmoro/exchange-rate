package com.neoris.service.tecnicalchallenge.exchangerate.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exchange_rate")
public class ExchangeRateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String day;
    @NotNull
    private String baseCurrency;
    @NotNull
    private String exchangeCurrency;
    @NotNull
    private Double rateAmount;
    @NotNull
    private Date createAt;
    private Date updateAt;

    @PrePersist
    public void prePersist() {
        if (this.createAt == null) {
            this.createAt = new Date();
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updateAt = new Date();
    }

}
