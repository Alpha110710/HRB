package com.hrb.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.MyAddRateCouponGiveCheckModel;
import com.hrb.model.MyAddRateCouponUnusedModel;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

/**
 * Created by Ls on 2016/10/25.
 */

public class AddCouponGivePopup extends PopupWindow implements View.OnClickListener, TextWatcher {

    private Context context;

    private View view;
    private ImageView add_rule_back;
    private TextView add_rule_name_gave;
    private TextView add_rule_content_tv;
    private EditText add_rule_tele_num_et;
    private TextView add_rule_tele_num_gave;
    private LinearLayout layout;
    private Button add_rule_tele_confirm;
    private Button add_rule_give_confirm;
    private MyAddRateCouponUnusedModel model;

    private String mobile;
    private TextView add_rule_from_tv;


    public AddCouponGivePopup(Context context, MyAddRateCouponUnusedModel model) {
        super(context);
        this.model = model;
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.popup_add_give_coupon, null);
        setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
//        this.setAnimationStyle(R.anim.bottom_dialog_enter);
//        实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        setFocusable(true);


        initView();

    }

    private void initView() {

        add_rule_tele_confirm = (Button) view.findViewById(R.id.add_rule_tele_confirm);
        add_rule_give_confirm = (Button) view.findViewById(R.id.add_rule_give_confirm);
        add_rule_back = (ImageView) view.findViewById(R.id.add_rule_back);
        add_rule_content_tv = (TextView) view.findViewById(R.id.add_rule_content_tv);
        add_rule_name_gave = (TextView) view.findViewById(R.id.add_rule_name_gave);
        add_rule_tele_num_et = (EditText) view.findViewById(R.id.add_rule_tele_num_et);
        add_rule_tele_num_gave = (TextView) view.findViewById(R.id.add_rule_tele_num_gave);
        add_rule_from_tv = (TextView) view.findViewById(R.id.add_rule_from_tv);
        layout = (LinearLayout) view.findViewById(R.id.layout);

        add_rule_content_tv.setText(Html.fromHtml(model.getRULE()));
        add_rule_back.setOnClickListener(this);
        add_rule_tele_confirm.setOnClickListener(this);
        add_rule_give_confirm.setOnClickListener(this);
        add_rule_tele_num_et.addTextChangedListener(this);
        add_rule_from_tv.setText("来源 : " + model.getRATE_COUPON_SEND_TYPE_NAME());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_rule_tele_confirm:
                //确认受赠人电话
                getGivedMsg();
                break;
            case R.id.add_rule_give_confirm:
                //确认赠送
                interestRateTransfer();
                break;
            case R.id.add_rule_back:
                dismiss();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        // editText监听改变 button颜色
        if (StringUtil.isEmpty(editable.toString().trim())) {
            // 设置按钮不可点击
            add_rule_tele_confirm.setClickable(false);
            add_rule_tele_confirm.setEnabled(false);
            add_rule_tele_confirm.setBackground(context.getResources().getDrawable(R.drawable.shape_round_grey_button));
        } else {
            // 设置按钮可点击
            add_rule_tele_confirm.setClickable(true);
            add_rule_tele_confirm.setEnabled(true);
            add_rule_tele_confirm.setBackground(context.getResources().getDrawable(R.drawable.shape_round_red_button));

        }

    }

    /**
     * 验证赠送好友任务
     */
    private void getGivedMsg() {
        BizDataAsyncTask<MyAddRateCouponGiveCheckModel> checkTask = new BizDataAsyncTask<MyAddRateCouponGiveCheckModel>() {
            @Override
            protected void onExecuteSucceeded(MyAddRateCouponGiveCheckModel result) {
                add_rule_name_gave.setText("受赠人姓名 : " + result.getUSER_NAME());
                add_rule_tele_num_gave.setText("受赠人手机号码 : " + result.getMOBILE());
                layout.setVisibility(View.VISIBLE);

                mobile = result.getMOBILE();
            }

            @Override
            protected MyAddRateCouponGiveCheckModel doExecute() throws ZYException, BizFailure {
                return AccountBiz.checkDoneeInfo(add_rule_tele_num_et.getText().toString().trim());
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(context, error);
                }
                layout.setVisibility(View.GONE);
            }
        };
        checkTask.execute();
    }

    /**
     * 确认赠送任务
     */
    private void interestRateTransfer() {

        BizDataAsyncTask<String> confirmTask = new BizDataAsyncTask<String>() {

            @Override
            protected void onExecuteSucceeded(String result) {
                AlertUtil.t(context, "赠送成功");
                refreshData.refreshListener();
                dismiss();
            }

            @Override
            protected String doExecute() throws ZYException, BizFailure {

                return AccountBiz.interestRateTransfer(mobile, model.getRATE_COUPON_SEND_ID());
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(context, error);
                }
            }
        };

        confirmTask.execute();
    }

    private RefreshData refreshData;

    public void setRefreshData(RefreshData refreshData) {
        this.refreshData = refreshData;
    }

    public interface RefreshData {
        void refreshListener();
    }

}
