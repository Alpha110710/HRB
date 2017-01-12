package com.hrb.ui.finance;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.ui.base.BaseActivity;

/**
 * Created by Ls on 2016/10/20.
 */

public class ActivityInvestDetailMore extends BaseActivity implements View.OnClickListener {

    private Button invest_detail_more_btn;
    private ViewPager invest_detail_more_pager;
    private TabLayout invest_detail_more_tab;

    private FragmentProjectDetail fragmentProjectDetail;
    private FragmentLoanData fragmentLoanData;
    private FragmentInvestRecord fragmentInvestRecord;
    private String[] titles = {"项目详细", "借款资料", "投资记录"};
    private String borrowId, investFlg, EXP_FLG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest_detail_more);
        borrowId = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.PRODUCT_ID);
        investFlg = getIntent().getStringExtra(ExtraConfig.IntentExtraKey.FLG);
        EXP_FLG = getIntent().getStringExtra("EXP_FLG");

        initView();
    }

    private void initView() {
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);

        invest_detail_more_btn = (Button) findViewById(R.id.invest_detail_more_btn);
        invest_detail_more_pager = (ViewPager) findViewById(R.id.invest_detail_more_pager);
        invest_detail_more_tab = (TabLayout) findViewById(R.id.invest_detail_more_tab);

        invest_detail_more_pager.setAdapter(new InvestDetailMoreAdapter(getSupportFragmentManager(), titles));
        invest_detail_more_tab.setupWithViewPager(invest_detail_more_pager);
        tv_title.setText("投资详情");
        iv_back.setOnClickListener(this);
        invest_detail_more_btn.setOnClickListener(this);

        if (!"立即投资".equals(investFlg))//图标变灰色不可点击
        {
            invest_detail_more_btn.setBackground(getResources().getDrawable(R.drawable.shape_round_grey_button));
            invest_detail_more_btn.setClickable(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.invest_detail_more_btn:
                //立即投资
                Intent intent = new Intent(this, ActivityInvestDetailImmediately.class);
                intent.putExtra(ExtraConfig.IntentExtraKey.PRODUCT_ID, borrowId);
                intent.putExtra("EXP_FLG", EXP_FLG);
                startActivity(intent);

                break;
            case R.id.iv_back:
                finish();
                break;
        }

    }


    public String getBorrowId() {
        return borrowId;
    }

    public String getInvestFlg() {
        return investFlg;
    }

    public class InvestDetailMoreAdapter extends FragmentPagerAdapter {

        private String[] titles;

        public InvestDetailMoreAdapter(FragmentManager fm, String[] titles) {
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
                    if (fragmentProjectDetail == null) {
                        fragmentProjectDetail = new FragmentProjectDetail();
                    }
                    return fragmentProjectDetail;
                case 1:
                    if (fragmentLoanData == null) {
                        fragmentLoanData = new FragmentLoanData();
                    }
                    return fragmentLoanData;
                case 2:
                    if (fragmentInvestRecord == null) {
                        fragmentInvestRecord = new FragmentInvestRecord();
                    }
                    return fragmentInvestRecord;
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