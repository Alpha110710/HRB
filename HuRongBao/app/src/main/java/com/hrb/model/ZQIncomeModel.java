package com.hrb.model;

/**
 * Created by Ls on 2016/11/9.
 */

public class ZQIncomeModel extends BaseModel {


    /**
     * ALL_AMOUNT : 0
     * DISCOUNT : 0
     * ACTUAL_ACCOUNT : 0
     * EXPECTED_INTEREST : 0
     */

    private String ALL_AMOUNT;
    private String DISCOUNT;
    private String ACTUAL_ACCOUNT;
    private String EXPECTED_INTEREST;

    public String getALL_AMOUNT() {
        return ALL_AMOUNT;
    }

    public void setALL_AMOUNT(String ALL_AMOUNT) {
        this.ALL_AMOUNT = ALL_AMOUNT;
    }

    public String getDISCOUNT() {
        return DISCOUNT;
    }

    public void setDISCOUNT(String DISCOUNT) {
        this.DISCOUNT = DISCOUNT;
    }

    public String getACTUAL_ACCOUNT() {
        return ACTUAL_ACCOUNT;
    }

    public void setACTUAL_ACCOUNT(String ACTUAL_ACCOUNT) {
        this.ACTUAL_ACCOUNT = ACTUAL_ACCOUNT;
    }

    public String getEXPECTED_INTEREST() {
        return EXPECTED_INTEREST;
    }

    public void setEXPECTED_INTEREST(String EXPECTED_INTEREST) {
        this.EXPECTED_INTEREST = EXPECTED_INTEREST;
    }

}
