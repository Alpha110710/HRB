package com.hrb.ui.account;

import android.content.Context;
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
import com.hrb.model.MyRedPacketOutDateModel;
import com.hrb.ui.base.BaseFragment;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/21.
 */

public class FragmentMyRedPackageOutData extends BaseFragment {

    private PullToRefreshListView pullToRefreshListView;
    private RedPacketOutDataListAdapter adapter;

    private int pageIndex = 0;
    private boolean isEnd = false;
    private TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_red_packet_used, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.list_red_used);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        adapter = new RedPacketOutDataListAdapter(getActivity());

        pullToRefreshListView.getRefreshableView().setAdapter(adapter);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getMyRedPacket(false, true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getMyRedPacket(false, false);
            }
        });

        tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");

        getMyRedPacket(true, true);
    }

    private void getMyRedPacket(final boolean first, final boolean isPullDown) {

        BizDataAsyncTask<List<MyRedPacketOutDateModel>> task = new BizDataAsyncTask<List<MyRedPacketOutDateModel>>() {

            @Override
            protected void onExecuteSucceeded(List<MyRedPacketOutDateModel> result) {

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
            protected List<MyRedPacketOutDateModel> doExecute() throws ZYException, BizFailure {
                if (isPullDown) {

                    return AccountBiz.getMyRedCouponOutDate("0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return AccountBiz.getMyRedCouponOutDate(pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                    }
                }
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)){
                    AlertUtil.t(getActivity(), error);
                }
                pullToRefreshListView.onRefreshComplete();
            }
        };
        if (first) {
            task.setWaitingView(getWaitingView());
        }

        task.execute();

    }

    class RedPacketOutDataListAdapter extends BaseAdapter {

        private List<MyRedPacketOutDateModel> list = new ArrayList<>();
        private Context context;

        public RedPacketOutDataListAdapter(Context context) {
            this.context = context;
        }

        public void addItem(MyRedPacketOutDateModel cellOptions) {
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
        public MyRedPacketOutDateModel getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            OutDataListViewHolder viewHolder;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.item_my_red_packet_out_of_data,
                        parent, false);
                viewHolder = new OutDataListViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (OutDataListViewHolder) convertView.getTag();
            }

            // list设置数据

            viewHolder.tv_item_red_out_data_amount.setText(list.get(position).getACQUISITION_AMOUNT());
            viewHolder.tv_item_red_out_data_data.setText(list.get(position).getEND_DATE());
            viewHolder.tv_item_red_out_data_from.setText(list.get(position).getCOUPON_ORIGIN_AVENUE());

            return convertView;
        }

        class OutDataListViewHolder {

            TextView tv_item_red_out_data_amount;// 金额
            TextView tv_item_red_out_data_data;// 有效日期
            TextView tv_item_red_out_data_from;// 来源

            public OutDataListViewHolder(View itemView) {
                tv_item_red_out_data_amount = (TextView) itemView.findViewById(R.id.tv_item_red_out_data_amount);
                tv_item_red_out_data_data = (TextView) itemView.findViewById(R.id.tv_item_red_out_data_data);
                tv_item_red_out_data_from = (TextView) itemView.findViewById(R.id.tv_item_red_out_data_from);

            }

        }

    }
}
