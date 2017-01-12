package com.hrb.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.BankCardHadModel;
import com.hrb.model.MyAccountModel;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

/**
 * Created by Ls on 2016/10/14.
 */

public class ActivityMyBankCard extends BaseActivity implements View.OnClickListener {

    private TextView my_bank_card_tv;
    private MyAccountModel myAccountModel;

    private LinearLayout my_bank_card_include;
    private LinearLayout my_bank_card_had_include;

    private TextView bank_card_num_tv;
    private ImageView bank_card_iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bank_card);
        myAccountModel = getIntent().getParcelableExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT);
        initView();

    }

    private void initView() {
        my_bank_card_tv = (TextView) findViewById(R.id.my_bank_card_tv);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);

        my_bank_card_include = (LinearLayout) findViewById(R.id.my_bank_card_include);
        my_bank_card_had_include = (LinearLayout) findViewById(R.id.my_bank_card_had_include);

        bank_card_num_tv = (TextView) findViewById(R.id.bank_card_num_tv);
        bank_card_iv = (ImageView) findViewById(R.id.bank_card_iv);


        iv_back.setOnClickListener(this);
        tv_title.setText("我的银行卡");
        my_bank_card_tv.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bingBankInfo();
    }

    private void bingBankInfo() {
        BizDataAsyncTask<BankCardHadModel> bingBankInfo = new BizDataAsyncTask<BankCardHadModel>(getWaitingView()) {
            @Override
            protected BankCardHadModel doExecute() throws ZYException, BizFailure {
                return AccountBiz.bingBankInfo("0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
            }

            @Override
            protected void onExecuteSucceeded(BankCardHadModel bankCardHadModel) {

                if (!bankCardHadModel.getCARD_STATUS().equals("1")) {
                    //未绑定
                    my_bank_card_include.setVisibility(View.VISIBLE);
                    my_bank_card_had_include.setVisibility(View.GONE);
                } else {
                    my_bank_card_had_include.setVisibility(View.VISIBLE);
                    my_bank_card_include.setVisibility(View.GONE);
                    bank_card_num_tv.setText(bankCardHadModel.getCARD_NO_CONCEAL());
                    chooseBank(bankCardHadModel);
                }
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityMyBankCard.this, error);
                }
            }
        };
        bingBankInfo.execute();
    }


    //选择银行卡图片
    private void chooseBank(BankCardHadModel bankCardHadModel) {
        if (bankCardHadModel.getBANK().equals("ceb")) {
            bank_card_iv.setImageResource(R.drawable.bank_guang_da);
        } else if (bankCardHadModel.getBANK().equals("spdb")) {
            bank_card_iv.setImageResource(R.drawable.bank_pu_fa);
        } else if (bankCardHadModel.getBANK().equals("gdb")) {
            bank_card_iv.setImageResource(R.drawable.bank_guang_fa);
        } else if (bankCardHadModel.getBANK().equals("cmb")) {
            bank_card_iv.setImageResource(R.drawable.bank_zhao_shang);
        } else if (bankCardHadModel.getBANK().equals("bocm")) {
            bank_card_iv.setImageResource(R.drawable.bank_jiao_tong);
        } else if (bankCardHadModel.getBANK().equals("boc")) {
            bank_card_iv.setImageResource(R.drawable.bank_china);
        } else if (bankCardHadModel.getBANK().equals("pingan")) {
            bank_card_iv.setImageResource(R.drawable.bank_ping_an);
        } else if (bankCardHadModel.getBANK().equals("citic")) {
            bank_card_iv.setImageResource(R.drawable.bank_zhong_xin);
        } else if (bankCardHadModel.getBANK().equals("cmbc")) {
            bank_card_iv.setImageResource(R.drawable.bank_min_sheng);
        } else if (bankCardHadModel.getBANK().equals("abc")) {
            bank_card_iv.setImageResource(R.drawable.bank_nong_ye);
        } else if (bankCardHadModel.getBANK().equals("icbc")) {
            bank_card_iv.setImageResource(R.drawable.bank_gong_shang);
        } else if (bankCardHadModel.getBANK().equals("cib")) {
            bank_card_iv.setImageResource(R.drawable.bank_xing_ye);
        } else {
            bank_card_iv.setImageResource(R.drawable.bank_jian_hang);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.my_bank_card_tv:
                Intent intent = new Intent(this, ActivityAddBankCard.class);
                intent.putExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT, myAccountModel);
                startActivity(intent);
                break;
        }

    }
}
