package com.hrb.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.MessageModelList;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ActivityMessageCenter extends BaseActivity implements OnClickListener {

    private PullToRefreshListView pullToRefreshListView;
    private MessageListAdapter adapter;

    private int pageIndex = 0;
    private boolean isEnd = false;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);

        initView();

        initData();
    }

    private void initView() {

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.message_center_list);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);

        tv_title.setText("消息中心");
        iv_back.setOnClickListener(this);

    }

    private void initData() {
        adapter = new MessageListAdapter(this);
        pullToRefreshListView.setAdapter(adapter);

        pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getMessageList(false, true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getMessageList(false, false);
            }
        });

        tv = new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据");
        getMessageList(true, true);

    }

    private void getMessageList(final boolean first, final boolean isPullDown) {
        BizDataAsyncTask<List<MessageModelList>> task = new BizDataAsyncTask<List<MessageModelList>>() {

            @Override
            protected void onExecuteSucceeded(List<MessageModelList> result) {
                if (result.size() < ExtraConfig.DEFAULT_PAGE_COUNT) {
                    isEnd = true;
                    if (isPullDown && result.size() == 0) {
                        pullToRefreshListView.setEmptyView(tv);
                    }

                    pullToRefreshListView.setPullLabel("没有更多数据", Mode.PULL_FROM_END);
                    pullToRefreshListView.setReleaseLabel("没有更多数据", Mode.PULL_FROM_END);
                    pullToRefreshListView.setRefreshingLabel("没有更多数据", Mode.PULL_FROM_END);
                } else {
                    isEnd = false;
                    pullToRefreshListView.setPullLabel("上拉刷新", Mode.PULL_FROM_END);
                    pullToRefreshListView.setReleaseLabel("放开以刷新", Mode.PULL_FROM_END);
                    pullToRefreshListView.setRefreshingLabel("正在载入", Mode.PULL_FROM_END);
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
            protected List<MessageModelList> doExecute() throws ZYException, BizFailure {
                if (isPullDown) {

                    return AccountBiz.getMessageList("0", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                } else {
                    if (isEnd) {
                        return new ArrayList<>();
                    } else {
                        return AccountBiz.getMessageList(pageIndex + "", ExtraConfig.DEFAULT_PAGE_COUNT + "");
                    }
                }
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityMessageCenter.this, error);
                }
            }
        };

        if (first) {
            task.setWaitingView(getWaitingView());
        }

        task.execute();


    }

    class MessageListAdapter extends BaseAdapter {

        private List<MessageModelList> list = new ArrayList<>();
        private Context context;

        public MessageListAdapter(Context context) {
            super();
            this.context = context;
        }

        public void addItem(MessageModelList item) {
            list.add(item);
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
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_message_center, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_item_message_content.setText(list.get(position).getTITLE());
            if (list.get(position).getOPEN_FLG().trim().equals("0")) {
                holder.iv_item_message_point.setBackgroundResource(R.drawable.message_03);
                holder.tv_item_message_content.setTextColor(0xff000000);
            } else {
                holder.iv_item_message_point.setBackgroundResource(R.drawable.message_04);
                holder.tv_item_message_content.setTextColor(0xff333333);
            }

            holder.tv_item_message_time.setText(list.get(position).getINS_DATE());
            holder.tv_item_message_type.setText(list.get(position).getGROUP_NAME());

            // 点击行布局跳转
            holder.ll_item_message.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ActivityMessageCenter.this, ActivityMessageWatch.class);
                    intent.putExtra(ExtraConfig.IntentExtraKey.ACCOUNT_MSG, list.get(position));
                    startActivityForResult(intent, ExtraConfig.RequestCode.REQUEST_CODE_FOR_MESSAGE);

                }
            });

            return convertView;
        }

        class ViewHolder {

            private final TextView tv_item_message_type;
            private final TextView tv_item_message_time;
            private final LinearLayout ll_item_message;
            private ImageView iv_item_message_point;
            private TextView tv_item_message_content;

            public ViewHolder(View itemView) {
                iv_item_message_point = (ImageView) itemView.findViewById(R.id.iv_item_message_point);
                tv_item_message_content = (TextView) itemView.findViewById(R.id.tv_item_message_content);
                tv_item_message_type = (TextView) itemView.findViewById(R.id.tv_item_message_type);
                tv_item_message_time = (TextView) itemView.findViewById(R.id.tv_item_message_time);
                ll_item_message = (LinearLayout) itemView.findViewById(R.id.ll_item_message);
            }

        }

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

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == ExtraConfig.RequestCode.REQUEST_CODE_FOR_MESSAGE) {
            getMessageList(true, true);
        }
    }

}
