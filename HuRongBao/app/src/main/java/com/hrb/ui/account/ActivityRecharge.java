package com.hrb.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.model.MyAccountModel;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;


/**
 * Created by Ls on 2016/10/17.
 */

public class ActivityRecharge extends BaseActivity implements View.OnClickListener {

    private Button recharge_btn;
    private EditText recharge_min_amount_et;
    private TextView recharge_can_use_tv;

    private MyAccountModel myAccountModel;
    private String user_recharge_amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        myAccountModel = getIntent().getParcelableExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT);
        initView();

    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        TextView tv_right = (TextView) findViewById(R.id.tv_right);

        recharge_btn = (Button) findViewById(R.id.recharge_btn);
        recharge_min_amount_et = (EditText) findViewById(R.id.recharge_min_amount_et);
        recharge_can_use_tv = (TextView) findViewById(R.id.recharge_can_use_tv);

        recharge_can_use_tv.setText((myAccountModel == null ? (0 + "") : myAccountModel.getUSABLE_AMOUNT()));
        recharge_min_amount_et.setHint("充值最小金额为" + (myAccountModel == null ? (0 + "") : myAccountModel.getMIN_ACCOUNT_ONE()) + "元");
        iv_back.setOnClickListener(this);
        tv_title.setText("充值");
        tv_right.setText("充值记录");
        tv_right.setOnClickListener(this);
        recharge_btn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                //充值记录
                Intent intent = new Intent(this, ActivityRechargeRecord.class);
                startActivity(intent);
                break;
            case R.id.recharge_btn:
                //充值
                if (StringUtil.isEmpty(recharge_min_amount_et.getText().toString())) {
                    AlertUtil.t(this, "请输入充值金额");
                    return;
                }

                if (Float.parseFloat(myAccountModel.getMIN_ACCOUNT_ONE()) > Float.parseFloat(recharge_min_amount_et.getText().toString())) {
                    AlertUtil.t(this, "最小充值金额为" + myAccountModel.getMIN_ACCOUNT_ONE() + "元");
                    return;
                }

                if (10000000f < Float.parseFloat(recharge_min_amount_et.getText().toString())) {
                    AlertUtil.t(this, "最大充值金额为10000000元");
                    return;
                }

                Intent i = new Intent(ActivityRecharge.this, ActivityRechargeFast.class);
                user_recharge_amount = recharge_min_amount_et.getText().toString();
                i.putExtra("user_recharge_amount", user_recharge_amount);
                startActivity(i);

                break;
        }
    }
}
