package com.hrb.ui.finance;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.FinanceBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.BiddingForTransferModel;
import com.hrb.model.GetInterestTotalForTransferModel;
import com.hrb.model.MyAccountModel;
import com.hrb.model.ZQIncomeModel;
import com.hrb.ui.account.ActivityRecharge;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

/**
 * Created by Ls on 2016/10/27.
 */

public class ActivityTransferDetailBuy extends BaseActivity implements View.OnClickListener {

    private Button buy_btn;
    private TextView buy_account_amount_tv;
    private TextView buy_actual_amount_tv;
    private TextView buy_discount_amount_tv;
    private EditText buy_invest_amount_et;
    private TextView buy_profit_amount_tv;
    private TextView buy_recharge_tv;
    private String transferId, USABLE_AMOUNT;
    private TextView buy_all_tv;

    private LinearLayout layout;
    private String mEditValue = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_detail_buy);
        transferId = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.TRANSFER_ID);
        USABLE_AMOUNT = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.USER_AMOUNT);
        initView();
    }

    private void initView() {

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);

        buy_btn = (Button) findViewById(R.id.buy_btn);
        buy_account_amount_tv = (TextView) findViewById(R.id.buy_account_amount_tv);
        buy_actual_amount_tv = (TextView) findViewById(R.id.buy_actual_amount_tv);
        buy_discount_amount_tv = (TextView) findViewById(R.id.buy_discount_amount_tv);
        buy_invest_amount_et = (EditText) findViewById(R.id.buy_invest_amount_et);
        buy_profit_amount_tv = (TextView) findViewById(R.id.buy_profit_amount_tv);
        buy_recharge_tv = (TextView) findViewById(R.id.buy_recharge_tv);
        buy_all_tv = (TextView) findViewById(R.id.buy_all_tv);
        layout = (LinearLayout) findViewById(R.id.layout);

        buy_btn.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_title.setText("转让详情");

        buy_account_amount_tv.setText(USABLE_AMOUNT);//可用余额
        buy_recharge_tv.setOnClickListener(this);
        buy_all_tv.setOnClickListener(this);
        // 判断edittext得到焦点还是失去焦点状态

        buy_invest_amount_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {// 失去焦点
                    // 获取收益（接口）
                    if (!mEditValue.equals((buy_invest_amount_et.getEditableText()
                            .toString()))) {

                        mEditValue = buy_invest_amount_et.getEditableText().toString();
                        if (StringUtil.isEmpty(buy_invest_amount_et.getText().toString().trim())) {
                            buy_discount_amount_tv.setText("折让金额--元");
                            buy_actual_amount_tv.setText("实际应付--元");
                            buy_profit_amount_tv.setText("--");
                        } else {
                            getInterestTotalForTransfer(buy_invest_amount_et.getText()
                                    .toString().trim());
                        }
                    }
                }
            }
        });

        layout.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                layout.setFocusable(true);
                layout.setFocusableInTouchMode(true);
                layout.requestFocus();

                return false;
            }
        });

    }

    //获取预计收益
    private void getInterestTotalForTransfer(final String transferAmount) {

        BizDataAsyncTask<GetInterestTotalForTransferModel> task =
                new BizDataAsyncTask<GetInterestTotalForTransferModel>(getWaitingView()) {
                    @Override
                    protected GetInterestTotalForTransferModel doExecute() throws ZYException, BizFailure {
                        return FinanceBiz.getInterestTotalForTransfer(transferId, transferAmount);
                    }

                    @Override
                    protected void onExecuteSucceeded(GetInterestTotalForTransferModel getInterestTotalForTransferModel) {
                        buy_discount_amount_tv.setText("折让金额" + getInterestTotalForTransferModel.getDISCOUNT() + "元");
                        buy_actual_amount_tv.setText("实际应付" + getInterestTotalForTransferModel.getACTUAL_ACCOUNT() + "元");
                        buy_profit_amount_tv.setText(getInterestTotalForTransferModel.getINTEREST_TOTAL());
                    }

                    @Override
                    protected void OnExecuteFailed(String error) {
                        if (!StringUtil.isEmpty(error)) {
                            AlertUtil.t(ActivityTransferDetailBuy.this, error);
                        }
                    }
                };
        task.execute();

    }

    /**
     * 全投资接口
     */
    private void getAllMoney(final String transferId) {
        BizDataAsyncTask<ZQIncomeModel> getAllMoney = new BizDataAsyncTask<ZQIncomeModel>(getWaitingView()) {

            @Override
            protected void onExecuteSucceeded(ZQIncomeModel result) {
                buy_invest_amount_et.setText(result.getALL_AMOUNT());
                buy_invest_amount_et.setSelection(buy_invest_amount_et.getEditableText().length());
                mEditValue = result.getALL_AMOUNT();
                buy_discount_amount_tv.setText("折让金额" + result.getDISCOUNT() + "元");
                buy_actual_amount_tv.setText("实际应付" + result.getACTUAL_ACCOUNT() + "元");
                buy_profit_amount_tv.setText(result.getEXPECTED_INTEREST());

            }

            @Override
            protected ZQIncomeModel doExecute() throws ZYException, BizFailure {
                return FinanceBiz.allTenderForTransfer(transferId);
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityTransferDetailBuy.this, error);
                }
            }
        };

        getAllMoney.execute();
    }

    //第一次发送验证码
    private void biddingForTransfer() {

        if (StringUtil.isEmpty(buy_invest_amount_et.getEditableText().toString().trim())) {
            AlertUtil.t(ActivityTransferDetailBuy.this, "请输入折让金额");
            return;
        }

        BizDataAsyncTask<BiddingForTransferModel> biddingForTransfer = new
                BizDataAsyncTask<BiddingForTransferModel>(getWaitingView()) {
                    @Override
                    protected BiddingForTransferModel doExecute() throws ZYException, BizFailure {
                        return FinanceBiz.biddingForTransfer(transferId, buy_invest_amount_et.getEditableText().toString().trim());
                    }

                    @Override
                    protected void onExecuteSucceeded(BiddingForTransferModel s) {
                        Intent intent = new Intent(ActivityTransferDetailBuy.this, ActivityTransferVerify.class);
                        intent.putExtra("ORDER_NO", s.getORDER_NO());
                        intent.putExtra(ExtraConfig.IntentExtraKey.TRANSFER_ID, transferId);
                        intent.putExtra(ExtraConfig.IntentExtraKey.AMOUNT, buy_invest_amount_et.getEditableText().toString().trim());
                        startActivity(intent);
                    }

                    @Override
                    protected void OnExecuteFailed(String error) {
                        if (!StringUtil.isEmpty(error)) {
                            AlertUtil.t(ActivityTransferDetailBuy.this, error);
                        }
                    }
                };

        biddingForTransfer.execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.buy_btn:
                //立即购买 发送验证码
                biddingForTransfer();
                break;
            case R.id.buy_recharge_tv:
                //充值
                getAccountPageData();
                break;
            case R.id.buy_all_tv:
                //全投
                getAllMoney(transferId);
                break;
        }
    }

    private void getAccountPageData() {
        BizDataAsyncTask<MyAccountModel> getMyAccountTask = new BizDataAsyncTask<MyAccountModel>(getWaitingView()) {
            @Override
            protected MyAccountModel doExecute() throws ZYException, BizFailure {
                return AccountBiz.getMyAccountPage();
            }

            @Override
            protected void onExecuteSucceeded(MyAccountModel myAccountModel) {

                Intent itRecharge = new Intent(ActivityTransferDetailBuy.this, ActivityRecharge.class);
                itRecharge.putExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT, myAccountModel);
                startActivity(itRecharge);

            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityTransferDetailBuy.this, error);
                }
            }
        };

        getMyAccountTask.execute();
    }
}
