package com.hrb.ui.account;

import android.content.Intent;
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
import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.storage.PreferenceCache;
import com.hrb.ui.base.BaseActivity;
import com.hrb.ui.init.ActivityLogin;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;
import com.hrb.utils.java.Util;

import static com.hrb.R.id.reset_pwd_new_pwd_et;
import static com.hrb.R.id.reset_pwd_new_pwd_two_et;

/**
 * Created by Ls on 2016/10/17.
 */

public class ActivityLoginPwdChange extends BaseActivity implements View.OnClickListener {

    private TextView tv_title;
    private ImageView iv_back;

    private EditText et_login_pwd_change_old;
    private EditText et_login_pwd_change_new;
    private Button btn_login_pwd_change_confirm;
    private CheckBox cb_login_pwd_change_new;
    private CheckBox cb_login_pwd_change_old;
    private LinearLayout ll_login_pwd_change_old;
    private LinearLayout ll_login_pwd_change_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pwd_change);
        initView();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        btn_login_pwd_change_confirm = (Button) findViewById(R.id.btn_login_pwd_change_confirm);
        et_login_pwd_change_new = (EditText) findViewById(R.id.et_login_pwd_change_new);
        et_login_pwd_change_old = (EditText) findViewById(R.id.et_login_pwd_change_old);
        cb_login_pwd_change_new = (CheckBox) findViewById(R.id.cb_login_pwd_change_new);
        cb_login_pwd_change_old = (CheckBox) findViewById(R.id.cb_login_pwd_change_old);
        ll_login_pwd_change_old = (LinearLayout) findViewById(R.id.ll_login_pwd_change_old);
        ll_login_pwd_change_new = (LinearLayout) findViewById(R.id.ll_login_pwd_change_new);

        iv_back.setOnClickListener(this);
        tv_title.setText("登录密码修改");
        btn_login_pwd_change_confirm.setOnClickListener(this);
        ll_login_pwd_change_old.setOnClickListener(this);
        ll_login_pwd_change_new.setOnClickListener(this);

    }

    private BizDataAsyncTask<String> changeTask;

    private void changeLoginPassword() {

        if (StringUtil.isEmpty(et_login_pwd_change_old.getEditableText().toString())) {//
            AlertUtil.t(this, "请输入原登录密码");
            et_login_pwd_change_old.requestFocus();
            return;
        }
        if (et_login_pwd_change_old.length() < 6 || et_login_pwd_change_old.length() > 20) { // 密码长度检查
            AlertUtil.t(this, R.string.msg_password_length_check);
            et_login_pwd_change_old.requestFocus();
            return;
        }

        if (StringUtil.isEmpty(et_login_pwd_change_new.getEditableText().toString())) {//
            AlertUtil.t(this, R.string.msg_new_login_pwd_empty);
            et_login_pwd_change_new.requestFocus();
            return;
        }
        if (et_login_pwd_change_new.length() < 6 || et_login_pwd_change_new.length() > 20) { // 密码长度检查
            AlertUtil.t(this, R.string.msg_password_length_check);
            et_login_pwd_change_new.requestFocus();
            return;
        }

        if (!Util.checkPwd(et_login_pwd_change_old.getEditableText().toString())) {
            AlertUtil.t(this, R.string.msg_pwd_pattern_check);
            et_login_pwd_change_old.requestFocus();
            return;
        }

        if (!Util.checkPwd(et_login_pwd_change_new.getEditableText().toString())) {
            AlertUtil.t(this, R.string.msg_pwd_pattern_check);
            et_login_pwd_change_new.requestFocus();
            return;
        }


        changeTask = new BizDataAsyncTask<String>(getWaitingView()) {

            @Override
            protected void onExecuteSucceeded(String result) {
                AlertUtil.t(ActivityLoginPwdChange.this, "密码已修改，请用新密码登录");

                PreferenceCache.putToken("");
                Intent it = new Intent(ActivityLoginPwdChange.this, ActivityLogin.class);
                it.putExtra(ExtraConfig.IntentExtraKey.LOGIN_FROM_MAIN, true);
                startActivity(it);
                finish();

            }

            @Override
            protected String doExecute() throws ZYException, BizFailure {
                // TODO Auto-generated method stub
                String oldPassword = et_login_pwd_change_old.getText().toString().trim();
                String newPassword = et_login_pwd_change_new.getText().toString().trim();
                return AccountBiz.changeLoginPassword(oldPassword, newPassword);
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityLoginPwdChange.this, error);
                }

            }
        };

        changeTask.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_login_pwd_change_new:
                //是否看密码
                if (cb_login_pwd_change_new.isChecked()) {
                    et_login_pwd_change_new.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_login_pwd_change_new.setSelection(et_login_pwd_change_new.getEditableText().toString().length());
                } else {
                    et_login_pwd_change_new.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_login_pwd_change_new.setSelection(et_login_pwd_change_new.getEditableText().toString().length());
                }
                cb_login_pwd_change_new.setChecked(!cb_login_pwd_change_new.isChecked());
                break;
            case R.id.ll_login_pwd_change_old:
                //是否看密码
                if (cb_login_pwd_change_old.isChecked()) {
                    et_login_pwd_change_old.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_login_pwd_change_old.setSelection(et_login_pwd_change_old.getEditableText().toString().length());
                } else {
                    et_login_pwd_change_old.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_login_pwd_change_old.setSelection(et_login_pwd_change_old.getEditableText().toString().length());
                }
                cb_login_pwd_change_old.setChecked(!cb_login_pwd_change_old.isChecked());
                break;
            case R.id.btn_login_pwd_change_confirm:
                changeLoginPassword();
                break;
        }
    }
}
