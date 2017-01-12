package com.hrb.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
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
import com.hrb.model.MyInvestBidModel;
import com.hrb.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/13.
 */

public class FragmentMyInvestBid extends BaseFragment {

    private PullToRefreshListView pullToRefreshListView;
    private InvestListBidAdapter adapter;

    private int pageIndex = 0;
    private boolean isEnd = false;
    private TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_invest_bidding, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.list_my_invest_bid);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {

        adapter = new InvestListBidAdapter(getActivity());
        pullToRefreshListView.getRefreshableView().setAdapter(adapter);

        // 刷新方法
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                getMyInvestBid(false, true);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                getMyInvestBid(false, false);
            }

        });

        tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");

        getMyInvestBid(true, true);
    }

    private BizDataAsyncTask<List<BaseModel>> getMyInvestListTask;

    private void getMyInvestBid(final boolean first, final boolean isPullDown) {

        getMyInvestListTask = new BizDataAsyncTask<List<BaseModel>>() {

            @Override
            protected void onExecuteSucceeded(List<BaseModel> result) {

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
                    adapter.removeAll();
                }
                pageIndex++;

                for (int i = 0; i < result.size(); i++) {
                    adapter.addItem((MyInvestBidModel) result.get(i));
                }
                adapter.notifyDataSetChanged();

                pullToRefreshListView.onRefreshComplete();

            }

            @Override
            protected List<BaseModel> doExecute() throws ZYException, BizFailure {

                if (isPullDown) {

                    return AccountBiz.getMyInvestList("2", "0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return AccountBiz.getMyInvestList("2", pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                    }
                }
            }

            @Override
            protected void OnExecuteFailed(String error) {
                pullToRefreshListView.onRefreshComplete();
            }
        };

        if (first) {
            getMyInvestListTask.setWaitingView(getWaitingView());
        }

        getMyInvestListTask.execute();

    }

    class InvestListBidAdapter extends BaseAdapter {

        private List<MyInvestBidModel> list = new ArrayList<>();
        private Context context;

        public InvestListBidAdapter(Context context) {

            this.context = context;
        }

        public void addItem(MyInvestBidModel cellOptions) {
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
        public MyInvestBidModel getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            BidViewHolder viewHolder;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.item_my_invest_bidding, parent,
                        false);
                viewHolder = new BidViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (BidViewHolder) convertView.getTag();
            }
            viewHolder.item_bid_title_tv.setText(list.get(position).getPRODUCTS_TITLE());
            viewHolder.item_bid_invest_amount_tv.setText(list.get(position).getTENDER_AMOUNT());
            viewHolder.item_bid_expect_profit_tv.setText(list.get(position).getRECOVER_AMOUNT_INTEREST());
            viewHolder.item_bid_year_profit_tv.setText(list.get(position).getFINANCE_INTEREST_RATE());
            viewHolder.item_bid_state_tv.setText(list.get(position).getSTATUS());

            if (list.get(position).getFINANCE_PERIOD_FORMAT().contains("个月")) {
                viewHolder.item_bid_loan_time_tv.setText(list.get(position).getFINANCE_PERIOD_FORMAT().replace("个月", ""));
                viewHolder.item_bid_loan_time_unit_tv.setText("个月");
            } else if (list.get(position).getFINANCE_PERIOD_FORMAT().contains("天")) {
                viewHolder.item_bid_loan_time_tv.setText(list.get(position).getFINANCE_PERIOD_FORMAT().replace("天", ""));
                viewHolder.item_bid_loan_time_unit_tv.setText("天");
            } else {
                viewHolder.item_bid_loan_time_tv.setText(list.get(position).getFINANCE_PERIOD_FORMAT().replace("个季度", ""));
                viewHolder.item_bid_loan_time_unit_tv.setText("个季度");
            }

            return convertView;
        }

        class BidViewHolder {


            private final TextView item_bid_state_tv;
            private final TextView item_bid_expect_profit_tv;
            private final TextView item_bid_invest_amount_tv;
            private final TextView item_bid_loan_time_tv;
            private final TextView item_bid_loan_time_unit_tv;
            private final TextView item_bid_title_tv;
            private final TextView item_bid_year_profit_tv;

            public BidViewHolder(View itemView) {
                item_bid_state_tv = (TextView) itemView.findViewById(R.id.item_bid_state_tv);
                item_bid_expect_profit_tv = (TextView) itemView.findViewById(R.id.item_bid_expect_profit_tv);
                item_bid_invest_amount_tv = (TextView) itemView.findViewById(R.id.item_bid_invest_amount_tv);
                item_bid_loan_time_tv = (TextView) itemView.findViewById(R.id.item_bid_loan_time_tv);
                item_bid_loan_time_unit_tv = (TextView) itemView.findViewById(R.id.item_bid_loan_time_unit_tv);
                item_bid_title_tv = (TextView) itemView.findViewById(R.id.item_bid_title_tv);
                item_bid_year_profit_tv = (TextView) itemView.findViewById(R.id.item_bid_year_profit_tv);

            }

        }

    }
}
