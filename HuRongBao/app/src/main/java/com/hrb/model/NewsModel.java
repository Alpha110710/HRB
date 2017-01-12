package com.hrb.model;

/**
 * Created by Ls on 2016/10/26.
 */

public class NewsModel extends BaseModel {

    /**
     * AUTHOR : admin
     * SUMMARY : 是打发达到阿斯顿发送到发送到发送到<br />
     * LINK_URL : null
     * NEWS_TYPE_ID : 2000
     * ID : 1
     * SHOW_TYPE_ID : 1
     * TITLE_SUMMARY : test
     * IMG_PATH : \0d465697a3d0416b8777e800b3b5a47b\202cb962ac59075b964b07152d234b70.png
     * STATUS : 3
     * PUBLISH_DATE : 2016-10-21
     * TITLE : 手机新闻公告测试
     */

    private String AUTHOR;
    private String SUMMARY;
    private Object LINK_URL;
    private String NEWS_TYPE_ID;
    private int ID;
    private String SHOW_TYPE_ID;
    private String TITLE_SUMMARY;
    private String IMG_PATH;
    private String STATUS;
    private String PUBLISH_DATE;
    private String TITLE;

    public String getAUTHOR() {
        return AUTHOR;
    }

    public void setAUTHOR(String AUTHOR) {
        this.AUTHOR = AUTHOR;
    }

    public String getSUMMARY() {
        return SUMMARY;
    }

    public void setSUMMARY(String SUMMARY) {
        this.SUMMARY = SUMMARY;
    }

    public Object getLINK_URL() {
        return LINK_URL;
    }

    public void setLINK_URL(Object LINK_URL) {
        this.LINK_URL = LINK_URL;
    }

    public String getNEWS_TYPE_ID() {
        return NEWS_TYPE_ID;
    }

    public void setNEWS_TYPE_ID(String NEWS_TYPE_ID) {
        this.NEWS_TYPE_ID = NEWS_TYPE_ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSHOW_TYPE_ID() {
        return SHOW_TYPE_ID;
    }

    public void setSHOW_TYPE_ID(String SHOW_TYPE_ID) {
        this.SHOW_TYPE_ID = SHOW_TYPE_ID;
    }

    public String getTITLE_SUMMARY() {
        return TITLE_SUMMARY;
    }

    public void setTITLE_SUMMARY(String TITLE_SUMMARY) {
        this.TITLE_SUMMARY = TITLE_SUMMARY;
    }

    public String getIMG_PATH() {
        return IMG_PATH;
    }

    public void setIMG_PATH(String IMG_PATH) {
        this.IMG_PATH = IMG_PATH;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getPUBLISH_DATE() {
        return PUBLISH_DATE;
    }

    public void setPUBLISH_DATE(String PUBLISH_DATE) {
        this.PUBLISH_DATE = PUBLISH_DATE;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }
}
