package com.hrb.ui.account;

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
import com.hrb.biz.AccountBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.PersonalProfitLossDetailModel;
import com.hrb.model.PersonalProfitLossModel;
import com.hrb.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/14.
 */

public class ActivityPersonalProfitLoss extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_title;
    private PullToRefreshListView pullToRefreshListView;
    private TextView personal_expenditure_total_tv;
    private TextView personal_profit_total_tv;

    private PersonalProfitLossAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profit_loss);

        initView();
        init();
        getData();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.personal_profit_loss_list);
        personal_expenditure_total_tv = (TextView) findViewById(R.id.personal_expenditure_total_tv);
        personal_profit_total_tv = (TextView) findViewById(R.id.personal_profit_total_tv);

        tv_title.setText("个人损益");
        iv_back.setOnClickListener(this);
    }


    private void init() {

        adapter = new PersonalProfitLossAdapter(this);
        pullToRefreshListView.getRefreshableView().setAdapter(adapter);

        // 刷新方法
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub

                getListData(false, true);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                getListData(false, false);
            }

        });

        tv = new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");

        getListData(true, true);
    }

    private int pageIndex = 0;
    private boolean isEnd = false;
    private TextView tv;

    private BizDataAsyncTask<List<PersonalProfitLossDetailModel>> task;

    private void getListData(final boolean first, final boolean isPullDown) {

        task = new BizDataAsyncTask<List<PersonalProfitLossDetailModel>>(getWaitingView()) {

            @Override
            protected void onExecuteSucceeded(List<PersonalProfitLossDetailModel> result) {
                // TODO Auto-generated method stub

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
            protected List<PersonalProfitLossDetailModel> doExecute() throws ZYException, BizFailure {
                // TODO Auto-generated method stub
                if (isPullDown) {

                    return AccountBiz.getProfitAndLossList("0", "0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return AccountBiz.getProfitAndLossList("0", pageIndex + "",
                                ExtraConfig.DEFAULT_PAGE_COUNT + "");
                    }
                }
            }

            @Override
            protected void OnExecuteFailed(String error) {
                // TODO Auto-generated method stub
                pullToRefreshListView.onRefreshComplete();
            }
        };

        if (first) {
            task.setWaitingView(getWaitingView());
        }

        task.execute();

    }

    private BizDataAsyncTask<PersonalProfitLossModel> task2;

    private void getData() {
        task2 = new BizDataAsyncTask<PersonalProfitLossModel>(getWaitingView()) {

            @Override
            protected void onExecuteSucceeded(PersonalProfitLossModel result) {
                personal_expenditure_total_tv.setText(result.getLOSS_AMOUNT());
                personal_profit_total_tv.setText(result.getPROFIT_AMOUNT());
            }

            @Override
            protected PersonalProfitLossModel doExecute() throws ZYException, BizFailure {
                return AccountBiz.getUserProfitAndLosses();
            }

            @Override
            protected void OnExecuteFailed(String error) {

            }
        };

        task2.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            default:
                break;
        }
    }

    class PersonalProfitLossAdapter extends BaseAdapter {

        private List<PersonalProfitLossDetailModel> list = new ArrayList<>();
        private Context context;

        public PersonalProfitLossAdapter(Context context) {
            // TODO Auto-generated constructor stub
            this.context = context;
        }

        public void addItem(PersonalProfitLossDetailModel cellOptions) {
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
            // TODO Auto-generated method stub
            return list == null ? 0 : list.size();
        }

        @Override
        public PersonalProfitLossDetailModel getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HoldListViewHolder viewHolder;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.item_personal_profit_loss, parent,
                        false);
                viewHolder = new HoldListViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (HoldListViewHolder) convertView.getTag();
            }

            // list设置数据

            if (list.get(position).getREVENUE_EXPEND_TYPE().equals("R")) {
                viewHolder.item_personal_profit_amount.setTextColor(getResources().getColor(R.color.font_orange));
            } else {
                viewHolder.item_personal_profit_amount.setTextColor(getResources().getColor(R.color.red_light));
            }

            viewHolder.item_personal_profit_award.setText(list.get(position).getFUND_TYPE());
            viewHolder.item_personal_profit_amount.setText(list.get(position).getAMOUNT());
            viewHolder.item_personal_profit_data.setText(list.get(position).getDATE());

            return convertView;
        }

        class HoldListViewHolder {

            TextView item_personal_profit_award;// 投资奖励
            TextView item_personal_profit_amount;// 投资奖励金额
            TextView item_personal_profit_data;// 日期

            public HoldListViewHolder(View itemView) {
                item_personal_profit_award = (TextView) itemView.findViewById(R.id.item_personal_profit_award);
                item_personal_profit_amount = (TextView) itemView.findViewById(R.id.item_personal_profit_amount);
                item_personal_profit_data = (TextView) itemView.findViewById(R.id.item_personal_profit_data);

            }

        }

    }


}
