package com.hrb.model;

/**
 * Created by Ls on 2016/10/26.
 */

public class UsablePointListModel extends BaseModel {


    /**
     * OID_USER_ID : 2aea729fe981400792b2a32a1a1142d8
     * POINT_TYPE : 99
     * COMMODITY_ID : null
     * POINT : +1.33
     * USABLE_POINT : 8.0
     * INS_DATE : 2016-10-26 10:58:43.0
     * TITLE : null
     * POINT_DESCRIPTION : 投资回报积分
     * PUBLISH_DATE : 2016-10-26 10:58:43
     */

    private String OID_USER_ID;
    private String POINT_TYPE;
    private Object COMMODITY_ID;
    private String POINT;
    private double USABLE_POINT;
    private String INS_DATE;
    private Object TITLE;
    private String POINT_DESCRIPTION;
    private String PUBLISH_DATE;

    public String getOID_USER_ID() {
        return OID_USER_ID;
    }

    public void setOID_USER_ID(String OID_USER_ID) {
        this.OID_USER_ID = OID_USER_ID;
    }

    public String getPOINT_TYPE() {
        return POINT_TYPE;
    }

    public void setPOINT_TYPE(String POINT_TYPE) {
        this.POINT_TYPE = POINT_TYPE;
    }

    public Object getCOMMODITY_ID() {
        return COMMODITY_ID;
    }

    public void setCOMMODITY_ID(Object COMMODITY_ID) {
        this.COMMODITY_ID = COMMODITY_ID;
    }

    public String getPOINT() {
        return POINT;
    }

    public void setPOINT(String POINT) {
        this.POINT = POINT;
    }

    public double getUSABLE_POINT() {
        return USABLE_POINT;
    }

    public void setUSABLE_POINT(double USABLE_POINT) {
        this.USABLE_POINT = USABLE_POINT;
    }

    public String getINS_DATE() {
        return INS_DATE;
    }

    public void setINS_DATE(String INS_DATE) {
        this.INS_DATE = INS_DATE;
    }

    public Object getTITLE() {
        return TITLE;
    }

    public void setTITLE(Object TITLE) {
        this.TITLE = TITLE;
    }

    public String getPOINT_DESCRIPTION() {
        return POINT_DESCRIPTION;
    }

    public void setPOINT_DESCRIPTION(String POINT_DESCRIPTION) {
        this.POINT_DESCRIPTION = POINT_DESCRIPTION;
    }

    public String getPUBLISH_DATE() {
        return PUBLISH_DATE;
    }

    public void setPUBLISH_DATE(String PUBLISH_DATE) {
        this.PUBLISH_DATE = PUBLISH_DATE;
    }
}
