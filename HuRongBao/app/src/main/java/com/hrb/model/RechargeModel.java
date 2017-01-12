package com.hrb.model;

/**
 * Created by Kindling on 2016/10/31 11:30.
 */

public class RechargeModel extends BaseModel {

    /**
     * ORDER_NO : DAA201610311608350959783690
     * BINDID : 2386
     * CONTRACTS : RB161021PWLJC66A
     * MOBILE : 18041196785
     * URL :
     */

    private String ORDER_NO;
    private String BINDID;
    private String CONTRACTS;
    private String MOBILE;
    private String URL;

    public String getORDER_NO() {
        return ORDER_NO;
    }

    public void setORDER_NO(String ORDER_NO) {
        this.ORDER_NO = ORDER_NO;
    }

    public String getBINDID() {
        return BINDID;
    }

    public void setBINDID(String BINDID) {
        this.BINDID = BINDID;
    }

    public String getCONTRACTS() {
        return CONTRACTS;
    }

    public void setCONTRACTS(String CONTRACTS) {
        this.CONTRACTS = CONTRACTS;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
