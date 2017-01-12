package com.hrb.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.BaseModel;
import com.hrb.model.ReturnMoneyPlanModel;
import com.hrb.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityReturnMoneyPlan extends BaseActivity implements OnClickListener {

    private PullToRefreshListView pullToRefreshListView;
    private ReturnMoneyPlanListAdapter adapter;

    private TextView textView;
    private int pageIndex;
    private boolean isEnd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_money_plan);
        initView();

        init();
    }

    private void initView() {
        TextView headerTitle = (TextView) findViewById(R.id.tv_title);
        ImageView headerBack = (ImageView) findViewById(R.id.iv_back);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list_return_money_plan);

        headerTitle.setText("回款计划");
        headerBack.setOnClickListener(this);

    }

    // private String productsTitle;//标题
    // private String financeInterestRate;//待回利息
    // private String financePeriod;//待回本金
    // private String interestRateType;//债权-非债权
    // private String recoverDate;//日期
    // private String currentPeriod;//期数

    private void init() {

        adapter = new ReturnMoneyPlanListAdapter(this);

        pullToRefreshListView.getRefreshableView().setAdapter(adapter);

        pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getReturnMoneyPlanList(false, true);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getReturnMoneyPlanList(false, false);
            }

        });

        textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setText("暂无数据");
        getReturnMoneyPlanList(true, true);

    }


    private void getReturnMoneyPlanList(boolean isFirst, final boolean isPullDown) {

        BizDataAsyncTask<List<BaseModel>> returnMoneyPlanTask = new BizDataAsyncTask<List<BaseModel>>() {

            @Override
            protected void onExecuteSucceeded(List<BaseModel> result) {
                if (result.size() < ExtraConfig.DEFAULT_PAGE_COUNT) {
                    isEnd = true;
                    if (isPullDown && result.size() == 0) {
                        pullToRefreshListView.setEmptyView(textView);
                    }

                    pullToRefreshListView.setPullLabel("没有更多数据", Mode.PULL_FROM_END);
                    pullToRefreshListView.setReleaseLabel("没有更多数据", Mode.PULL_FROM_END);
                    pullToRefreshListView.setRefreshingLabel("没有更多数据", Mode.PULL_FROM_END);
                } else {
                    isEnd = false;
                    pullToRefreshListView.setPullLabel("上拉刷新", Mode.PULL_FROM_END);
                    pullToRefreshListView.setReleaseLabel("放开以刷新", Mode.PULL_FROM_END);
                    pullToRefreshListView.setRefreshingLabel("正在载入", Mode.PULL_FROM_END);
                }

                if (isPullDown) {
                    pageIndex = 0;
                    adapter.removeAll();
                }
                pageIndex++;

                for (int i = 0; i < result.size(); i++) {
                    adapter.addItem((ReturnMoneyPlanModel) result.get(i));
                }
                adapter.notifyDataSetChanged();

                pullToRefreshListView.onRefreshComplete();

            }

            @Override
            protected void OnExecuteFailed(String error) {
                pullToRefreshListView.onRefreshComplete();
            }

            @Override
            protected List<BaseModel> doExecute() throws ZYException, BizFailure {

                if (isPullDown) {
                    return AccountBiz.getReturnMoneyPlanList("", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return AccountBiz.getReturnMoneyPlanList(pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                    }
                }
            }

        };

        if (isFirst) {
            returnMoneyPlanTask.setWaitingView(getWaitingView());
        }
        returnMoneyPlanTask.execute();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }

    }


    class ReturnMoneyPlanListAdapter extends BaseAdapter {

        private List<ReturnMoneyPlanModel> list = new ArrayList<>();
        private Context context;

        public ReturnMoneyPlanListAdapter(Context context) {
            this.context = context;
        }

        public void addItem(ReturnMoneyPlanModel cellOptions) {
            list.add(cellOptions);
        }

        public void removeAll() {
            if (list != null && list.size() > 0) {
                for (int i = list.size() - 1; i >= 0; i--) {
                    list.remove(i);
                }
            }
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public ReturnMoneyPlanModel getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ReturnMoneyPlanListViewHolder viewHolder;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.item_return_money_plan, parent,
                        false);
                viewHolder = new ReturnMoneyPlanListViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ReturnMoneyPlanListViewHolder) convertView.getTag();
            }

            viewHolder.tv_item_plan_benjin.setText(list.get(position).getRECOVER_AMOUNT_CAPITAL());
            viewHolder.tv_item_plan_data.setText(list.get(position).getRECOVER_DATE());
            viewHolder.tv_item_plan_lixi.setText(list.get(position).getRECOVER_AMOUNT_INTEREST());
            viewHolder.tv_item_plan_periods.setText(list.get(position).getCURRENT_PERIOD());


            return convertView;
        }

        class ReturnMoneyPlanListViewHolder {

            TextView tv_item_plan_benjin;// 待回本金
            TextView tv_item_plan_data;// 日期
            TextView tv_item_plan_lixi;// 待回利息
            TextView tv_item_plan_periods;// 期数

            ReturnMoneyPlanListViewHolder(View itemView) {
                tv_item_plan_benjin = (TextView) itemView.findViewById(R.id.tv_item_plan_benjin);
                tv_item_plan_data = (TextView) itemView.findViewById(R.id.tv_item_plan_data);
                tv_item_plan_lixi = (TextView) itemView.findViewById(R.id.tv_item_plan_lixi);
                tv_item_plan_periods = (TextView) itemView.findViewById(R.id.tv_item_plan_periods);

            }

        }

    }

}

