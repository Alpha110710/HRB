package com.hrb.ui.finance;

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
import com.hrb.biz.FinanceBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.InvestRecordModel;
import com.hrb.ui.base.BaseFragment;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/20.
 */

public class FragmentInvestRecord extends BaseFragment {

    private PullToRefreshListView pullToRefreshListView;
    private InvestRecordListAdapter adapter;

    private int pageIndex = 0;
    private boolean isEnd = false;
    private TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_invest_record, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.invest_record_list);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {

        adapter = new InvestRecordListAdapter(getActivity());
        pullToRefreshListView.getRefreshableView().setAdapter(adapter);

        // 刷新方法
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getInvestRecord(false, true);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getInvestRecord(false, false);
            }

        });

        tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");
        getInvestRecord(true, true);
    }

    private void getInvestRecord(final boolean first, final boolean isPullDown) {
        BizDataAsyncTask<List<InvestRecordModel>> getInvestors = new BizDataAsyncTask<List<InvestRecordModel>>() {
            @Override
            protected List<InvestRecordModel> doExecute() throws ZYException, BizFailure {

                if (isPullDown) {
                    return FinanceBiz.getInvestors(((ActivityInvestDetailMore) getActivity()).getBorrowId(), "0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return FinanceBiz.getInvestors(((ActivityInvestDetailMore) getActivity()).getBorrowId(),
                                pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                    }
                }

            }

            @Override
            protected void onExecuteSucceeded(List<InvestRecordModel> investRecordModels) {
                if (investRecordModels.size() < ExtraConfig.DEFAULT_PAGE_COUNT) {
                    isEnd = true;
                    if (isPullDown && investRecordModels.size() == 0) {
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

                for (int i = 0; i < investRecordModels.size(); i++) {
                    adapter.addItem(investRecordModels.get(i));
                }
                adapter.notifyDataSetChanged();

                pullToRefreshListView.onRefreshComplete();
            }

            @Override
            protected void OnExecuteFailed(String error) {
                pullToRefreshListView.onRefreshComplete();
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(getActivity(), error);
                }
            }
        };

        if (first) {
            getInvestors.setWaitingView(getWaitingView());
        }

        getInvestors.execute();
    }

    public class InvestRecordListAdapter extends BaseAdapter {

        private Context context;
        private List<InvestRecordModel> list = new ArrayList<>();

        public InvestRecordListAdapter(Context context) {
            this.context = context;
        }

        public List<InvestRecordModel> getList() {
            return list;
        }

        // 添加数据
        public void addItem(InvestRecordModel investRecordModel) {
            list.add(investRecordModel);
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
            InvestRecordListViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_invest_record, parent, false);
                viewHolder = new InvestRecordListViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (InvestRecordListViewHolder) convertView.getTag();
            }

            viewHolder.item_invest_record_tele_num_tv.setText(list.get(position).getUSER_PHONE());
            viewHolder.item_invest_record_amount_tv.setText(list.get(position).getTENDER_AMOUNT_FORMAT());
            viewHolder.item_invest_record_time_tv.setText(list.get(position).getTENDER_DATE());

            return convertView;
        }

        public class InvestRecordListViewHolder {

            private final TextView item_invest_record_time_tv;
            private final TextView item_invest_record_amount_tv;
            private final TextView item_invest_record_tele_num_tv;

            public InvestRecordListViewHolder(View itemView) {
                item_invest_record_time_tv = (TextView) itemView.findViewById(R.id.item_invest_record_time_tv);
                item_invest_record_amount_tv = (TextView) itemView.findViewById(R.id.item_invest_record_amount_tv);
                item_invest_record_tele_num_tv = (TextView) itemView.findViewById(R.id.item_invest_record_tele_num_tv);

            }
        }

    }
}
