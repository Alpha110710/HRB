package com.hrb.biz;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.hrb.HuRongBaoConfig;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.model.HomePageModel;
import com.hrb.model.RechargeModel;
import com.hrb.model.RedomVerifyCodeModel;
import com.hrb.model.VersionDescription;
import com.hrb.utils.android.HttpUtil;
import com.hrb.utils.android.LogUtil;

public class HomeBiz extends BaseBiz {

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static String checkUserLogin(String userName, String password,
                                        String terminalType) // 终端 "2" 安卓  "3"ios , 当前版本 ： 0
            throws BizFailure, ZYException {
        SoapProcessor ksoap = new SoapProcessor("Service", "checkUserLogin",
                false);

        ksoap.setProperty("userName", userName, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap.setProperty("password", password, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap.setProperty("terminalType", terminalType,
                SoapProcessor.PropertyType.TYPE_STRING);

        return ksoap.request().getAsString();

    }


    //注册
    public static String regist(String phoneNumber, String verifyCode,
                                String passWord, String introducer, String terminalType, String introducerCode
    ) throws BizFailure,
            ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "regist", false);

        ksoap2.setProperty("phoneNumber", phoneNumber, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("verifyCode", verifyCode, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("passWord", passWord, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("introducer", introducer, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("terminalType", terminalType,
                SoapProcessor.PropertyType.TYPE_STRING); // 终端 2" 安卓 "3" ios ,
        ksoap2.setProperty("introducerCode", introducerCode, SoapProcessor.PropertyType.TYPE_STRING);

        return ksoap2.request().getAsString();
    }


    //注册获取短信验证码
    public static String getRegMobileCode(String mobile) throws BizFailure, ZYException {

        SoapProcessor ksoap2 = new SoapProcessor("Service", "getRegMobileCode", false);
        ksoap2.setProperty("mobile", mobile, SoapProcessor.PropertyType.TYPE_STRING);
        return ksoap2.request().getAsString();
    }

    //获取随机验证码
    public static RedomVerifyCodeModel getCaptchaImage() throws BizFailure,
            ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getVerifyCode",
                false);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(element, RedomVerifyCodeModel.class);
    }

    //找回密码 获取手机验证码
    // operateType 0修改手机号时获取验证码，1找回密码时获取手机验证码,2追加新手机号时，校验新手机号是否存在
    public static String getPhoneVerifyCode(String mobile, String operateType) throws BizFailure,
            ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getPhoneVerifyCode",
                false);
        ksoap2.setProperty("mobile", mobile, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("operateType", operateType, SoapProcessor.PropertyType.TYPE_STRING);

        return ksoap2.request().getAsString();
    }


    //获取充值验证码 rechargeSms(String orderNo, String contracts)
    public static String rechargeSms(String orderNo, String contracts) throws BizFailure,
            ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "rechargeSms",
                false);
        ksoap2.setProperty("orderNo", orderNo, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("contracts", contracts, SoapProcessor.PropertyType.TYPE_STRING);

        Log.e("orderNo", orderNo);
        Log.e("contracts", contracts);
        return ksoap2.request().getAsString();
    }


    //  验证手机验证码
    // （获取验证码 operateType 0修改手机号时获取验证码，1找回密码时获取手机验证码,2追加新手机号
    public static String checkPhoneVerifyCode(String mobile, String operateType, String verifyCode) throws BizFailure,
            ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "checkPhoneVerifyCode",
                false);
        ksoap2.setProperty("mobile", mobile, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("operateType", operateType, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("verifyCode", verifyCode, SoapProcessor.PropertyType.TYPE_STRING);

        return ksoap2.request().getAsString();
    }

    //resetPassword 重置密码（忘记密码）
    public static String resetPassword(String userMobile, String newPassword, String newPasswordVerify) throws BizFailure,
            ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "resetPassword",
                false);
        ksoap2.setProperty("userMobile", userMobile, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("newPassword", newPassword, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("newPasswordVerify", newPasswordVerify, SoapProcessor.PropertyType.TYPE_STRING);

        return ksoap2.request().getAsString();
    }

    // 获取首页信息
    public static HomePageModel getHomePage() throws BizFailure,
            ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getHomePage",
                false);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(element, HomePageModel.class);
    }

    // 开通资金账户
    public static String realNameAuth(String realName, String cardId) throws BizFailure,
            ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "realNameAuth",
                true);

        ksoap2.setProperty("userName", realName, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("userIdentity", cardId, SoapProcessor.PropertyType.TYPE_STRING);

        return ksoap2.request().getAsString();
    }


    public static RechargeModel recharge(String cardId, String userName, String idCard, String mobile, String rechargeAmount) throws BizFailure, ZYException {
        SoapProcessor soapProcessor = new SoapProcessor("Service", "recharge", true);
        soapProcessor.setProperty("CardId", cardId, SoapProcessor.PropertyType.TYPE_STRING);
        soapProcessor.setProperty("UserName", userName, SoapProcessor.PropertyType.TYPE_STRING);
        soapProcessor.setProperty("IdCard", idCard, SoapProcessor.PropertyType.TYPE_STRING);
        soapProcessor.setProperty("mobile", mobile, SoapProcessor.PropertyType.TYPE_STRING);
        soapProcessor.setProperty("rechargeAmount", rechargeAmount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = soapProcessor.request();
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(element, RechargeModel.class);
    }

    public static String rechargeConfirm(String orderNo, String contracts,
                                         String cardId, String mobile, String Verifycode) throws BizFailure, ZYException {
        SoapProcessor soapProcessor = new SoapProcessor("Service", "rechargeConfirm", true);
        soapProcessor.setProperty("orderNo", orderNo, SoapProcessor.PropertyType.TYPE_STRING);
        soapProcessor.setProperty("contracts", contracts, SoapProcessor.PropertyType.TYPE_STRING);
        soapProcessor.setProperty("cardId", cardId, SoapProcessor.PropertyType.TYPE_STRING);
        soapProcessor.setProperty("mobile", mobile, SoapProcessor.PropertyType.TYPE_STRING);
        soapProcessor.setProperty("Verifycode", Verifycode, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = soapProcessor.request();

        return element.getAsString();
    }

    /***********************************************************************************/

//	/**
//	 * 找回登录密码
//	 *
//	 * @param mobile
//	 * @param verifyCode
//	 * @param pwdType
//	 * @return
//	 * @throws BizFailure
//	 * @throws ZYException
//	 */
//
//	public static String findPassword(String mobile, String verifyCode)
//			throws BizFailure, ZYException {
//		SoapProcessor ksoap2 = new SoapProcessor("Service", "findPassword",
//				false);
//
//		ksoap2.setProperty("mobile", mobile, PropertyType.TYPE_STRING);
//		ksoap2.setProperty("verifyCode", verifyCode, PropertyType.TYPE_STRING);
//		return ksoap2.request().getAsString();
//	}
//
//
//

    /**
     * 检测新版本
     *
     * @throws ZYException
     */
    public static VersionDescription detectNewVersion() throws ZYException {
		try {
            String result = HttpUtil
                    .postRespString(HuRongBaoConfig.VERSION_DETECTION_URL);
            LogUtil.e(result);
            Gson gson = new Gson();
            return gson.fromJson(result, VersionDescription.class);
        } catch (Exception e) {
            throw new ZYException();
        }
    }

}
