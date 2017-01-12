package com.hrb.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.hrb.model.MyTransferedModel;
import com.hrb.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/14.
 */

public class FragmentTransfered extends BaseFragment {

    private PullToRefreshListView pullToRefreshListView;
    private TransferedListAdapter adapter;

    private int pageIndex = 0;
    private boolean isEnd = false;
    private TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_transfer_had, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

    }

    private void init() {
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.my_transfer_had_list);

        adapter = new TransferedListAdapter(getActivity());
        pullToRefreshListView.getRefreshableView().setAdapter(adapter);

        // 刷新方法
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                getMyTransferedModel(false, true);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getMyTransferedModel(false, false);
            }

        });

        tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");

        getMyTransferedModel(true, true);
    }

    private BizDataAsyncTask<List<BaseModel>> task;

    private void getMyTransferedModel(final boolean first, final boolean isPullDown) {

        task = new BizDataAsyncTask<List<BaseModel>>() {

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
                    adapter.addItem((MyTransferedModel) result.get(i));
                }
                adapter.notifyDataSetChanged();

                pullToRefreshListView.onRefreshComplete();

            }

            @Override
            protected List<BaseModel> doExecute() throws ZYException, BizFailure {
                if (isPullDown) {

                    return AccountBiz.getMyInvestList("5", "0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return AccountBiz.getMyInvestList("5", pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                    }
                }
            }

            @Override
            protected void OnExecuteFailed(String error) {
                pullToRefreshListView.onRefreshComplete();
            }
        };

        if (first) {
            task.setWaitingView(getWaitingView());
        }

        task.execute();

    }

    class TransferedListAdapter extends BaseAdapter {

        private List<MyTransferedModel> list = new ArrayList<>();
        private Context context;

        public TransferedListAdapter(Context context) {

            this.context = context;
        }

        public void addItem(MyTransferedModel cellOptions) {
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
            // 
            return list == null ? 0 : list.size();
        }

        @Override
        public MyTransferedModel getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TransferedListViewHolder viewHolder;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.item_my_transfer_had, parent, false);
                viewHolder = new TransferedListViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (TransferedListViewHolder) convertView.getTag();
            }


            // list设置数据
            viewHolder.tv_item_transfered_deal_amount.setText(list.get(position).getTRANSFER_AMOUNT());
            viewHolder.tv_item_transfered_debt_amount.setText(list.get(position).getTENDER_AMOUNT());
            viewHolder.tv_item_transfered_out_amount.setText(list.get(position).getTRANSFER_CAPITAL());
            viewHolder.tv_item_transfered_title.setText(list.get(position).getPRODUCTS_TITLE());

            // 转让详情
            final int pos = position;
            viewHolder.tv_item_transfered_transfer_detail.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 点击跳转到回款详情 需要有一个标记 确认跳转到回款详情--债权
                    Intent intent = new Intent(getActivity(), ActivityTransferedDetail.class);
                    intent.putExtra("title", list.get(pos).getPRODUCTS_TITLE());
                    intent.putExtra(ExtraConfig.IntentExtraKey.MY_TRANSFER_DETAIL_BEAN, list.get(pos).getTRANSFER_DETAIL());
                    startActivity(intent);

                }
            });

            return convertView;
        }

        class TransferedListViewHolder {

            TextView tv_item_transfered_deal_amount;// 成交金额
            TextView tv_item_transfered_debt_amount;// 债权金额
            TextView tv_item_transfered_out_amount;// 转出金额
            TextView tv_item_transfered_title;// 标题
            TextView tv_item_transfered_transfer_detail;// 转让详情

            public TransferedListViewHolder(View itemView) {
                tv_item_transfered_deal_amount = (TextView) itemView.findViewById(R.id.tv_item_transfered_deal_amount);
                tv_item_transfered_debt_amount = (TextView) itemView.findViewById(R.id.tv_item_transfered_debt_amount);
                tv_item_transfered_out_amount = (TextView) itemView.findViewById(R.id.tv_item_transfered_out_amount);
                tv_item_transfered_title = (TextView) itemView.findViewById(R.id.tv_item_transfered_title);
                tv_item_transfered_transfer_detail = (TextView) itemView
                        .findViewById(R.id.tv_item_transfered_transfer_detail);

            }

        }

    }


}
