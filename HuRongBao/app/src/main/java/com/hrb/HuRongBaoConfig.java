package com.hrb;

import android.os.Environment;

public class HuRongBaoConfig {

    //http://192.168.1.111:9292/iloanWebService/services/Service?wsdl
    //http://www.0080.cn/iloanWebService/services/Service?wsdl
    //http://192.168.1.249:8080/iloanWebService/services/Service?wsdl

    //    public static final String WS_BASE_DOMAIN = "http://192.168.1.111:9292/";
//    public static final String WS_BASE_DOMAIN = "http://192.168.1.185:8080/";
    public static final String WS_BASE_DOMAIN = "http://www.0080.cn/";

    public static final String WS_BASE_URL = WS_BASE_DOMAIN + "iloanWebService/services/";
    public static final String VERSION_DETECTION_URL = WS_BASE_DOMAIN + "iloanWebService/version.json";

    // 命名空间
    public static final String WS_NAME_SPACE = "http://impl.service.iloan.yingCanTechnology.com";
    public static final String INVESTMENT_TEXT = WS_BASE_DOMAIN + "iloanWebService/borrowcontract_moban.html";

    /**
     * 本地存储的根路径
     */
    public static final String EXT_STORAGE_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     * 本地存储根目录名
     */
    public static final String CACHE_ROOT_NAME = "HuRongBao";

    /**
     * 本地存储缓存根目录名
     */
    public static final String CACHE_ROOT_CACHE_NAME = "cache";

    /**
     * apk安装包名称
     */
    public static final String APK_NAME = "HRB.apk";

    /**
     * 本地存储图片根目录名
     */
    public static final String CACHE_PIC_ROOT_NAME = "虎融宝";

    public static final String ACTION_BASE_PREFIX = "HuRongBao.action.";

    public static final int pageCount = 20;
}
