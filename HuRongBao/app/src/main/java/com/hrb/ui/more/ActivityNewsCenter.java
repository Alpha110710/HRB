package com.hrb.ui.more;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.biz.MoreBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.NewsListModel;
import com.hrb.ui.base.BaseActivity;
import com.hrb.ui.init.ActivityWebView;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/17.
 */

public class ActivityNewsCenter extends BaseActivity implements View.OnClickListener {

    private TextView tv_title;
    private ImageView iv_back;
    private PullToRefreshListView pullToRefreshListView;
    private NewsCenterListAdapter adapter;

    private int pageIndex = 0;
    private boolean isEnd = false;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_center);
        initView();
        init();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.news_center_list);

        iv_back.setOnClickListener(this);
        tv_title.setText("新闻中心");
    }


    private void init() {

        adapter = new NewsCenterListAdapter(this);
        pullToRefreshListView.getRefreshableView().setAdapter(adapter);

        // 刷新方法
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                getNewsCenterData(false, true);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                getNewsCenterData(false, false);
            }

        });

        tv = new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");

        getNewsCenterData(true, true);
    }

    private void getNewsCenterData(final boolean first, final boolean isPullDown) {

        BizDataAsyncTask<List<NewsListModel>> getData = new BizDataAsyncTask<List<NewsListModel>>() {

            @Override
            protected void onExecuteSucceeded(List<NewsListModel> result) {

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
                    adapter.addItem((NewsListModel) result.get(i));
                }
                adapter.notifyDataSetChanged();

                pullToRefreshListView.onRefreshComplete();

            }

            @Override
            protected List<NewsListModel> doExecute() throws ZYException, BizFailure {
                if (isPullDown) {

                    return MoreBiz.getNoticeList("0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return MoreBiz.getNoticeList(pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                    }
                }
            }

            @Override
            protected void OnExecuteFailed(String error) {
                pullToRefreshListView.onRefreshComplete();
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityNewsCenter.this, error);
                }
            }
        };

        if (first) {
            getData.setWaitingView(getWaitingView());
        }

        getData.execute();

    }

    class NewsCenterListAdapter extends BaseAdapter {

        private List<NewsListModel> list = new ArrayList<>();
        private Context context;

        public NewsCenterListAdapter(Context context) {

            this.context = context;
        }

        public void addItem(NewsListModel cellOptions) {
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
        public NewsListModel getItem(int position) {

            return list.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            NewsCenterViewHolder viewHolder;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.item_news_center, parent,
                        false);
                viewHolder = new NewsCenterViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (NewsCenterViewHolder) convertView.getTag();
            }

            viewHolder.item_news_content_tv.setText(list.get(position).getTITLE_SUMMARY());
            viewHolder.item_news_title_tv.setText(list.get(position).getTITLE());
            ImageLoader.getInstance().displayImage(
                    (list.get(position)).getIMG_PATH(), viewHolder.item_news_iv);
            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ActivityNewsCenter.this, ActivityWebView.class);
                    intent.putExtra("LINKURL", list.get(position).getID());
                    intent.putExtra(ExtraConfig.IntentExtraKey.WEB_VIEW_FROM, 5);
                    startActivity(intent);

                }
            });

            return convertView;
        }

        class NewsCenterViewHolder {

            private final TextView item_news_content_tv;
            private final ImageView item_news_iv;
            private final TextView item_news_title_tv;
            private final LinearLayout layout;

            public NewsCenterViewHolder(View itemView) {
                item_news_content_tv = (TextView) itemView.findViewById(R.id.item_news_content_tv);
                item_news_iv = (ImageView) itemView.findViewById(R.id.item_news_iv);
                item_news_title_tv = (TextView) itemView.findViewById(R.id.item_news_title_tv);
                layout = (LinearLayout) itemView.findViewById(R.id.layout);

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

