package com.hrb.ui.account;


import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.GetUserTransferInfoModel;
import com.hrb.model.TransferAjaxEventModel;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.SoftInputUtil;
import com.hrb.utils.java.StringUtil;

public class ActivityTransfer extends BaseActivity implements OnClickListener {

    private TextView tv_title;
    private ImageView iv_back;

    private Button btn_transfer;// 确认转让
    private TextView tv_transfer_amount;// 转让金额
    private EditText et_transfer_discount_amont;// 折让金额
    private TextView tv_transfer_title;// 标题
    private TextView tv_transfer_gong_yun_value;// 公允价值
    private TextView tv_transfer_discount_proporty;// 折让比例
    private TextView tv_transfer_counter_fee;// 转让手续费
    private TextView tv_transfer_amont_actual;// 实收转让金
    private TextView tv_transfer_explain1;// 转让说明1
    private TextView tv_transfer_explain2;// 转让说明2
    private TextView transfer_allmoney;// 全额转让
    private LinearLayout ll_transfer;// 全部保围的线性布局

    private String oidTenderId, tenderFrom;
    private String TENDER_AMOUNT = "", mDiscountsAmount = "";// 转让金额, 折让金额
    private GetUserTransferInfoModel mDebtTransferInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        oidTenderId = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.MY_TRANSFER_OID_TENDER_ID);
        tenderFrom = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.MY_TRANSFER_TENDER_FROM);
        TENDER_AMOUNT = getIntent().getStringExtra("TENDER_AMOUNT");
        initView();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        btn_transfer = (Button) findViewById(R.id.btn_transfer);
        tv_transfer_title = (TextView) findViewById(R.id.tv_transfer_title);
        tv_transfer_amount = (TextView) findViewById(R.id.tv_transfer_amount);
        tv_transfer_gong_yun_value = (TextView) findViewById(R.id.tv_transfer_gong_yun_value);
        et_transfer_discount_amont = (EditText) findViewById(R.id.et_transfer_discount_amont);
        tv_transfer_discount_proporty = (TextView) findViewById(R.id.tv_transfer_discount_proporty);
        tv_transfer_counter_fee = (TextView) findViewById(R.id.tv_transfer_counter_fee);
        tv_transfer_amont_actual = (TextView) findViewById(R.id.tv_transfer_amont_actual);
        tv_transfer_explain1 = (TextView) findViewById(R.id.tv_transfer_explain1);
        tv_transfer_explain2 = (TextView) findViewById(R.id.tv_transfer_explain2);
        transfer_allmoney = (TextView) findViewById(R.id.transfer_allmoney);

        et_transfer_discount_amont.setText("0.00");
        tv_transfer_amount.setText(TENDER_AMOUNT);

        iv_back.setOnClickListener(this);
        btn_transfer.setOnClickListener(this);
        transfer_allmoney.setOnClickListener(this);
        tv_title.setText("转让");


        et_transfer_discount_amont.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {

                } else {
                    if (mDiscountsAmount.equals(et_transfer_discount_amont.getText().toString())) {

                    } else {
                        mDiscountsAmount = et_transfer_discount_amont.getText().toString();

                        if (StringUtil.isEmpty(mDiscountsAmount)) {
                            tv_transfer_discount_proporty.setText("--");
                            tv_transfer_counter_fee.setText("--");
                            tv_transfer_amont_actual.setText("--");
                            tv_transfer_gong_yun_value.setText("--");

                        }
                        getDebtTransferData();

                    }
                    SoftInputUtil.hideSoftKeyboard(et_transfer_discount_amont);

                }

            }

        });

        ll_transfer = (LinearLayout) findViewById(R.id.ll_transfer);
        ll_transfer.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                ll_transfer.setFocusable(true);
                ll_transfer.setFocusableInTouchMode(true);
                ll_transfer.requestFocus();

                return false;
            }
        });

        getUserTransferInfo();

    }


    /**
     * 计算折让比例/手续费任务
     */
    private void getDebtTransferData() {
        BizDataAsyncTask<TransferAjaxEventModel> taskEvent = new BizDataAsyncTask<TransferAjaxEventModel>() {

            @Override
            protected void onExecuteSucceeded(TransferAjaxEventModel result) {
                tv_transfer_discount_proporty.setText(result.getDISCOUNT_RATE() + "%");
                tv_transfer_counter_fee.setText(result.getFEE() + "元");
                tv_transfer_amont_actual.setText(result.getREAL_TRANSFER_VALUE() + "元");
                tv_transfer_gong_yun_value.setText(result.getFAIR_VALUE() + "元");// 公允价值
            }

            @Override
            protected TransferAjaxEventModel doExecute() throws ZYException, BizFailure {
                if (mDiscountsAmount.equals("")) {
                    mDiscountsAmount = "0.00";
                }
                return AccountBiz.transferAjaxEvent(oidTenderId, tenderFrom, TENDER_AMOUNT, mDiscountsAmount);
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityTransfer.this, error);
                }

            }
        };
        taskEvent.execute();
    }

    /**
     * 获取转让信息
     */
    private BizDataAsyncTask<GetUserTransferInfoModel> taskInfo;

    private void getUserTransferInfo() {
        taskInfo = new BizDataAsyncTask<GetUserTransferInfoModel>() {

            @Override
            protected void onExecuteSucceeded(GetUserTransferInfoModel result) {
                tv_transfer_gong_yun_value.setText(result.getFAIR_VALUE() + "元");
                tv_transfer_title.setText(result.getPRODUCTS_TITLE());
                tv_transfer_explain1
                        .setText("1.债权转让需缴纳" + result.getFEE_RATE() + "%的手续费，即手续费=转让金额*" + result.getFEE_RATE() + "%");

                mDebtTransferInfo = result;

            }

            @Override
            protected GetUserTransferInfoModel doExecute() throws ZYException, BizFailure {
                return AccountBiz.getUserTransferInfo(oidTenderId, tenderFrom);
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityTransfer.this, error);
                }

            }
        };
        taskInfo.execute();
    }

    // 确认转让
    BizDataAsyncTask<String> confirmTransferTask;

    private void confirmTransfer() {
        confirmTransferTask = new BizDataAsyncTask<String>(getWaitingView()) {

            @Override
            protected void onExecuteSucceeded(String result) {
                AlertUtil.t(ActivityTransfer.this, "转让成功");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return AccountBiz.transferBtnClick(oidTenderId, tenderFrom, TENDER_AMOUNT, mDiscountsAmount);
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityTransfer.this, error);
                }

            }
        };
        confirmTransferTask.execute();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_transfer:
                confirmTransfer();
                break;
            case R.id.transfer_allmoney:
                if (mDebtTransferInfo != null) {

                    tv_transfer_amount.setText(mDebtTransferInfo.getENABLE_AMOUNT());
                }
                break;

            default:
                break;
        }
    }

}
