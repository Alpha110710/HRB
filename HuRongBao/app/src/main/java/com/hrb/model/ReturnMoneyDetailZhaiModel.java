package com.hrb.model;

/**
 * Created by Kindling on 2016/10/25 18:54.
 */

public class ReturnMoneyDetailZhaiModel extends BaseModel{
    /**
     * PRODUCTS_TITLE : 手机测试债权001
     * TRANSFER_CONTRACT_ID : HRB1610210001Z
     * RATE : 15.00
     * TENDER_AMOUNT_TRANS : 10000.00
     * ACTUAL_AMOUNT : 9904.08
     * FAIR_VALUE : 10004.08
     * SUCCESS_DATE : 2016-10-21
     * RECOVER_END_DATE : 17/01/21
     * REPAY_TYPE : 到期还本付息
     * STATUS : 回款中
     */


        private String PRODUCTS_TITLE;
        private String TRANSFER_CONTRACT_ID;
        private String RATE;
        private String TENDER_AMOUNT_TRANS;
        private String ACTUAL_AMOUNT;
        private String FAIR_VALUE;
        private String SUCCESS_DATE;
        private String RECOVER_END_DATE;
        private String REPAY_TYPE;
        private String STATUS;
        private String COUPON_RATE;


    public String getCOUPON_RATE() {
        return COUPON_RATE;
    }

    public void setCOUPON_RATE(String COUPON_RATE) {
        this.COUPON_RATE = COUPON_RATE;
    }

    public String getPRODUCTS_TITLE() {
            return PRODUCTS_TITLE;
        }

        public void setPRODUCTS_TITLE(String PRODUCTS_TITLE) {
            this.PRODUCTS_TITLE = PRODUCTS_TITLE;
        }

        public String getTRANSFER_CONTRACT_ID() {
            return TRANSFER_CONTRACT_ID;
        }

        public void setTRANSFER_CONTRACT_ID(String TRANSFER_CONTRACT_ID) {
            this.TRANSFER_CONTRACT_ID = TRANSFER_CONTRACT_ID;
        }

        public String getRATE() {
            return RATE;
        }

        public void setRATE(String RATE) {
            this.RATE = RATE;
        }

        public String getTENDER_AMOUNT_TRANS() {
            return TENDER_AMOUNT_TRANS;
        }

        public void setTENDER_AMOUNT_TRANS(String TENDER_AMOUNT_TRANS) {
            this.TENDER_AMOUNT_TRANS = TENDER_AMOUNT_TRANS;
        }

        public String getACTUAL_AMOUNT() {
            return ACTUAL_AMOUNT;
        }

        public void setACTUAL_AMOUNT(String ACTUAL_AMOUNT) {
            this.ACTUAL_AMOUNT = ACTUAL_AMOUNT;
        }

        public String getFAIR_VALUE() {
            return FAIR_VALUE;
        }

        public void setFAIR_VALUE(String FAIR_VALUE) {
            this.FAIR_VALUE = FAIR_VALUE;
        }

        public String getSUCCESS_DATE() {
            return SUCCESS_DATE;
        }

        public void setSUCCESS_DATE(String SUCCESS_DATE) {
            this.SUCCESS_DATE = SUCCESS_DATE;
        }

        public String getRECOVER_END_DATE() {
            return RECOVER_END_DATE;
        }

        public void setRECOVER_END_DATE(String RECOVER_END_DATE) {
            this.RECOVER_END_DATE = RECOVER_END_DATE;
        }

        public String getREPAY_TYPE() {
            return REPAY_TYPE;
        }

        public void setREPAY_TYPE(String REPAY_TYPE) {
            this.REPAY_TYPE = REPAY_TYPE;
        }

        public String getSTATUS() {
            return STATUS;
        }

        public void setSTATUS(String STATUS) {
            this.STATUS = STATUS;
        }
}
