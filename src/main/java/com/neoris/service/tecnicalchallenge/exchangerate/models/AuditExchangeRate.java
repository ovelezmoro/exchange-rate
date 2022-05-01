package com.neoris.service.tecnicalchallenge.exchangerate.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "aud_exchange_rate")
public class AuditExchangeRate  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String user;
    private Date createAt;
    private String baseCurrency;
    private Double amount;
    private Double rate;
    private String exchangeCurrency;

    @PrePersist
    public void prePersist() {
        if (this.createAt == null) {
            this.createAt = new Date();
        }
    }

}
