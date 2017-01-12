package com.hrb.model;

/**
 * Created by Kindling on 2016/11/03 10:06.
 */

public class WithdrawRecordModel extends BaseModel {

    /**
     * WITHDRAW_AMOUNT : 0.10
     * ACTUAL_AMOUNT : 0.10
     * WITHDRAW_DATE : 2016-11-03 09:57:32
     * WITHDRAW_STATUS : 审核中
     */

    private String WITHDRAW_AMOUNT;
    private String ACTUAL_AMOUNT;
    private String WITHDRAW_DATE;
    private String WITHDRAW_STATUS;

    public String getWITHDRAW_AMOUNT() {
        return WITHDRAW_AMOUNT;
    }

    public void setWITHDRAW_AMOUNT(String WITHDRAW_AMOUNT) {
        this.WITHDRAW_AMOUNT = WITHDRAW_AMOUNT;
    }

    public String getACTUAL_AMOUNT() {
        return ACTUAL_AMOUNT;
    }

    public void setACTUAL_AMOUNT(String ACTUAL_AMOUNT) {
        this.ACTUAL_AMOUNT = ACTUAL_AMOUNT;
    }

    public String getWITHDRAW_DATE() {
        return WITHDRAW_DATE;
    }

    public void setWITHDRAW_DATE(String WITHDRAW_DATE) {
        this.WITHDRAW_DATE = WITHDRAW_DATE;
    }

    public String getWITHDRAW_STATUS() {
        return WITHDRAW_STATUS;
    }

    public void setWITHDRAW_STATUS(String WITHDRAW_STATUS) {
        this.WITHDRAW_STATUS = WITHDRAW_STATUS;
    }
}
