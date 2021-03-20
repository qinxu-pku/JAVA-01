package org.example.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Account implements Serializable {

    private static final long serialVersionUID = 6957734749389133832L;

    private Long id;

    private Long userId;

    private Integer accountType;

    private BigDecimal accountBalance;

    private BigDecimal accountBalanceLocked;

}
