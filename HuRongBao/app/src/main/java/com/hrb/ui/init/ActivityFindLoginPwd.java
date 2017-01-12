package com.hrb.ui.init;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.R;
import com.hrb.biz.HomeBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.storage.PreferenceCache;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

import java.util.Timer;
import java.util.TimerTask;


public class ActivityFindLoginPwd extends BaseActivity implements
        OnClickListener {

    private TextView tv_title;
    private ImageView iv_back;

    private Timer timer;
    private TimerTask timerTask;
    private int count = 60;
    private Button find_pwd_go_next_btn;
    private EditText find_pwd_tele_num_et;
    private Button find_pwd_verify_btn;
    private EditText find_pwd_verify_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        initView();
    }

    private void initView() {

        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        find_pwd_go_next_btn = (Button) findViewById(R.id.find_pwd_go_next_btn);
        find_pwd_tele_num_et = (EditText) findViewById(R.id.find_pwd_tele_num_et);
        find_pwd_verify_btn = (Button) findViewById(R.id.find_pwd_verify_btn);
        find_pwd_verify_et = (EditText) findViewById(R.id.find_pwd_verify_et);

        iv_back.setOnClickListener(this);
        find_pwd_go_next_btn.setOnClickListener(this);
        find_pwd_verify_btn.setOnClickListener(this);
        tv_title.setText("找回密码");

    }

    private Handler mHandler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            // TODO Auto-generated method stub
            if (count >= 0) {
                find_pwd_verify_btn.setText(count + "s");
                find_pwd_verify_btn.setClickable(false);
                count--;
            } else {
                resetTimer();
            }
        }
    };

    private void resetTimer() {
        find_pwd_verify_btn.setText(R.string.find_getverifycode);
        find_pwd_verify_btn.setClickable(true);
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
    private void getVerifyCode() {

        if (StringUtil.isEmpty(find_pwd_tele_num_et.getEditableText()
                .toString())) {// 手机号码不能为空
            AlertUtil.t(this, R.string.msg_phone_number);
            find_pwd_tele_num_et.requestFocus();
            return;
        }

        BizDataAsyncTask<String> getVerificationTask = new BizDataAsyncTask<String>(getWaitingView()) {
            @Override
            protected void onExecuteSucceeded(String result) {
                find_pwd_tele_num_et.setClickable(false);
                find_pwd_tele_num_et.setFocusable(false);
                PreferenceCache.putVerificationCode(result);
                runTimerTask();

            }

            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return HomeBiz.getPhoneVerifyCode(find_pwd_tele_num_et
                        .getEditableText().toString().trim(), "1");
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityFindLoginPwd.this, error);
                }
            }
        };

        getVerificationTask.execute();
    }

    /**
     * 校验短信验证码
     */
    private void findPassWord() {
        BizDataAsyncTask<String> findPassWordTask = new BizDataAsyncTask<String>(getWaitingView()) {

            @Override
            protected void onExecuteSucceeded(String result) {
                Intent intent = new Intent(ActivityFindLoginPwd.this, ActivityResetLoginPwd.class);
                intent.putExtra("PHONE_NUM", find_pwd_tele_num_et.getEditableText().toString());
                startActivity(intent);
                finish();
            }

            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return HomeBiz.checkPhoneVerifyCode(find_pwd_tele_num_et.getEditableText().toString(), "1",
                        find_pwd_verify_et.getEditableText().toString());
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityFindLoginPwd.this, error);
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
            case R.id.find_pwd_go_next_btn:
                // 确认
                // 本地校验验证码 跳转

                if (StringUtil.isEmpty(find_pwd_tele_num_et.getEditableText().toString().trim())) {
                    AlertUtil.t(this, R.string.msg_phone_number);
                    find_pwd_tele_num_et.requestFocus();
                    return;
                }
                if (StringUtil.isEmpty(find_pwd_verify_et.getEditableText().toString().trim())) {
                    AlertUtil.t(this, R.string.msg_verify_code_empty);
                    find_pwd_verify_et.requestFocus();
                    return;
                }

                findPassWord();
                break;
            case R.id.find_pwd_verify_btn:
                // 获取验证码
                getVerifyCode();
                break;

            default:
                break;
        }
    }

}
