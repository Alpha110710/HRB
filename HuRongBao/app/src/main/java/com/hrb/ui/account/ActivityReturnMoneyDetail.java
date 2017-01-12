package com.hrb.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.BaseModel;
import com.hrb.model.ReturnMoneyDetailListModel;
import com.hrb.model.ReturnMoneyDetailModel;
import com.hrb.model.ReturnMoneyDetailZhaiModel;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/18.
 */


//getPaymentDetails(String tenderId, String tenderType)//(tenderType:1投资 2债权转让)
//getPaymentDetailList(String tenderId, String tenderType, String firstIdx, String maxCount)//(tenderType:可以不填)

public class ActivityReturnMoneyDetail extends BaseActivity implements View.OnClickListener {

    //item_return_money_detail
    LinearLayout ll_money_detail;
    //item_return_money_detail_zhai
    LinearLayout ll_money_detail_zhai;


    //没有数据的TextView
    private TextView tv;

    private String tenderId;
    private String tenderType;

    BaseModel baseModel = null;

    TextView tv_title;
    ImageView iv_back;

    private int pageIndex = 0;
    private boolean isEnd = false;

    PullToRefreshListView pullToRefreshListView;
    private ReturnMoneyDetailListAdapter adapter;

    //回款详情信息 页面
    TextView detail_data_tv;
    TextView detail_data_unit_tv;
    TextView detail_deal_amount_tv;
    TextView detail_deal_time_tv;
    TextView detail_fair_value_tv;
    TextView detail_return_style_tv;
    TextView detail_title_tv;
    TextView detail_transfer_amount_tv;
    TextView detail_year_tv;
    TextView detail_add_rate_tv;

    //回款详情债权  页面
    TextView detail_zhai_title_tv;//题目
    TextView detail_zhai_year_tv;//%
    TextView detail_zhai_data_tv;//天
    TextView detail_zhai_transfer_amount_tv;//元
    TextView detail_zhai_fair_value_tv;//公允价值
    TextView detail_zhai_deal_amount_tv;//成交价格
    TextView detail_zhai_return_style_tv;//还款方式
    TextView detail_zhai_deal_time_tv;//交易时间
    private TextView detail_zhai_data_unit_tv;//到期时间单位
    private TextView detail_zhai_title_title_tv;
    private TextView detail_zhai_add_rate_tv;//加息


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_money_detail);
        initDate();
        initView();
        initInternetData();
    }

    //初始化从从其他Activity传递过来的参数
    private void initDate() {
        tenderId = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.RETURN_MONEY_DETAIL_ONE_ID);
        tenderType = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.RETURN_MONEY_DETAIL_ONE_TYPR);
    }


    //创建Activity之前就请求网络  获取页面数据
    private void initInternetData() {
        getReturnMoneyDetail();
        getReturnMoneyDetailList(true, true);
    }


    private void initView() {
        adapter = new ReturnMoneyDetailListAdapter(this);
        ll_money_detail = (LinearLayout) findViewById(R.id.ll_money_detail);
        ll_money_detail_zhai = (LinearLayout) findViewById(R.id.ll_money_detail_zhai);

        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.detail_list);
        detail_data_tv = (TextView) findViewById(R.id.detail_data_tv);
        detail_data_unit_tv = (TextView) findViewById(R.id.detail_data_unit_tv);
        detail_deal_amount_tv = (TextView) findViewById(R.id.detail_deal_amount_tv);
        detail_deal_time_tv = (TextView) findViewById(R.id.detail_deal_time_tv);
        detail_fair_value_tv = (TextView) findViewById(R.id.detail_fair_value_tv);
        detail_return_style_tv = (TextView) findViewById(R.id.detail_return_style_tv);
        detail_title_tv = (TextView) findViewById(R.id.detail_title_tv);
        detail_transfer_amount_tv = (TextView) findViewById(R.id.detail_transfer_amount_tv);
        detail_year_tv = (TextView) findViewById(R.id.detail_year_tv);
        detail_add_rate_tv = (TextView) findViewById(R.id.detail_add_rate_tv);

        tv_title.setText("回款详情");
        iv_back.setOnClickListener(this);

        tv = new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");

        detail_zhai_title_tv = (TextView) findViewById(R.id.detail_zhai_title_tv);
        detail_zhai_year_tv = (TextView) findViewById(R.id.detail_zhai_year_tv);
        detail_zhai_data_tv = (TextView) findViewById(R.id.detail_zhai_data_tv);//到期时间
        detail_zhai_data_unit_tv = (TextView) findViewById(R.id.detail_zhai_data_unit_tv1);//时间单位没用上
        detail_zhai_transfer_amount_tv = (TextView) findViewById(R.id.detail_zhai_transfer_amount_tv);
        detail_zhai_fair_value_tv = (TextView) findViewById(R.id.detail_zhai_fair_value_tv);
        detail_zhai_deal_amount_tv = (TextView) findViewById(R.id.detail_zhai_deal_amount_tv);
        detail_zhai_return_style_tv = (TextView) findViewById(R.id.detail_zhai_return_style_tv);
        detail_zhai_deal_time_tv = (TextView) findViewById(R.id.detail_zhai_deal_time_tv);
        detail_zhai_title_title_tv = (TextView) findViewById(R.id.detail_zhai_title_title_tv);
        detail_zhai_add_rate_tv = (TextView) findViewById(R.id.detail_zhai_add_rate_tv);

        pullToRefreshListView.setAdapter(adapter);
    }


    //设置非债权或者债权  回款详情页面
    private void setReturnMoneyView(BaseModel baseModel) {
        if (baseModel instanceof ReturnMoneyDetailModel) {
            //非债权
            ll_money_detail.setVisibility(View.VISIBLE);
            ll_money_detail_zhai.setVisibility(View.GONE);

            if (((ReturnMoneyDetailModel) baseModel).getPERIOD().contains("个月")) {
                detail_data_unit_tv.setText("个月");
                detail_data_tv.setText(((ReturnMoneyDetailModel) baseModel).getPERIOD().replace("个月", ""));
            } else if (((ReturnMoneyDetailModel) baseModel).getPERIOD().contains("个季度")) {
                detail_data_unit_tv.setText("个季度");
                detail_data_tv.setText(((ReturnMoneyDetailModel) baseModel).getPERIOD().replace("个季度", ""));
            } else {
                detail_data_unit_tv.setText("天");
                detail_data_tv.setText(((ReturnMoneyDetailModel) baseModel).getPERIOD().replace("天", ""));
            }
            detail_return_style_tv.setText(((ReturnMoneyDetailModel) baseModel).getREPAY_TYPE());
            detail_title_tv.setText(((ReturnMoneyDetailModel) baseModel).getPRODUCTS_TITLE());
            detail_year_tv.setText(((ReturnMoneyDetailModel) baseModel).getRATE());
            detail_transfer_amount_tv.setText(((ReturnMoneyDetailModel) baseModel).getTENDER_AMOUNT());
            //加息
            if (StringUtil.isEmpty(((ReturnMoneyDetailModel) baseModel).getCOUPON_RATE())) {
                detail_add_rate_tv.setText("%");
            } else {
                detail_add_rate_tv.setText("%+" + ((ReturnMoneyDetailModel) baseModel).
                        getCOUPON_RATE() + "%");
            }

        } else if (baseModel instanceof ReturnMoneyDetailZhaiModel) {
            //债权
            ll_money_detail.setVisibility(View.GONE);
            ll_money_detail_zhai.setVisibility(View.VISIBLE);
            detail_zhai_title_tv.setText("债权转让编号 : " + ((ReturnMoneyDetailZhaiModel) baseModel).getTRANSFER_CONTRACT_ID());
            detail_zhai_year_tv.setText(((ReturnMoneyDetailZhaiModel) baseModel).getRATE());
            detail_zhai_data_tv.setText(((ReturnMoneyDetailZhaiModel) baseModel).getRECOVER_END_DATE());
            detail_zhai_transfer_amount_tv.setText(((ReturnMoneyDetailZhaiModel) baseModel).getTENDER_AMOUNT_TRANS());
            detail_zhai_fair_value_tv.setText(((ReturnMoneyDetailZhaiModel) baseModel).getFAIR_VALUE());
            detail_zhai_deal_amount_tv.setText(((ReturnMoneyDetailZhaiModel) baseModel).getACTUAL_AMOUNT());
            detail_zhai_return_style_tv.setText(((ReturnMoneyDetailZhaiModel) baseModel).getREPAY_TYPE());
            detail_zhai_deal_time_tv.setText(((ReturnMoneyDetailZhaiModel) baseModel).getSUCCESS_DATE());
            detail_zhai_title_title_tv.setText(((ReturnMoneyDetailZhaiModel) baseModel).getPRODUCTS_TITLE());
            //加息
            if (StringUtil.isEmpty(((ReturnMoneyDetailZhaiModel) baseModel).getCOUPON_RATE())) {
                detail_zhai_add_rate_tv.setText("%");
            } else {
                detail_zhai_add_rate_tv.setText("%+" + ((ReturnMoneyDetailZhaiModel) baseModel).
                        getCOUPON_RATE() + "%");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }

    }

    private void getReturnMoneyDetail() {

        BizDataAsyncTask<BaseModel> getReturnMoneyDetailTask = new BizDataAsyncTask<BaseModel>() {

            @Override
            protected void onExecuteSucceeded(BaseModel result) {
                baseModel = result;
                if (baseModel != null)
                    setReturnMoneyView(baseModel);
            }

            @Override
            protected BaseModel doExecute() throws ZYException, BizFailure {
                return AccountBiz.getPaymentDetails(tenderId, tenderType);
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityReturnMoneyDetail.this, error);
                }
            }
        };

        getReturnMoneyDetailTask.setWaitingView(getWaitingView());

        getReturnMoneyDetailTask.execute();

    }


    private BizDataAsyncTask<List<ReturnMoneyDetailListModel>> getReturnMoneyDetailListTask;

    private void getReturnMoneyDetailList(final boolean first, final boolean isPullDown) {

        getReturnMoneyDetailListTask = new BizDataAsyncTask<List<ReturnMoneyDetailListModel>>() {

            @Override
            protected void onExecuteSucceeded(List<ReturnMoneyDetailListModel> result) {

                if (result.size() < ExtraConfig.DEFAULT_PAGE_COUNT) {
                    isEnd = true;
                    if (isPullDown && result.size() == 0) {
                        pullToRefreshListView.setEmptyView(tv);
                    }

                    pullToRefreshListView.setPullLabel("没有更多数据", PullToRefreshBase.Mode.PULL_FROM_END);
                    pullToRefreshListView.setReleaseLabel("没有更多数据", PullToRefreshBase.Mode.PULL_FROM_END);
                    pullToRefreshListView.setRefreshingLabel("没有更多数据", PullToRefreshBase.Mode.PULL_FROM_END);
                } else {
                    isEnd = false;
                    pullToRefreshListView.setPullLabel("上拉刷新", PullToRefreshBase.Mode.PULL_FROM_END);
                    pullToRefreshListView.setReleaseLabel("放开以刷新", PullToRefreshBase.Mode.PULL_FROM_END);
                    pullToRefreshListView.setRefreshingLabel("正在载入", PullToRefreshBase.Mode.PULL_FROM_END);
                }

                if (isPullDown) {
                    pageIndex = 0;
                    if (adapter != null)
                        adapter.removeAll();
                    else
                        Log.e("Tag++++", "adapter为空");
                }
                pageIndex++;

                for (int i = 0; i < result.size(); i++) {
                    adapter.addItem(result.get(i));
                }
                Log.e("Tag++++", "result.size" + result.size());
                adapter.notifyDataSetChanged();

                pullToRefreshListView.onRefreshComplete();

            }

            @Override
            protected List<ReturnMoneyDetailListModel> doExecute() throws ZYException, BizFailure {

                if (isPullDown) {
                    return AccountBiz.getReturnMoneyDetailList(tenderId, tenderType, "0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return AccountBiz.getReturnMoneyDetailList(tenderId, tenderType, pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                    }
                }
            }

            @Override
            protected void OnExecuteFailed(String error) {

                pullToRefreshListView.onRefreshComplete();
            }
        };

        if (first) {
            getReturnMoneyDetailListTask.setWaitingView(getWaitingView());
        }

        getReturnMoneyDetailListTask.execute();

    }


    class ReturnMoneyDetailListAdapter extends BaseAdapter {

        private List<ReturnMoneyDetailListModel> list = new ArrayList<>();
        private Context context;

        public ReturnMoneyDetailListAdapter(Context context) {
            this.context = context;
        }

        public void addItem(ReturnMoneyDetailListModel cellOptions) {
            list.add(cellOptions);
        }

        public void removeAll() {
            if (list != null) {
                if (list.size() > 0) {
                    for (int i = list.size() - 1; i >= 0; i--) {
                        list.remove(i);
                    }
                }

            }
        }

        @Override
        public int getCount() {

            return list == null ? 0 : list.size();
        }

        @Override
        public ReturnMoneyDetailListModel getItem(int position) {

            return list.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ActivityReturnMoneyDetail.ReturnMoneyDetailListAdapter.HoldViewHolder viewHolder;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.item_return_money_detail, parent,
                        false);
                viewHolder = new ActivityReturnMoneyDetail.ReturnMoneyDetailListAdapter.HoldViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ActivityReturnMoneyDetail.ReturnMoneyDetailListAdapter.HoldViewHolder) convertView.getTag();
            }
            if (list.size() > position && viewHolder != null) {
                viewHolder.tv_item_return_money_detail_date.setText(list.get(position).getRECOVER_DATE());
                viewHolder.tv_item_return_money_detail_original.setText(list.get(position).getRECOVER_AMOUNT_CAPITAL());
                viewHolder.tv_item_return_money_detail_accrual.setText(list.get(position).getRECOVER_AMOUNT_INTEREST());
                if (list.get(position).getSUB_STATUS().equals("0")) {
                    //未回款
                    viewHolder.iv_flg.setImageDrawable(null);
                } else {
                    viewHolder.iv_flg.setImageDrawable(getResources().getDrawable(R.drawable.return_money_detail_03));
                }

                if (list.get(position).getOVERDUE_FLG().equals("0")) {
                    viewHolder.ll_hide.setVisibility(View.GONE);
                } else {
                    viewHolder.iv_flg.setImageDrawable(getResources().getDrawable(R.drawable.auto_bidding_03_03));
                    viewHolder.tv_item_return_money_detail_out_day.setText(list.get(position).getOVERDUE_DAY());
                    viewHolder.tv_item_return_money_detail_overdue_pubnish.setText(list.get(position).getOVERDUE_INTEREST() + "元");
                    viewHolder.tv_item_return_money_detail_out_interest_punishment.setText(list.get(position).getOVERDUE_FORFEIT() + "元");
                }

            }


            return convertView;
        }

        class HoldViewHolder {

            private final TextView tv_item_return_money_detail_date;
            private final TextView tv_item_return_money_detail_original;
            private final TextView tv_item_return_money_detail_accrual;
            private final LinearLayout ll_hide;
            private final TextView tv_item_return_money_detail_out_day;
            private final TextView tv_item_return_money_detail_overdue_pubnish;
            private final TextView tv_item_return_money_detail_out_interest_punishment;
            private final ImageView iv_flg;

            public HoldViewHolder(View itemView) {
                ll_hide = (LinearLayout) itemView.findViewById(R.id.ll_hide);
                tv_item_return_money_detail_date = (TextView) itemView.findViewById(R.id.tv_item_return_money_detail_date);
                tv_item_return_money_detail_original = (TextView) itemView.findViewById(R.id.tv_item_return_money_detail_original);
                tv_item_return_money_detail_accrual = (TextView) itemView.findViewById(R.id.tv_item_return_money_detail_accrual);
                tv_item_return_money_detail_out_day = (TextView) itemView.findViewById(R.id.tv_item_return_money_detail_out_day);
                tv_item_return_money_detail_overdue_pubnish = (TextView) itemView.findViewById(R.id.tv_item_return_money_detail_overdue_pubnish);
                tv_item_return_money_detail_out_interest_punishment = (TextView) itemView.findViewById(R.id.tv_item_return_money_detail_out_interest_punishment);
                iv_flg = (ImageView) itemView.findViewById(R.id.iv_flg);
            }

        }

    }


}
