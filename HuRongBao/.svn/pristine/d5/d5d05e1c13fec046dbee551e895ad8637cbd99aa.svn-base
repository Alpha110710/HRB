package com.hrb.ui.init;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.HuRongBaoApp;
import com.hrb.R;
import com.hrb.biz.HomeBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.RedomVerifyCodeModel;
import com.hrb.storage.PreferenceCache;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;
import com.hrb.utils.java.Util;

import org.kobjects.base64.Base64;

/**
 * Created by Ls on 2016/10/9.
 */

public class ActivityLogin extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_title;
    private TextView tv_right;

    private boolean tokenOverDue;
    private int toLogin = 0;

    private String verifyCodeContent = "";
    private Bitmap bit;
    private Button login_confirm_btn; //登录
    private TextView login_forget_pwd_tv;//忘记密码
    private EditText login_psw_et;//输入密码
    private CheckBox login_remember_cb;//记住账号
    private EditText login_tele_num_et;//输入手机号
    private ImageView login_verify_change_iv;//获取验证码
    private EditText login_verify_et;//输入验证码
    private ImageView login_verify_iv;//验证码
    private LinearLayout login_hide_show_ll;//眼睛
    private CheckBox login_hide_show_cb;
    private LinearLayout login_verify_ll;//显示验证码行布局


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toLogin = getIntent().getIntExtra("TO_LOGIN", 0);
        tokenOverDue = getIntent().getBooleanExtra(ExtraConfig.IntentExtraKey.LOGIN_FROM_MAIN, false);

        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);

        login_confirm_btn = (Button) findViewById(R.id.login_confirm_btn);
        login_forget_pwd_tv = (TextView) findViewById(R.id.login_forget_pwd_tv);
        login_psw_et = (EditText) findViewById(R.id.login_psw_et);
        login_remember_cb = (CheckBox) findViewById(R.id.login_remember_cb);
        login_tele_num_et = (EditText) findViewById(R.id.login_tele_num_et);
        login_verify_change_iv = (ImageView) findViewById(R.id.login_verify_change_iv);
        login_verify_et = (EditText) findViewById(R.id.login_verify_et);
        login_verify_iv = (ImageView) findViewById(R.id.login_verify_iv);
        login_hide_show_ll = (LinearLayout) findViewById(R.id.login_hide_show_ll);
        login_hide_show_cb = (CheckBox) findViewById(R.id.login_hide_show_cb);
        login_verify_ll = (LinearLayout) findViewById(R.id.login_verify_ll);
        login_tele_num_et.setText(PreferenceCache.getUsername());
        login_tele_num_et.setSelection(login_tele_num_et.getEditableText().length());


        tv_title.setText("用户登录");
        tv_right.setText("注册");
        iv_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        login_confirm_btn.setOnClickListener(this);
        login_hide_show_ll.setOnClickListener(this);
        login_verify_change_iv.setOnClickListener(this);
        login_remember_cb.setOnClickListener(this);
        login_forget_pwd_tv.setOnClickListener(this);

        login_remember_cb.setChecked(PreferenceCache.isAutoLogin());//是否记住密码

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                if (tokenOverDue) {
                    HuRongBaoApp.globalIndex = 0;
                    Util.gotoMain(ActivityLogin.this);
                } else {
                    finish();
                }
                break;
            case R.id.tv_right:
                //注册
                if (toLogin == 1) {
                    finish();
                } else {
                    Intent itToRegist = new Intent(this, ActivityRegist.class);
                    itToRegist.putExtra("TO_REGIST", 1);
                    startActivity(itToRegist);
                }

                break;
            case R.id.login_confirm_btn:
                //登录
                if (checkLogin()) {
                    doLogin();
                }
                break;

            case R.id.login_hide_show_ll:
                //是否看密码
                if (login_hide_show_cb.isChecked()) {
                    login_psw_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    login_psw_et.setSelection(login_psw_et.getEditableText().toString().length());
                } else {
                    login_psw_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    login_psw_et.setSelection(login_psw_et.getEditableText().toString().length());
                }
                login_hide_show_cb.setChecked(!login_hide_show_cb.isChecked());
                break;
            case R.id.login_verify_change_iv:
                //获取验证码
                getCaptchaImage();
                break;

//            case R.id.login_remember_cb:
//                //记住账号
//                break;
            case R.id.login_forget_pwd_tv:
                //忘记密码
                Intent intent = new Intent(this, ActivityFindLoginPwd.class);
                startActivity(intent);
                break;

        }

    }

    BizDataAsyncTask<String> loginTask;

    //登录
    private void doLogin() {
        loginTask = new BizDataAsyncTask<String>(getWaitingView()) {
            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return HomeBiz.checkUserLogin(login_tele_num_et.getEditableText().toString().trim(),
                        login_psw_et.getEditableText().toString().trim(), "0");

//                return HomeBiz.checkUserLogin("15940831164",
//                        "sh111111", "0");
            }

            @Override
            protected void onExecuteSucceeded(String s) {
                PreferenceCache.putToken(s); // 持久化缓存token

				/*
                 * PreferenceCache.putIfSkipLogin(true); // 跳过登录环节
				 * PreferenceCache.putAutoLogin(ckRemberAccount.isChecked());//
				 * 记录是否自动登录
				 */
                PreferenceCache.putAutoLogin(login_remember_cb.isChecked());//
                if (login_remember_cb.isChecked()) {
                    PreferenceCache.putUsername(login_tele_num_et.getEditableText().toString());
                } else {
                    PreferenceCache.putUsername("");
                }

				/*
                 * if (PreferenceCache.isAutoLogin()) {
				 * PreferenceCache.putPhoneNum(edtPhoneNum.getEditableText()
				 * .toString()); }
				 */
                if (PreferenceCache.getLoginFlag()) {
                    //判断是不是债权详情或投资详情跳入的
                    HuRongBaoApp.globalIndex = 1;
                    PreferenceCache.putLoginFlag(false);
                } else {
                    HuRongBaoApp.globalIndex = 2;
                }
                Util.gotoMain(ActivityLogin.this);
                /*
                 * if(loginFromMain){ Util.gotoMain(ActivityLogin.this); }else {
				 * finish(); }
				 */
            }

            @Override
            protected void OnExecuteFailed(String error) {
                login_verify_ll.setVisibility(View.VISIBLE);
                getCaptchaImage();
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityLogin.this, error);
                }
            }
        };
        loginTask.execute();
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
                login_verify_iv.setImageBitmap(bit);
                login_verify_iv.setScaleType(ImageView.ScaleType.FIT_XY);
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

    private boolean checkLogin() {
        if (StringUtil.isEmpty(login_tele_num_et.getEditableText().toString().trim())) {
            AlertUtil.t(this, "请输入手机号");
            login_tele_num_et.requestFocus();
            return false;
        }
        if (StringUtil.isEmpty(login_psw_et.getEditableText().toString().trim())) {
            AlertUtil.t(this, "请输入登录密码");
            login_psw_et.requestFocus();
            return false;
        }

        if (!verifyCodeContent.equals("") && !verifyCodeContent.equals(login_verify_et.getEditableText().toString().trim())) {
            AlertUtil.t(this, "请输入正确的校验码");
            login_verify_et.requestFocus();
            return false;
        }

        return true;
    }


    @Override
    public void onBackPressed() {

        if (tokenOverDue) {
            HuRongBaoApp.globalIndex = 0;
            Util.gotoMain(ActivityLogin.this);
        } else {

            finish();
        }

    }
}
