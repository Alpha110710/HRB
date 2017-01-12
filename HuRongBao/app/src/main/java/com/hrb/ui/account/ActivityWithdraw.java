package com.hrb.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.HuRongBaoApp;
import com.hrb.MainActivity;
import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

/**
 * Created by Ls on 2016/10/17.
 */

public class ActivityWithdraw extends BaseActivity implements View.OnClickListener {

    private Button withdraw_btn;
    private EditText withdraw_amount_et;
    private TextView withdraw_can_use_tv;
    private String withdrawMoney;
    private String user_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        user_amount = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.USER_AMOUNT);
        initView();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        TextView tv_right = (TextView) findViewById(R.id.tv_right);
        withdraw_btn = (Button) findViewById(R.id.withdraw_btn);
        withdraw_amount_et = (EditText) findViewById(R.id.withdraw_amount_et);
        withdraw_can_use_tv = (TextView) findViewById(R.id.withdraw_can_use_tv);


        iv_back.setOnClickListener(this);
        tv_title.setText("提现");
        tv_right.setText("提现记录");
        tv_right.setOnClickListener(this);
        withdraw_btn.setOnClickListener(this);
        withdraw_can_use_tv.setText((user_amount == null || user_amount.equals("")) ? (0 + "") : user_amount);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                //提现记录
                Intent intent = new Intent(this, ActivityWithdrawRecord.class);
                startActivity(intent);
                break;
            case R.id.withdraw_btn:
                //提现
                if (StringUtil.isEmpty(withdraw_amount_et.getText().toString())) {
                    AlertUtil.t(this, "请输入提现金额");
                    return;
                }
                withDraw();
                break;
        }
    }

    BizDataAsyncTask<String> withDrawTask;

    private void withDraw() {
        withdrawMoney = withdraw_amount_et.getText().toString();

        withDrawTask = new BizDataAsyncTask<String>(getWaitingView()) {
            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return AccountBiz.withdraw(withdrawMoney);
            }

            @Override
            protected void onExecuteSucceeded(String redomVerifyCodeModel) {
                AlertUtil.t(ActivityWithdraw.this, "提现成功");
                Intent intent = new Intent(ActivityWithdraw.this, MainActivity.class);
                HuRongBaoApp.globalIndex = 2;
                startActivity(intent);
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityWithdraw.this, error);
                }
            }
        };
        withDrawTask.execute();

    }
}
