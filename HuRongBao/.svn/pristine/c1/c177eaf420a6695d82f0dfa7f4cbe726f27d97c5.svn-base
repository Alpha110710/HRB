package com.hrb.ui.account;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.model.MyTransferingModel;
import com.hrb.ui.base.BaseActivity;

public class ActivityTransferingDetail extends BaseActivity implements OnClickListener {

    private TextView tv_title;
    private ImageView iv_back;
    private MyTransferingModel.TRANSFERDETAILBean detailBean;


    private TextView transfer_scale;//折让比例
    private TextView transfer_scale_Amount;//折让金额
    private TextView transfer_managefee;//服务费
    private TextView transfer_status;//状态
    private TextView transfer_title_tv;//标题
    private TextView transfer_out_amount_tv;//转出金额
    private TextView transfer_value_tv;//公允价值
    private TextView transfer_deal_amount_tv;//成交价格
    private TextView transfer_zq_value_old_tv;//原债权价格
    private TextView transfer_time_tv;//转让时间
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_detail_gray);
        detailBean = (MyTransferingModel.TRANSFERDETAILBean) getIntent().getParcelableExtra(ExtraConfig.IntentExtraKey.MY_TRANSFER_DETAIL_BEAN);
        title = getIntent().getStringExtra("title");
        initView();
        getData();
    }


    private void getData() {
        if (detailBean != null) {
            transfer_zq_value_old_tv.setText("原债权金额(元) : " + detailBean.getDETAIL_TENDER_AMOUNT());
            transfer_out_amount_tv.setText(detailBean.getDETAIL_TRANSFER_CAPITAL());
            transfer_value_tv.setText(detailBean.getDETAIL_FAIR_VALUE());
            transfer_deal_amount_tv.setText(detailBean.getDETAIL_TRANSFER_AMOUNT());
            transfer_scale.setText(detailBean.getDETAIL_DISCOUNT_SCALE());
            transfer_scale_Amount.setText(detailBean.getDETAIL_DISCOUNT_AMOUNT());
            transfer_managefee.setText(detailBean.getDETAIL_TRANSFER_MANAGE_FEE());
            transfer_status.setText(detailBean.getDETAIL_TRANSFER_STATUS());
            transfer_time_tv.setText(detailBean.getDETAIL_TRANSFER_TIME());
            transfer_title_tv.setText(title);
        }
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        iv_back.setOnClickListener(this);
        tv_title.setText("转让详情");

        transfer_scale = (TextView) findViewById(R.id.transfer_scale);
        transfer_scale_Amount = (TextView) findViewById(R.id.transfer_scale_Amount);
        transfer_managefee = (TextView) findViewById(R.id.transfer_managefee);
        transfer_status = (TextView) findViewById(R.id.transfer_status);
        transfer_title_tv = (TextView) findViewById(R.id.transfer_title_tv);//标题
        transfer_out_amount_tv = (TextView) findViewById(R.id.transfer_out_amount_tv);//转出金额
        transfer_value_tv = (TextView) findViewById(R.id.transfer_value_tv);//公允价值
        transfer_deal_amount_tv = (TextView) findViewById(R.id.transfer_deal_amount_tv);//成交价格
        transfer_zq_value_old_tv = (TextView) findViewById(R.id.transfer_zq_value_old_tv);//原债权价格
        transfer_time_tv = (TextView) findViewById(R.id.transfer_time_tv);//转让时间

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            default:
                break;
        }
    }

}
