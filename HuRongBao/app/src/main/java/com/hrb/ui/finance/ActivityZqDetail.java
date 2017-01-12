package com.hrb.ui.finance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.biz.FinanceBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.GetTransferInfoModel;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;


public class ActivityZqDetail extends BaseActivity implements OnClickListener {

    private TextView transfer_zq_profit;
    private TextView transfer_zq_amount;
    private TextView transfer_zq_date;
    private TextView transfer_zq_fair_value;
    private TextView transfer_zq_old_value;
    private TextView transfer_zq_rest_amount;
    private TextView transfer_zq_return_style;
    private TextView transfer_zq_title;
    private TextView transfer_zq_value;
    private TextView transfer_zq_year;
    private Button zqSubmit;// 立即投资

    private GetTransferInfoModel model;
    private String transferId;// 债券id
    private String TRANSFER_FULL_STATUS, USABLE_AMOUNT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfert_detail_red);
        transferId = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.TRANSFER_ID);
        TRANSFER_FULL_STATUS = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.TRANSFER_FULL_STATUS);//0转让中 1 转让成功

        initView();
        setTitle("债权详情");
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        TextView tv_right = (TextView) findViewById(R.id.tv_right);

        transfer_zq_profit = (TextView) findViewById(R.id.transfer_zq_profit);
        transfer_zq_amount = (TextView) findViewById(R.id.transfer_zq_amount);
        transfer_zq_date = (TextView) findViewById(R.id.transfer_zq_date);
        transfer_zq_fair_value = (TextView) findViewById(R.id.transfer_zq_fair_value);
        transfer_zq_old_value = (TextView) findViewById(R.id.transfer_zq_old_value);
        transfer_zq_rest_amount = (TextView) findViewById(R.id.transfer_zq_rest_amount);
        transfer_zq_return_style = (TextView) findViewById(R.id.transfer_zq_return_style);
        transfer_zq_title = (TextView) findViewById(R.id.transfer_zq_title);
        transfer_zq_value = (TextView) findViewById(R.id.transfer_zq_value);
        transfer_zq_year = (TextView) findViewById(R.id.transfer_zq_year);
        zqSubmit = (Button) findViewById(R.id.investment_submit);

        tv_right.setText("转让记录");
        iv_back.setOnClickListener(this);
        tv_title.setText("转让详情");

        zqSubmit.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        getData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.investment_submit:
                //立即投资
                if (transferId == null || model == null) {
                    return;
                }
                Intent intent = new Intent(this, ActivityTransferDetailBuy.class);
                intent.putExtra(ExtraConfig.IntentExtraKey.TRANSFER_ID, transferId);
                intent.putExtra(ExtraConfig.IntentExtraKey.USER_AMOUNT, USABLE_AMOUNT);
                startActivity(intent);

                break;
            case R.id.tv_right:
                Intent intent1 = new Intent(this, ActivityTransferRecord.class);
                intent1.putExtra(ExtraConfig.IntentExtraKey.TRANSFER_FULL_STATUS, TRANSFER_FULL_STATUS);
                intent1.putExtra(ExtraConfig.IntentExtraKey.TRANSFER_ID, transferId);
                startActivity(intent1);
                break;
        }
    }


    // 访问接口获取数据
    private void getData() {
        BizDataAsyncTask<GetTransferInfoModel> getData =
                new BizDataAsyncTask<GetTransferInfoModel>(getWaitingView()) {

                    @Override
                    protected GetTransferInfoModel doExecute() throws ZYException, BizFailure {
                        return FinanceBiz.getTransferInfo(transferId);// 传递标的唯一id
                    }

                    @SuppressWarnings("deprecation")
                    @Override
                    protected void onExecuteSucceeded(GetTransferInfoModel result) {
                        model = result;
                        transfer_zq_amount.setText(result.getTRANSFER_CAPITAL());
                        transfer_zq_year.setText(result.getRATE());
                        transfer_zq_date.setText(result.getPERIOD());
                        transfer_zq_title.setText(result.getPRODUCTS_TITLE());
                        transfer_zq_rest_amount.setText(result.getTRANSFER_CAPTIAL_WAIT());//剩余可投
                        transfer_zq_value.setText(result.getTRANSFER_CAPITAL());
                        transfer_zq_return_style.setText(result.getFINANCE_REPAY_TYPE());
                        transfer_zq_fair_value.setText(result.getFAIR_VALUE() + "元");
                        transfer_zq_old_value.setText(result.getTRANSFER_CAPITAL() + "元");
                        transfer_zq_profit.setText(result.getDISCOUNT_SCALE() + "%");
                        USABLE_AMOUNT = result.getUSABLE_AMOUNT();
                        zqSubmit.setText(result.getBORROW_STATUS());

                        if (!"1".equals(result.getSTATUS()))//图标变灰色不可点击
                        {
                            zqSubmit.setBackground(getResources().getDrawable(R.drawable.shape_round_grey_button));
                            zqSubmit.setClickable(false);
                        }
                    }

                    @Override
                    protected void OnExecuteFailed(String error) {
                        if (!StringUtil.isEmpty(error)) {
                            AlertUtil.t(ActivityZqDetail.this, error);
                        }
                    }

                };

        getData.execute();

    }


}
