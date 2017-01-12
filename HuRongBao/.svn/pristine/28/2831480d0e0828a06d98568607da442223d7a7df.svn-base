package com.hrb.biz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.model.BankCardHadModel;
import com.hrb.model.BankModel;
import com.hrb.model.BaseModel;
import com.hrb.model.DealRecordModel;
import com.hrb.model.GetMessageInfoModel;
import com.hrb.model.GetUserTransferInfoModel;
import com.hrb.model.InviteInfoModel;
import com.hrb.model.MessageModelList;
import com.hrb.model.MyAccountModel;
import com.hrb.model.MyAddRateCouponGiveCheckModel;
import com.hrb.model.MyAddRateCouponOutDateModel;
import com.hrb.model.MyAddRateCouponPresentedModel;
import com.hrb.model.MyAddRateCouponUnusedModel;
import com.hrb.model.MyAddRateCouponUsedModel;
import com.hrb.model.MyInvestBidModel;
import com.hrb.model.MyInvestHoldModel;
import com.hrb.model.MyInvestPaymentModel;
import com.hrb.model.MyRedPacketOutDateModel;
import com.hrb.model.MyRedPacketUnusedModel;
import com.hrb.model.MyRedPacketUsedModel;
import com.hrb.model.MyTransferModel;
import com.hrb.model.MyTransferedModel;
import com.hrb.model.MyTransferingModel;
import com.hrb.model.PersonalProfitLossDetailModel;
import com.hrb.model.PersonalProfitLossModel;
import com.hrb.model.ProvinceModel;
import com.hrb.model.RechargeRecordModel;
import com.hrb.model.ReturnMoneyDetailListModel;
import com.hrb.model.ReturnMoneyDetailModel;
import com.hrb.model.ReturnMoneyDetailZhaiModel;
import com.hrb.model.ReturnMoneyPlanModel;
import com.hrb.model.TransferAjaxEventModel;
import com.hrb.model.UsablePointListModel;
import com.hrb.model.UsablePointModel;
import com.hrb.model.WithdrawRecordModel;

import java.util.ArrayList;
import java.util.List;

public class AccountBiz extends BaseBiz {

    /*
     * 获取我的账户信息
     */
    public static MyAccountModel getMyAccountPage() throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getUserAccount", true);
        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(element, MyAccountModel.class);
    }

	/*
     * 获取我的投资 持有中 投标中 已回款 列表
	 */

    public static List<BaseModel> getMyInvestList(String status, String firstIdx, String maxCount)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "myInvest", true);

        ksoap2.setProperty("status", status, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        List<BaseModel> fs = null;

        if (status.equals("1")) {
            TypeToken<List<MyInvestHoldModel>> tt = new TypeToken<List<MyInvestHoldModel>>() {
            };
            fs = gson.fromJson(element, tt.getType());
        } else if (status.equals("6")) {
            TypeToken<List<MyInvestPaymentModel>> tt = new TypeToken<List<MyInvestPaymentModel>>() {
            };
            fs = gson.fromJson(element, tt.getType());
        } else if (status.equals("2")) {

            TypeToken<List<MyInvestBidModel>> tt = new TypeToken<List<MyInvestBidModel>>() {
            };
            fs = gson.fromJson(element, tt.getType());
        } else if (status.equals("3")) { // 可转让
            TypeToken<List<MyTransferModel>> tt = new TypeToken<List<MyTransferModel>>() {
            };
            fs = gson.fromJson(element, tt.getType());
        } else if (status.equals("4")) { // 转让中
            TypeToken<List<MyTransferingModel>> tt = new TypeToken<List<MyTransferingModel>>() {
            };
            fs = gson.fromJson(element, tt.getType());
        } else if (status.equals("5")) { // 已转让
            TypeToken<List<MyTransferedModel>> tt = new TypeToken<List<MyTransferedModel>>() {
            };
            fs = gson.fromJson(element, tt.getType());
        }

        List<BaseModel> bms = new ArrayList<>();
        bms.addAll(fs);
        return bms;
    }

    /**
     * 取得个人损益合计
     */
    public static PersonalProfitLossModel getUserProfitAndLosses() throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "userProfitAndLosses", true);
        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(element, PersonalProfitLossModel.class);

    }

	/*
     * 获取个人损益 列表
	 */

    public static List<PersonalProfitLossDetailModel> getProfitAndLossList(String status, String firstIdx,
                                                                           String maxCount) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "userProfitAndLossesDetail", true);

        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        List<PersonalProfitLossDetailModel> fs = null;

        TypeToken<List<PersonalProfitLossDetailModel>> tt = new TypeToken<List<PersonalProfitLossDetailModel>>() {
        };
        fs = gson.fromJson(element, tt.getType());

        return fs;
    }

    /**
     * 交易记录
     */
    public static List<DealRecordModel> getTradingRecord(String firstIdx, String maxCount)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "TransactionRecord", true);

        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);
        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        TypeToken<List<DealRecordModel>> tt = new TypeToken<List<DealRecordModel>>() {
        };
        List<DealRecordModel> fs = gson.fromJson(element, tt.getType());
        return fs;
    }

    //changeLoginPassword

    /**
     * 修改登录密码
     *
     * @param oldPassword
     * @param newPassword
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static String changeLoginPassword(String oldPassword, String newPassword)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "changePassword", true);

        ksoap2.setProperty("oldAccountPassword", oldPassword, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("newAccountPassword", newPassword, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();

        return element.getAsString();
    }


    /**
     * 获取所属银行列表
     */
    public static List<BankModel> getSiteBankList()
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getSiteBankList", false);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        TypeToken<List<BankModel>> tt = new TypeToken<List<BankModel>>() {
        };
        List<BankModel> fs = gson.fromJson(element, tt.getType());
        return fs;
    }

    /**
     * 获取省市列表
     *
     * @param parentId 省不用传id
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static List<ProvinceModel> getAreaList(String parentId)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getAreaList", false);
        ksoap2.setProperty("parentId", parentId, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        TypeToken<List<ProvinceModel>> tt = new TypeToken<List<ProvinceModel>>() {
        };
        List<ProvinceModel> fs = gson.fromJson(element, tt.getType());
        return fs;
    }

    // 绑定银行卡
    public static String bindBank(String bankId, String provinceId, String cityId,
                                  String branchName, String subbranchName, String bankCard, String mobile)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "bindBank", true);

        ksoap2.setProperty("bankId", bankId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("provinceId", provinceId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("cityId", cityId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("branchName", branchName, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("subbranchName", subbranchName, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("bankCard", bankCard, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("mobile", mobile, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();

        return element.getAsString();
    }

    //我的银行卡信息
    public static BankCardHadModel bingBankInfo(String firstIdx, String maxCount)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "bingBankInfo", true);
        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(element, BankCardHadModel.class);
    }

    // 获取消息列表
    public static List<MessageModelList> getMessageList(String firstIdx, String maxCount)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getMessageCenterList", true);

        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        TypeToken<List<MessageModelList>> tt = new TypeToken<List<MessageModelList>>() {
        };
        List<MessageModelList> fs = gson.fromJson(element, tt.getType());

        return fs;

    }

    //获取消息详细
    public static GetMessageInfoModel getMessageInfo(String messageId) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getMessageInfo", false);

        ksoap2.setProperty("messageId", messageId, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(element, GetMessageInfoModel.class);

    }

    //获取可用积分
    public static UsablePointModel getUsablePoint() throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getUsablePoint", true);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(element, UsablePointModel.class);
    }

    //获取可用积分列表
    public static List<UsablePointListModel> getPoint(String firstIdx, String maxCount)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getPoint", true);

        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        TypeToken<List<UsablePointListModel>> tt = new TypeToken<List<UsablePointListModel>>() {
        };
        List<UsablePointListModel> fs = gson.fromJson(element, tt.getType());

        return fs;

    }


    // 修改手机号码  验证身份证号
    public static String checkCardId(String cardId) throws BizFailure,
            ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "checkCardId",
                true);
        ksoap2.setProperty("cardId", cardId, SoapProcessor.PropertyType.TYPE_STRING);

        return ksoap2.request().getAsString();
    }

    /**
     * 修改手机号
     */

    public static String modifyPhoneNumber(String newPhoneNumber, String verifyCode) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "modifyPhoneNumber", true);

        ksoap2.setProperty("newPhoneNumber", newPhoneNumber, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("verifyCode", verifyCode,
                SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.request();
        return ksoap2.request().getAsString();
    }

    /**
     * 我的红包未使用
     *
     * @param firstIdx
     * @param maxCount
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static List<MyRedPacketUnusedModel> getMyRedCouponUnused(String firstIdx, String maxCount)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getUnusedCoupon", true);

        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        TypeToken<List<MyRedPacketUnusedModel>> tt = new TypeToken<List<MyRedPacketUnusedModel>>() {
        };
        List<MyRedPacketUnusedModel> fs = gson.fromJson(element, tt.getType());

        return fs;
    }

    /**
     * 我的红包兑现
     *
     * @param redPacketTempletId
     * @param redPacketLogId
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static String couponToCash(String redPacketTempletId, String redPacketLogId) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "couponToCash", true);

        ksoap2.setProperty("redPacketTempletId", redPacketTempletId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("redPacketLogId", redPacketLogId, SoapProcessor.PropertyType.TYPE_STRING);

        return ksoap2.request().getAsString();
    }


    /**
     * 我的红包兑换码兑换
     *
     * @param exchangeCode
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static String couponExchange(String exchangeCode) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "couponExchange", true);

        ksoap2.setProperty("exchangeCode", exchangeCode, SoapProcessor.PropertyType.TYPE_STRING);

        return ksoap2.request().getAsString();
    }

    /**
     * 我的加息券未使用
     *
     * @param firstIdx
     * @param maxCount
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static List<MyAddRateCouponUnusedModel> getMyAddCouponUnused(String firstIdx, String maxCount)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getUnusedInterestRate", true);

        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        TypeToken<List<MyAddRateCouponUnusedModel>> tt = new TypeToken<List<MyAddRateCouponUnusedModel>>() {
        };
        List<MyAddRateCouponUnusedModel> fs = gson.fromJson(element, tt.getType());

        return fs;
    }


    /**
     * 我的加息券兑换码兑换
     *
     * @param exchangeCode
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static String interestRateExchange(String exchangeCode) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "interestRateExchange", true);

        ksoap2.setProperty("exchangeCode", exchangeCode, SoapProcessor.PropertyType.TYPE_STRING);

        return ksoap2.request().getAsString();
    }

    /**
     * 我的加息券已赠送
     *
     * @param firstIdx
     * @param maxCount
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static List<MyAddRateCouponPresentedModel> getLargessedInterestRate(String firstIdx, String maxCount)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getLargessedInterestRate", true);

        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        TypeToken<List<MyAddRateCouponPresentedModel>> tt = new TypeToken<List<MyAddRateCouponPresentedModel>>() {
        };
        List<MyAddRateCouponPresentedModel> fs = gson.fromJson(element, tt.getType());

        return fs;
    }

    /**
     * 获取回款计划列表
     *
     * @param firstIdx
     * @param maxCount
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static List<BaseModel> getReturnMoneyPlanList(String firstIdx, String maxCount)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "recoverPlan", true);

        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        TypeToken<List<ReturnMoneyPlanModel>> tt = new TypeToken<List<ReturnMoneyPlanModel>>() {
        };
        List<ReturnMoneyPlanModel> fs = gson.fromJson(element, tt.getType());

        List<BaseModel> bms = new ArrayList<BaseModel>();
        bms.addAll(fs);
        return bms;
    }


    /**
     * 我的红包已使用
     *
     * @param firstIdx
     * @param maxCount
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static List<MyRedPacketUsedModel> getMyRedCouponUsed(String firstIdx, String maxCount)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getUsedCoupon", true);

        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        TypeToken<List<MyRedPacketUsedModel>> tt = new TypeToken<List<MyRedPacketUsedModel>>() {
        };
        List<MyRedPacketUsedModel> fs = gson.fromJson(element, tt.getType());

        return fs;
    }

    /**
     * 我的红包已过期
     *
     * @param firstIdx
     * @param maxCount
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static List<MyRedPacketOutDateModel> getMyRedCouponOutDate(String firstIdx, String maxCount)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getExpiredCoupon", true);

        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        TypeToken<List<MyRedPacketOutDateModel>> tt = new TypeToken<List<MyRedPacketOutDateModel>>() {
        };
        List<MyRedPacketOutDateModel> fs = gson.fromJson(element, tt.getType());

        return fs;
    }

    /**
     * 我的加息券 验证受赠人信息
     *
     * @param doneeMobile
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static MyAddRateCouponGiveCheckModel checkDoneeInfo(String doneeMobile) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "checkDoneeInfo", true);

        ksoap2.setProperty("doneeMobile", doneeMobile, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(element, MyAddRateCouponGiveCheckModel.class);
    }

    /**
     * 我的加息券 确认赠送
     *
     * @param doneeMobile
     * @param rateCouponSendId
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static String interestRateTransfer(String doneeMobile, String rateCouponSendId)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "interestRateTransfer", true);

        ksoap2.setProperty("doneeMobile", doneeMobile, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("rateCouponSendId", rateCouponSendId, SoapProcessor.PropertyType.TYPE_STRING);

        return ksoap2.request().getAsString();
    }

    /**
     * 我的加息券已使用
     *
     * @param firstIdx
     * @param maxCount
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static List<MyAddRateCouponUsedModel> getUsedInterestRate(String firstIdx, String maxCount)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getUsedInterestRate", true);

        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        TypeToken<List<MyAddRateCouponUsedModel>> tt = new TypeToken<List<MyAddRateCouponUsedModel>>() {
        };
        List<MyAddRateCouponUsedModel> fs = gson.fromJson(element, tt.getType());

        return fs;
    }

    /**
     * 我的加息券过期
     *
     * @param firstIdx
     * @param maxCount
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static List<MyAddRateCouponOutDateModel> getExpiredInterestRate(String firstIdx, String maxCount)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getExpiredInterestRate", true);

        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        TypeToken<List<MyAddRateCouponOutDateModel>> tt = new TypeToken<List<MyAddRateCouponOutDateModel>>() {
        };
        List<MyAddRateCouponOutDateModel> fs = gson.fromJson(element, tt.getType());

        return fs;
    }

    // 获取回款详情
    public static BaseModel getPaymentDetails(String tenderId, String tenderType)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getPaymentDetails", false);

        ksoap2.setProperty("tenderId", tenderId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("tenderType", tenderType, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        BaseModel baseModel = null;

        if (tenderType.equals("1")) {// 非债权
            baseModel = new ReturnMoneyDetailModel();
            baseModel = gson.fromJson(element, ReturnMoneyDetailModel.class);
        } else {// 债权
            baseModel = new ReturnMoneyDetailZhaiModel();
            baseModel = gson.fromJson(element, ReturnMoneyDetailZhaiModel.class);
        }

        return baseModel;
    }

    /**
     * 获取我的回款详情列表 (包括债权)
     *
     * @param firstIdx
     * @param maxCount
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static List<ReturnMoneyDetailListModel> getReturnMoneyDetailList(String tenderId, String tenderType,
                                                                            String firstIdx, String maxCount) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getPaymentDetailList", false);

        ksoap2.setProperty("tenderId", tenderId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("tenderType", tenderType, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        TypeToken<List<ReturnMoneyDetailListModel>> tt = new TypeToken<List<ReturnMoneyDetailListModel>>() {
        };
        List<ReturnMoneyDetailListModel> fs = gson.fromJson(element, tt.getType());
        return fs;
    }

    /**
     * 转让 计算接口
     *
     * @param oidTenderId
     * @param tenderFrom
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static TransferAjaxEventModel transferAjaxEvent(String oidTenderId, String tenderFrom, String transferAmount,
                                                           String discountAmount) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "transferAjaxEvent", false);
        ksoap2.setProperty("oidTenderId", oidTenderId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("tenderFrom", tenderFrom, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("transferAmount", transferAmount, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("discountAmount", discountAmount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(element, TransferAjaxEventModel.class);
    }


    /**
     * 获取转让信息
     *
     * @param oidTenderId
     * @param tenderFrom
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static GetUserTransferInfoModel getUserTransferInfo(String oidTenderId, String tenderFrom)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getUserTransferInfo", true);
        ksoap2.setProperty("oidTenderId", oidTenderId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("tenderFrom", tenderFrom, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(element, GetUserTransferInfoModel.class);
    }


    /**
     * 确认转让
     *
     * @param oidTenderId
     * @param tenderFrom
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static String transferBtnClick(String oidTenderId, String tenderFrom, String transferAmount,
                                          String discountAmount) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "transferBtnClick", true);

        ksoap2.setProperty("oidTenderId", oidTenderId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("tenderFrom", tenderFrom, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("transferAmount", transferAmount, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("discountAmount", discountAmount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();

        return element.getAsString();
    }

    /**
     * 充值记录
     *
     * @param firstIdx
     * @param maxCount
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static List<RechargeRecordModel> rechargeRecord(
            String firstIdx, String maxCount) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "rechargeRecord", true);

        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        TypeToken<List<RechargeRecordModel>> tt = new TypeToken<List<RechargeRecordModel>>() {
        };
        List<RechargeRecordModel> fs = gson.fromJson(element, tt.getType());
        return fs;
    }

    /********************************************************/

//	/*
//	 * 充值
//	 */
//
//	public static String recharge(String rechargeAmount, String mobileCode, String mobile)
//			throws BizFailure, ZYException {
//		SoapProcessor ksoap2 = new SoapProcessor("Service", "recharge", true);
//
//		ksoap2.setProperty("rechargeAmount", rechargeAmount, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("mobileCode", mobileCode, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("mobile", mobile, SoapProcessor.PropertyType.TYPE_STRING);
//
//		return ksoap2.request().getAsString();
//	}
//
//	/**
//	 * 与银行数据同步
//	 *
//	 * @return
//	 * @throws BizFailure
//	 * @throws ZYException
//	 */
//	public static GetUserAccountOfBankModel getUserAccountOfBank() throws BizFailure, ZYException {
//		SoapProcessor ksoap2 = new SoapProcessor("Service", "getUserAccountOfBank", true);
//
//		JsonElement element = ksoap2.request();
//		Gson gson = new GsonBuilder().create();
//		return gson.fromJson(element, GetUserAccountOfBankModel.class);
//	}
//
//	/**
//	 * 获取银行验证码 4充值 5提现
//	 */
//
//	public static String getVertifyCodeForBank(String mobile, String smsType) throws BizFailure, ZYException {
//		SoapProcessor ksoap2 = new SoapProcessor("Service", "getBankSmsInfo", false);
//
//		ksoap2.setProperty("mobile", mobile, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("smsType", smsType, SoapProcessor.PropertyType.TYPE_STRING);
//
//		return ksoap2.request().getAsString();
//	}
//
//	/*
//	 * 获取充值记录信息
//	 */
//
//	public static RechargeModel rechargeRecord(String firstIdx, String maxCount) throws BizFailure, ZYException {
//		SoapProcessor ksoap2 = new SoapProcessor("Service", "rechargeRecord", true);
//
//		ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);
//
//		JsonElement element = ksoap2.request();
//		Gson gson = new GsonBuilder().create();
//
//
//		return gson.fromJson(element, RechargeModel.class);
//	}
//
//	/**
//	 * 安全中心
//	 *
//	 * @return
//	 * @throws BizFailure
//	 * @throws ZYException
//	 */
//	public static SecurityCenterModel userSafeCenterDetail() throws BizFailure, ZYException {
//		SoapProcessor ksoap2 = new SoapProcessor("Service", "userSafeCenterDetail", true);
//
//		JsonElement element = ksoap2.request();
//		Gson gson = new GsonBuilder().create();
//
//		SecurityCenterModel centerModel = gson.fromJson(element, SecurityCenterModel.class);
//		return centerModel;
//	}
//

	/*
     * 提现
	 */
    public static String withdraw(String withdrawMoney)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "withDraw", true);

        ksoap2.setProperty("withdrawMoney", withdrawMoney, SoapProcessor.PropertyType.TYPE_STRING);

        return ksoap2.request().getAsString();
    }

	/*
     * 获取提现记录信息
	 */

    public static List<WithdrawRecordModel> withdrawRecord(String firstIdx, String maxCount) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "withdrawRecord", true);

        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        TypeToken<List<WithdrawRecordModel>> tt = new TypeToken<List<WithdrawRecordModel>>() {
        };
        List<WithdrawRecordModel> fs = gson.fromJson(element, tt.getType());

        return fs;
    }

    //邀请好友信息
    public static InviteInfoModel getInviteInfo() throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "inviteInfo", true);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(element, InviteInfoModel.class);

    }


//
//	// 个人基本信息(头像进入)
//	public static PersonalInformationDetailModel getPersonalInfoDetail() throws BizFailure, ZYException {
//		SoapProcessor ksoap2 = new SoapProcessor("Service", "userInformationDetail", true);
//
//		JsonElement element = ksoap2.request();
//		Gson gson = new GsonBuilder().create();
//		return gson.fromJson(element, PersonalInformationDetailModel.class);
//
//	}
//


//
//	// updateUserSafeCenterDetail
//	/**
//	 * 更新安全中心
//	 *
//	 * @param oidTenderId
//	 * @param tenderFrom
//	 * @return 1成功 0失败
//	 * @throws BizFailure
//	 * @throws ZYException
//	 */
//	public static String updateUserSafeCenterDetail(String province, String city, String address)
//			throws BizFailure, ZYException {
//		SoapProcessor ksoap2 = new SoapProcessor("Service", "updateUserSafeCenterDetail", true);
//
//		ksoap2.setProperty("province", province, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("city", city, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("address", address, SoapProcessor.PropertyType.TYPE_STRING);
//
//		JsonElement element = ksoap2.request();
//
//		return element.getAsString();
//	}
//
//	//changeLoginPassword
//	/**
//	 * 修改登录密码
//	 * @param oldPassword
//	 * @param newPassword
//	 * @return
//	 * @throws BizFailure
//	 * @throws ZYException
//	 */
//	public static String changeLoginPassword(String oldPassword, String newPassword)
//			throws BizFailure, ZYException {
//		SoapProcessor ksoap2 = new SoapProcessor("Service", "changeLoginPassword", true);
//
//		ksoap2.setProperty("oldPassword", oldPassword, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("newPassword", newPassword, SoapProcessor.PropertyType.TYPE_STRING);
//
//		JsonElement element = ksoap2.request();
//
//		return element.getAsString();
//	}
//
//	/**
//	 * 修改支付密码
//	 * @param oldPassword
//	 * @param newPassword
//	 * @return
//	 * @throws BizFailure
//	 * @throws ZYException
//	 */
//	public static String changePayPassword(String oldPassword, String newPassword)
//			throws BizFailure, ZYException {
//		SoapProcessor ksoap2 = new SoapProcessor("Service", "changePayPassword", true);
//
//		ksoap2.setProperty("oldPassword", oldPassword, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("newPassword", newPassword, SoapProcessor.PropertyType.TYPE_STRING);
//
//		JsonElement element = ksoap2.request();
//
//		return element.getAsString();
//	}
//
//	/**
//	 * 查看投资合同
//	 * @param tenderId
//	 * @return
//	 * @throws BizFailure
//	 * @throws ZYException
//	 */
//	public static InvestContractModel showProductsInfo(String tenderId)
//			throws BizFailure, ZYException {
//		SoapProcessor ksoap2 = new SoapProcessor("Service", "showProductsInfo", false);
//
//		ksoap2.setProperty("key", tenderId, SoapProcessor.PropertyType.TYPE_STRING);
//
//		JsonElement element = ksoap2.request();
//		Gson gson = new GsonBuilder().create();
//
//		return gson.fromJson(element, InvestContractModel.class);
//	}
//
//
//	/**
//	 * 重置支付密码  获取短信验证吗
//	 */
//
//	public static String getMobileCode(String mobile) throws BizFailure, ZYException {
//		SoapProcessor ksoap2 = new SoapProcessor("Service", "getMobileCode", false);
//
//		ksoap2.setProperty("mobile", mobile, SoapProcessor.PropertyType.TYPE_STRING);
//
//		return ksoap2.request().getAsString();
//	}
//
//
//
//	/**
//	 * 重置支付密码  确认提交验证码下一步
//	 * @param mobile
//	 * @return
//	 * @throws BizFailure
//	 * @throws ZYException
//	 */
//	public static String findPassword(String mobile, String verifyCode) throws BizFailure, ZYException {
//		SoapProcessor ksoap2 = new SoapProcessor("Service", "findPassword", false);
//
//		ksoap2.setProperty("mobile", mobile, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("verifyCode", verifyCode, SoapProcessor.PropertyType.TYPE_STRING);
//
//		return ksoap2.request().getAsString();
//	}
//
//	//modifyPassword
//	/**
//	 * 重置登录密码  确认
//	 * @param mobile
//	 * @return
//	 * @throws BizFailure
//	 * @throws ZYException
//	 */
//	public static String modifyPassword(String mobile, String pwdType, String newPassword) throws BizFailure, ZYException {
//		SoapProcessor ksoap2 = new SoapProcessor("Service", "modifyPassword", true);
//
//		ksoap2.setProperty("mobile", mobile, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("pwdType", pwdType, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("newPassword", newPassword, SoapProcessor.PropertyType.TYPE_STRING);
//
//		return ksoap2.request().getAsString();
//	}
//
//
//
//
//	/*********************** 以上接口是调试完毕的 ***********************************************/
//
//
//
//

//
//	/***
//	 * 用户基本信息修改
//	 * @param birthday
//	 * @param sex
//	 * @param education
//	 * @param qq
//	 * @param marriage
//	 * @param hangye
//	 * @param income
//	 * @return
//	 * @throws BizFailure
//	 * @throws ZYException
//	 */
//	public static String save( String birthday, String sex,  String education, String qq, String marriage, String hangye, String income)
//			throws BizFailure, ZYException {
//		SoapProcessor ksoap2 = new SoapProcessor("Service", "updateUserInformationDetail", true);
//
//		ksoap2.setProperty("shouru", income, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("birthday", birthday, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("xueli", education, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("hangye", hangye, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("sex", sex, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("hunying", marriage, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("qq", qq, SoapProcessor.PropertyType.TYPE_STRING);
//
//		JsonElement element = ksoap2.request();
//		return element.getAsString();
//	}
//
//
//	public static String getUserPic(String fileName,String image)
//			throws BizFailure, ZYException {
//		SoapProcessor ksoap2 = new SoapProcessor("Service", "getUserPic", true);
//
//		ksoap2.setProperty("filnaeme", fileName, SoapProcessor.PropertyType.TYPE_STRING);
//		ksoap2.setProperty("image", image, SoapProcessor.PropertyType.TYPE_STRING);
//
//		JsonElement element = ksoap2.request();
//		return element.getAsString();
//	}

}
