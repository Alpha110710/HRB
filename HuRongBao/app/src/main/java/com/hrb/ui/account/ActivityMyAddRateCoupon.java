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


/**
 * 我的加息券
 *
 * @author Ls
 */
public class ActivityMyAddRateCoupon extends BaseActivity implements OnClickListener {

    private TabLayout tabs;
    private ViewPager pager;

    private FragmentMyAddRateCouponUnused unusedFrag;
    private FragmentMyAddRateCouponUsed usedFrag;
    private FragmentMyAddRateCouponOutData outDateFrag;
    private FragmentMyAddRateCouponPresented presentedFrag;
    private String[] titles = {"未使用", "已使用", "已过期", "已赠送"};

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_my_add_rate_coupon);
        pager = (ViewPager) findViewById(R.id.pager_add_rate_coupon);
        tabs = (TabLayout) findViewById(R.id.tabs_add_rate_coupon);
        view = findViewById(R.id.view);

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);

        tv_title.setText("我的加息券");
        iv_back.setOnClickListener(this);

        pager.setAdapter(new MyAdapter(getSupportFragmentManager(), titles));
        tabs.setupWithViewPager(pager);

    }

    public View getView() {
        return view;
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
                    if (unusedFrag == null) {
                        unusedFrag = new FragmentMyAddRateCouponUnused();
                    }
                    return unusedFrag;
                case 1:
                    if (usedFrag == null) {
                        usedFrag = new FragmentMyAddRateCouponUsed();
                    }
                    return usedFrag;
                case 2:
                    if (outDateFrag == null) {
                        outDateFrag = new FragmentMyAddRateCouponOutData();
                    }
                    return outDateFrag;
                case 3:
                    if (presentedFrag == null) {
                        presentedFrag = new FragmentMyAddRateCouponPresented();
                    }
                    return presentedFrag;
                default:
                    return null;
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

}