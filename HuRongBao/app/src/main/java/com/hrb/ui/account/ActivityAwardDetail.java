package com.hrb.ui.account;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hrb.R;
import com.hrb.ui.base.BaseActivity;

/**
 * Created by Ls on 2017/1/11.
 */

public class ActivityAwardDetail extends BaseActivity {

    private PullToRefreshListView pullToRefreshListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_award_detail);
        initView();
    }

    private void initView() {
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list_award_detail);
    }


}
