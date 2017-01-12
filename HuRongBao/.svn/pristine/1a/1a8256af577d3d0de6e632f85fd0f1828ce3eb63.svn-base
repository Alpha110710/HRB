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
import com.hrb.model.RechargeRecordModel;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/17.
 */

public class ActivityRechargeRecord extends BaseActivity implements View.OnClickListener {

    private PullToRefreshListView pullToRefreshListView;
    private RecordListAdapter adapter;

    private int pageIndex = 0;
    private boolean isEnd = false;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_record);
        initView();
        init();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.recharge_record_list);

        iv_back.setOnClickListener(this);
        tv_title.setText("充值记录");

    }

    private void init() {
        adapter = new RecordListAdapter(this);

        pullToRefreshListView.getRefreshableView().setAdapter(adapter);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                rechargeRecord(false, true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                rechargeRecord(false, false);
            }
        });

        tv = new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");

        rechargeRecord(true, true);
    }

    private void rechargeRecord(final boolean first, final boolean isPullDown) {
        BizDataAsyncTask<List<RechargeRecordModel>> rechargeRecord = new BizDataAsyncTask<List<RechargeRecordModel>>() {
            @Override
            protected List<RechargeRecordModel> doExecute() throws ZYException, BizFailure {
                if (isPullDown) {

                    return AccountBiz.rechargeRecord("0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return AccountBiz.rechargeRecord(pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                    }
                }
            }

            @Override
            protected void onExecuteSucceeded(List<RechargeRecordModel> rechargeRecordModels) {
                if (rechargeRecordModels.size() < ExtraConfig.DEFAULT_PAGE_COUNT) {
                    isEnd = true;
                    if (isPullDown && rechargeRecordModels.size() == 0) {
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

                for (int i = 0; i < rechargeRecordModels.size(); i++) {
                    adapter.addItem(rechargeRecordModels.get(i));
                }
                adapter.notifyDataSetChanged();

                pullToRefreshListView.onRefreshComplete();
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityRechargeRecord.this, error);
                }
                pullToRefreshListView.onRefreshComplete();
            }
        };

        if (first) {
            rechargeRecord.setWaitingView(getWaitingView());
        }
        rechargeRecord.execute();

    }

    class RecordListAdapter extends BaseAdapter {

        private List<RechargeRecordModel> list = new ArrayList<>();
        private Context context;

        RecordListAdapter(Context context) {
            this.context = context;
        }

        public void addItem(RechargeRecordModel cellOptions) {
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
        public RechargeRecordModel getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ListViewHolder viewHolder;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.item_recharge_record,
                        parent, false);
                viewHolder = new ListViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ListViewHolder) convertView.getTag();
            }

            viewHolder.tv_item_recharge_title.setText(list.get(position).getRECHARGE_STATUS());//成功充值
            viewHolder.tv_item_recharge_data.setText(list.get(position).getRECHARGE_DATE());//时间
            viewHolder.tv_item_recharge_deal_amount.setText(list.get(position).getRECHARGE_AMOUNT_FORMAT());//充值金额
            viewHolder.tv_item_recharge_balance.setText(list.get(position).getACTUAL_AMOUNT_FORMAT());//到账金额

            // list设置数据
            return convertView;
        }

        class ListViewHolder {

            private final TextView tv_item_recharge_title;
            private final TextView tv_item_recharge_data;
            private final TextView tv_item_recharge_deal_amount;
            private final TextView tv_item_recharge_balance;

            ListViewHolder(View itemView) {
                tv_item_recharge_title = (TextView) itemView.findViewById(R.id.tv_item_recharge_title);
                tv_item_recharge_data = (TextView) itemView.findViewById(R.id.tv_item_recharge_data);
                tv_item_recharge_deal_amount = (TextView) itemView.findViewById(R.id.tv_item_recharge_deal_amount);
                tv_item_recharge_balance = (TextView) itemView.findViewById(R.id.tv_item_recharge_balance);
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
}
