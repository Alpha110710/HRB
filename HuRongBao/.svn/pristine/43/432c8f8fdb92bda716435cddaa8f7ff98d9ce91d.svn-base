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
import com.hrb.biz.AccountBiz;
import com.hrb.biz.HomeBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.storage.PreferenceCache;
import com.hrb.ui.base.BaseActivity;
import com.hrb.ui.init.ActivityLogin;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ls on 2016/10/21.
 */

public class ActivityResetTeleNumThree extends BaseActivity implements View.OnClickListener {

    private EditText et_reset_num_three_new;// 新手机号码
    private EditText et_reset_num_three_verify;// 验证码
    private Button btn_reset_num_three_confirm;// 确认修改
    private Button btn_reset_num_three_verify;// 获取验证码

    private Timer timer;
    private TimerTask timerTask;
    private int count = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_tele_num_three);
        initView();
    }

    private void initView() {
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);

        btn_reset_num_three_confirm = (Button) findViewById(R.id.btn_reset_num_three_confirm);
        btn_reset_num_three_verify = (Button) findViewById(R.id.btn_reset_num_three_verify);
        et_reset_num_three_new = (EditText) findViewById(R.id.et_reset_num_three_new);
        et_reset_num_three_verify = (EditText) findViewById(R.id.et_reset_num_three_verify);

        tv_title.setText("修改手机");
        iv_back.setOnClickListener(this);
        btn_reset_num_three_verify.setOnClickListener(this);
        btn_reset_num_three_confirm.setOnClickListener(this);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            // TODO Auto-generated method stub
            if (count >= 0) {
                btn_reset_num_three_verify.setText(count + "s");
                btn_reset_num_three_verify.setClickable(false);
                count--;
            } else {
                resetTimer();
            }
        }
    };

    private void resetTimer() {
        btn_reset_num_three_verify.setText(R.string.find_getverifycode);
        btn_reset_num_three_verify.setClickable(true);
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
     * 修改手机号码
     */
    private void changePhoneNum() {

        if (StringUtil.isEmpty(et_reset_num_three_new.getEditableText().toString())) {//
            AlertUtil.t(this, R.string.msg_new_phone_num_empty);
            et_reset_num_three_new.requestFocus();
            return;
        }
        if (StringUtil.isEmpty(et_reset_num_three_verify.getEditableText().toString())) {//
            AlertUtil.t(this, R.string.msg_verify_code_empty);
            et_reset_num_three_new.requestFocus();
            return;
        }

        BizDataAsyncTask<Void> changePhoneNumTask = new BizDataAsyncTask<Void>(getWaitingView()) {

            @Override
            protected void onExecuteSucceeded(Void result) {
                AlertUtil.t(ActivityResetTeleNumThree.this, "修改手机号码成功，请重新登录");

                // PreferenceCache.putVerificationCode(result);

                PreferenceCache.putToken("");
                PreferenceCache.putIfSkipLogin(false);
                Intent it = new Intent(ActivityResetTeleNumThree.this,
                        ActivityLogin.class);
                // it.putExtra(ExtraConfig.IntentExtraKey.LOGIN_FROM_MAIN,
                // true);
                startActivity(it);
                // // Util.showLogin(ActivityLoginPwdChange.this);
                ActivityResetTeleNumThree.this.finish();
            }

            @Override
            protected Void doExecute() throws ZYException, BizFailure {
                AccountBiz.modifyPhoneNumber(et_reset_num_three_new.getEditableText().toString(),
                        et_reset_num_three_verify.getEditableText().toString());
                return null;
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityResetTeleNumThree.this, error);
                }
            }
        };
        changePhoneNumTask.execute();
    }


    /**
     * 申请验证码
     */
    private void getVerifyCodeForNewPhoneNum() {

        if (StringUtil.isEmpty(et_reset_num_three_new.getEditableText().toString())) {// 新手机号码不能为空
            AlertUtil.t(this, "请输入新手机号码");
            et_reset_num_three_new.requestFocus();
            return;
        }

        // SoftInputUtil.hideSoftKeyboard(ket_phoneNum.getEditText());
        BizDataAsyncTask<String> getVerificationTask = new BizDataAsyncTask<String>(getWaitingView()) {

            @Override
            protected void onExecuteSucceeded(String result) {
                runTimerTask();
                et_reset_num_three_new.setClickable(false);
                et_reset_num_three_new.setFocusable(false);
            }

            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return HomeBiz.getPhoneVerifyCode(et_reset_num_three_new.getEditableText().toString(), "2");
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityResetTeleNumThree.this, error);
                }
            }
        };

        getVerificationTask.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_reset_num_three_verify:
                // 获取验证码
                getVerifyCodeForNewPhoneNum();
                break;
            case R.id.btn_reset_num_three_confirm:
                // 确认修改
                changePhoneNum();
                break;
        }
    }
}
