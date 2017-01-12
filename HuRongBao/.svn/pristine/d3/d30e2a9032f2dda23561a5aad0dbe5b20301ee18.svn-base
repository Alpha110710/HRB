package com.hrb.ui.finance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.HuRongBaoApp;
import com.hrb.MainActivity;
import com.hrb.R;
import com.hrb.biz.FinanceBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.BiddingForTransferModel;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ls on 2016/10/31.
 */

public class ActivityInvestVerify extends BaseActivity implements View.OnClickListener {

    private TextView tv_account_amount;
    private EditText et_verify;
    private Button btn_verify;
    private Button btn_invest_confirm;

    private Timer timer;
    private TimerTask timerTask;
    private int count = 60;
    private String productId, amount, password, redId, ticketId, DIRECTIONAL_PWD_FLG;
    private String ORDER_NO = "";

    private Handler mHandler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            if (count >= 0) {
                btn_verify.setText(count + "s");
                btn_verify.setClickable(false);
                count--;
            } else {
                resetTimer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest_verify);

        productId = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.PRODUCT_ID);
        amount = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.AMOUNT);
        password = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.PASSWORD);
        redId = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.RED_ID);
        ticketId = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.TICKET_ID);
        DIRECTIONAL_PWD_FLG = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.DIRECTIONAL_PWD_FLG);//从上上页获取的定向密码flg
        ORDER_NO = getIntent().getStringExtra("ORDER_NO");
        runTimerTask();
        initView();

    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_account_amount = (TextView) findViewById(R.id.tv_account_amount);
        et_verify = (EditText) findViewById(R.id.et_verify);
        btn_verify = (Button) findViewById(R.id.btn_verify);
        btn_invest_confirm = (Button) findViewById(R.id.btn_invest_confirm);

        tv_title.setText("投资详情");
        iv_back.setOnClickListener(this);
        btn_verify.setOnClickListener(this);
        btn_invest_confirm.setOnClickListener(this);
        tv_account_amount.setText(amount);
    }

    private void resetTimer() {
        btn_verify.setText(R.string.find_getverifycode);
        btn_verify.setClickable(true);
        count = 60;
        timerTask.cancel();
        timer.cancel();
        timerTask = null;
        timer = null;
    }

    private void runTimerTask() {
        timer = new Timer();
        timerTask = new TimerTask() {

            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    // 立即投资
    private void promptlyInvestment() {
        BizDataAsyncTask<String> promptlyInvestment = new BizDataAsyncTask<String>(getWaitingView()) {

            @Override
            protected void onExecuteSucceeded(String result) {
                // 投资成功
//                PromptlyInvestmentDialog dialog = new PromptlyInvestmentDialog(
//                        ActivityInvestVerify.this, R.style.My_Dialog,
//                        true);
//                dialog.setCancelable(false);
//                dialog.show();

                AlertUtil.t(ActivityInvestVerify.this, "投资成功!");
                Intent intent = new Intent(ActivityInvestVerify.this, MainActivity.class);
                HuRongBaoApp.globalIndex = 1;
                HuRongBaoApp.goInvest = 1;
                startActivity(intent);

            }

            @Override
            protected String doExecute() throws ZYException, BizFailure {

                return FinanceBiz.promptlyInvestment(productId,
                        amount, password, redId, ticketId, et_verify.getEditableText().toString(),
                        ORDER_NO, "");

            }

            @Override
            protected void OnExecuteFailed(String error) {
                // 投资失败
//                if ("投资失败！".equals(error)) {
//                    PromptlyInvestmentDialog dialog = new PromptlyInvestmentDialog(
//                            ActivityInvestVerify.this,
//                            R.style.My_Dialog, false);
//                    dialog.setCancelable(false);
//                    dialog.show()
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityInvestVerify.this, error);
                    finish();
                }
            }

        };

        promptlyInvestment.execute();
    }


    //再次发送验证码
    private void biddingSmsAgain() {
        BizDataAsyncTask<BiddingForTransferModel> biddingSms = new BizDataAsyncTask<BiddingForTransferModel>(getWaitingView()) {
            @Override
            protected BiddingForTransferModel doExecute() throws ZYException, BizFailure {
                return FinanceBiz.biddingSmsAgain(productId, ORDER_NO);
            }

            @Override
            protected void onExecuteSucceeded(BiddingForTransferModel s) {
                runTimerTask();
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityInvestVerify.this, error);
                }
            }
        };

        biddingSms.execute();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_verify:
                biddingSmsAgain();
                break;
            case R.id.btn_invest_confirm:
                if (StringUtil.isEmpty(et_verify.getEditableText().toString())) {
                    AlertUtil.t(this, "请输入验证码");
                    return;
                }
                promptlyInvestment();
                break;
        }
    }
}
