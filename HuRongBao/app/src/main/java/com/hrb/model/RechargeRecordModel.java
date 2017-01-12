package com.hrb.model;

/**
 * Created by Ls on 2016/10/31.
 */

public class RechargeRecordModel extends BaseModel {


    /**
     * RECHARGE_AMOUNT_FORMAT : 1,000,000.00
     * ACTUAL_AMOUNT_FORMAT : 1,000,000.00
     * RECHARGE_DATE : 2016-10-24 11:26:16
     * RECHARGE_STATUS : 成功
     */

    private String RECHARGE_AMOUNT_FORMAT;
    private String ACTUAL_AMOUNT_FORMAT;
    private String RECHARGE_DATE;
    private String RECHARGE_STATUS;

    public String getRECHARGE_AMOUNT_FORMAT() {
        return RECHARGE_AMOUNT_FORMAT;
    }

    public void setRECHARGE_AMOUNT_FORMAT(String RECHARGE_AMOUNT_FORMAT) {
        this.RECHARGE_AMOUNT_FORMAT = RECHARGE_AMOUNT_FORMAT;
    }

    public String getACTUAL_AMOUNT_FORMAT() {
        return ACTUAL_AMOUNT_FORMAT;
    }

    public void setACTUAL_AMOUNT_FORMAT(String ACTUAL_AMOUNT_FORMAT) {
        this.ACTUAL_AMOUNT_FORMAT = ACTUAL_AMOUNT_FORMAT;
    }

    public String getRECHARGE_DATE() {
        return RECHARGE_DATE;
    }

    public void setRECHARGE_DATE(String RECHARGE_DATE) {
        this.RECHARGE_DATE = RECHARGE_DATE;
    }

    public String getRECHARGE_STATUS() {
        return RECHARGE_STATUS;
    }

    public void setRECHARGE_STATUS(String RECHARGE_STATUS) {
        this.RECHARGE_STATUS = RECHARGE_STATUS;
    }

}
