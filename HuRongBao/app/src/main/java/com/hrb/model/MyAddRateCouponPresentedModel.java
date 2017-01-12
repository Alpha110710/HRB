package com.hrb.model;

public class MyAddRateCouponPresentedModel extends BaseModel {


    /**
     * TYPE : 加息券
     * RATE_COUPON_POSITION : 11%
     * COUPON_ORIGIN_AVENUE : 后台手动
     * USER_DONEE : 18609853377
     * TRANSFER_DATE : 16/10/25
     */

    private String TYPE;
    private String RATE_COUPON_POSITION;
    private String COUPON_ORIGIN_AVENUE;
    private String USER_DONEE;
    private String TRANSFER_DATE;

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getRATE_COUPON_POSITION() {
        return RATE_COUPON_POSITION;
    }

    public void setRATE_COUPON_POSITION(String RATE_COUPON_POSITION) {
        this.RATE_COUPON_POSITION = RATE_COUPON_POSITION;
    }

    public String getCOUPON_ORIGIN_AVENUE() {
        return COUPON_ORIGIN_AVENUE;
    }

    public void setCOUPON_ORIGIN_AVENUE(String COUPON_ORIGIN_AVENUE) {
        this.COUPON_ORIGIN_AVENUE = COUPON_ORIGIN_AVENUE;
    }

    public String getUSER_DONEE() {
        return USER_DONEE;
    }

    public void setUSER_DONEE(String USER_DONEE) {
        this.USER_DONEE = USER_DONEE;
    }

    public String getTRANSFER_DATE() {
        return TRANSFER_DATE;
    }

    public void setTRANSFER_DATE(String TRANSFER_DATE) {
        this.TRANSFER_DATE = TRANSFER_DATE;
    }

}
