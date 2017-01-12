package com.hrb.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.model.MyAccountModel;
import com.hrb.ui.base.BaseActivity;

/**
 * Created by Ls on 2016/10/17.
 */

public class ActivitySecurityCenter extends BaseActivity implements View.OnClickListener {

    private TextView security_center_trust_mark_tv;
    private TextView security_center_trust_num_tv;
    private TextView security_center_trust_title_tv;
    private TextView security_center_id_card_tv;
    private TextView security_center_phone_num_tv;
    private ImageView security_center_trust_mark_iv;
    private ImageView security_center_id_card_mark_iv;
    private TextView security_center_id_card_mark_tv;
    private MyAccountModel myAccountModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_center);
        myAccountModel = getIntent().getParcelableExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT);
        initView();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        security_center_trust_mark_tv = (TextView) findViewById(R.id.security_center_trust_mark_tv);
        security_center_trust_num_tv = (TextView) findViewById(R.id.security_center_trust_num_tv);
        security_center_trust_title_tv = (TextView) findViewById(R.id.security_center_trust_title_tv);
        security_center_id_card_tv = (TextView) findViewById(R.id.security_center_id_card_tv);
        security_center_phone_num_tv = (TextView) findViewById(R.id.security_center_phone_num_tv);
        security_center_trust_mark_iv = (ImageView) findViewById(R.id.security_center_trust_mark_iv);
        security_center_id_card_mark_iv = (ImageView) findViewById(R.id.security_center_id_card_mark_iv);
        security_center_id_card_mark_tv = (TextView) findViewById(R.id.security_center_id_card_mark_tv);

        findViewById(R.id.security_center_deal_pwd_reset_ll).setOnClickListener(this);
        findViewById(R.id.security_center_login_pwd_change_ll).setOnClickListener(this);
//        findViewById(R.id.security_center_phone_num_ll).setOnClickListener(this);
        security_center_phone_num_tv.setText(myAccountModel.getPHONE_NUMBER());

        iv_back.setOnClickListener(this);
        tv_title.setText("安全中心");

        if (myAccountModel.getCONTRACTS() == null) {
            //证件信息未认证
            security_center_id_card_tv.setVisibility(View.INVISIBLE);
            security_center_id_card_mark_iv.setVisibility(View.VISIBLE);
            security_center_id_card_mark_tv.setText("未认证");
            security_center_id_card_mark_tv.setTextColor(0xFFFF5A5C);
            findViewById(R.id.security_center_id_card_ll).setOnClickListener(this);
        } else if (!myAccountModel.getCONTRACTS().equals("")) {
            security_center_id_card_tv.setVisibility(View.VISIBLE);
            security_center_id_card_tv.setText(myAccountModel.getID_CARD_CONCEAL());
            security_center_id_card_mark_tv.setText("已认证");
            security_center_id_card_mark_tv.setTextColor(0xFF999999);
            security_center_id_card_mark_iv.setVisibility(View.GONE);
        }

        if (myAccountModel.getCONTRACTS() == null) {
            //托管账户未认证
            security_center_trust_title_tv.setVisibility(View.INVISIBLE);
            security_center_trust_num_tv.setVisibility(View.INVISIBLE);
            security_center_trust_mark_iv.setVisibility(View.VISIBLE);
            security_center_trust_mark_tv.setText("未绑定");
            security_center_trust_mark_tv.setTextColor(0xFFFF5A5C);
            findViewById(R.id.security_center_trust_ll).setOnClickListener(this);
        } else if (!myAccountModel.getCONTRACTS().equals("")) {
            security_center_trust_title_tv.setVisibility(View.VISIBLE);
            security_center_trust_num_tv.setVisibility(View.VISIBLE);
            security_center_trust_title_tv.setText("协议号");
            security_center_trust_num_tv.setText(myAccountModel.getCONTRACTS());
            security_center_trust_mark_tv.setText("已绑定");
            security_center_trust_mark_tv.setTextColor(0xFF999999);
            security_center_trust_mark_iv.setVisibility(View.GONE);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.security_center_deal_pwd_reset_ll:
                //重置交易密码
                break;

            case R.id.security_center_login_pwd_change_ll:
                //修改登录密码
                Intent intent = new Intent(this, ActivityLoginPwdChange.class);
                startActivity(intent);
                break;
            case R.id.security_center_phone_num_ll:
                //手机号码
                Intent intent1 = new Intent(this, ActivityResetTeleNumOne.class);
                intent1.putExtra("teleNum", myAccountModel.getPHONE_NUMBER());
                startActivity(intent1);
                break;
            case R.id.security_center_id_card_ll:
                //证件信息
                Intent intent2 = new Intent(ActivitySecurityCenter.this, ActivityRealNameAuth.class);
                startActivity(intent2);
                break;
            case R.id.security_center_trust_ll:
                //托管账户
                Intent intent3 = new Intent(ActivitySecurityCenter.this, ActivityRealNameAuth.class);
                startActivity(intent3);
                break;


        }
    }
}
