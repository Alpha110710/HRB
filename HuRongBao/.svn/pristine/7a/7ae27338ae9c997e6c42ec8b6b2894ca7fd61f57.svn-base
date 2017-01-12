package com.hrb.ui.finance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.biz.FinanceBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.TenderDetailInfoModel;
import com.hrb.ui.base.BaseActivity;
import com.hrb.ui.widget.BottomScrollView;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

/**
 * Created by Ls on 2016/10/20.
 */

public class ActivityInvestDetail extends BaseActivity implements View.OnClickListener {

    private TextView invest_detail_amount_can_tv;
    private TextView invest_detail_amount_quota_tv;
    private TextView invest_detail_loan_amount_tv;
    private TextView invest_detail_date_tv;
    private TextView invest_detail_people_num_tv;
    private TextView invest_detail_return_type_tv;
    private TextView invest_detail_time_rest_tv;
    private TextView invest_detail_title_tv;
    private TextView invest_detail_year_tv;
    private TextView invest_rest_amount_tv;
    private Button invest_detail_confirm;
    private TextView invest_detail_touch_tv;
    private LinearLayout invest_detail_touch_center_ll;
    private TextView invest_detail_time_rest_word_tv;
    private BottomScrollView bottomScrollView;

    private String productId, DIRECTIONAL_PWD_FLG, EXP_FLG;
    private boolean isFirst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest_detail);
        productId = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.PRODUCT_ID);
        initView();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        invest_detail_confirm = (Button) findViewById(R.id.invest_detail_confirm);
        invest_detail_amount_can_tv = (TextView) findViewById(R.id.invest_detail_amount_can_tv);
        invest_detail_amount_quota_tv = (TextView) findViewById(R.id.invest_detail_amount_quota_tv);
        invest_detail_date_tv = (TextView) findViewById(R.id.invest_detail_date_tv);
        invest_detail_loan_amount_tv = (TextView) findViewById(R.id.invest_detail_loan_amount_tv);
        invest_detail_people_num_tv = (TextView) findViewById(R.id.invest_detail_people_num_tv);
        invest_detail_return_type_tv = (TextView) findViewById(R.id.invest_detail_return_type_tv);
        invest_detail_time_rest_tv = (TextView) findViewById(R.id.invest_detail_time_rest_tv);
        invest_detail_title_tv = (TextView) findViewById(R.id.invest_detail_title_tv);
        invest_detail_touch_tv = (TextView) findViewById(R.id.invest_detail_touch_tv);
        invest_detail_year_tv = (TextView) findViewById(R.id.invest_detail_year_tv);
        invest_detail_touch_center_ll = (LinearLayout) findViewById(R.id.invest_detail_touch_center_ll);
        invest_detail_time_rest_word_tv = (TextView) findViewById(R.id.invest_detail_time_rest_word_tv);
        bottomScrollView = (BottomScrollView) findViewById(R.id.middle);
        invest_rest_amount_tv = (TextView) findViewById(R.id.invest_rest_amount_tv);

        tv_title.setText("投资详情");
        iv_back.setOnClickListener(this);
        invest_detail_confirm.setOnClickListener(this);
        getTenderDetailInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isFirst = true;
        bottomScrollView.fullScroll(ScrollView.FOCUS_UP);
        a = -1;
    }

    private void getTenderDetailInfo() {
        BizDataAsyncTask<TenderDetailInfoModel> getTenderDetailInfo = new BizDataAsyncTask<TenderDetailInfoModel>(getWaitingView()) {
            @Override
            protected TenderDetailInfoModel doExecute() throws ZYException, BizFailure {
                return FinanceBiz.getTenderDetailInfo(productId);
            }

            @Override
            protected void onExecuteSucceeded(TenderDetailInfoModel tenderDetailInfoModel) {
                DIRECTIONAL_PWD_FLG = tenderDetailInfoModel.getDIRECTIONAL_PWD_FLG();
                EXP_FLG = tenderDetailInfoModel.getEXP_FLG();
                getData(tenderDetailInfoModel);
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityInvestDetail.this, error);
                }
            }
        };
        getTenderDetailInfo.execute();
    }

    public int top, bottom;
    private int a = -1;

    private void getData(final TenderDetailInfoModel tenderDetailInfoModel) {

        if (!"立即投资".equals(tenderDetailInfoModel.getBORROW_STATUS()))//图标变灰色不可点击
        {
            invest_detail_confirm.setBackground(getResources().getDrawable(R.drawable.shape_round_grey_button));
            invest_detail_confirm.setClickable(false);
        }
        // 标还未开始时, 显示发布时间. 标开始后显示剩余时间
        if ("等待开始".equals(tenderDetailInfoModel.getBORROW_STATUS())) {
            invest_detail_time_rest_word_tv.setText("投标开始时间");
            invest_detail_time_rest_tv.setText(tenderDetailInfoModel.getFINANCE_START_DATE());//发布时间
        } else {
            invest_detail_time_rest_tv.setText(tenderDetailInfoModel.getFinanceEndtime());//剩余时间
        }
        invest_detail_year_tv.setText(tenderDetailInfoModel.getFINANCE_INTEREST_RATE());//年化收益
        invest_detail_confirm.setText(tenderDetailInfoModel.getBORROW_STATUS());
        invest_detail_date_tv.setText(tenderDetailInfoModel.getPeriod() +
                tenderDetailInfoModel.getPeriodType());//借款期限
        invest_detail_title_tv.setText(tenderDetailInfoModel.getPRODUCTS_TITLE());//标题
        invest_detail_loan_amount_tv.setText(tenderDetailInfoModel.getFINANCE_AMOUNT() + "元");//借款金额
        invest_detail_amount_can_tv.setText(tenderDetailInfoModel.getWAIT_AMOUNT() + "元");//可投金额
        invest_rest_amount_tv.setText(tenderDetailInfoModel.getFINANCE_WAIT_AMOUNT() + "元");//剩余金额
        invest_detail_return_type_tv.setText(tenderDetailInfoModel.getFINANCE_REPAY_TYPE());//还款方式
        if (StringUtil.isEmpty(tenderDetailInfoModel.getTENDER_MAX_CAPTION())) {
            invest_detail_amount_quota_tv.setText("无限制");
        } else {
            invest_detail_amount_quota_tv.setText(tenderDetailInfoModel.getTENDER_MAX_CAPTION());//单笔限额
        }
        invest_detail_people_num_tv.setText(tenderDetailInfoModel.getTenderUserCntWyl() + "人");//已购人数

        bottomScrollView.setOnScrollToBottomLintener(new BottomScrollView.OnScrollToBottomListener() {
            @Override
            public void onScrollBottomListener(boolean isBottom) {
                if (isFirst && isBottom && a == -1) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                                a = 0;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    return;
                }

                if (isFirst && isBottom && a == 0) {
                    if (EXP_FLG != null) {
                        Intent intent = new Intent(ActivityInvestDetail.this,
                                ActivityInvestDetailMore.class);
                        intent.putExtra(ExtraConfig.IntentExtraKey.PRODUCT_ID, productId);
                        intent.putExtra(ExtraConfig.IntentExtraKey.FLG, tenderDetailInfoModel.getBORROW_STATUS());
                        intent.putExtra("EXP_FLG", EXP_FLG);
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_bottom_in,
                                R.anim.push_bottom_out);
                    }
                    isFirst = false;
                    a = -1;
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.invest_detail_confirm:// 立即投资
                if (DIRECTIONAL_PWD_FLG == null) {
                    return;
                }
                Intent intent = new Intent(this, ActivityInvestDetailImmediately.class);
                intent.putExtra(ExtraConfig.IntentExtraKey.PRODUCT_ID, productId);
                intent.putExtra(ExtraConfig.IntentExtraKey.DIRECTIONAL_PWD_FLG, DIRECTIONAL_PWD_FLG);
                intent.putExtra("EXP_FLG", EXP_FLG);
                startActivity(intent);

                break;
        }
    }


}
