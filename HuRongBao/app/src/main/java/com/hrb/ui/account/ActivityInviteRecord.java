package com.hrb.ui.account;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hrb.R;
import com.hrb.ui.base.BaseActivity;

/**
 * Created by Ls on 2017/1/11.
 */

public class ActivityInviteRecord extends BaseActivity{

    private PullToRefreshListView list_invite_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_record);
        initView();
    }

    private void initView() {
        list_invite_record = (PullToRefreshListView) findViewById(R.id.list_invite_record);
    }

}
