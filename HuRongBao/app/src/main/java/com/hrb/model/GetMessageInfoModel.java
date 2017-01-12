package com.hrb.model;

/**
 * Created by Ls on 2016/10/19.
 */

public class GetMessageInfoModel extends BaseModel {

    /**
     * TITLE : 发放新手体验金
     * MSG_CONTENT : 您已签约成功，为您发放新手体验金。金额：10,000.00元。有效期：10天
     */


    private String TITLE;
    private String MSG_CONTENT;

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getMSG_CONTENT() {
        return MSG_CONTENT;
    }

    public void setMSG_CONTENT(String MSG_CONTENT) {
        this.MSG_CONTENT = MSG_CONTENT;
    }

}
