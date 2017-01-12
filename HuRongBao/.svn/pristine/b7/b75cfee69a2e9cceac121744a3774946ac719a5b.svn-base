package com.hrb.ui.account;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.R;
import com.hrb.ui.base.BaseActivity;

/**
 * Created by Ls on 2016/10/13.
 */
public class ActivityMyInvest extends BaseActivity implements View.OnClickListener {

    private ViewPager my_invest_vp;
    private TabLayout my_invest_tab;

    private String[] titles = {"持有中", "投标中", "已回款"};
    private FragmentMyInvestBid fragmentMyInvestBid;
    private FragmentMyInvestHold fragmentMyInvestHold;
    private FragmentMyInvestPayment fragmentMyInvestPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_invest);

        initView();
    }

    private void initView() {
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        my_invest_vp = (ViewPager) findViewById(R.id.my_invest_vp);
        my_invest_tab = (TabLayout) findViewById(R.id.my_invest_tab);

        tv_title.setText("我的投资");
        iv_back.setOnClickListener(this);
        my_invest_vp.setAdapter(new MyInvestAdapter(getSupportFragmentManager(), titles));
        my_invest_tab.setupWithViewPager(my_invest_vp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public class MyInvestAdapter extends FragmentPagerAdapter {

        private String[] titles;

        public MyInvestAdapter(FragmentManager fm, String[] titles) {
            super(fm);
            this.titles = titles;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (fragmentMyInvestHold == null) {
                        fragmentMyInvestHold = new FragmentMyInvestHold();
                    }
                    return fragmentMyInvestHold;
                case 1:
                    if (fragmentMyInvestBid == null) {
                        fragmentMyInvestBid = new FragmentMyInvestBid();
                    }
                    return fragmentMyInvestBid;

                case 2:
                    if (fragmentMyInvestPayment == null) {
                        fragmentMyInvestPayment = new FragmentMyInvestPayment();
                    }
                    return fragmentMyInvestPayment;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
