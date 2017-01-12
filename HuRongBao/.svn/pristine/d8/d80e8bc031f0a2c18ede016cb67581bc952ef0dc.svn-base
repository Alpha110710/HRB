package com.hrb.ui.finance;

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
import com.hrb.biz.FinanceBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.TransferRecordModel;
import com.hrb.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/28.
 */

public class ActivityTransferRecord extends BaseActivity implements View.OnClickListener {

    private PullToRefreshListView pullToRefreshListView;
    private TextView tv;
    private int pageIndex = 0;
    private boolean end = false;
    private String TRANSFER_FULL_STATUS, transferId;

    private TransferRecordAdapter adapter;
    private TextView transfer_record_time;//购买时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_record);
        TRANSFER_FULL_STATUS = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.TRANSFER_FULL_STATUS);//0转让中 1 转让成功
        transferId = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.TRANSFER_ID);
        initView();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.transfer_record_list);
        TextView transfer_record_return = (TextView) findViewById(R.id.transfer_record_return);
        TextView transfer_record_period = (TextView) findViewById(R.id.transfer_record_period);
        transfer_record_time = (TextView) findViewById(R.id.transfer_record_time);


//        if (TRANSFER_FULL_STATUS.equals("0")) {
//            transfer_record_return.setText("还款金额 ( 元 )");
//            transfer_record_period.setText("期数");
//            transfer_record_time.setText("预计还款时间");
//        }
        tv_title.setText("转让记录");
        iv_back.setOnClickListener(this);
        initData();
    }


    private void initData() {
        adapter = new TransferRecordAdapter(this);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.getRefreshableView().setAdapter(adapter);

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getList(false, true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getList(false, false);
            }
        });
        tv = new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");
        getList(false, true);
    }

    private void getList(final boolean first, final boolean isPullDown) {

        BizDataAsyncTask<List<TransferRecordModel>> getList = new BizDataAsyncTask<List<TransferRecordModel>>() {
            @Override
            protected List<TransferRecordModel> doExecute() throws ZYException, BizFailure {
                if (isPullDown) {

                    return FinanceBiz.getTransferInvestors(transferId, "0", ExtraConfig.DEFAULT_PAGE_COUNT + "", "1");
                } else {
                    if (end) {
                        return new ArrayList<>();
                    } else {
                        return FinanceBiz.getTransferInvestors(transferId, pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "", "1");
                    }
                }
            }

            @SuppressWarnings("deprecation")
            @Override
            protected void onExecuteSucceeded(List<TransferRecordModel> transferListModels) {
                if (transferListModels.size() < ExtraConfig.DEFAULT_PAGE_COUNT) {

                    end = true;
                    if (isPullDown && transferListModels.size() == 0) {
                        pullToRefreshListView.setEmptyView(tv);
                    }
                    pullToRefreshListView.setPullLabel("没有更多数据", PullToRefreshBase.Mode.PULL_FROM_END);
                    pullToRefreshListView.setReleaseLabel("没有更多数据", PullToRefreshBase.Mode.PULL_FROM_END);
                    pullToRefreshListView.setRefreshingLabel("没有更多数据", PullToRefreshBase.Mode.PULL_FROM_END);
                } else {
                    end = false;
                    pullToRefreshListView.setPullLabel("上拉刷新", PullToRefreshBase.Mode.PULL_FROM_END);
                    pullToRefreshListView.setReleaseLabel("放开以刷新", PullToRefreshBase.Mode.PULL_FROM_END);
                    pullToRefreshListView.setRefreshingLabel("正在载入", PullToRefreshBase.Mode.PULL_FROM_END);
                }

                if (isPullDown) {
                    pageIndex = 0;
                    adapter.removeAll();
                }
                pageIndex++;

                for (int i = 0; i < transferListModels.size(); i++) {
                    adapter.addItem(transferListModels.get(i));
                }
                adapter.notifyDataSetChanged();

                pullToRefreshListView.onRefreshComplete();
            }

            @Override
            protected void OnExecuteFailed(String error) {
                pullToRefreshListView.onRefreshComplete();
            }
        };
        if (first) {
            getList.setWaitingView(getWaitingView());
        }
        getList.execute();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public class TransferRecordAdapter extends BaseAdapter {

        private Context context;
        private List<TransferRecordModel> list = new ArrayList<>();

        public TransferRecordAdapter(Context context) {
            this.context = context;
        }


        // 添加数据
        public void addItem(TransferRecordModel transferRecordModel) {
            list.add(transferRecordModel);
        }

        // 移除所有数据
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
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RecordListViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_transfer_record, parent, false);
                viewHolder = new RecordListViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (RecordListViewHolder) convertView.getTag();
            }
//            if (TRANSFER_FULL_STATUS.equals("0")) {
//                //转让中
//                viewHolder.item_record_actual_amount.setText(list.get(position).getRECOVER_AMOUNT_FORMAT());
//                viewHolder.item_record_time.setText(list.get(position).getRECOVER_DATE_FORMAT());
//                viewHolder.item_record_buy_amount.setText(list.get(position).getRECOVER_PERIOD());//期数
//            } else {
            viewHolder.item_record_actual_amount.setText(list.get(position).getREAL_AMOUNT());
            viewHolder.item_record_time.setText(list.get(position).getBUY_DATE());
            viewHolder.item_record_buy_amount.setText(list.get(position).getBUY_AMOUNT());
//            }


            return convertView;
        }


        public class RecordListViewHolder {

            private final TextView item_record_buy_amount;
            private final TextView item_record_actual_amount;
            private final TextView item_record_time;

            RecordListViewHolder(View itemView) {
                item_record_buy_amount = (TextView) itemView.findViewById(R.id.item_record_buy_amount);
                item_record_actual_amount = (TextView) itemView.findViewById(R.id.item_record_actual_amount);
                item_record_time = (TextView) itemView.findViewById(R.id.item_record_time);

            }
        }

    }
}
