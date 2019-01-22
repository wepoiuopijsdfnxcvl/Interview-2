package com.zcy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class UserVirtualCard {
    private Integer virtualCardId;

    private String cardId;

    private Integer payMerchantId;

    private Integer userId;

    private Date createTime;

    private BigDecimal cardBalance;

    private Integer cardStat;

    private Date validityDate;

}