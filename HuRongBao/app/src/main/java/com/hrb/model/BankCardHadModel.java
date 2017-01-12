package com.hrb.model;

/**
 * Created by Ls on 2016/10/19.
 */

public class BankCardHadModel extends BaseModel {


    /**
     * BANK : cmb
     * CARD_NO : 6214830134694859
     * CARD_NO_CONCEAL : ****	****	****	4859
     * CARD_TYPE : 1
     * CARD_STATUS : 1
     * USER_NAME : Nanguolin
     * ID_CARD : 2102****2011
     * ID_CARD_CONCEAL : 210212199102252011
     * MOBILE : 18609853377
     * BANK_IMG : http://192.168.1.111:9292/iloanWebService/images/bankImages/cmb.png
     */

    private String BANK;
    private String CARD_NO;
    private String CARD_NO_CONCEAL;
    private String CARD_TYPE;
    private String CARD_STATUS;
    private String USER_NAME;
    private String ID_CARD;
    private String ID_CARD_CONCEAL;
    private String MOBILE;
    private String BANK_IMG;

    public String getBANK() {
        return BANK;
    }

    public void setBANK(String BANK) {
        this.BANK = BANK;
    }

    public String getCARD_NO() {
        return CARD_NO;
    }

    public void setCARD_NO(String CARD_NO) {
        this.CARD_NO = CARD_NO;
    }

    public String getCARD_NO_CONCEAL() {
        return CARD_NO_CONCEAL;
    }

    public void setCARD_NO_CONCEAL(String CARD_NO_CONCEAL) {
        this.CARD_NO_CONCEAL = CARD_NO_CONCEAL;
    }

    public String getCARD_TYPE() {
        return CARD_TYPE;
    }

    public void setCARD_TYPE(String CARD_TYPE) {
        this.CARD_TYPE = CARD_TYPE;
    }

    public String getCARD_STATUS() {
        return CARD_STATUS;
    }

    public void setCARD_STATUS(String CARD_STATUS) {
        this.CARD_STATUS = CARD_STATUS;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getID_CARD() {
        return ID_CARD;
    }

    public void setID_CARD(String ID_CARD) {
        this.ID_CARD = ID_CARD;
    }

    public String getID_CARD_CONCEAL() {
        return ID_CARD_CONCEAL;
    }

    public void setID_CARD_CONCEAL(String ID_CARD_CONCEAL) {
        this.ID_CARD_CONCEAL = ID_CARD_CONCEAL;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getBANK_IMG() {
        return BANK_IMG;
    }

    public void setBANK_IMG(String BANK_IMG) {
        this.BANK_IMG = BANK_IMG;
    }
}
