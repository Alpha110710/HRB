package com.hrb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hrb.utils.android.QuickSetParcelableUtil;

/**
 * Created by Ls on 2016/10/19.
 */

public class MessageModelList extends BaseModel implements Parcelable{

    /**
     * ID : 2359
     * SENDER : system
     * RECEIVER : 354b1978c9f740dd95c8e9edfc963a9d
     * SUBJECT : 发放新手体验金
     * CONTENTS : 您已签约成功，为您发放新手体验金。金额：10,000.00元。有效期：10天
     * REPLY_FLG : 0
     * OPEN_FLG : 0
     * DEL_FLG : 0
     * INS_DATE : 2016-10-19 15:55:17.0
     * UPD_DATE : 2016-10-19 15:55:17.0
     * GROUP_NAME : 平台账户
     * UD_USER_NAME : null
     * OD_USER_NAME : null
     * MAIL_STATUS : 未读
     * TITLE : 发放新手体验金
     */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        QuickSetParcelableUtil.write(dest, this);
    }

    public static final Parcelable.Creator<MessageModelList> CREATOR = new Parcelable.Creator<MessageModelList>() {

        @Override
        public MessageModelList createFromParcel(Parcel source) {
            MessageModelList obj = (MessageModelList) QuickSetParcelableUtil
                    .read(source, MessageModelList.class);
            return obj;
        }

        @Override
        public MessageModelList[] newArray(int size) {
            return new MessageModelList[size];
        }

    };
    private String ID;
    private String SENDER;
    private String RECEIVER;
    private String SUBJECT;
    private String CONTENTS;
    private String REPLY_FLG;
    private String OPEN_FLG;
    private String DEL_FLG;
    private String INS_DATE;
    private String UPD_DATE;
    private String GROUP_NAME;
    private Object UD_USER_NAME;
    private Object OD_USER_NAME;
    private String MAIL_STATUS;
    private String TITLE;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSENDER() {
        return SENDER;
    }

    public void setSENDER(String SENDER) {
        this.SENDER = SENDER;
    }

    public String getRECEIVER() {
        return RECEIVER;
    }

    public void setRECEIVER(String RECEIVER) {
        this.RECEIVER = RECEIVER;
    }

    public String getSUBJECT() {
        return SUBJECT;
    }

    public void setSUBJECT(String SUBJECT) {
        this.SUBJECT = SUBJECT;
    }

    public String getCONTENTS() {
        return CONTENTS;
    }

    public void setCONTENTS(String CONTENTS) {
        this.CONTENTS = CONTENTS;
    }

    public String getREPLY_FLG() {
        return REPLY_FLG;
    }

    public void setREPLY_FLG(String REPLY_FLG) {
        this.REPLY_FLG = REPLY_FLG;
    }

    public String getOPEN_FLG() {
        return OPEN_FLG;
    }

    public void setOPEN_FLG(String OPEN_FLG) {
        this.OPEN_FLG = OPEN_FLG;
    }

    public String getDEL_FLG() {
        return DEL_FLG;
    }

    public void setDEL_FLG(String DEL_FLG) {
        this.DEL_FLG = DEL_FLG;
    }

    public String getINS_DATE() {
        return INS_DATE;
    }

    public void setINS_DATE(String INS_DATE) {
        this.INS_DATE = INS_DATE;
    }

    public String getUPD_DATE() {
        return UPD_DATE;
    }

    public void setUPD_DATE(String UPD_DATE) {
        this.UPD_DATE = UPD_DATE;
    }

    public String getGROUP_NAME() {
        return GROUP_NAME;
    }

    public void setGROUP_NAME(String GROUP_NAME) {
        this.GROUP_NAME = GROUP_NAME;
    }

    public Object getUD_USER_NAME() {
        return UD_USER_NAME;
    }

    public void setUD_USER_NAME(Object UD_USER_NAME) {
        this.UD_USER_NAME = UD_USER_NAME;
    }

    public Object getOD_USER_NAME() {
        return OD_USER_NAME;
    }

    public void setOD_USER_NAME(Object OD_USER_NAME) {
        this.OD_USER_NAME = OD_USER_NAME;
    }

    public String getMAIL_STATUS() {
        return MAIL_STATUS;
    }

    public void setMAIL_STATUS(String MAIL_STATUS) {
        this.MAIL_STATUS = MAIL_STATUS;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

}
