package com.hrb.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.model.MyAccountModel;
import com.hrb.storage.PreferenceCache;
import com.hrb.ui.base.BaseActivity;
import com.hrb.ui.init.ActivityWebView;
import com.hrb.ui.widget.PromptOkCancel;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.Util;

/**
 * Created by Ls on 2016/10/13.
 */

public class ActivitySetHelp extends BaseActivity implements View.OnClickListener {

    private MyAccountModel myAccountModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_help);
        myAccountModel = getIntent().getParcelableExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT);
        initView();
    }

    private void initView() {
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);

        findViewById(R.id.set_help_login_out_btn).setOnClickListener(this);
        findViewById(R.id.set_help_auto_bidding_tv).setOnClickListener(this);
        findViewById(R.id.set_help_bank_card_tv).setOnClickListener(this);
        findViewById(R.id.set_help_help_center_tv).setOnClickListener(this);
        findViewById(R.id.set_help_profit_loss_tv).setOnClickListener(this);
        findViewById(R.id.set_help_security_center_tv).setOnClickListener(this);
        tv_title.setText("设置与帮助");
        iv_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_help_login_out_btn:
                //退出登录
                PromptOkCancel dialog = new PromptOkCancel(this) {
                    @Override
                    protected void onOk() {
                        PreferenceCache.putToken("");
                        Util.showLogin(ActivitySetHelp.this);
                    }
                };
                dialog.show("确认", "是否退出账户?");
                break;
            case R.id.iv_back:
                finish();
                break;

            case R.id.set_help_profit_loss_tv:
                //个人损益
                Intent itProfitLoss = new Intent(this, ActivityPersonalProfitLoss.class);
                startActivity(itProfitLoss);
                break;

            case R.id.set_help_bank_card_tv:
                //银行卡
                if (myAccountModel != null) {
                    if (myAccountModel.getCONTRACTS() == null) {
                        AlertUtil.t(ActivitySetHelp.this, "请开通实名认证");
                    } else {
                        Intent inBankCard = new Intent(this, ActivityMyBankCard.class);
                        inBankCard.putExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT, myAccountModel);
                        startActivity(inBankCard);
                    }
                }
                break;
            case R.id.set_help_security_center_tv:
                //安全中心
                Intent inSecurityCenter = new Intent(this, ActivitySecurityCenter.class);
                inSecurityCenter.putExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT, myAccountModel);
                startActivity(inSecurityCenter);
                break;
            case R.id.set_help_help_center_tv:
                //设置与帮助
                Intent intent = new Intent(this, ActivityWebView.class);
                intent.putExtra("LINKURL", "");
                intent.putExtra(ExtraConfig.IntentExtraKey.WEB_VIEW_FROM, 3);
                startActivity(intent);
                break;
        }

    }
}
