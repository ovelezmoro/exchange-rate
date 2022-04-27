package com.neoris.service.tecnicalchallenge.exchangerate.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exchange_rate")
public class ExchangeRate implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
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
