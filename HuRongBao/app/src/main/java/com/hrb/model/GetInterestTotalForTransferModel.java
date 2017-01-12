package com.hrb.model;

/**
 * Created by Ls on 2016/10/27.
 */

public class GetInterestTotalForTransferModel {


    /**
     * DISCOUNT : 40.00
     * ACTUAL_ACCOUNT : 960.27
     * INTEREST_TOTAL : 25.00
     */

    private String DISCOUNT;
    private String ACTUAL_ACCOUNT;
    private String INTEREST_TOTAL;

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

    public String getINTEREST_TOTAL() {
        return INTEREST_TOTAL;
    }

    public void setINTEREST_TOTAL(String INTEREST_TOTAL) {
        this.INTEREST_TOTAL = INTEREST_TOTAL;
    }

}
