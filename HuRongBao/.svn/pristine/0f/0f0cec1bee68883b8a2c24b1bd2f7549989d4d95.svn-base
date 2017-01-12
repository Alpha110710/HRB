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
import com.hrb.model.MyTransferingModel;
import com.hrb.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/14.
 */

public class FragmentTransfering extends BaseFragment {

    private PullToRefreshListView pullToRefreshListView;
    private MyTransferingListAdapter adapter;

    private int pageIndex = 0;
    private boolean isEnd = false;
    private TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_transfer_ing, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

    }

    private void init() {
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.my_transfer_ing_list);

        adapter = new MyTransferingListAdapter(getActivity());
        pullToRefreshListView.getRefreshableView().setAdapter(adapter);

        // 刷新方法
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                getMyTransfering(false, true);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getMyTransfering(false, false);
            }

        });

        tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");

        getMyTransfering(true, true);
    }

    private BizDataAsyncTask<List<BaseModel>> task;

    public void getMyTransfering(final boolean first, final boolean isPullDown) {

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
                    adapter.addItem((MyTransferingModel) result.get(i));
                }
                adapter.notifyDataSetChanged();

                pullToRefreshListView.onRefreshComplete();

            }

            @Override
            protected List<BaseModel> doExecute() throws ZYException, BizFailure {
                if (isPullDown) {

                    return AccountBiz.getMyInvestList("4", "0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return AccountBiz.getMyInvestList("4", pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
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

    class MyTransferingListAdapter extends BaseAdapter {

        private List<MyTransferingModel> list = new ArrayList<>();
        private Context context;

        public MyTransferingListAdapter(Context context) {

            this.context = context;
        }

        public void addItem(MyTransferingModel cellOptions) {
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
        public MyTransferingModel getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.item_my_transfer_ing, parent,
                        false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            if (list.get(position).getREMAIN_TIME().contains("已到期")) {
                viewHolder.tv_item_transfering_hour.setText("已到期");
                viewHolder.tv_item_transfering_xiaoshi.setVisibility(View.GONE);
                viewHolder.tv_item_transfering_fen.setVisibility(View.GONE);
                viewHolder.tv_item_transfering_miniutes.setVisibility(View.GONE);
            } else {

                if (Integer.parseInt(list.get(position).getHORE()) > 0) {
                    viewHolder.tv_item_transfering_hour.setText(list.get(position).getHORE());
                    viewHolder.tv_item_transfering_hour.setVisibility(View.VISIBLE);
                    viewHolder.tv_item_transfering_xiaoshi.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tv_item_transfering_xiaoshi.setVisibility(View.GONE);
                    viewHolder.tv_item_transfering_hour.setVisibility(View.GONE);
                }

                if (Integer.parseInt(list.get(position).getHORE()) > 0 || Integer.parseInt(list.get(position).getMINUTE()) > 0) {
                    viewHolder.tv_item_transfering_miniutes.setText(list.get(position).getMINUTE());
                    viewHolder.tv_item_transfering_fen.setVisibility(View.VISIBLE);
                    viewHolder.tv_item_transfering_miniutes.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tv_item_transfering_miniutes.setText("少于一分钟");
                    viewHolder.tv_item_transfering_miniutes.setVisibility(View.VISIBLE);
                    viewHolder.tv_item_transfering_fen.setVisibility(View.GONE);
                }
            }

            viewHolder.tv_item_transfering_had.setText(list.get(position).getTRANSFER_CAPITAL_YES());
            viewHolder.tv_item_transfering_money.setText(list.get(position).getTRANSFER_CAPITAL());
            viewHolder.tv_item_transfering_title.setText(list.get(position).getPRODUCTS_TITLE());

            // 转让详情
            final int pos = position;
            viewHolder.tv_item_transfering_detail.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), ActivityTransferingDetail.class);
                    intent.putExtra(ExtraConfig.IntentExtraKey.MY_TRANSFER_DETAIL_BEAN,
                            list.get(pos).getTRANSFER_DETAIL());
                    intent.putExtra("title", list.get(pos).getPRODUCTS_TITLE());

                    startActivity(intent);

                }
            });

            return convertView;
        }

        class ViewHolder {

            TextView tv_item_transfering_detail;// 转让详情按钮
            TextView tv_item_transfering_had;// 已转让金额
            TextView tv_item_transfering_hour;// 小时
            TextView tv_item_transfering_miniutes;// 转让分
            TextView tv_item_transfering_money;// 转让金额
            TextView tv_item_transfering_title;// 标题
            TextView tv_item_transfering_fen;// 分
            TextView tv_item_transfering_xiaoshi;// 小时

            public ViewHolder(View itemView) {

                tv_item_transfering_detail = (TextView) itemView.findViewById(R.id.tv_item_transfering_detail);
                tv_item_transfering_had = (TextView) itemView.findViewById(R.id.tv_item_transfering_had);
                tv_item_transfering_hour = (TextView) itemView.findViewById(R.id.tv_item_transfering_hour);
                tv_item_transfering_miniutes = (TextView) itemView.findViewById(R.id.tv_item_transfering_miniutes);
                tv_item_transfering_money = (TextView) itemView.findViewById(R.id.tv_item_transfering_money);
                tv_item_transfering_title = (TextView) itemView.findViewById(R.id.tv_item_transfering_title);
                tv_item_transfering_fen = (TextView) itemView.findViewById(R.id.tv_item_transfering_fen);
                tv_item_transfering_xiaoshi = (TextView) itemView.findViewById(R.id.tv_item_transfering_xiaoshi);

            }

        }

    }

}
