package com.hrb.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.ui.popupwindow.RedPacketRulePopup;
import com.hrb.model.MyRedPacketUnusedModel;
import com.hrb.ui.base.BaseFragment;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/21.
 */

public class FragmentMyRedPackageUnused extends BaseFragment implements View.OnClickListener {

    private PullToRefreshListView pullToRefreshListView;
    private RedPacketUnusedListAdapter adapter;

    private TextView tv_red_unused_exchange;
    private EditText et_red_unused_exchange;

    private int pageIndex = 0;
    private boolean isEnd = false;
    private TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_red_packet_unused, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.list_red_unused);
        tv_red_unused_exchange = (TextView) view.findViewById(R.id.tv_red_unused_exchange);
        et_red_unused_exchange = (EditText) view.findViewById(R.id.et_red_unused_exchange);

        tv_red_unused_exchange.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

    }


    private void init() {
        adapter = new RedPacketUnusedListAdapter(getActivity());

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

        BizDataAsyncTask<List<MyRedPacketUnusedModel>> task = new BizDataAsyncTask<List<MyRedPacketUnusedModel>>() {

            @Override
            protected void onExecuteSucceeded(List<MyRedPacketUnusedModel> result) {

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
            protected List<MyRedPacketUnusedModel> doExecute() throws ZYException, BizFailure {
                if (isPullDown) {

                    return AccountBiz.getMyRedCouponUnused("0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return AccountBiz.getMyRedCouponUnused(pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
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
            task.setWaitingView(getWaitingView());
        }

        task.execute();

    }

    //兑换码兑换红包
    private void couponExchange() {
        BizDataAsyncTask<String> changeTask = new BizDataAsyncTask<String>(getWaitingView()) {

            @Override
            protected void onExecuteSucceeded(String result) {
                if (result.trim().equals("1")) {
                    AlertUtil.t(getActivity(), "兑换成功");
                } else {
                    AlertUtil.t(getActivity(), "兑换失败");
                }

                getMyRedPacket(true, true);
                et_red_unused_exchange.setText("");
            }

            @Override
            protected String doExecute() throws ZYException, BizFailure {

                return AccountBiz.couponExchange(et_red_unused_exchange.getText().toString().trim());
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

    class RedPacketUnusedListAdapter extends BaseAdapter {

        private List<MyRedPacketUnusedModel> list = new ArrayList<>();
        private Context context;

        public RedPacketUnusedListAdapter(Context context) {
            this.context = context;
        }

        public void addItem(MyRedPacketUnusedModel cellOptions) {
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
        public MyRedPacketUnusedModel getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            UnusedListViewHolder viewHolder;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.item_my_red_packet_red, parent,
                        false);
                viewHolder = new UnusedListViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (UnusedListViewHolder) convertView.getTag();
            }

            // list设置数据

            viewHolder.tv_item_red_unused_amount.setText(list.get(position).getOVERPLUS_AMOUNT().replace("元", ""));
            viewHolder.tv_item_red_unused_data.setText(list.get(position).getEND_DATE());
            viewHolder.tv_item_red_unused_min_amount.setText("投资" + list.get(position).getMINIMUM_TENDER_AMOUNT() + "可用");
            viewHolder.tv_item_red_unused_origin.setText(list.get(position).getRED_PACKET_TYPE_NAME());

            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RedPacketRulePopup popup = new RedPacketRulePopup(getActivity(), list.get(position));
                    popup.showAsDropDown(((ActivityMyRedPackage) getActivity()).getView());
                    popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            getMyRedPacket(true, true);
                        }
                    });
                }
            });

            return convertView;
        }

        class UnusedListViewHolder {

            private final TextView tv_item_red_unused_origin;
            private final TextView tv_item_red_unused_amount_unit;
            private final TextView tv_item_red_unused_min_amount;
            private final TextView tv_item_red_unused_condition;
            private final TextView tv_item_red_unused_data;// 有效期
            private final TextView tv_item_red_unused_amount;// 金额
            private final LinearLayout layout;

            public UnusedListViewHolder(View itemView) {
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
            case R.id.tv_red_unused_exchange:
                // 兑换
                if (StringUtil.isEmpty(et_red_unused_exchange.getText().toString().trim())) {
                    AlertUtil.t(getActivity(), "请输入兑换码");
                    return;
                }
                couponExchange();
                break;

            default:
                break;
        }
    }

}
