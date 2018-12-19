package com.zcy.zcmorefun.jifen.model.system;

import java.util.Date;

public class MerchantSetup {
    private Integer setupId;

    private Integer merchantId;

    private Integer exchangeStatus;

    private String integralUnit;

    private Integer exchangeRate;

    private Integer optionalStatus;

    private Integer limitStatus;

    private Date exchangeBeginDate;

    private Date exchangeEndDate;

    private Integer exchangeLimit;

    private Date updateTime;

    private Integer integralRate;

    private Integer clearStatus;

    private Date clearTime;

    private Integer informStatus;

    private Date informTime;

    public Integer getSetupId() {
        return setupId;
    }

    public void setSetupId(Integer setupId) {
        this.setupId = setupId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getExchangeStatus() {
        return exchangeStatus;
    }

    public void setExchangeStatus(Integer exchangeStatus) {
        this.exchangeStatus = exchangeStatus;
    }

    public String getIntegralUnit() {
        return integralUnit;
    }

    public void setIntegralUnit(String integralUnit) {
        this.integralUnit = integralUnit == null ? null : integralUnit.trim();
    }

    public Integer getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Integer exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Integer getOptionalStatus() {
        return optionalStatus;
    }

    public void setOptionalStatus(Integer optionalStatus) {
        this.optionalStatus = optionalStatus;
    }

    public Integer getLimitStatus() {
        return limitStatus;
    }

    public void setLimitStatus(Integer limitStatus) {
        this.limitStatus = limitStatus;
    }

    public Date getExchangeBeginDate() {
        return exchangeBeginDate;
    }

    public void setExchangeBeginDate(Date exchangeBeginDate) {
        this.exchangeBeginDate = exchangeBeginDate;
    }

    public Date getExchangeEndDate() {
        return exchangeEndDate;
    }

    public void setExchangeEndDate(Date exchangeEndDate) {
        this.exchangeEndDate = exchangeEndDate;
    }

    public Integer getExchangeLimit() {
        return exchangeLimit;
    }

    public void setExchangeLimit(Integer exchangeLimit) {
        this.exchangeLimit = exchangeLimit;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIntegralRate() {
        return integralRate;
    }

    public void setIntegralRate(Integer integralRate) {
        this.integralRate = integralRate;
    }

    public Integer getClearStatus() {
        return clearStatus;
    }

    public void setClearStatus(Integer clearStatus) {
        this.clearStatus = clearStatus;
    }

    public Date getClearTime() {
        return clearTime;
    }

    public void setClearTime(Date clearTime) {
        this.clearTime = clearTime;
    }

    public Integer getInformStatus() {
        return informStatus;
    }

    public void setInformStatus(Integer informStatus) {
        this.informStatus = informStatus;
    }

    public Date getInformTime() {
        return informTime;
    }

    public void setInformTime(Date informTime) {
        this.informTime = informTime;
    }
}