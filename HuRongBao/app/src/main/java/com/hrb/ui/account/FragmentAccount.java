package com.hrb.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.hrb.ExtraConfig;
import com.hrb.HuRongBaoApp;
import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.MyAccountModel;
import com.hrb.ui.base.BaseFragment;
import com.hrb.ui.widget.WaveView;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

/**
 * Created by Ls on 2016/10/9.
 */

public class FragmentAccount extends BaseFragment implements View.OnClickListener {

    private TextView account_balance_tv; //账户余额
    private TextView account_profit_total_tv; //累计收益
    private TextView account_wait_interest_tv; //待收本息
    private TextView account_experience_amount_tv;// 体验金
    private PullToRefreshScrollView pullToRefreshScrollView;

    private MyAccountModel myAccountModel;
    private WaveView wave_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView iv_back = (ImageView) view.findViewById(R.id.iv_back);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        ImageView iv_right = (ImageView) view.findViewById(R.id.iv_right);

        account_profit_total_tv = (TextView) view.findViewById(R.id.account_profit_total_tv);
        account_balance_tv = (TextView) view.findViewById(R.id.account_balance_tv);
        account_wait_interest_tv = (TextView) view.findViewById(R.id.account_wait_interest_tv);
        pullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.sv_account);
        account_experience_amount_tv = (TextView) view.findViewById(R.id.account_experience_amount_tv);
        wave_view = (WaveView) findViewById(R.id.wave_view);

        view.findViewById(R.id.account_withdraw_tv).setOnClickListener(this);
        view.findViewById(R.id.account_recharge_tv).setOnClickListener(this);
        view.findViewById(R.id.account_deal_record_ll).setOnClickListener(this);
        view.findViewById(R.id.account_integral_ll).setOnClickListener(this);
        view.findViewById(R.id.account_invite_friends_ll).setOnClickListener(this);
        view.findViewById(R.id.account_my_invest_ll).setOnClickListener(this);
        view.findViewById(R.id.account_my_transfer_ll).setOnClickListener(this);
        view.findViewById(R.id.account_add_rate_coupon_ll).setOnClickListener(this);
        view.findViewById(R.id.account_red_packet_ll).setOnClickListener(this);
        view.findViewById(R.id.account_return_plan_ll).setOnClickListener(this);

        tv_title.setText("我的");
        iv_back.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        wave_view.setProgress(40);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (HuRongBaoApp.globalIndex == 2) {
            init();
        }
    }

    private void init() {
        getAccountPageData(true);

        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        // 上拉监听函数
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {

                if (PullToRefreshBase.Mode.PULL_FROM_START == refreshView.getCurrentMode()) {
                    getAccountPageData(false);
                }

            }
        });
    }

    private void getAccountPageData(boolean b) {
        BizDataAsyncTask<MyAccountModel> getMyAccountTask = new BizDataAsyncTask<MyAccountModel>() {
            @Override
            protected MyAccountModel doExecute() throws ZYException, BizFailure {
                return AccountBiz.getMyAccountPage();
            }

            @Override
            protected void onExecuteSucceeded(MyAccountModel myAccountModel) {
                account_profit_total_tv.setText(myAccountModel.getUSABLE_AMOUNT());//可用余额
                account_wait_interest_tv.setText(myAccountModel.getAWAIT());//待收金额
                account_balance_tv.setText(myAccountModel.getFROZE_AMOUNT());//冻结金额


                if (myAccountModel.getEXP_OPEN() != null && myAccountModel.getEXP_OPEN().equals("1") && myAccountModel.getEXPERIENCE_CASH() != null && !myAccountModel.getEXPERIENCE_CASH().equals("0.00") && !myAccountModel.getEXPERIENCE_ENDFLG().equals("1")) {
                    //体验金为0 或已过期不显示体验金
                    account_experience_amount_tv.setVisibility(View.VISIBLE);
                } else {
                    account_experience_amount_tv.setVisibility(View.GONE);
                }

                account_experience_amount_tv.setText("体验金 : " + myAccountModel.getEXPERIENCE_CASH() + "元");//体验金额

                pullToRefreshScrollView.onRefreshComplete();
                FragmentAccount.this.myAccountModel = myAccountModel;
            }

            @Override
            protected void OnExecuteFailed(String error) {
                pullToRefreshScrollView.onRefreshComplete();
                FragmentAccount.this.myAccountModel = null;
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(getActivity(), error);
                }
            }
        };
        if (b) {
            getMyAccountTask.setWaitingView(getWaitingView());
        }
        getMyAccountTask.execute();
    }


    private boolean resume = false;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onPause();
        } else {
            if (resume) {
                onResume();
            }
            resume = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_withdraw_tv:
                //提现
                if (myAccountModel != null) {
                    if (myAccountModel.getCONTRACTS() == null) {
                        AlertUtil.t(getActivity(), "请开通实名认证");
                    } else {
                        Intent itWithDraw = new Intent(getActivity(), ActivityWithdraw.class);
                        itWithDraw.putExtra(ExtraConfig.IntentExtraKey.USER_AMOUNT, myAccountModel.getUSABLE_AMOUNT());
                        startActivity(itWithDraw);
                    }
                }
                break;
            case R.id.account_recharge_tv:
                //充值
                if (myAccountModel != null) {
                    if (myAccountModel.getCONTRACTS() == null) {
                        AlertUtil.t(getActivity(), "请开通实名认证");
                    } else {
                        Intent itRecharge = new Intent(getActivity(), ActivityRecharge.class);
                        itRecharge.putExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT, myAccountModel);
                        startActivity(itRecharge);
                    }
                }
                break;
            case R.id.account_deal_record_ll:
                //交易记录
                Intent itDealRecord = new Intent(getActivity(), ActivityDealRecord.class);
                startActivity(itDealRecord);
                break;
            case R.id.account_integral_ll:
                //积分
                Intent itIntegral = new Intent(getActivity(), ActivityIntegral.class);
                startActivity(itIntegral);
                break;
            case R.id.account_invite_friends_ll:
                //邀请好友
                Intent itInvite = new Intent(getActivity(), ActivityInviteFriend.class);
                startActivity(itInvite);
                break;
            case R.id.account_my_invest_ll:
                //我的投资
                Intent itMyInvest = new Intent();
                itMyInvest.setClass(FragmentAccount.this.getActivity(), ActivityMyInvest.class);
                startActivity(itMyInvest);
                break;
            case R.id.account_my_transfer_ll:
                //我的转让
                Intent itMyTransfer = new Intent(getActivity(), ActivityMyTransfer.class);
                startActivity(itMyTransfer);
                break;
            case R.id.account_add_rate_coupon_ll:
                //加息券
                Intent itAdd = new Intent(getActivity(), ActivityMyAddRateCoupon.class);
                startActivity(itAdd);
                break;
            case R.id.account_red_packet_ll:
                //红包
                Intent itRed = new Intent(getActivity(), ActivityMyRedPackage.class);
                startActivity(itRed);
                break;
            case R.id.account_return_plan_ll:
                //回款计划
                Intent itReturnMoneyPlan = new Intent(getActivity(), ActivityReturnMoneyPlan.class);
                startActivity(itReturnMoneyPlan);
                break;
            case R.id.iv_back:
                //设置
                if (myAccountModel != null) {
                    Intent itSetHelp = new Intent(getActivity(), ActivitySetHelp.class);
                    itSetHelp.putExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT, myAccountModel);
                    startActivity(itSetHelp);
                }
                break;
            case R.id.iv_right:
                //消息
                Intent itMsg = new Intent(getActivity(), ActivityMessageCenter.class);
                startActivity(itMsg);
                break;

        }

    }
}
