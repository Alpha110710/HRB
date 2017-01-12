package com.hrb.ui.init;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hrb.ExtraConfig;
import com.hrb.HuRongBaoApp;
import com.hrb.R;
import com.hrb.biz.HomeBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.RedomVerifyCodeModel;
import com.hrb.storage.PreferenceCache;
import com.hrb.ui.account.ActivityOpenAccount;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;
import com.hrb.utils.java.Util;

import org.kobjects.base64.Base64;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ls on 2016/10/12.
 */

public class ActivityRegist extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_title;
    private TextView tv_right;

    private Button regist_confirm_btn;
    private EditText regist_pwd_et;
    private EditText regist_tele_num_et;
    private EditText regist_tele_num_suggest_et;
    private Button regist_verify_btn;
    private EditText regist_verify_et;
    private TextView regist_suggest_num_et;
    private CheckBox regist_remember_cb;
    private TextView regist_agreement_tv;

    private Timer timer;
    private TimerTask timerTask;
    private int count = 60;
    private int toRegist = 0;

    private Handler mHandler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            if (count >= 0) {
                regist_verify_btn.setText(count + "s");
                regist_verify_btn.setClickable(false);
                count--;
            } else {
                resetTimer();
            }
        }
    };
    private EditText regist_pic_verify_et;//图片验证码输入
    private ImageView regist_pic_verify_iv;//图片
    private ImageView regist_verify_change_iv;//切换
    private String verifyCodeContent = "";
    private Bitmap bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        toRegist = getIntent().getIntExtra("TO_REGIST", 0);

        initView();

    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);

        regist_confirm_btn = (Button) findViewById(R.id.regist_confirm_btn);
        regist_pwd_et = (EditText) findViewById(R.id.regist_pwd_et);
        regist_tele_num_et = (EditText) findViewById(R.id.regist_tele_num_et);
        regist_tele_num_suggest_et = (EditText) findViewById(R.id.regist_tele_num_suggest_et);
        regist_verify_btn = (Button) findViewById(R.id.regist_verify_btn);
        regist_verify_et = (EditText) findViewById(R.id.regist_verify_et);
        regist_suggest_num_et = (TextView) findViewById(R.id.regist_suggest_num_et);
        regist_remember_cb = (CheckBox) findViewById(R.id.regist_remember_cb);//用户协议
        regist_agreement_tv = (TextView) findViewById(R.id.regist_agreement_tv);
        regist_pic_verify_et = (EditText) findViewById(R.id.regist_pic_verify_et);
        regist_pic_verify_iv = (ImageView) findViewById(R.id.regist_pic_verify_iv);
        regist_verify_change_iv = (ImageView) findViewById(R.id.regist_verify_change_iv);

        tv_title.setText("注册");
        tv_right.setText("登录");
        iv_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        regist_confirm_btn.setOnClickListener(this);
        regist_verify_btn.setOnClickListener(this);
        regist_agreement_tv.setOnClickListener(this);
        regist_verify_change_iv.setOnClickListener(this);

        getCaptchaImage();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regist_confirm_btn:
                //注册
                if (checkRegist()) {
                    doRegist();
                }
                break;
            case R.id.regist_verify_btn:
                //获取验证码
                if (StringUtil.isEmpty(regist_tele_num_et.getEditableText().toString().trim())) {
                    AlertUtil.t(this, "请输入手机号");
                    regist_tele_num_et.requestFocus();
                } else {
                    getRegMobileCode();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                //登录
                if (toRegist == 1) {
                    finish();
                } else {
                    Intent itToLogin = new Intent(this, ActivityLogin.class);
                    itToLogin.putExtra("TO_LOGIN", 1);
                    startActivity(itToLogin);
                }
                break;
            case R.id.regist_agreement_tv:
                //用户协议
                Intent intent = new Intent(this, ActivityWebView.class);
                intent.putExtra("LINKURL", "");
                intent.putExtra(ExtraConfig.IntentExtraKey.WEB_VIEW_FROM, 6);
                startActivity(intent);
                break;
            case R.id.regist_verify_change_iv:
                //获取验证码
                getCaptchaImage();
                break;
        }
    }

    //获取验证码
    private void getCaptchaImage() {
        BizDataAsyncTask<RedomVerifyCodeModel> getCaptchaImageTask = new BizDataAsyncTask<RedomVerifyCodeModel>(getWaitingView()) {
            @Override
            protected RedomVerifyCodeModel doExecute() throws ZYException, BizFailure {
                return HomeBiz.getCaptchaImage();
            }

            @Override
            protected void onExecuteSucceeded(RedomVerifyCodeModel redomVerifyCodeModel) {
                verifyCodeContent = redomVerifyCodeModel.getCode();
                byte[] srtbyte = Base64.decode(redomVerifyCodeModel.getByteContent());
                bit = getBitmapFromByte(srtbyte);
                regist_pic_verify_iv.setImageBitmap(bit);
                regist_pic_verify_iv.setScaleType(ImageView.ScaleType.FIT_XY);
            }

            @Override
            protected void OnExecuteFailed(String error) {

            }
        };
        getCaptchaImageTask.execute();

    }

    public Bitmap getBitmapFromByte(byte[] temp) {
        if (temp != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        } else {
            return null;
        }
    }

    BizDataAsyncTask<String> registTask;

    /**
     * 注册
     */
    private void doRegist() {
        registTask = new BizDataAsyncTask<String>(getWaitingView()) {
            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return HomeBiz.regist(regist_tele_num_et.getEditableText().toString().trim()
                        , regist_verify_et.getEditableText().toString().trim(), regist_pwd_et.getEditableText().toString(),
                        regist_tele_num_suggest_et.getEditableText().toString().trim(), "0",
                        regist_suggest_num_et.getEditableText().toString());
            }

            @Override
            protected void onExecuteSucceeded(String s) {
                PreferenceCache.putUsername(regist_tele_num_et.getEditableText()
                        .toString().trim());
                PreferenceCache.putToken(s);

                doLogin();
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    Toast.makeText(HuRongBaoApp.getAppContext(), error,
                            Toast.LENGTH_LONG).show();
                }
            }
        };
        registTask.execute();
    }

    BizDataAsyncTask<String> loginTask;

    /**
     * 登录
     */
    private void doLogin() {
        loginTask = new BizDataAsyncTask<String>(getWaitingView()) {
            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return HomeBiz.checkUserLogin(regist_tele_num_et.getEditableText().toString().trim(),
                        regist_pwd_et.getEditableText().toString().trim(), "0");
            }

            @Override
            protected void onExecuteSucceeded(String s) {
                PreferenceCache.putToken(s); // 持久化缓存token
                // PreferenceCache.putIfSkipLogin(true);
                PreferenceCache.putUsername(regist_tele_num_et.getEditableText()
                        .toString());
                //跳转到开通资金账户
                Intent intent = new Intent(ActivityRegist.this, ActivityOpenAccount.class);
                startActivity(intent);
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    Toast.makeText(HuRongBaoApp.getAppContext(), error,
                            Toast.LENGTH_LONG).show();
                }
            }
        };
        loginTask.execute();
    }

    private BizDataAsyncTask<String> getRegMobileCodeTask;

    /**
     * 获取短信验证码
     */
    private void getRegMobileCode() {
        getRegMobileCodeTask = new BizDataAsyncTask<String>(getWaitingView()) {
            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return HomeBiz.getRegMobileCode(regist_tele_num_et.getEditableText().toString().trim());
            }

            @Override
            protected void onExecuteSucceeded(String s) {
                runTimerTask();
                regist_tele_num_et.setClickable(false);
                regist_tele_num_et.setFocusable(false);
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    Toast.makeText(ActivityRegist.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        };
        getRegMobileCodeTask.execute();

    }

    private void resetTimer() {
        regist_verify_btn.setText(R.string.find_getverifycode);
        regist_verify_btn.setClickable(true);
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

    private boolean checkRegist() {

        if (StringUtil
                .isEmpty(regist_tele_num_et.getEditableText().toString().trim())) {
            AlertUtil.t(this, "请输入手机号");
            regist_tele_num_et.requestFocus();
            return false;
        }
        if (StringUtil
                .isEmpty(regist_pwd_et.getEditableText().toString().trim())) {
            AlertUtil.t(this, "请输入登录密码");
            regist_pwd_et.requestFocus();
            return false;
        }

        if (regist_pwd_et.length() < 6 || regist_pwd_et.length() > 20) { // 密码长度检查
            AlertUtil.t(this, R.string.msg_password_length_check);
            regist_pwd_et.requestFocus();
            return false;
        }
        if (!Util.checkPwd(regist_pwd_et.getEditableText().toString())) {
            AlertUtil.t(this, R.string.msg_pwd_pattern_check);
            regist_pwd_et.requestFocus();
            return false;
        }


        if (StringUtil.isEmpty(regist_verify_et.getEditableText().toString()
                .trim())) {
            AlertUtil.t(this, "请输入短信验证码");
            regist_verify_et.requestFocus();
            return false;
        }

        if (!verifyCodeContent.equals("") && !verifyCodeContent.equals(regist_pic_verify_et.getEditableText().toString().trim())) {
            AlertUtil.t(this, "请输入正确的校验码");
            regist_pic_verify_et.requestFocus();
            return false;
        }

        if (!regist_remember_cb.isChecked()) {
            AlertUtil.t(this, "请确认同意用户协议");
            return false;
        }

        return true;
    }
}
