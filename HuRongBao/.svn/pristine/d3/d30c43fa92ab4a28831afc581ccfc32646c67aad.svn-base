package com.hrb.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.hrb.model.MyInvestHoldModel;
import com.hrb.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import static com.hrb.R.id.list_my_invest_hold;

/**
 * Created by Ls on 2016/10/13.
 */

public class FragmentMyInvestHold extends BaseFragment {

    private PullToRefreshListView pullToRefreshListView;
    private InvestListHoldAdapter adapter;

    private int pageIndex = 0;
    private boolean isEnd = false;
    private TextView tv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_invest_hold, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(list_my_invest_hold);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {

        adapter = new InvestListHoldAdapter(getActivity());
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

        tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");

        getMyInvestHold(true, true);
    }

    private BizDataAsyncTask<List<BaseModel>> getMyInvestListTask;

    private void getMyInvestHold(final boolean first, final boolean isPullDown) {
        Log.e("FragmentMyInvestHold", "获取数据");

        getMyInvestListTask = new BizDataAsyncTask<List<BaseModel>>() {

            @Override
            protected void onExecuteSucceeded(List<BaseModel> result) {

                Log.e("FragmentMyInvestHold", "得到的数据为" + result.toString());

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
                    adapter.addItem((MyInvestHoldModel) result.get(i));
                }
                adapter.notifyDataSetChanged();

                pullToRefreshListView.onRefreshComplete();

            }

            @Override
            protected List<BaseModel> doExecute() throws ZYException, BizFailure {

                if (isPullDown) {

                    return AccountBiz.getMyInvestList("1", "0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return AccountBiz.getMyInvestList("1", pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
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

    class InvestListHoldAdapter extends BaseAdapter {

        private List<MyInvestHoldModel> list = new ArrayList<>();
        private Context context;

        public InvestListHoldAdapter(Context context) {

            this.context = context;
        }

        public void addItem(MyInvestHoldModel cellOptions) {
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
        public MyInvestHoldModel getItem(int position) {

            return list.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            HoldViewHolder viewHolder;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.item_my_invest_hold, parent,
                        false);
                viewHolder = new HoldViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (HoldViewHolder) convertView.getTag();
            }


            viewHolder.item_hold_invest_amount_tv.setText(list.get(position).getTENDER_AMOUNT());
            viewHolder.item_hold_had_collect_tv.setText(list.get(position).getRECOVER_AMOUNT_TOTAL_YES());
            viewHolder.item_hold_wait_collect_tv.setText(list.get(position).getRECOVER_AMOUNT_TOTAL());

            if (list.get(position).getTENDER_FROM().equals("1")) {
                //非债权
                viewHolder.item_hold_title_tv.setText(list.get(position).getPRODUCTS_TITLE());
            } else {
                viewHolder.item_hold_title_tv.setText(list.get(position).getTRANSFER_CONTRACT_ID());
            }

            final int pos = position;
            viewHolder.item_hold_detail_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 点击跳转到回款详情 需要有一个标记 确认跳转到回款详情--债权
                    // 非债权
                    Intent intent = new Intent(getActivity(), ActivityReturnMoneyDetail.class);
                    intent.putExtra(ExtraConfig.IntentExtraKey.RETURN_MONEY_DETAIL_ONE_ID,
                            list.get(pos).getOID_TENDER_ID());
                    intent.putExtra(ExtraConfig.IntentExtraKey.RETURN_MONEY_DETAIL_ONE_TYPR,
                            list.get(pos).getTENDER_FROM());

                    startActivity(intent);

                }
            });


            return convertView;
        }

        class HoldViewHolder {

            private final TextView item_hold_detail_tv;
            private final TextView item_hold_had_collect_tv;
            private final TextView item_hold_invest_amount_tv;
            private final TextView item_hold_title_tv;
            private final TextView item_hold_wait_collect_tv;

            public HoldViewHolder(View itemView) {
                item_hold_detail_tv = (TextView) itemView.findViewById(R.id.item_hold_detail_tv);
                item_hold_had_collect_tv = (TextView) itemView.findViewById(R.id.item_hold_had_collect_tv);
                item_hold_invest_amount_tv = (TextView) itemView.findViewById(R.id.item_hold_invest_amount_tv);
                item_hold_title_tv = (TextView) itemView.findViewById(R.id.item_hold_title_tv);
                item_hold_wait_collect_tv = (TextView) itemView.findViewById(R.id.item_hold_wait_collect_tv);
            }

        }

    }
}
