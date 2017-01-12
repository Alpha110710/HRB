package com.hrb.ui.finance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.HuRongBaoApp;
import com.hrb.R;
import com.hrb.ui.base.BaseFragment;

/**
 * Created by Ls on 2016/10/9.
 */

public class FragmentFinance extends BaseFragment {

    private TabLayout tab_finance;
    private ViewPager vp_finance;

    private String[] titles = {"投资专区", "债权转让"};
    private FragmentInvestTopic fragmentInvestTopic;
    private FragmentZqTransfer fragmentZqTransfer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_finance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView iv_back = (ImageView) view.findViewById(R.id.iv_back);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tab_finance = (TabLayout) view.findViewById(R.id.tab_finance);
        vp_finance = (ViewPager) view.findViewById(R.id.vp_finance);

        tv_title.setText("投资列表");
        iv_back.setVisibility(View.GONE);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vp_finance.setAdapter(new FinanceAdapter(getChildFragmentManager(), titles));
        tab_finance.setupWithViewPager(vp_finance);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (HuRongBaoApp.globalIndex == 1 && HuRongBaoApp.goInvest == 1) {
            HuRongBaoApp.goInvest = 0;
            if (fragmentInvestTopic != null)
                fragmentInvestTopic.initHoldingList(true, true, "", "", "");
            if (fragmentZqTransfer != null)
                fragmentZqTransfer.getList(true, true);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden == false) {
            onResume();
        } else {
            onPause();
        }
    }

    public class FinanceAdapter extends FragmentPagerAdapter {

        private String[] titles;

        public FinanceAdapter(FragmentManager fm, String[] titles) {
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
                    if (fragmentInvestTopic == null) {
                        fragmentInvestTopic = new FragmentInvestTopic();
                    }
                    return fragmentInvestTopic;
                case 1:
                    if (fragmentZqTransfer == null) {
                        fragmentZqTransfer = new FragmentZqTransfer();
                    }
                    return fragmentZqTransfer;
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
