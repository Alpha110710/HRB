package com.hrb.ui.account;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.R;
import com.hrb.ui.base.BaseActivity;

public class ActivityMyTransfer extends BaseActivity implements OnClickListener {


    private FragmentTransfer transfer;
    private FragmentTransfering transfering;
    private FragmentTransfered transfered;
    private String[] titles = {"可转让", "转让中", "已转让"};

    private TextView tv_title;
    private ImageView iv_back;

    private TabLayout my_transfer_tab;
    private ViewPager my_transfer_vp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_transfer);
        initView();
    }

    private void initView() {

        my_transfer_tab = (TabLayout) findViewById(R.id.my_transfer_tab);
        my_transfer_vp = (ViewPager) findViewById(R.id.my_transfer_vp);
        my_transfer_vp.setAdapter(new MyAdapter(getSupportFragmentManager(), titles));
        my_transfer_tab.setupWithViewPager(my_transfer_vp);

        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        tv_title.setText("我的转让");
        iv_back.setOnClickListener(this);

        if (transfer == null) {
            transfer = new FragmentTransfer();
        }


    }

    public class MyAdapter extends FragmentPagerAdapter {
        String[] _titles;

        public MyAdapter(FragmentManager fm, String[] titles) {
            super(fm);
            _titles = titles;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return _titles[position];
        }

        @Override
        public int getCount() {
            return _titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (transfer == null) {
                        transfer = new FragmentTransfer();
                    }
                    return transfer;
                case 1:
                    if (transfering == null) {
                        transfering = new FragmentTransfering();
                    }
                    return transfering;
                case 2:
                    if (transfered == null) {
                        transfered = new FragmentTransfered();
                    }
                    return transfered;
                default:
                    return null;
            }
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.iv_back:
                // 返回
                finish();
                break;
            default:
                break;

        }

    }

}
