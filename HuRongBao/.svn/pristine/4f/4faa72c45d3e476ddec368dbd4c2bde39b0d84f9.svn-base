package com.hrb.biz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.model.AlllMoneyModel;
import com.hrb.model.BiddingForTransferModel;
import com.hrb.model.GetBorrowBaseInfoModel;
import com.hrb.model.GetInterestTotalForTransferModel;
import com.hrb.model.GetInterestTotalModel;
import com.hrb.model.GetTenderInfoModel;
import com.hrb.model.GetTransferInfoModel;
import com.hrb.model.InvestListModel;
import com.hrb.model.InvestRecordModel;
import com.hrb.model.RedPacketModel;
import com.hrb.model.TenderDetailInfoModel;
import com.hrb.model.TicketModel;
import com.hrb.model.TransferListModel;
import com.hrb.model.TransferRecordModel;
import com.hrb.model.ZQIncomeModel;

import java.util.List;

public class FinanceBiz extends BaseBiz {

    /**
     * @param firstIdx
     * @param maxCount
     * @param status   状态：投标中1，还款中2，预约中3
     * @param rateFlg  利率：升序1,降序2
     * @param perFlg   期限：升序2,降序1
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    //投资列表
    public static List<InvestListModel> getInvestmentList(String firstIdx, String maxCount,
                                                          String status, String rateFlg, String perFlg)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getInvestmentList",
                false);
        // 接口需要的参数
        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("status", status, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("rateFlg", rateFlg, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("perFlg", perFlg, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        List<InvestListModel> fs = null;

        TypeToken<List<InvestListModel>> tt = new TypeToken<List<InvestListModel>>() {
        };
        fs = gson.fromJson(element, tt.getType());

        return fs;
    }

    //债权列表getTransferList
    public static List<TransferListModel> getTransferList(String firstIdx, String maxCount)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getTransferList",
                false);
        // 接口需要的参数
        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        List<TransferListModel> fs = null;

        TypeToken<List<TransferListModel>> tt = new TypeToken<List<TransferListModel>>() {
        };
        fs = gson.fromJson(element, tt.getType());

        return fs;
    }

    //getTenderDetailInfo  投资详情
    public static TenderDetailInfoModel getTenderDetailInfo(String borrowId) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getTenderDetailInfo", true);

        ksoap2.setProperty("borrowId", borrowId, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(element, TenderDetailInfoModel.class);

    }

    //投资详情-投资记录
    public static List<InvestRecordModel> getInvestors(String borrowId, String firstIdx, String maxCount)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getInvestors",
                false);
        // 接口需要的参数
        ksoap2.setProperty("borrowId", borrowId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        List<InvestRecordModel> fs = null;

        TypeToken<List<InvestRecordModel>> tt = new TypeToken<List<InvestRecordModel>>() {
        };
        fs = gson.fromJson(element, tt.getType());

        return fs;
    }


    //投资详情 立即投资
    public static GetTenderInfoModel getTenderInfo(String borrowId) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getTenderInfo", true);

        ksoap2.setProperty("borrowId", borrowId, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(element, GetTenderInfoModel.class);

    }

    //预计收益
    public static GetInterestTotalModel getInterestTotal(String productsId, String tenderAmount, String rateAmountTo)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service",
                "getInterestTotal", true);// 需要token
        // 接口需要的参数
        ksoap2.setProperty("productsId", productsId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("tenderAmount", tenderAmount, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("rateAmountTo", rateAmountTo, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(element, GetInterestTotalModel.class);
    }

    //投资选择红包
    public static List<RedPacketModel> getRedPacket(String productId)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service",
                "getCouponListForTender", true);

        // 接口需要的参数
        ksoap2.setProperty("oidPlatformProductsId", productId,
                SoapProcessor.PropertyType.TYPE_STRING);
        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        List<RedPacketModel> fs = null;
        TypeToken<List<RedPacketModel>> tt = new TypeToken<List<RedPacketModel>>() {
        };
        fs = gson.fromJson(element, tt.getType());
        return fs;
    }

    //投资选择加息券
    public static List<TicketModel> getTicket(String productId)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getInterestRateListForTender", true);

        //接口需要的参数
        ksoap2.setProperty("oidPlatformProductsId", productId,
                SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        List<TicketModel> fs = null;
        TypeToken<List<TicketModel>> tt = new TypeToken<List<TicketModel>>() {
        };
        fs = gson.fromJson(element, tt.getType());
        return fs;
    }


    //立即投资
    public static String promptlyInvestment(String borrowId, String tenderAccount,
                                            String directionalPwd, String redPackId, String rateCouponSendId,
                                            String checkCode, String orderNo, String vouchersFlg) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "bidding", true);// 需要token
        // 接口需要的参数
        ksoap2.setProperty("borrowId", borrowId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("tenderAccount", tenderAccount, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("directionalPwd", directionalPwd, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("vouchersFlg", vouchersFlg, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("redPackId", redPackId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("rateCouponSendId", rateCouponSendId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("checkCode", checkCode, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("orderNo", orderNo, SoapProcessor.PropertyType.TYPE_STRING);

        return ksoap2.request().getAsString();
    }

    //getTransferInfo  转让详情
    public static GetTransferInfoModel getTransferInfo(String transferId) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getTransferInfo", true);

        ksoap2.setProperty("transferId", transferId, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(element, GetTransferInfoModel.class);

    }

    //getInterestTotalForTransferModel 预计收益债权
    public static GetInterestTotalForTransferModel getInterestTotalForTransfer(String transferId, String transferAmount) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getInterestTotalForTransfer", true);

        ksoap2.setProperty("transferId", transferId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("transferAmount", transferAmount, SoapProcessor.PropertyType.TYPE_STRING);


        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(element, GetInterestTotalForTransferModel.class);

    }


    //转让记录
    public static List<TransferRecordModel> getTransferInvestors(String transferId, String firstIdx,
                                                                 String maxCount, String type)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getTransferInvestors", false);

        //接口需要的参数
        ksoap2.setProperty("transferId", transferId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("type", type, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        List<TransferRecordModel> fs = null;
        TypeToken<List<TransferRecordModel>> tt = new TypeToken<List<TransferRecordModel>>() {
        };
        fs = gson.fromJson(element, tt.getType());
        return fs;
    }

    //借款资料

    public static List<GetBorrowBaseInfoModel> getBorrowBaseData(String borrowId)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getBorrowBaseData", false);

        //接口需要的参数
        ksoap2.setProperty("borrowId", borrowId, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        List<GetBorrowBaseInfoModel> fs = null;
        TypeToken<List<GetBorrowBaseInfoModel>> tt = new TypeToken<List<GetBorrowBaseInfoModel>>() {
        };
        fs = gson.fromJson(element, tt.getType());
        return fs;
    }

    //项目详细
    public static List<GetBorrowBaseInfoModel> getBorrowBaseInfo(String borrowId)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getBorrowBaseInfo", false);

        //接口需要的参数
        ksoap2.setProperty("borrowId", borrowId, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();
        List<GetBorrowBaseInfoModel> fs = null;
        TypeToken<List<GetBorrowBaseInfoModel>> tt = new TypeToken<List<GetBorrowBaseInfoModel>>() {
        };
        fs = gson.fromJson(element, tt.getType());
        return fs;
    }

    //投资获取验证码
    public static BiddingForTransferModel biddingSms(String borrowId, String tenderAccount, String directionalPwd,
                                                     String vouchersFlg, String redPackId, String rateCouponSendId)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "biddingSms", true);// 需要token
        // 接口需要的参数
        ksoap2.setProperty("borrowId", borrowId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("tenderAccount", tenderAccount, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("directionalPwd", directionalPwd, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("vouchersFlg", vouchersFlg, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("redPackId", redPackId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("rateCouponSendId", rateCouponSendId, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(element, BiddingForTransferModel.class);
    }

    //再次投资获取验证码
    public static BiddingForTransferModel biddingSmsAgain(String borrowId, String orderNo) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "biddingSmsAgain", true);// 需要token
        // 接口需要的参数
        ksoap2.setProperty("borrowId", borrowId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("orderNo", orderNo, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(element, BiddingForTransferModel.class);
    }

    //债权转让获取验证码
    public static BiddingForTransferModel biddingForTransfer(String transferId, String transferAmount) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "biddingForTransfer", true);// 需要token
        // 接口需要的参数
        ksoap2.setProperty("transferId", transferId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("transferAmount", transferAmount, SoapProcessor.PropertyType.TYPE_STRING);
        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(element, BiddingForTransferModel.class);
    }

    //再次投资获取验证码
    public static BiddingForTransferModel biddingForTransferSmsAgain(String orderNo) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "biddingForTransferSmsAgain", true);// 需要token
        // 接口需要的参数
        ksoap2.setProperty("orderNo", orderNo, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(element, BiddingForTransferModel.class);
    }

    //确认转让
    public static String biddingForTransferConfirm(String orderNo, String checkCode) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "biddingForTransferConfirm", true);// 需要token
        // 接口需要的参数
        ksoap2.setProperty("orderNo", orderNo, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("checkCode", checkCode, SoapProcessor.PropertyType.TYPE_STRING);


        return ksoap2.request().getAsString();
    }

    //全投
    public static AlllMoneyModel allTender(String productsId, String rateAmountTo) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "allTender", true);// 需要token
        // 接口需要的参数
        ksoap2.setProperty("productsId", productsId, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("rateAmountTo", rateAmountTo, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(element, AlllMoneyModel.class);
    }

    //全投-债权allTenderForTransfer
    public static ZQIncomeModel allTenderForTransfer(String transferId) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "allTenderForTransfer", true);// 需要token
        // 接口需要的参数
        ksoap2.setProperty("transferId", transferId, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(element, ZQIncomeModel.class);
    }
}












