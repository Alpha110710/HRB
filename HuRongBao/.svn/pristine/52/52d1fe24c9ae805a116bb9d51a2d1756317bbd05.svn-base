package com.hrb.biz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.model.NewsListModel;
import com.hrb.model.NewsModel;

import java.util.List;

public class MoreBiz extends BaseBiz {

    /**
     * 获取新闻消息列表
     *
     * @param firstIdx
     * @param maxCount
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static List<NewsListModel> getNoticeList(String firstIdx,
                                                    String maxCount) throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getNoticeList", false);

        ksoap2.setProperty("firstIdx", firstIdx, SoapProcessor.PropertyType.TYPE_STRING);
        ksoap2.setProperty("maxCount", maxCount, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        List<NewsListModel> fs = null;

        TypeToken<List<NewsListModel>> tt = new TypeToken<List<NewsListModel>>() {
        };
        fs = gson.fromJson(element, tt.getType());

        return fs;
    }

    /**
     * 修改登录密码
     *
     * @return
     * @throws BizFailure
     * @throws ZYException
     */
    public static NewsModel getNewsInfo(String newsId)
            throws BizFailure, ZYException {
        SoapProcessor ksoap2 = new SoapProcessor("Service", "getNewsInfo", true);

        ksoap2.setProperty("newsId", newsId, SoapProcessor.PropertyType.TYPE_STRING);

        JsonElement element = ksoap2.request();
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(element, NewsModel.class);
    }


}