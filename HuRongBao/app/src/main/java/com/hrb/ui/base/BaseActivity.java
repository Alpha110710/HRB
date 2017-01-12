package com.hrb.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hrb.ExtraConfig.BaseReceiverAction;
import com.hrb.HuRongBaoApp;
import com.hrb.R;
import com.hrb.storage.PreferenceCache;
import com.hrb.ui.finance.ActivityInvestDetail;
import com.hrb.ui.finance.ActivityZqDetail;
import com.hrb.ui.init.ActivityLogin;
import com.hrb.ui.widget.WaitingView;
import com.hrb.utils.java.Util;

/**
 * @author lenovo
 */
public class BaseActivity extends FragmentActivity {

    protected BroadcastReceiver receiver;
    /**
     * block 标志位
     * 当activity onPause(当前activity被隐藏的时候) canShowLogin = false 不可显示超时的Login页面
     * 当activity onResume(activity 恢复的时候) canShowLogin = false 可以显示超时Login页面 (针对MainActivity中的   MyAccount 和 More 设置)
     */
    protected boolean canShowLogin = true;
    protected boolean queryForFirstTime = false;
    protected boolean canQueryFromResume = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queryForFirstTime = true;

        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                onReceiveBroadcast(intent);
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(BaseReceiverAction.ACTION_TOKEN_EXPIRE); //token过期
//		filter.addAction(BaseReceiverAction.ACTION_TAB_CHANGE);
//		filter.addAction(BaseReceiverAction.LOGIN_FROM_LIST);
        registerReceiver(receiver, filter);

	/*	IntentFilter tabfilter = new IntentFilter();
        filter.addAction(BaseReceiverAction.ACTION_TAB_CHANGE); //token过期
		registerReceiver(receiver, tabfilter);*/
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.global);
        ViewGroup container = (FrameLayout) findViewById(R.id.fl_content_view);
        View.inflate(this, layoutResID, container);
    }


    //添加标题
//	public void setTitle(String title) {
//		TextView tv_title = (TextView) findViewById(R.id.tv_title);
//		ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
//
//		tv_title.setText(title);
//		iv_back.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				finish();
//			}
//		});
//	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    protected void onReceiveBroadcast(Intent intent) {
        String action = intent.getAction();
        if (action.equals(BaseReceiverAction.ACTION_TOKEN_EXPIRE)) {//token过期
            /**
             * MainActivity 与 其他 Activity 都继承与BaseActivity ，当超时时 会显示出多个Login页面
             * 这里block住所有onPause的Activity(意在只保留当前显示的Activity) 防止重复出现多个login
             */
            if (!(BaseActivity.this instanceof ActivityLogin) && canShowLogin) {
                Util.showLogin(BaseActivity.this);
                if (BaseActivity.this instanceof ActivityInvestDetail ||
                        BaseActivity.this instanceof ActivityZqDetail) {
                    PreferenceCache.putLoginFlag(true);
                }
            }

        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        canShowLogin = false;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        /**
         * 控制是否显示Login
         */
        canShowLogin = true;

	/*	if ((BaseActivity.this instanceof MainActivity) && (CsyhApp.canQueryFromOnResume || queryForFirstTime)){
            *//**
         * 刷新主页 4个tab
         *//*
            queryFromOnResume();
		}else if( !(BaseActivity.this instanceof MainActivity) && !(BaseActivity.this instanceof ActivityLogin) && CsyhApp.canQueryFromOnResume ){
			*//**
         * 当token=null 或者token过期， 登录后刷新当前页面
         *//*
			resumeFromLogin();
			CsyhApp.canQueryFromOnResume = false;
		}*/

    }

    /**
     * 用于刷新主页面4个tab ,调用首页中Fragment的onResume方法
     */
    protected void queryFromOnResume() {
        HuRongBaoApp.canQueryFromOnResume = false;
        queryForFirstTime = false;
    }

    /**
     * 用于非MainActivity的Activity ， 当请求接口返回token过期 则显示登录页面， 登录成功后
     * 返回当前Activity 并调用 resumeFromLogin
     */
    protected void resumeFromLogin() {

    }

    public WaitingView getWaitingView() {
        WaitingView waitingView = (WaitingView) findViewById(WaitingView.WAITING_VIEW_ID);
        if (waitingView == null) {
            throw new RuntimeException("your layout has not included the \"waitingView\"");
        } else {
            return waitingView;
        }
    }
}
