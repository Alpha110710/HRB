package com.hrb.model;

/**
 * Created by Ls on 2016/10/27.
 */

public class GetTransferInfoModel {


    /**
     * TRANSFER_CONTRACT_ID : HRB1610270001Z
     * PRODUCTS_TITLE : 可转让可转让
     * TRANSFER_CAPITAL : 1000.00
     * TRANSFER_AMOUNT : 960.27
     * FAIR_VALUE : 1000.27
     * PERIOD : 92
     * TRANSFER_CAPTIAL_WAIT : 1000.00
     * RATE : 10.00
     * DISCOUNT_SCALE : 4
     * MIN_TENDER_AMOUNT : 100
     * STATUS : 1
     * AUTO_FINANCE_AMOUNT : 0.00
     * USABLE_AMOUNT : 0.00
     */


    private String TRANSFER_CONTRACT_ID;
    private String PRODUCTS_TITLE;
    private String TRANSFER_CAPITAL;
    private String TRANSFER_AMOUNT;
    private String FAIR_VALUE;
    private String PERIOD;
    private String TRANSFER_CAPTIAL_WAIT;
    private String RATE;
    private String DISCOUNT_SCALE;
    private String MIN_TENDER_AMOUNT;
    private String STATUS;
    private String AUTO_FINANCE_AMOUNT;
    private String USABLE_AMOUNT;
    private String FINANCE_REPAY_TYPE;
    private String BORROW_STATUS;


    public String getBORROW_STATUS() {
        return BORROW_STATUS;
    }

    public void setBORROW_STATUS(String BORROW_STATUS) {
        this.BORROW_STATUS = BORROW_STATUS;
    }

    public String getFINANCE_REPAY_TYPE() {
        return FINANCE_REPAY_TYPE;
    }

    public void setFINANCE_REPAY_TYPE(String FINANCE_REPAY_TYPE) {
        this.FINANCE_REPAY_TYPE = FINANCE_REPAY_TYPE;
    }

    public String getTRANSFER_CONTRACT_ID() {
        return TRANSFER_CONTRACT_ID;
    }

    public void setTRANSFER_CONTRACT_ID(String TRANSFER_CONTRACT_ID) {
        this.TRANSFER_CONTRACT_ID = TRANSFER_CONTRACT_ID;
    }

    public String getPRODUCTS_TITLE() {
        return PRODUCTS_TITLE;
    }

    public void setPRODUCTS_TITLE(String PRODUCTS_TITLE) {
        this.PRODUCTS_TITLE = PRODUCTS_TITLE;
    }

    public String getTRANSFER_CAPITAL() {
        return TRANSFER_CAPITAL;
    }

    public void setTRANSFER_CAPITAL(String TRANSFER_CAPITAL) {
        this.TRANSFER_CAPITAL = TRANSFER_CAPITAL;
    }

    public String getTRANSFER_AMOUNT() {
        return TRANSFER_AMOUNT;
    }

    public void setTRANSFER_AMOUNT(String TRANSFER_AMOUNT) {
        this.TRANSFER_AMOUNT = TRANSFER_AMOUNT;
    }

    public String getFAIR_VALUE() {
        return FAIR_VALUE;
    }

    public void setFAIR_VALUE(String FAIR_VALUE) {
        this.FAIR_VALUE = FAIR_VALUE;
    }

    public String getPERIOD() {
        return PERIOD;
    }

    public void setPERIOD(String PERIOD) {
        this.PERIOD = PERIOD;
    }

    public String getTRANSFER_CAPTIAL_WAIT() {
        return TRANSFER_CAPTIAL_WAIT;
    }

    public void setTRANSFER_CAPTIAL_WAIT(String TRANSFER_CAPTIAL_WAIT) {
        this.TRANSFER_CAPTIAL_WAIT = TRANSFER_CAPTIAL_WAIT;
    }

    public String getRATE() {
        return RATE;
    }

    public void setRATE(String RATE) {
        this.RATE = RATE;
    }

    public String getDISCOUNT_SCALE() {
        return DISCOUNT_SCALE;
    }

    public void setDISCOUNT_SCALE(String DISCOUNT_SCALE) {
        this.DISCOUNT_SCALE = DISCOUNT_SCALE;
    }

    public String getMIN_TENDER_AMOUNT() {
        return MIN_TENDER_AMOUNT;
    }

    public void setMIN_TENDER_AMOUNT(String MIN_TENDER_AMOUNT) {
        this.MIN_TENDER_AMOUNT = MIN_TENDER_AMOUNT;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getAUTO_FINANCE_AMOUNT() {
        return AUTO_FINANCE_AMOUNT;
    }

    public void setAUTO_FINANCE_AMOUNT(String AUTO_FINANCE_AMOUNT) {
        this.AUTO_FINANCE_AMOUNT = AUTO_FINANCE_AMOUNT;
    }

    public String getUSABLE_AMOUNT() {
        return USABLE_AMOUNT;
    }

    public void setUSABLE_AMOUNT(String USABLE_AMOUNT) {
        this.USABLE_AMOUNT = USABLE_AMOUNT;
    }

}
