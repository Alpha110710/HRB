package com.hrb.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Created by Ls on 2016/10/21.
 */

public class ActivityResetTeleNumOne extends BaseActivity implements View.OnClickListener {

    private EditText et_reset_num_one_old;// 验证码
    private Button btn_reset_num_one_go_next;// 下一步
    private Button btn_reset_num_one_get_verify;// 获取验证码
    private TextView tv_reset_num_one_old_cant_use;// 原手机不可用
    private String teleNum;

    private Timer timer;
    private TimerTask timerTask;
    private int count = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_tele_num_one);
        teleNum = getIntent().getStringExtra("teleNum");
        initView();
    }

    private void initView() {
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);

        et_reset_num_one_old = (EditText) findViewById(R.id.et_reset_num_one_old);
        btn_reset_num_one_get_verify = (Button) findViewById(R.id.btn_reset_num_one_get_verify);
        btn_reset_num_one_go_next = (Button) findViewById(R.id.btn_reset_num_one_go_next);
        tv_reset_num_one_old_cant_use = (TextView) findViewById(R.id.tv_reset_num_one_old_cant_use);

        tv_title.setText("修改手机");
        iv_back.setOnClickListener(this);
        btn_reset_num_one_go_next.setOnClickListener(this);
        btn_reset_num_one_get_verify.setOnClickListener(this);
        tv_reset_num_one_old_cant_use.setOnClickListener(this);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            // TODO Auto-generated method stub
            if (count >= 0) {
                btn_reset_num_one_get_verify.setText(count + "s");
                btn_reset_num_one_get_verify.setClickable(false);
                count--;
            } else {
                resetTimer();
            }
        }
    };

    private void resetTimer() {
        btn_reset_num_one_get_verify.setText(R.string.find_getverifycode);
        btn_reset_num_one_get_verify.setClickable(true);
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

    /**
     * 申请验证码
     */
    private void getVertifyCodeForNewPhoneNum() {
        BizDataAsyncTask<String> getVerificationTask = new BizDataAsyncTask<String>(getWaitingView()) {

            @Override
            protected void onExecuteSucceeded(String result) {
                runTimerTask();
            }

            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return HomeBiz.getPhoneVerifyCode(teleNum, "0");
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityResetTeleNumOne.this, error);
                }
            }
        };

        getVerificationTask.execute();
    }

    /**
     * 校验短信验证码
     */
    private void findPassWord() {

        if (StringUtil.isEmpty(et_reset_num_one_old.getEditableText().toString())) {// 新手机号码不能为空
            AlertUtil.t(this, "请输入原手机验证码");
            et_reset_num_one_old.requestFocus();
            return;
        }

        BizDataAsyncTask<String> findPassWordTask = new BizDataAsyncTask<String>(getWaitingView()) {

            @Override
            protected void onExecuteSucceeded(String result) {
                Intent intent = new Intent(ActivityResetTeleNumOne.this, ActivityResetTeleNumThree.class);
                startActivity(intent);
                finish();
            }

            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return HomeBiz.checkPhoneVerifyCode(teleNum, "0",
                        et_reset_num_one_old.getEditableText().toString());
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityResetTeleNumOne.this, error);
                }
            }
        };

        findPassWordTask.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_reset_num_one_go_next:
                // 下一步
                findPassWord();
                break;
            case R.id.btn_reset_num_one_get_verify:
                // 获取手机验证码
                getVertifyCodeForNewPhoneNum();
                break;
            case R.id.tv_reset_num_one_old_cant_use:
                // 原手机不可用
                Intent intent1 = new Intent(this, ActivityResetTeleNumTwo.class);
                startActivity(intent1);
                break;
        }
    }
}
