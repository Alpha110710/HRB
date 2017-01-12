package com.hrb.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.hrb.model.DealRecordModel;
import com.hrb.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/14.
 */

public class ActivityDealRecord extends BaseActivity implements View.OnClickListener {

    private PullToRefreshListView pullToRefreshListView;
    private ListAdapter adapter;

    private int pageIndex = 0;
    private boolean isEnd = false;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_record);
        initView();
        init();
    }

    private void initView() {
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list_trading_record);

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);

        iv_back.setOnClickListener(this);
        tv_title.setText("交易记录");
    }

    private void init() {

        adapter = new ListAdapter(this);
        pullToRefreshListView.getRefreshableView().setAdapter(adapter);

        // 刷新方法
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                getMyInvestHold(false, true);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                getMyInvestHold(false, false);
            }

        });

        tv = new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");

        getMyInvestHold(true, true);
    }

    private BizDataAsyncTask<List<DealRecordModel>> getMyInvestListTask;

    private void getMyInvestHold(final boolean first, final boolean isPullDown) {

        getMyInvestListTask = new BizDataAsyncTask<List<DealRecordModel>>() {

            @Override
            protected void onExecuteSucceeded(List<DealRecordModel> result) {
                // TODO Auto-generated method stub

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
                    adapter.addItem(result.get(i));
                }
                adapter.notifyDataSetChanged();

                pullToRefreshListView.onRefreshComplete();

            }

            @Override
            protected List<DealRecordModel> doExecute() throws ZYException, BizFailure {
                // TODO Auto-generated method stub
                if (isPullDown) {

                    return AccountBiz.getTradingRecord("0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return AccountBiz.getTradingRecord(pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                    }
                }
            }

            @Override
            protected void OnExecuteFailed(String error) {
                // TODO Auto-generated method stub
                pullToRefreshListView.onRefreshComplete();
            }
        };

        if (first) {
            getMyInvestListTask.setWaitingView(getWaitingView());
        }

        getMyInvestListTask.execute();

    }

    class ListAdapter extends BaseAdapter {

        private List<DealRecordModel> list = new ArrayList<>();
        private Context context;

        public ListAdapter(Context context) {

            this.context = context;
        }

        public void addItem(DealRecordModel cellOptions) {
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
            // TODO Auto-generated method stub
            return list == null ? 0 : list.size();
        }

        @Override
        public DealRecordModel getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RecordListViewHolder viewHolder;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.item_deal_record, parent,
                        false);
                viewHolder = new RecordListViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (RecordListViewHolder) convertView.getTag();
            }

            // list设置数据

            if (list.get(position).getREVENUE_EXPEND_TYPE().equals("R")) {
                viewHolder.tv_item_trading_deal_amount.setTextColor(getResources().getColor(R.color.font_orange));
            } else {
                viewHolder.tv_item_trading_deal_amount.setTextColor(getResources().getColor(R.color.red_light));

            }

            viewHolder.tv_item_trading_balance.setText(list.get(position).getUSABLE_AMOUNT());
            viewHolder.tv_item_trading_data.setText(list.get(position).getDATE());
            viewHolder.tv_item_trading_deal_amount.setText(list.get(position).getAMOUNT());
            viewHolder.tv_item_trading_title.setText(list.get(position).getFUND_TYPE());

            return convertView;
        }

        class RecordListViewHolder {

            TextView tv_item_trading_balance;// 账户余额
            TextView tv_item_trading_data;// 日期
            TextView tv_item_trading_deal_amount;// 交易金额
            TextView tv_item_trading_title;// 标题

            public RecordListViewHolder(View itemView) {

                tv_item_trading_balance = (TextView) itemView.findViewById(R.id.tv_item_trading_balance);
                tv_item_trading_data = (TextView) itemView.findViewById(R.id.tv_item_trading_data);
                tv_item_trading_deal_amount = (TextView) itemView.findViewById(R.id.tv_item_trading_deal_amount);
                tv_item_trading_title = (TextView) itemView.findViewById(R.id.tv_item_trading_title);

            }

        }

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
