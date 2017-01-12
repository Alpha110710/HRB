package com.hrb.model;

import com.google.gson.annotations.SerializedName;

public class MyRedPacketOutDateModel extends BaseModel{
	
	  /**
     * TYPE : 红包
     * ACQUISITION_AMOUNT : 28元
     * COUPON_ORIGIN_AVENUE : 邀请
     * END_DATE : 16/08/11
     */

	@SerializedName("TYPE")
    private String TYPE;
	
	@SerializedName("ACQUISITION_AMOUNT")
    private String ACQUISITION_AMOUNT;
	
	@SerializedName("COUPON_ORIGIN_AVENUE")
    private String COUPON_ORIGIN_AVENUE;
	
	@SerializedName("END_DATE")
    private String END_DATE;

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getACQUISITION_AMOUNT() {
        return ACQUISITION_AMOUNT;
    }

    public void setACQUISITION_AMOUNT(String ACQUISITION_AMOUNT) {
        this.ACQUISITION_AMOUNT = ACQUISITION_AMOUNT;
    }

    public String getCOUPON_ORIGIN_AVENUE() {
        return COUPON_ORIGIN_AVENUE;
    }

    public void setCOUPON_ORIGIN_AVENUE(String COUPON_ORIGIN_AVENUE) {
        this.COUPON_ORIGIN_AVENUE = COUPON_ORIGIN_AVENUE;
    }

    public String getEND_DATE() {
        return END_DATE;
    }

    public void setEND_DATE(String END_DATE) {
        this.END_DATE = END_DATE;
    }

}
