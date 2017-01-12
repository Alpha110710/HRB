package com.hrb.ui.more;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.R;
import com.hrb.biz.MoreBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.NewsModel;
import com.hrb.ui.base.BaseActivity;

/**
 * Created by Ls on 2016/10/17.
 * 已经不使用
 */

public class ActivityNewsCenterDetail extends BaseActivity implements View.OnClickListener {

    private String newsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_center_detail);
        newsId = getIntent().getStringExtra("newId");
        initView();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);

        iv_back.setOnClickListener(this);
        tv_title.setText("新闻中心");
    }


    private void getNewsInfo(){
        BizDataAsyncTask<NewsModel> getNewsInfo = new BizDataAsyncTask<NewsModel>(getWaitingView()) {
            @Override
            protected NewsModel doExecute() throws ZYException, BizFailure {
                return MoreBiz.getNewsInfo(newsId);
            }

            @Override
            protected void onExecuteSucceeded(NewsModel newsModel) {

            }

            @Override
            protected void OnExecuteFailed(String error) {

            }
        };
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

