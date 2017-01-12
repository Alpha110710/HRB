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
import com.hrb.model.UsablePointListModel;
import com.hrb.model.UsablePointModel;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/20.
 */

public class ActivityIntegral extends BaseActivity implements View.OnClickListener {

    private PullToRefreshListView pullToRefreshListView;
    private TextView integral_usable_tv;
    private IntegralListAdapter adapter;

    private int pageIndex = 0;
    private boolean isEnd = false;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);
        initView();
        init();

    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.integral_list);
        integral_usable_tv = (TextView) findViewById(R.id.integral_usable_tv);

        iv_back.setOnClickListener(this);
        tv_title.setText("积分");
        getUsablePoint();
    }

    private void getUsablePoint() {
        BizDataAsyncTask<UsablePointModel> getUsablePoint = new BizDataAsyncTask<UsablePointModel>(getWaitingView()) {
            @Override
            protected UsablePointModel doExecute() throws ZYException, BizFailure {
                return AccountBiz.getUsablePoint();
            }

            @Override
            protected void onExecuteSucceeded(UsablePointModel usablePointModel) {
                integral_usable_tv.setText(usablePointModel.getUSABLE_POINT());
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityIntegral.this, error);
                }
            }
        };
        getUsablePoint.execute();
    }

    private void init() {
        adapter = new IntegralListAdapter(this);

        pullToRefreshListView.getRefreshableView().setAdapter(adapter);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getData(false, true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getData(false, false);
            }
        });

        tv = new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");

        getData(true, true);
    }

    private void getData(final boolean first, final boolean isPullDown) {
        BizDataAsyncTask<List<UsablePointListModel>> task = new BizDataAsyncTask<List<UsablePointListModel>>() {

            @Override
            protected void onExecuteSucceeded(List<UsablePointListModel> result) {

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
            protected List<UsablePointListModel> doExecute() throws ZYException, BizFailure {
                if (isPullDown) {

                    return AccountBiz.getPoint("0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return AccountBiz.getPoint(pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                    }
                }
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityIntegral.this, error);
                }
                pullToRefreshListView.onRefreshComplete();
            }
        };

        if (first) {
            task.setWaitingView(getWaitingView());
        }

        task.execute();

    }

    class IntegralListAdapter extends BaseAdapter {

        private List<UsablePointListModel> list = new ArrayList<>();
        private Context context;

        public IntegralListAdapter(Context context) {
            this.context = context;
        }

        public void addItem(UsablePointListModel cellOptions) {
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
        public UsablePointListModel getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            IntegralListViewHolder viewHolder;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.item_inegral,
                        parent, false);
                viewHolder = new IntegralListViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (IntegralListViewHolder) convertView.getTag();
            }

            viewHolder.item_integral_data_tv.setText(list.get(position).getPUBLISH_DATE());
            viewHolder.item_integral_title_tv.setText(list.get(position).getPOINT_DESCRIPTION());
            viewHolder.item_integral_num_tv.setText(list.get(position).getPOINT());

            // list设置数据

            return convertView;
        }

        class IntegralListViewHolder {

            private final TextView item_integral_title_tv;
            private final TextView item_integral_num_tv;
            private final TextView item_integral_data_tv;

            public IntegralListViewHolder(View itemView) {
                item_integral_title_tv = (TextView) itemView.findViewById(R.id.item_integral_title_tv);
                item_integral_data_tv = (TextView) itemView.findViewById(R.id.item_integral_data_tv);
                item_integral_num_tv = (TextView) itemView.findViewById(R.id.item_integral_num_tv);
            }

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
