package com.hrb.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.hrb.HuRongBaoApp;

/**
 * 缓存应用程序的配置信息和必要的业务数据
 *
 * @author lenovo
 */
public class PreferenceCache {

    public static final String PF_TOKEN = "token"; // token
    public static final String PF_GUIDE_PAGE = "guide_page";
    public static final String PF_VERSION = "";
    public static final String PF_AUTO_LOGIN = "auton_login"; // 自动登录
    public static final String PF_PHONE_NUM = "phone_number"; // 自动登录
    public static final String PF_USERNAME = "username";// 保存上次登录的用户名
    public static final String PF_SKIP_LOGIN = "skip_login"; // 跳过登录环节
    public static final String PF_MOBILE_VERIFICATION_CODE = "mobile_verification_code"; // 手机验证码
    public static final String BANK_OPEN_FLG = "bank_open_flg"; // 登陆后是否跳出dialog提示银行开通账户

    private static SharedPreferences getSharedPreferences() {
        HuRongBaoApp app = (HuRongBaoApp) HuRongBaoApp.getAppContext();
        return app.getSharedPreferences("csyh", Context.MODE_PRIVATE);
    }

    public static void putToken(String token) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putString(PF_TOKEN, token);
        editor.commit();
    }

    public static String getToken() {
        return getSharedPreferences().getString(PF_TOKEN, "");
    }

    public static void putUsername(String username) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putString(PF_USERNAME, username);
        editor.commit();
    }

    public static String getUsername() {
        return getSharedPreferences().getString(PF_USERNAME, "");
    }

    public static boolean ifSkipLogin() {
        return getSharedPreferences().getBoolean(PF_SKIP_LOGIN, false);
    }

    public static void putIfSkipLogin(boolean skipLogin) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putBoolean(PF_SKIP_LOGIN, skipLogin);
        editor.commit();
    }

    public static void putAutoLogin(boolean isAutonLogin) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putBoolean(PF_AUTO_LOGIN, isAutonLogin);
        editor.commit();
    }

    public static boolean isAutoLogin() {
        return getSharedPreferences().getBoolean(PF_AUTO_LOGIN, true);
    }

    public static void putPhoneNum(String phoneNum) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putString(PF_PHONE_NUM, phoneNum);
        editor.commit();
    }

    public static String getPhoneNum() {
        return getSharedPreferences().getString(PF_PHONE_NUM, "");
    }

    public static void putGuidePage(boolean guidePage) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putBoolean(PF_GUIDE_PAGE, guidePage);
        editor.commit();
    }

    public static boolean getGuidePage() {
        return getSharedPreferences().getBoolean(PF_GUIDE_PAGE, true);
    }

    // 版本
    public static String getVersion() {
        return getSharedPreferences().getString(PF_VERSION, "");
    }

    public static void putVersion(String version) {
        SharedPreferences pref = getSharedPreferences();
        Editor editor = pref.edit();
        editor.putString(PF_VERSION, version);
        editor.commit();
    }

    public static void putVerificationCode(String mobileVerificationCode) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putString(PF_MOBILE_VERIFICATION_CODE, mobileVerificationCode);
        editor.commit();
    }

    public static String getVerificationCode() {
        return getSharedPreferences()
                .getString(PF_MOBILE_VERIFICATION_CODE, "");
    }


    public static void putLoginFlag(boolean flg) {
        SharedPreferences pref = getSharedPreferences();

        Editor editor = pref.edit();
        editor.putBoolean(BANK_OPEN_FLG, flg);
        editor.commit();
    }

    public static boolean getLoginFlag() {
        return getSharedPreferences()
                .getBoolean(BANK_OPEN_FLG, false);
    }

}
