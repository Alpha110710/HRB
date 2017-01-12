package com.hrb.ui.account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.HuRongBaoApp;
import com.hrb.MainActivity;
import com.hrb.R;
import com.hrb.biz.HomeBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kindling on 2016/10/31 11:10.
 */


public class ActivityRechargeVerify extends BaseActivity implements View.OnClickListener {

    private String ORDER_NO;
    private String CONTRACTS;
    private String CARD_ID;
    private String MOBILE;

    private String user_recharge_amount;

    private TextView recharge_can_use_tv;
    private Button btn_confirm_recharge;

    private Timer timer;
    private TimerTask timerTask;
    private int count = 60;

    private EditText recharge_min_amount_et;

    private Button btn_get_verifycode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_verify);
        initView();
        runTimerTask();
    }

    private Handler mHandler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            if (count >= 0) {
                btn_get_verifycode.setText(count + "s");
                btn_get_verifycode.setClickable(false);
                count--;
            } else {
                resetTimer();
            }
        }
    };

    private void initView() {
        user_recharge_amount = getIntent().getStringExtra("user_recharge_amount");
//        LINKURL=getIntent().getStringExtra("LINKURL");

        ORDER_NO = getIntent().getStringExtra("ORDER_NO");
        CONTRACTS = getIntent().getStringExtra("CONTRACTS");
        MOBILE = getIntent().getStringExtra("MOBILE");
        CARD_ID = getIntent().getStringExtra("CARD_ID");

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        recharge_can_use_tv = (TextView) findViewById(R.id.recharge_can_use_tv);
        recharge_can_use_tv.setText((user_recharge_amount == null || user_recharge_amount.equals("")) ? (0 + "") : user_recharge_amount);
        btn_confirm_recharge = (Button) findViewById(R.id.btn_confirm_recharge);
        recharge_min_amount_et = (EditText) findViewById(R.id.recharge_min_amount_et);
        btn_get_verifycode = (Button) findViewById(R.id.btn_get_verifycode);

        iv_back.setOnClickListener(this);
        tv_title.setText("充值");
        btn_get_verifycode.setOnClickListener(this);
        btn_confirm_recharge.setOnClickListener(this);
    }

    /**
     * 申请验证码
     */
    private void getVerifyCode() {

        BizDataAsyncTask<String> getVerificationTask = new BizDataAsyncTask<String>(getWaitingView()) {
            @Override
            protected void onExecuteSucceeded(String result) {
                runTimerTask();
            }

            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return HomeBiz.rechargeSms(ORDER_NO
                        , CONTRACTS);
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityRechargeVerify.this, error);
                }
            }
        };

        getVerificationTask.execute();
    }

    private void resetTimer() {
        btn_get_verifycode.setText(R.string.find_getverifycode);
        btn_get_verifycode.setClickable(true);
        count = 60;
        timerTask.cancel();
        timer.cancel();
        timerTask = null;
        timer = null;
    }

    private void runTimerTask() {
        btn_get_verifycode.setClickable(false);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    private void rechargeConfirm() {

        BizDataAsyncTask<String> recharge = new BizDataAsyncTask<String>(getWaitingView()) {
            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return HomeBiz.rechargeConfirm(ORDER_NO, CONTRACTS, CARD_ID,
                        MOBILE, recharge_min_amount_et.getEditableText().toString().trim());
            }

            @Override
            protected void onExecuteSucceeded(String openAccountModel) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityRechargeVerify.this);
                builder.setTitle("充值成功, 请等待后台处理!");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ActivityRechargeVerify.this, MainActivity.class);
                        HuRongBaoApp.globalIndex = 2;
                        startActivity(intent);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityRechargeVerify.this, error);
                }
            }
        };
        recharge.execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_recharge:

                if (StringUtil.isEmpty(recharge_min_amount_et.getEditableText().toString())) {
                    AlertUtil.t(ActivityRechargeVerify.this, "请输入验证码");
                    return;
                }
                rechargeConfirm();
                break;
            case R.id.btn_get_verifycode:
                getVerifyCode();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
