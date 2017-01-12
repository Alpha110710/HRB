package com.hrb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hrb.utils.android.QuickSetParcelableUtil;

/**
 * Created by Ls on 2016/10/13.
 */

public class MyAccountModel extends BaseModel implements Parcelable {


    /**
     * AUTO_FINANCE_AMOUNT : ￥0.00
     * BALANCE : ￥0.00
     * FROZE_AMOUNT : ￥5,500.00
     * AWAIT : 18,690.69
     * INTEREST_YES : 92.45
     * USABLE_AMOUNT : 85,436.18
     * EXPERIENCE_CASH : 0.00
     * PHONE_NUMBER : 15940831164
     * PHONE_NUMBER_CONCEAL : 159****1164
     * PWD_SAME_FLG : 2
     * USER_NAME : 孙浩
     * USER_NAME_CONCEAL : 孙**
     * ID_CARD : 210782198609155212
     * ID_CARD_CONCEAL : 2******2
     * ID_CARD_VERIFY_FLG : 1
     * EMAIL_VERIFY_FLG : 2
     * BANK_REAL_NAME :
     * BRANCH :
     * CARD_NO :
     * BANK_FLG : 0
     * BANK_ID :
     * PROVINCE_ID :
     * PROVINCE_NAME :
     * CITY_ID :
     * CITY_NAME :
     * BANK_IMG :
     * AUTO_FLG : 0
     * HFTX_ID :
     * CONTRACTS : RB160920KFQNKKL0
     */


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        QuickSetParcelableUtil.write(dest, this);
    }

    public static final Creator<MyAccountModel> CREATOR = new Creator<MyAccountModel>() {

        @Override
        public MyAccountModel createFromParcel(Parcel source) {
            MyAccountModel obj = (MyAccountModel) QuickSetParcelableUtil
                    .read(source, MyAccountModel.class);
            return obj;
        }

        @Override
        public MyAccountModel[] newArray(int size) {
            return new MyAccountModel[size];
        }

    };

    private String AUTO_FINANCE_AMOUNT;
    private String BALANCE;
    private String FROZE_AMOUNT;
    private String AWAIT;
    private String INTEREST_YES;
    private String USABLE_AMOUNT;
    private String EXPERIENCE_CASH;
    private String PHONE_NUMBER;
    private String PHONE_NUMBER_CONCEAL;
    private String PWD_SAME_FLG;
    private String USER_NAME;
    private String USER_NAME_CONCEAL;
    private String ID_CARD;
    private String ID_CARD_CONCEAL;
    private String ID_CARD_VERIFY_FLG;
    private String EMAIL_VERIFY_FLG;
    private String BANK_REAL_NAME;
    private String BRANCH;
    private String CARD_NO;
    private String BANK_FLG;
    private String BANK_ID;
    private String PROVINCE_ID;
    private String PROVINCE_NAME;
    private String CITY_ID;
    private String CITY_NAME;
    private String BANK_IMG;
    private String AUTO_FLG;
    private String HFTX_ID;
    private String CONTRACTS;
    private String MIN_ACCOUNT_ONE;
    private String EXPERIENCE_ENDFLG;
    private String EXP_OPEN;


    public String getEXP_OPEN() {
        return EXP_OPEN;
    }

    public void setEXP_OPEN(String EXP_OPEN) {
        this.EXP_OPEN = EXP_OPEN;
    }

    public String getEXPERIENCE_ENDFLG() {
        return EXPERIENCE_ENDFLG;
    }

    public void setEXPERIENCE_ENDFLG(String EXPERIENCE_ENDFLG) {
        this.EXPERIENCE_ENDFLG = EXPERIENCE_ENDFLG;
    }

    public String getMIN_ACCOUNT_ONE() {
        return MIN_ACCOUNT_ONE;
    }

    public void setMIN_ACCOUNT_ONE(String MIN_ACCOUNT_ONE) {
        this.MIN_ACCOUNT_ONE = MIN_ACCOUNT_ONE;
    }

    public static Creator<MyAccountModel> getCREATOR() {
        return CREATOR;
    }

    public String getAUTO_FINANCE_AMOUNT() {
        return AUTO_FINANCE_AMOUNT;
    }

    public void setAUTO_FINANCE_AMOUNT(String AUTO_FINANCE_AMOUNT) {
        this.AUTO_FINANCE_AMOUNT = AUTO_FINANCE_AMOUNT;
    }

    public String getBALANCE() {
        return BALANCE;
    }

    public void setBALANCE(String BALANCE) {
        this.BALANCE = BALANCE;
    }

    public String getFROZE_AMOUNT() {
        return FROZE_AMOUNT;
    }

    public void setFROZE_AMOUNT(String FROZE_AMOUNT) {
        this.FROZE_AMOUNT = FROZE_AMOUNT;
    }

    public String getAWAIT() {
        return AWAIT;
    }

    public void setAWAIT(String AWAIT) {
        this.AWAIT = AWAIT;
    }

    public String getINTEREST_YES() {
        return INTEREST_YES;
    }

    public void setINTEREST_YES(String INTEREST_YES) {
        this.INTEREST_YES = INTEREST_YES;
    }

    public String getUSABLE_AMOUNT() {
        return USABLE_AMOUNT;
    }

    public void setUSABLE_AMOUNT(String USABLE_AMOUNT) {
        this.USABLE_AMOUNT = USABLE_AMOUNT;
    }

    public String getEXPERIENCE_CASH() {
        return EXPERIENCE_CASH;
    }

    public void setEXPERIENCE_CASH(String EXPERIENCE_CASH) {
        this.EXPERIENCE_CASH = EXPERIENCE_CASH;
    }

    public String getPHONE_NUMBER() {
        return PHONE_NUMBER;
    }

    public void setPHONE_NUMBER(String PHONE_NUMBER) {
        this.PHONE_NUMBER = PHONE_NUMBER;
    }

    public String getPHONE_NUMBER_CONCEAL() {
        return PHONE_NUMBER_CONCEAL;
    }

    public void setPHONE_NUMBER_CONCEAL(String PHONE_NUMBER_CONCEAL) {
        this.PHONE_NUMBER_CONCEAL = PHONE_NUMBER_CONCEAL;
    }

    public String getPWD_SAME_FLG() {
        return PWD_SAME_FLG;
    }

    public void setPWD_SAME_FLG(String PWD_SAME_FLG) {
        this.PWD_SAME_FLG = PWD_SAME_FLG;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getUSER_NAME_CONCEAL() {
        return USER_NAME_CONCEAL;
    }

    public void setUSER_NAME_CONCEAL(String USER_NAME_CONCEAL) {
        this.USER_NAME_CONCEAL = USER_NAME_CONCEAL;
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

    public String getID_CARD_VERIFY_FLG() {
        return ID_CARD_VERIFY_FLG;
    }

    public void setID_CARD_VERIFY_FLG(String ID_CARD_VERIFY_FLG) {
        this.ID_CARD_VERIFY_FLG = ID_CARD_VERIFY_FLG;
    }

    public String getEMAIL_VERIFY_FLG() {
        return EMAIL_VERIFY_FLG;
    }

    public void setEMAIL_VERIFY_FLG(String EMAIL_VERIFY_FLG) {
        this.EMAIL_VERIFY_FLG = EMAIL_VERIFY_FLG;
    }

    public String getBANK_REAL_NAME() {
        return BANK_REAL_NAME;
    }

    public void setBANK_REAL_NAME(String BANK_REAL_NAME) {
        this.BANK_REAL_NAME = BANK_REAL_NAME;
    }

    public String getBRANCH() {
        return BRANCH;
    }

    public void setBRANCH(String BRANCH) {
        this.BRANCH = BRANCH;
    }

    public String getCARD_NO() {
        return CARD_NO;
    }

    public void setCARD_NO(String CARD_NO) {
        this.CARD_NO = CARD_NO;
    }

    public String getBANK_FLG() {
        return BANK_FLG;
    }

    public void setBANK_FLG(String BANK_FLG) {
        this.BANK_FLG = BANK_FLG;
    }

    public String getBANK_ID() {
        return BANK_ID;
    }

    public void setBANK_ID(String BANK_ID) {
        this.BANK_ID = BANK_ID;
    }

    public String getPROVINCE_ID() {
        return PROVINCE_ID;
    }

    public void setPROVINCE_ID(String PROVINCE_ID) {
        this.PROVINCE_ID = PROVINCE_ID;
    }

    public String getPROVINCE_NAME() {
        return PROVINCE_NAME;
    }

    public void setPROVINCE_NAME(String PROVINCE_NAME) {
        this.PROVINCE_NAME = PROVINCE_NAME;
    }

    public String getCITY_ID() {
        return CITY_ID;
    }

    public void setCITY_ID(String CITY_ID) {
        this.CITY_ID = CITY_ID;
    }

    public String getCITY_NAME() {
        return CITY_NAME;
    }

    public void setCITY_NAME(String CITY_NAME) {
        this.CITY_NAME = CITY_NAME;
    }

    public String getBANK_IMG() {
        return BANK_IMG;
    }

    public void setBANK_IMG(String BANK_IMG) {
        this.BANK_IMG = BANK_IMG;
    }

    public String getAUTO_FLG() {
        return AUTO_FLG;
    }

    public void setAUTO_FLG(String AUTO_FLG) {
        this.AUTO_FLG = AUTO_FLG;
    }

    public String getHFTX_ID() {
        return HFTX_ID;
    }

    public void setHFTX_ID(String HFTX_ID) {
        this.HFTX_ID = HFTX_ID;
    }

    public String getCONTRACTS() {
        return CONTRACTS;
    }

    public void setCONTRACTS(String CONTRACTS) {
        this.CONTRACTS = CONTRACTS;
    }

}
