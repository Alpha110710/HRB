package com.hrb.ui.finance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.hrb.model.TransferListModel;
import com.hrb.ui.base.BaseFragment;
import com.hrb.ui.widget.CircleProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/12.
 */

public class FragmentZqTransfer extends BaseFragment {

    private PullToRefreshListView pullToRefreshListView;
    private ZqListAdapter adapter;
    private TextView tv;
    private int pageIndex = 0;
    private boolean end = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zq_transfer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.zq_transfer_list);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ZqListAdapter(getActivity());
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.getRefreshableView().setAdapter(adapter);
        initData();
    }

    private void initData() {
        pullToRefreshListView.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getList().get(position - 1).getOID_TRANSFER_ID() == null) {
                    return;
                }
                if (adapter.getList().get(position - 1).getTRANSFER_FULL_STATUS() == null) {
                    return;
                }
                Intent intent = new Intent(getActivity(), ActivityZqDetail.class);
                intent.putExtra(ExtraConfig.IntentExtraKey.TRANSFER_ID, adapter.getList().get(position - 1).getOID_TRANSFER_ID());
                intent.putExtra(ExtraConfig.IntentExtraKey.TRANSFER_FULL_STATUS, adapter.getList().get(position - 1).getTRANSFER_FULL_STATUS());
                startActivity(intent);
            }
        });
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
        tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");
        getList(false, true);
    }

    public void getList(final boolean first, final boolean isPullDown) {

        BizDataAsyncTask<List<TransferListModel>> getList = new BizDataAsyncTask<List<TransferListModel>>() {
            @Override
            protected List<TransferListModel> doExecute() throws ZYException, BizFailure {
                if (isPullDown) {

                    return FinanceBiz.getTransferList(0 + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (end) {
                        return new ArrayList<>();
                    } else {
                        return FinanceBiz.getTransferList(pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                    }
                }
            }

            @SuppressWarnings("deprecation")
            @Override
            protected void onExecuteSucceeded(List<TransferListModel> transferListModels) {
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

    public class ZqListAdapter extends BaseAdapter {

        private Context context;
        private List<TransferListModel> list = new ArrayList<>();

        public ZqListAdapter(Context context) {
            this.context = context;
        }


        // 添加数据
        public void addItem(TransferListModel transferListModel) {
            list.add(transferListModel);
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
            ZqListViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_zq_transfer, parent, false);
                viewHolder = new ZqListViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ZqListViewHolder) convertView.getTag();
            }

            viewHolder.item_zq_transfer_title_tv.setText(list.get(position).getTRANSFER_CONTRACT_ID());
            viewHolder.item_zq_transfer_year_tv.setText(list.get(position).getFINANCE_INTEREST_RATE());
            viewHolder.item_zq_transfer_date_tv.setText(list.get(position).getTRANSFER_DAYS());
//            viewHolder.item_zq_transfer_date_unit_tv.setText(list.get(position).getINTEREST_RATE_TYPE());
            viewHolder.item_zq_transfer_discount_tv.setText(list.get(position).getDISCOUNT_SCALE() + "%");

            if ("立即投资".equals(list.get(position).getTRANSFER_STATUS_CONTENT())) {// 显示投资进度
                viewHolder.item_zq_transfer_ll.setVisibility(View.GONE);
                viewHolder.item_zq_transfer_pb.setVisibility(View.VISIBLE);
                viewHolder.item_zq_transfer_iv.setVisibility(View.GONE);
                viewHolder.item_zq_transfer_pb
                        .setProgress(Float.parseFloat(list.get(position).getTRANSFER_CAPITAL_SCALE()));
            } else if ("转让成功".equals(list.get(position).getTRANSFER_STATUS_CONTENT())) {// 根据状态值显示不同图片
                viewHolder.item_zq_transfer_pb.setVisibility(View.GONE);
                viewHolder.item_zq_transfer_ll.setVisibility(View.GONE);
                viewHolder.item_zq_transfer_iv.setVisibility(View.VISIBLE);
                viewHolder.item_zq_transfer_iv.setImageResource(R.drawable.transfer_list2_03);
            } else {
                viewHolder.item_zq_transfer_pb.setVisibility(View.GONE);
                viewHolder.item_zq_transfer_ll.setVisibility(View.GONE);
                viewHolder.item_zq_transfer_iv.setVisibility(View.VISIBLE);
                viewHolder.item_zq_transfer_iv.setImageResource(R.drawable.tranfer_list_03);
            }

            return convertView;
        }

        public List<TransferListModel> getList() {
            return list;
        }

        public class ZqListViewHolder {

            private final TextView item_zq_transfer_date_tv;
            private final ImageView item_zq_transfer_iv;
            private final LinearLayout item_zq_transfer_ll;
            private final TextView item_zq_transfer_date_unit_tv;
            private final TextView item_zq_transfer_discount_tv;
            private final CircleProgressBar item_zq_transfer_pb;
            private final TextView item_zq_transfer_title_tv;
            private final TextView item_zq_transfer_time_tv;
            private final TextView item_zq_transfer_year_tv;


            public ZqListViewHolder(View itemView) {

                item_zq_transfer_date_tv = (TextView) itemView.findViewById(R.id.item_zq_transfer_date_tv);
                item_zq_transfer_date_unit_tv = (TextView) itemView.findViewById(R.id.item_zq_transfer_date_unit_tv);
                item_zq_transfer_pb = (CircleProgressBar) itemView.findViewById(R.id.item_zq_transfer_pb);
                item_zq_transfer_title_tv = (TextView) itemView.findViewById(R.id.item_zq_transfer_title_tv);
                item_zq_transfer_iv = (ImageView) itemView.findViewById(R.id.item_zq_transfer_iv);
                item_zq_transfer_discount_tv = (TextView) itemView.findViewById(R.id.item_zq_transfer_discount_tv);
                item_zq_transfer_ll = (LinearLayout) itemView.findViewById(R.id.item_zq_transfer_ll);
                item_zq_transfer_time_tv = (TextView) itemView.findViewById(R.id.item_zq_transfer_time_tv);
                item_zq_transfer_year_tv = (TextView) itemView.findViewById(R.id.item_zq_transfer_year_tv);

            }
        }

    }
}
