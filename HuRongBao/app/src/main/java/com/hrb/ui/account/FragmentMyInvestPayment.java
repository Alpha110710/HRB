package com.hrb.ui.account;

import android.content.Context;
import android.content.Intent;
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
import com.hrb.model.MyInvestPaymentModel;
import com.hrb.ui.base.BaseFragment;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/13.
 */

public class FragmentMyInvestPayment extends BaseFragment {

    private PullToRefreshListView pullToRefreshListView;
    private InvestListPaymentAdapter adapter;

    private int pageIndex = 0;
    private boolean isEnd = false;
    private TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_invest_payment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.list_my_invest_payment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {

        adapter = new InvestListPaymentAdapter(getActivity());
        pullToRefreshListView.getRefreshableView().setAdapter(adapter);

        // 刷新方法
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                getMyInvestPayment(false, true);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                getMyInvestPayment(false, false);
            }

        });

        tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");

        getMyInvestPayment(true, true);
    }

    private void getMyInvestPayment(final boolean first, final boolean isPullDown) {

        BizDataAsyncTask<List<BaseModel>> getMyInvestListTask = new BizDataAsyncTask<List<BaseModel>>() {

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
                    adapter.addItem((MyInvestPaymentModel) result.get(i));
                }
                adapter.notifyDataSetChanged();

                pullToRefreshListView.onRefreshComplete();

            }

            @Override
            protected List<BaseModel> doExecute() throws ZYException, BizFailure {

                if (isPullDown) {

                    return AccountBiz.getMyInvestList("6", "0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return AccountBiz.getMyInvestList("6", pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                    }
                }
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
            getMyInvestListTask.setWaitingView(getWaitingView());
        }

        getMyInvestListTask.execute();

    }

    class InvestListPaymentAdapter extends BaseAdapter {

        private List<MyInvestPaymentModel> list = new ArrayList<>();
        private Context context;

        public InvestListPaymentAdapter(Context context) {

            this.context = context;
        }

        public void addItem(MyInvestPaymentModel cellOptions) {
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
        public MyInvestPaymentModel getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            PaymentViewHolder viewHolder;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.item_my_invest_payment, parent,
                        false);
                viewHolder = new PaymentViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (PaymentViewHolder) convertView.getTag();
            }

            viewHolder.item_payment_title_tv.setText(list.get(position).getPRODUCTS_TITLE());
            viewHolder.item_payment_invest_amount_tv.setText(list.get(position).getTENDER_AMOUNT());
            viewHolder.item_payment_had_collect_tv.setText(list.get(position).getRECOVER_AMOUNT_TOTAL());
            viewHolder.item_payment_collect_profit_tv.setText(list.get(position).getRECOVER_AMOUNT_INTEREST_YES());


            final int pos = position;
            viewHolder.item_payment_detail_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

        class PaymentViewHolder {

            private final TextView item_payment_collect_profit_tv;
            private final TextView item_payment_detail_tv;
            private final TextView item_payment_had_collect_tv;
            private final TextView item_payment_invest_amount_tv;
            private final TextView item_payment_title_tv;

            public PaymentViewHolder(View itemView) {
                item_payment_collect_profit_tv = (TextView) itemView.findViewById(R.id.item_payment_collect_profit_tv);
                item_payment_detail_tv = (TextView) itemView.findViewById(R.id.item_payment_detail_tv);
                item_payment_had_collect_tv = (TextView) itemView.findViewById(R.id.item_payment_had_collect_tv);
                item_payment_invest_amount_tv = (TextView) itemView.findViewById(R.id.item_payment_invest_amount_tv);
                item_payment_title_tv = (TextView) itemView.findViewById(R.id.item_payment_title_tv);

            }

        }

    }
}
