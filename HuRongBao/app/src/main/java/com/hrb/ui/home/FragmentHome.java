package com.hrb.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.hrb.ExtraConfig;
import com.hrb.HuRongBaoApp;
import com.hrb.R;
import com.hrb.biz.HomeBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.HomePageModel;
import com.hrb.storage.PreferenceCache;
import com.hrb.ui.account.ActivityMessageCenter;
import com.hrb.ui.base.BaseFragment;
import com.hrb.ui.finance.ActivityInvestDetail;
import com.hrb.ui.init.ActivityLogin;
import com.hrb.ui.init.ActivityWebView;
import com.hrb.ui.widget.AutoTextView;
import com.hrb.ui.widget.CircleProgressBar;
import com.hrb.ui.widget.WaterView;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ls on 2016/10/9.
 */

public class FragmentHome extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {


    private ViewPager viewPager;
    private LinearLayout tipsLayout;
    private PullToRefreshScrollView pullToRefreshScrollView;

    /**
     * 装点点的ImageView数组
     */
    private ImageView[] tips;

    /**
     * 装ImageView数组
     */
    private ImageView[] mImageViews;
    private List<HomePageModel.ADVERTISINGLISTBean> imgs;
    private AtomicInteger what = new AtomicInteger(0);
    private boolean isContinue = false, isRuning = true;
    private Thread thread;


    private TextView home_big_mark_min_amount_tv;
    private TextView home_big_mark_scale_tv;
    private TextView home_big_mark_title_tv;
    private Button home_confirm_btn;
    private TextView home_many_ensure_tv;
    private TextView home_new_user_tv;
    private TextView home_next_day_tv;
    private TextView home_right_tv;
    private ImageView home_right_iv;
    private TextView home_small_mark_name_tv;
    private RelativeLayout home_small_mark_rl;
    private TextView home_small_mark_scale_tv;
    private TextView home_title_tv;
    private ViewPager home_viewPager;
    private CircleProgressBar cpb_progress;
    private WaterView waterView;
    private RelativeLayout viewpager_layout;

    private String OID_PLATFORM_PRODUCTS_ID0 = "", OID_PLATFORM_PRODUCTS_ID1 = "";

    private final Handler viewHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(msg.what);
            super.handleMessage(msg);
        }

    };

    //修改的首页添加公告
    private AutoTextView tv_notice;//公告
    private TextView tv_open_account;//去开户
    private HomePageModel homePageModel;

    private boolean isTvRunning = false;
    private static int sCount = 0;
    private final Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 在此处添加执行的代码

            sCount++;
            if (sCount >= Integer.MAX_VALUE) {
                sCount = homePageModel.getNOTICE().size();
            }
            HomePageModel.NOTICEBean noticeBean = homePageModel.getNOTICE().get(
                    sCount % (homePageModel.getNOTICE().size()));
            //autoNoticeTv.setImgType(Integer.parseInt(mNewsModel.getNewsImg()));
//            Log.d("FragmentHome", noticeBean.getTITLE());
            tv_notice.setText(noticeBean.getTITLE());
            //autoNoticeTv.next();
            if (homePageModel.getNOTICE().size() > 1) {
                handler.postDelayed(this, 10000);// 50是延时时长
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        home_big_mark_min_amount_tv = (TextView) view.findViewById(R.id.home_big_mark_min_amount_tv);
        home_big_mark_scale_tv = (TextView) view.findViewById(R.id.home_big_mark_scale_tv);
        home_big_mark_title_tv = (TextView) view.findViewById(R.id.home_big_mark_title_tv);
        home_confirm_btn = (Button) view.findViewById(R.id.home_confirm_btn);
        home_many_ensure_tv = (TextView) view.findViewById(R.id.home_many_ensure_tv);
        home_new_user_tv = (TextView) view.findViewById(R.id.home_new_user_tv);
        home_next_day_tv = (TextView) view.findViewById(R.id.home_next_day_tv);
        home_right_tv = (TextView) view.findViewById(R.id.home_right_tv);
        home_right_iv = (ImageView) view.findViewById(R.id.home_right_iv);
        home_small_mark_name_tv = (TextView) view.findViewById(R.id.home_small_mark_name_tv);
        home_small_mark_rl = (RelativeLayout) view.findViewById(R.id.home_small_mark_rl);
        home_small_mark_scale_tv = (TextView) view.findViewById(R.id.home_small_mark_scale_tv);
        home_title_tv = (TextView) view.findViewById(R.id.home_title_tv);
        viewPager = (ViewPager) view.findViewById(R.id.home_viewPager);
        cpb_progress = (CircleProgressBar) view.findViewById(R.id.cpb_progress);
        tipsLayout = (LinearLayout) findViewById(R.id.viewGroup);
        pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.home_scroll);
        waterView = (WaterView) findViewById(R.id.waterview);
        viewpager_layout = (RelativeLayout) findViewById(R.id.viewpager_layout);
        tv_open_account = (TextView) findViewById(R.id.tv_open_account);
        tv_notice = (AutoTextView) findViewById(R.id.tv_notice);

        findViewById(R.id.ll_brand_introduction).setOnClickListener(this);
        findViewById(R.id.ll_novice_guide).setOnClickListener(this);
        findViewById(R.id.ll_wind_control_security).setOnClickListener(this);
        findViewById(R.id.ll_operation_data).setOnClickListener(this);
        tv_notice.setOnClickListener(this);

        home_small_mark_rl.setOnClickListener(this);
        cpb_progress.setOnClickListener(this);
        home_right_tv.setOnClickListener(this);
        home_right_iv.setOnClickListener(this);
        home_confirm_btn.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);

        viewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isContinue = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        isContinue = true;
                        break;
                    default:
                        isContinue = true;
                        break;
                }
                return false;
            }
        });
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (isRuning) {
                    if (isContinue) {
                        viewHandler.sendEmptyMessage(what.get());
                        whatOption();
                    }
                }
            }

        });

        thread.start();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取数据
        getData();
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        // 上拉监听函数
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {

                if (PullToRefreshBase.Mode.PULL_FROM_START == refreshView.getCurrentMode()) {
                    getData();
                }

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        if (HuRongBaoApp.globalIndex == 0) {
            //设置轮播图 宽高2:1
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenWidth = dm.widthPixels;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewPager.getLayoutParams();
            params.height = screenWidth * 2 / 3;
            params.width = screenWidth;
            viewPager.setLayoutParams(params);

            if (StringUtil.isEmpty(PreferenceCache.getToken())) {
                home_right_tv.setVisibility(View.VISIBLE);
                home_right_iv.setVisibility(View.GONE);
            } else {
                home_right_tv.setVisibility(View.GONE);
                home_right_iv.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onPause();
        } else {
            onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getHomePage != null) {
            getHomePage.cancel(true);
        }
        if (thread != null) {
            isRuning = false;
            thread.interrupt();
        }
    }

    private BizDataAsyncTask<HomePageModel> getHomePage;

    private void getData() {
        getHomePage = new BizDataAsyncTask<HomePageModel>(getWaitingView()) {
            @Override
            protected HomePageModel doExecute() throws ZYException, BizFailure {
                return HomeBiz.getHomePage();

            }

            @Override
            protected void onExecuteSucceeded(HomePageModel homePageModel) {
                //根据长度判断 返回有无体验标
                FragmentHome.this.homePageModel = homePageModel;
                if (homePageModel.getBULLETED_LIST().size() == 1) {
                    home_small_mark_rl.setVisibility(View.GONE);

                } else if (homePageModel.getBULLETED_LIST().size() == 2) {
                    home_small_mark_rl.setVisibility(View.GONE);//原本为显示出来,  后续修改体验标删除, 此处标记为隐藏
                    home_small_mark_name_tv.setText("体验标");
                    home_small_mark_scale_tv.setText(homePageModel.getBULLETED_LIST().get(1).getFINANCE_INTEREST_RATE());
                    OID_PLATFORM_PRODUCTS_ID1 = homePageModel.getBULLETED_LIST().get(1).getOID_PLATFORM_PRODUCTS_ID(); //体验标
                }
                home_big_mark_min_amount_tv.setText(homePageModel.getBULLETED_LIST().get(0).
                        getMIN_TENDER_AMOUNT() + "元起购");
                home_big_mark_scale_tv.setText(homePageModel.getBULLETED_LIST().get(0).getFINANCE_INTEREST_RATE());
                home_big_mark_title_tv.setText(homePageModel.getBULLETED_LIST().get(0).getPRODUCTS_TITLE());
                home_next_day_tv.setText(homePageModel.getBULLETED_LIST().get(0).getSTART_INTEREST_TIME());// 起息方式
                cpb_progress.setProgress(Float.parseFloat(homePageModel.getBULLETED_LIST().get(0).getFINANCE_AMOUNT_SCALE()));

                waterView.reset();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(300);
                            waterView.start();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
//                waterView.setProcess(0.5f);
                waterView.setProcess(Float.parseFloat(homePageModel.getBULLETED_LIST().get(0).getFINANCE_AMOUNT_SCALE()) / 100f);

                OID_PLATFORM_PRODUCTS_ID0 = homePageModel.getBULLETED_LIST().get(0).getOID_PLATFORM_PRODUCTS_ID();// 新手标
//                if (!homePageModel.getBULLETED_LIST().get(0).getBORROW_STATUS().equals("立即投资")) {
//                    home_confirm_btn.setBackground(getResources().getDrawable(R.drawable.shape_round_grey_button));
//                    home_confirm_btn.setClickable(false);
//                }

                // 添加banner 数据 begin
                imgs = homePageModel.getADVERTISING_LIST();
                if (imgs != null) {
                    setViewPagerImgs();
                    what = new AtomicInteger(0);
                    isContinue = true;
                }
                // 添加banner 数据 end

                //设置公告
                sCount = homePageModel.getNOTICE().size();

                if (sCount >= 1) {

                    tv_notice.setVisibility(View.VISIBLE);
                    tv_notice.setText(homePageModel.getNOTICE().get(0).getTITLE());
                    //autoNoticeTv.setImgType(Integer.parseInt(mhomedata.getNEWS_INFO().get(0).getNewsImg()));
                    // 启动计时器
                    if (!isTvRunning) {
                        handler.postDelayed(runnable, 10000);
                        isTvRunning = true;
                    }
                }


                pullToRefreshScrollView.onRefreshComplete();
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(getActivity(), error);
                }
                pullToRefreshScrollView.onRefreshComplete();
            }
        };
        getHomePage.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_right_tv:
                Intent intent = new Intent(getActivity(), ActivityLogin.class);
                startActivity(intent);
                break;
            case R.id.home_right_iv:
                //跳转到消息
                Intent itMsg = new Intent(getActivity(), ActivityMessageCenter.class);
                startActivity(itMsg);
                break;
            case R.id.home_confirm_btn:
                //立即投资
                if (OID_PLATFORM_PRODUCTS_ID0 != null && !StringUtil.isEmpty(OID_PLATFORM_PRODUCTS_ID0)) {
                    Intent intent1 = new Intent(getActivity(), ActivityInvestDetail.class);
                    intent1.putExtra("productId", OID_PLATFORM_PRODUCTS_ID0);
                    startActivity(intent1);
                }
                break;
            case R.id.home_small_mark_rl:
                //体验标立即投资
                if (OID_PLATFORM_PRODUCTS_ID1 != null && !StringUtil.isEmpty(OID_PLATFORM_PRODUCTS_ID1)) {
                    Intent intent1 = new Intent(getActivity(), ActivityInvestDetail.class);
                    intent1.putExtra("productId", OID_PLATFORM_PRODUCTS_ID1);
                    startActivity(intent1);
                }
                break;
            case R.id.cpb_progress:
                //点击大球标
                if (OID_PLATFORM_PRODUCTS_ID0 != null && !StringUtil.isEmpty(OID_PLATFORM_PRODUCTS_ID0)) {
                    Intent intent1 = new Intent(getActivity(), ActivityInvestDetail.class);
                    intent1.putExtra("productId", OID_PLATFORM_PRODUCTS_ID0);
                    startActivity(intent1);
                }
                break;

            case R.id.tv_notice:
                //公告
                if (homePageModel == null) {
                    return;
                }
                String noticeId = homePageModel.getNOTICE().get(
                        sCount % (homePageModel.getNOTICE().size())).getID();
                Intent intent2 = new Intent(getActivity(), ActivityWebView.class);
                intent2.putExtra("LINKURL", noticeId);
                intent2.putExtra(ExtraConfig.IntentExtraKey.WEB_VIEW_FROM, 5);
                startActivity(intent2);
                break;

            case R.id.ll_brand_introduction:
                //品牌介绍
                Intent intent4 = new Intent(getActivity(), ActivityWebView.class);
                intent4.putExtra("LINKURL", "");
                intent4.putExtra(ExtraConfig.IntentExtraKey.WEB_VIEW_FROM, 8);
                startActivity(intent4);
                break;
            case R.id.ll_novice_guide:
                //新手引导
                Intent intent3 = new Intent(getActivity(), ActivityWebView.class);
                intent3.putExtra("LINKURL", "");
                intent3.putExtra(ExtraConfig.IntentExtraKey.WEB_VIEW_FROM, 9);
                startActivity(intent3);
                break;
            case R.id.ll_wind_control_security:
                //风控保障
                Intent intent5 = new Intent(getActivity(), ActivityWebView.class);
                intent5.putExtra("LINKURL", "");
                intent5.putExtra(ExtraConfig.IntentExtraKey.WEB_VIEW_FROM, 10);
                startActivity(intent5);
                break;
            case R.id.ll_operation_data:
                //运营数据
                Intent intent6 = new Intent(getActivity(), ActivityWebView.class);
                intent6.putExtra("LINKURL", "");
                intent6.putExtra(ExtraConfig.IntentExtraKey.WEB_VIEW_FROM, 11);
                startActivity(intent6);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        what.getAndSet(position);
        setTipsBackground(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void whatOption() {
        what.incrementAndGet();
        if (what.get() > mImageViews.length - 1) {
            what.getAndAdd(-mImageViews.length);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setTipsBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.drawable.point_sel);
            } else {
                tips[i].setBackgroundResource(R.drawable.point_normal);
            }
        }
    }

    private void setViewPagerImgs() {
        tipsLayout.removeAllViews();
        // 将点点加入到ViewGroup中
        tips = new ImageView[imgs.size()];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(15, 15));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.drawable.point_sel);
            } else {
                tips[i].setBackgroundResource(R.drawable.point_normal);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutParams.setMargins(6, 0, 6, 20);
            tipsLayout.addView(imageView, layoutParams);

        }

        // 将图片装载到数组中
        mImageViews = new ImageView[imgs.size()];
        for (int i = 0; i < mImageViews.length; i++) {

            final int j = i;
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageViews[i] = imageView;
            ImageLoader.getInstance().displayImage(
                    (imgs.get(i)).getIMG_PATH(), imageView); // ,
            // options);//
            // 显示图片

            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String linkUrl = imgs.get(j).getLINK_URL();
                    // LogUtil.e("1111111111");
                    if (StringUtil.isEmpty(linkUrl)) {
                        return;
                    }

                    Intent itlinkUrl = new Intent(getActivity(), ActivityWebView.class);
                    itlinkUrl.putExtra("LINKURL", linkUrl);
                    itlinkUrl.putExtra(ExtraConfig.IntentExtraKey.WEB_VIEW_FROM, 7);
                    startActivity(itlinkUrl);


                }
            });

        }

        // 设置Adapter
        viewPager.setAdapter(new viewPagerAdapter());
        // 设置ViewPager的默认项。
        viewPager.setCurrentItem(0);

    }

    public class viewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViews != null ? mImageViews.length : 0;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mImageViews[position]);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ((ViewPager) container).addView(mImageViews[position]);

            return mImageViews[position];
        }

    }

}
