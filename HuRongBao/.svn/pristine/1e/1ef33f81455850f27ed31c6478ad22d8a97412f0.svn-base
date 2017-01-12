package com.hrb.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.hrb.model.MyAddRateCouponUnusedModel;
import com.hrb.ui.base.BaseFragment;
import com.hrb.ui.popupwindow.AddCouponGivePopup;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/24.
 */

public class FragmentMyAddRateCouponUnused extends BaseFragment implements View.OnClickListener {

    private PullToRefreshListView pullToRefreshListView;
    private RedPacketOutDataListAdapter adapter;

    private TextView tv_add_rate_unused_exchange;// 兑现
    private EditText et_add_rate_unused_code;// 兑换码

    private int pageIndex = 0;
    private boolean isEnd = false;
    private TextView tv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_add_rate_coupon_unused, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.list_add_rate_unused);
        et_add_rate_unused_code = (EditText) view.findViewById(R.id.et_add_rate_unused_code);
        tv_add_rate_unused_exchange = (TextView) view.findViewById(R.id.tv_add_rate_unused_exchange);

        tv_add_rate_unused_exchange.setOnClickListener(this);
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
                getMyAddRateCoupon(false, true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getMyAddRateCoupon(false, false);
            }
        });

        tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");

        getMyAddRateCoupon(true, true);
    }


    private void getMyAddRateCoupon(final boolean first, final boolean isPullDown) {
        BizDataAsyncTask<List<MyAddRateCouponUnusedModel>> task = new BizDataAsyncTask<List<MyAddRateCouponUnusedModel>>() {

            @Override
            protected void onExecuteSucceeded(List<MyAddRateCouponUnusedModel> result) {

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
            protected List<MyAddRateCouponUnusedModel> doExecute() throws ZYException, BizFailure {
                if (isPullDown) {

                    return AccountBiz.getMyAddCouponUnused("0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return AccountBiz.getMyAddCouponUnused(pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                    }
                }
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
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

    // 兑换码兑换任务
    private void interestRateExchange() {

        BizDataAsyncTask<String> changeTask = new BizDataAsyncTask<String>(getWaitingView()) {

            @Override
            protected void onExecuteSucceeded(String result) {
                if (result.trim().equals("1")) {
                    AlertUtil.t(getActivity(), "兑换成功");
                    et_add_rate_unused_code.setText("");
                } else {
                    AlertUtil.t(getActivity(), "兑换失败");
                }

                getMyAddRateCoupon(true, true);
            }

            @Override
            protected String doExecute() throws ZYException, BizFailure {

                return AccountBiz.interestRateExchange(et_add_rate_unused_code.getText().toString().trim());
            }

            @Override
            protected void OnExecuteFailed(String error) {

                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(getActivity(), error);
                }
            }
        };

        changeTask.execute();
    }

    class RedPacketOutDataListAdapter extends BaseAdapter {

        private List<MyAddRateCouponUnusedModel> list = new ArrayList<>();
        private Context context;

        public RedPacketOutDataListAdapter(Context context) {
            this.context = context;
        }

        public void addItem(MyAddRateCouponUnusedModel cellOptions) {
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
        public MyAddRateCouponUnusedModel getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            OutDataListViewHolder viewHolder;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.item_my_add_coupon_red,
                        parent, false);
                viewHolder = new OutDataListViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (OutDataListViewHolder) convertView.getTag();
            }

            // list设置数据
            viewHolder.tv_item_red_unused_amount.setText(list.get(position).getRATE_COUPON_POSITION().replace("%", ""));
            viewHolder.tv_item_red_unused_amount_unit.setText("%");
            viewHolder.tv_item_red_unused_data.setText(list.get(position).getEFFECTIVE_DATE());
            viewHolder.tv_item_red_unused_min_amount.setText("投资" + list.get(position).getMIN_TENDER_AMOUNT() + "可用");
            viewHolder.tv_item_red_unused_origin.setText(list.get(position).getRATE_COUPON_SEND_TYPE_NAME());


            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddCouponGivePopup popup = new AddCouponGivePopup(getActivity(), list.get(position));
                    popup.showAsDropDown(((ActivityMyAddRateCoupon) getActivity()).getView());
                    popup.setRefreshData(new AddCouponGivePopup.RefreshData() {
                        @Override
                        public void refreshListener() {
                            getMyAddRateCoupon(true, true);
                        }
                    });

                }
            });

            return convertView;
        }

        class OutDataListViewHolder {

            private final TextView tv_item_red_unused_origin;
            private final TextView tv_item_red_unused_amount_unit;
            private final TextView tv_item_red_unused_min_amount;
            private final TextView tv_item_red_unused_condition;
            private final TextView tv_item_red_unused_data;// 有效期
            private final TextView tv_item_red_unused_amount;// 金额
            private final LinearLayout layout;

            public OutDataListViewHolder(View itemView) {
                tv_item_red_unused_data = (TextView) itemView.findViewById(R.id.tv_item_red_unused_data);
                tv_item_red_unused_amount = (TextView) itemView.findViewById(R.id.tv_item_red_unused_amount);
                tv_item_red_unused_origin = (TextView) itemView.findViewById(R.id.tv_item_red_unused_origin);
                tv_item_red_unused_condition = (TextView) itemView.findViewById(R.id.tv_item_red_unused_condition);
                tv_item_red_unused_amount_unit = (TextView) itemView.findViewById(R.id.tv_item_red_unused_amount_unit);
                tv_item_red_unused_min_amount = (TextView) itemView.findViewById(R.id.tv_item_red_unused_min_amount);
                layout = (LinearLayout) itemView.findViewById(R.id.layout);

            }

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_rate_unused_exchange:
                // 兑换
                if (StringUtil.isEmpty(et_add_rate_unused_code.getText().toString().trim())) {
                    AlertUtil.t(getActivity(), "请输入兑换码");
                    return;
                }
                interestRateExchange();

                break;

            default:
                break;
        }
    }
}
