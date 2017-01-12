package com.hrb.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.HuRongBaoApp;
import com.hrb.MainActivity;
import com.hrb.R;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.Util;

/**
 * Created by Ls on 2016/10/19.
 */

public class ActivityOpenAccount extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_account);

        initView();
    }

    private void initView() {
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        findViewById(R.id.open_account_no_tv).setOnClickListener(this);
        findViewById(R.id.open_account_btn).setOnClickListener(this);

        tv_title.setText("开通资金账户");
        iv_back.setOnClickListener(this);
    }

//    private void realNameAuth() {
//        BizDataAsyncTask<String> realNameAuth = new BizDataAsyncTask<String>(getWaitingView()) {
//            @Override
//            protected String doExecute() throws ZYException, BizFailure {
//                return HomeBiz.realNameAuth("", "");
//            }
//
//            @Override
//            protected void onExecuteSucceeded(String openAccountModel) {
//                Intent intent = new Intent(ActivityOpenAccount.this, ActivityRealNameAuth.class);
//                startActivity(intent);
//            }
//
//            @Override
//            protected void OnExecuteFailed(String error) {
//                if (!StringUtil.isEmpty(error)){
//                    AlertUtil.t(ActivityOpenAccount.this, error);
//                }
//            }
//        };
//        realNameAuth.execute();
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                Intent intent1 = new Intent(this, MainActivity.class);
                HuRongBaoApp.globalIndex = 2;
                startActivity(intent1);
                break;
            case R.id.open_account_btn:
                //开通账户
                Intent intent = new Intent(ActivityOpenAccount.this, ActivityRealNameAuth.class);
                startActivity(intent);
                break;
            case R.id.open_account_no_tv:
                //暂不开通, 先去逛逛
                HuRongBaoApp.globalIndex = 0;
                Util.gotoMain(ActivityOpenAccount.this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        HuRongBaoApp.globalIndex = 0;
        Util.gotoMain(ActivityOpenAccount.this);
    }
}
