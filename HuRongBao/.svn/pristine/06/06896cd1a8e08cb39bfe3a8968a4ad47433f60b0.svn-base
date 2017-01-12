package com.hrb.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.MyRedPacketUnusedModel;
import com.hrb.ui.account.ActivityMyRedPackage;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

/**
 * Created by Ls on 2016/10/25.
 */

public class RedPacketRulePopup extends PopupWindow implements View.OnClickListener {

    private Context context;
    private ImageView red_rule_back_iv;
    private TextView red_rule_content_tv;
    private TextView red_rule_from_tv;
    private TextView red_rule_exchange_tv;
    private MyRedPacketUnusedModel model;
    private View view;


    public RedPacketRulePopup(Context context, MyRedPacketUnusedModel model) {
        super(context);
        this.model = model;
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_red_packet_use_rule, null);
        setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setAnimationStyle(R.anim.bottom_dialog_enter);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
//        setFocusable(true);

        initView();

    }

    private void initView() {
        red_rule_back_iv = (ImageView) view.findViewById(R.id.red_rule_back_iv);
        red_rule_content_tv = (TextView) view.findViewById(R.id.red_rule_content_tv);
        red_rule_from_tv = (TextView) view.findViewById(R.id.red_rule_from_tv);
        red_rule_exchange_tv = (TextView) view.findViewById(R.id.red_rule_exchange_tv);
        red_rule_back_iv.setOnClickListener(this);
        red_rule_exchange_tv.setOnClickListener(this);
        red_rule_content_tv.setText(Html.fromHtml(model.getRULE()));
        red_rule_from_tv.setText("来源 : " + model.getRED_PACKET_TYPE_NAME());
        if (model.getCASH_FLG().equals("0")) {
            red_rule_exchange_tv.setVisibility(View.GONE);
        } else {
            red_rule_exchange_tv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.red_rule_back_iv:
                dismiss();
                break;
            case R.id.red_rule_exchange_tv:
                //兑现
                couponToCash();
                break;
        }
    }

    //兑现
    private void couponToCash() {
        BizDataAsyncTask<String> couponToCash = new BizDataAsyncTask<String>() {
            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return AccountBiz.couponToCash(model.getRED_PACKET_TEMPLET_ID(), model.getRED_PACKET_LOG_ID());
            }

            @Override
            protected void onExecuteSucceeded(String s) {
                AlertUtil.t(context, "兑现成功");
                dismiss();
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(context, error);
                }
            }
        };
        couponToCash.setWaitingView(((ActivityMyRedPackage) context).getWaitingView());
        couponToCash.execute();
    }
}
