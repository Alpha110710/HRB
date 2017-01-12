package com.hrb.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.HomeBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.BankCardHadModel;
import com.hrb.model.RechargeModel;
import com.hrb.ui.base.BaseActivity;
import com.hrb.ui.init.ActivityWebView;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

/**
 * Created by Kindling on 2016/10/31 11:10.
 */

//String recharge(String token, String CardId, String UserName, String IdCard, String mobile, String rechargeAmount)
// CardId 银行卡号 UserName持卡人姓名 IdCard身份证号 mobile 预留手机号 rechargeAmount充值金额

public class ActivityRechargeFast extends BaseActivity implements View.OnClickListener {

    private String user_recharge_amount;
    private TextView recharge_can_use_tv;
    private EditText et_bank_account;
    private EditText et_bank_name;
    private EditText et_id_number;
    private EditText et_bank_phone;
    private Button btn_recharge;

    private String cardId, userName, idCard, mobile, rechargeAmount;
    private BankCardHadModel bankCardHadModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_fast);
        initView();
        bingBankInfo();

    }

    private void initView() {
        user_recharge_amount = getIntent().getStringExtra("user_recharge_amount");
        rechargeAmount = user_recharge_amount;

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        recharge_can_use_tv = (TextView) findViewById(R.id.recharge_can_use_tv);
        recharge_can_use_tv.setText((user_recharge_amount == null || user_recharge_amount.equals("")) ? (0 + "") : user_recharge_amount);
        et_bank_account = (EditText) findViewById(R.id.et_bank_account);
        et_bank_name = (EditText) findViewById(R.id.et_bank_name);
        et_id_number = (EditText) findViewById(R.id.et_id_number);
        et_bank_phone = (EditText) findViewById(R.id.et_bank_phone);
        btn_recharge = (Button) findViewById(R.id.btn_recharge);
        btn_recharge.setOnClickListener(this);
        recharge_can_use_tv = (TextView) findViewById(R.id.recharge_can_use_tv);
        iv_back.setOnClickListener(this);
        tv_title.setText("充值");
    }

    private void recharge() {

        if (ActivityRechargeFast.this.bankCardHadModel == null) {
            return;
        }

        BizDataAsyncTask<RechargeModel> recharge = new BizDataAsyncTask<RechargeModel>(getWaitingView()) {
            @Override
            protected RechargeModel doExecute() throws ZYException, BizFailure {

                if (ActivityRechargeFast.this.bankCardHadModel.getCARD_TYPE().equals("1") && ActivityRechargeFast.this.bankCardHadModel.getCARD_STATUS().equals("1")) {
                    //第二次充值
                } else {
                    //第一次充值
                    idCard = et_id_number.getEditableText().toString();
                    userName = et_bank_name.getEditableText().toString();
                    mobile = et_bank_phone.getEditableText().toString();
                    cardId = et_bank_account.getEditableText().toString();
                }

                return HomeBiz.recharge(cardId, userName, idCard, mobile, rechargeAmount);
            }

            @Override
            protected void onExecuteSucceeded(RechargeModel rechargeModel) {

                if (rechargeModel.getURL() != null && !rechargeModel.getURL().equals("")) {
                    //返回数据  有url 信息就 跳到WebView
                    Intent intent = new Intent(ActivityRechargeFast.this, ActivityWebView.class);
                    intent.putExtra(ExtraConfig.IntentExtraKey.WEB_VIEW_FROM, 4);
                    intent.putExtra("LINKURL", rechargeModel.getURL());
                    intent.putExtra("ORDER_NO", rechargeModel.getORDER_NO());
                    intent.putExtra("CONTRACTS", rechargeModel.getCONTRACTS());
                    intent.putExtra("MOBILE", rechargeModel.getMOBILE());
                    intent.putExtra("CARD_ID", cardId);
                    intent.putExtra("user_recharge_amount", user_recharge_amount);

                    startActivity(intent);
                    finish();

                } else {
                    //返回数据 没有URL 信息 就直接跳到 验证码 界面
                    Intent intent = new Intent(ActivityRechargeFast.this, ActivityRechargeVerify.class);
                    intent.putExtra(ExtraConfig.IntentExtraKey.WEB_VIEW_FROM, 4);
                    intent.putExtra("ORDER_NO", rechargeModel.getORDER_NO());
                    intent.putExtra("MOBILE", rechargeModel.getMOBILE());
                    intent.putExtra("CONTRACTS", rechargeModel.getCONTRACTS());
                    intent.putExtra("CARD_ID", cardId);
                    intent.putExtra("user_recharge_amount", user_recharge_amount);

                    startActivity(intent);
                    finish();
                }


            }

            @Override
            protected void OnExecuteFailed(String error) {

                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityRechargeFast.this, error);
                }
            }
        };
        recharge.execute();
    }

    //获取绑定银行卡身份信息
    private void bingBankInfo() {
        //Activity创建之后，先去访问网络
        //如果能得到  用户 身份信息，就在此页面 设置 用户信息  （得到信息之后 页面设置为不可以更改）
        //如果 得不到用户身份信息，那么就需要用户 自己输入信息
        BizDataAsyncTask<BankCardHadModel> bingBankInfo = new BizDataAsyncTask<BankCardHadModel>(getWaitingView()) {
            @Override
            protected BankCardHadModel doExecute() throws ZYException, BizFailure {
                return AccountBiz.bingBankInfo("0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
            }

            @Override
            protected void onExecuteSucceeded(BankCardHadModel bankCardHadModel) {
                ActivityRechargeFast.this.bankCardHadModel = bankCardHadModel;
                if (bankCardHadModel != null && bankCardHadModel.getCARD_TYPE().equals("1") && bankCardHadModel.getCARD_STATUS().equals("1")) {
                    et_bank_account.setText(bankCardHadModel.getCARD_NO_CONCEAL());
                    cardId = bankCardHadModel.getCARD_NO();
                    et_bank_account.setFocusable(false);
                    et_bank_account.setEnabled(false);
                    et_bank_name.setText(bankCardHadModel.getUSER_NAME());
                    userName = bankCardHadModel.getUSER_NAME();
                    et_bank_name.setFocusable(false);
                    et_bank_name.setEnabled(false);
                    et_id_number.setText(bankCardHadModel.getID_CARD());
                    idCard = bankCardHadModel.getID_CARD_CONCEAL();
                    et_id_number.setFocusable(false);
                    et_id_number.setEnabled(false);
                    et_bank_phone.setText(bankCardHadModel.getMOBILE());
                    mobile = bankCardHadModel.getMOBILE();
                    et_bank_phone.setFocusable(false);
                    et_bank_phone.setEnabled(false);
                } else {
                    et_bank_account.setFocusable(true);
                    et_bank_account.setEnabled(true);
                    et_bank_name.setFocusable(true);
                    et_bank_name.setEnabled(true);
                    et_id_number.setFocusable(true);
                    et_id_number.setEnabled(true);
                    et_bank_phone.setFocusable(true);
                    et_bank_phone.setEnabled(true);

                }
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityRechargeFast.this, error);
                }
                et_bank_account.setFocusable(false);
                et_bank_account.setEnabled(false);
                et_bank_name.setFocusable(false);
                et_bank_name.setEnabled(false);
                et_id_number.setFocusable(false);
                et_id_number.setEnabled(false);
                et_bank_phone.setFocusable(false);
                et_bank_phone.setEnabled(false);
            }
        };
        bingBankInfo.execute();
    }

    private boolean invoke() {
        if (StringUtil.isEmpty(et_bank_account.getText().toString())) {
            AlertUtil.t(this, "请输入银行账号");
            return false;
        }

        if (StringUtil.isEmpty(et_bank_name.getText().toString())) {
            AlertUtil.t(this, "请输入持卡人姓名");
            return false;
        }

        if (StringUtil.isEmpty(et_id_number.getText().toString())) {
            AlertUtil.t(this, "请输入身份证号码");
            return false;
        }

        if (StringUtil.isEmpty(et_bank_phone.getText().toString())) {
            AlertUtil.t(this, "请输入预留手机号");
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_recharge:
                if (invoke()) {
                    recharge();
                }
                break;
        }
    }
}
