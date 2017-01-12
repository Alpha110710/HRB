package com.hrb.ui.init;


import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hrb.HuRongBaoApp;
import com.hrb.R;
import com.hrb.biz.HomeBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.storage.PreferenceCache;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;
import com.hrb.utils.java.Util;

public class ActivityResetLoginPwd extends BaseActivity implements OnClickListener {

    private TextView tv_title;
    private ImageView iv_back;

    String phoneNum = "";

    private Button reset_pwd_confirm_btn;
    private CheckBox reset_pwd_new_eye_cb;
    private LinearLayout reset_pwd_new_eye_ll;
    private CheckBox reset_pwd_new_eye_two_cb;
    private LinearLayout reset_pwd_new_eye_two_ll;
    private EditText reset_pwd_new_pwd_et;
    private EditText reset_pwd_new_pwd_two_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        phoneNum = getIntent().getStringExtra("PHONE_NUM");
        initView();
    }

    private void initView() {

        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        reset_pwd_confirm_btn = (Button) findViewById(R.id.reset_pwd_confirm_btn);
        reset_pwd_new_eye_cb = (CheckBox) findViewById(R.id.reset_pwd_new_eye_cb);
        reset_pwd_new_eye_ll = (LinearLayout) findViewById(R.id.reset_pwd_new_eye_ll);
        reset_pwd_new_eye_two_cb = (CheckBox) findViewById(R.id.reset_pwd_new_eye_two_cb);
        reset_pwd_new_eye_two_ll = (LinearLayout) findViewById(R.id.reset_pwd_new_eye_two_ll);
        reset_pwd_new_pwd_et = (EditText) findViewById(R.id.reset_pwd_new_pwd_et);
        reset_pwd_new_pwd_two_et = (EditText) findViewById(R.id.reset_pwd_new_pwd_two_et);


        iv_back.setOnClickListener(this);
        reset_pwd_confirm_btn.setOnClickListener(this);
        reset_pwd_new_eye_ll.setOnClickListener(this);
        reset_pwd_new_eye_two_ll.setOnClickListener(this);
        tv_title.setText("密码重置");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.reset_pwd_new_eye_ll:
                //是否看密码
                if (reset_pwd_new_eye_cb.isChecked()) {
                    reset_pwd_new_pwd_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    reset_pwd_new_pwd_et.setSelection(reset_pwd_new_pwd_et.getEditableText().toString().length());
                } else {
                    reset_pwd_new_pwd_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    reset_pwd_new_pwd_et.setSelection(reset_pwd_new_pwd_et.getEditableText().toString().length());
                }
                reset_pwd_new_eye_cb.setChecked(!reset_pwd_new_eye_cb.isChecked());
                break;

            case R.id.reset_pwd_new_eye_two_ll:
                if (reset_pwd_new_eye_two_cb.isChecked()) {
                    reset_pwd_new_pwd_two_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    reset_pwd_new_pwd_two_et.setSelection(reset_pwd_new_pwd_et.getEditableText().toString().length());
                } else {
                    reset_pwd_new_pwd_two_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    reset_pwd_new_pwd_two_et.setSelection(reset_pwd_new_pwd_two_et.getEditableText().toString().length());
                }
                reset_pwd_new_eye_two_cb.setChecked(!reset_pwd_new_eye_two_cb.isChecked());
                break;

            case R.id.reset_pwd_confirm_btn:
                // 确认修改
                resetLoginPwd();

                break;

            default:
                break;
        }
    }

    private BizDataAsyncTask<Void> task;

    private void resetLoginPwd() {

        if (StringUtil.isEmpty(reset_pwd_new_pwd_et.getEditableText().toString())) {//
            AlertUtil.t(this, R.string.msg_new_login_pwd_empty);
            reset_pwd_new_pwd_et.requestFocus();
            return;
        }
        if (reset_pwd_new_pwd_et.length() < 6 || reset_pwd_new_pwd_et.length() > 20) { // 密码长度检查
            AlertUtil.t(this, R.string.msg_password_length_check);
            reset_pwd_new_pwd_et.requestFocus();
            return;
        }

        if (StringUtil.isEmpty(reset_pwd_new_pwd_two_et.getEditableText().toString())) {//
            AlertUtil.t(this, R.string.msg_new_login_pwd_empty);
            reset_pwd_new_pwd_two_et.requestFocus();
            return;
        }
        if (reset_pwd_new_pwd_two_et.length() < 6 || reset_pwd_new_pwd_two_et.length() > 20) { // 密码长度检查
            AlertUtil.t(this, R.string.msg_password_length_check);
            reset_pwd_new_pwd_two_et.requestFocus();
            return;
        }

        if (!Util.checkPwd(reset_pwd_new_pwd_et.getEditableText().toString())) {
            AlertUtil.t(this, R.string.msg_pwd_pattern_check);
            reset_pwd_new_pwd_et.requestFocus();
            return;
        }

        if (!Util.checkPwd(reset_pwd_new_pwd_two_et.getEditableText().toString())) {
            AlertUtil.t(this, R.string.msg_pwd_pattern_check);
            reset_pwd_new_pwd_two_et.requestFocus();
            return;
        }

        task = new BizDataAsyncTask<Void>(getWaitingView()) {

            @Override
            protected void onExecuteSucceeded(Void result) {
                AlertUtil.t(ActivityResetLoginPwd.this, "密码修改成功");
                // PreferenceCache.putVerificationCode(result);
                PreferenceCache.putToken("");
                PreferenceCache.putIfSkipLogin(false);
                // WajrApp.canQueryFromOnResume = false;
                HuRongBaoApp.globalIndex = 0;
                // Intent it = new Intent(ActivitydealPwdChange.this,
                // Activitydeal.class);
                // it.putExtra(ExtraConfig.IntentExtraKey.deal_FROM_MAIN, true);
                // startActivity(it);
                // Util.showdeal(ActivitydealPwdChange.this);
                finish();
            }

            @Override
            protected Void doExecute() throws ZYException, BizFailure {
                HomeBiz.resetPassword(phoneNum,
                        reset_pwd_new_pwd_et.getEditableText().toString(), reset_pwd_new_pwd_et.getEditableText().toString());
                return null;
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityResetLoginPwd.this, error);
                }
            }

        };

        task.execute();

    }


}
