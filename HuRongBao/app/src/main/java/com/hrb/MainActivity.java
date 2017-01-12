package com.hrb;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.format.Time;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.hrb.biz.HomeBiz;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.EasyLocalTask;
import com.hrb.model.VersionDescription;
import com.hrb.storage.PreferenceCache;
import com.hrb.ui.account.FragmentAccount;
import com.hrb.ui.base.BaseActivity;
import com.hrb.ui.finance.FragmentFinance;
import com.hrb.ui.home.FragmentHome;
import com.hrb.ui.init.ActivityLogin;
import com.hrb.ui.more.FragmentMore;
import com.hrb.ui.widget.PromptOkCancel;
import com.hrb.utils.android.DeviceUtil;
import com.hrb.utils.android.HttpUtil;
import com.hrb.utils.android.LogUtil;
import com.hrb.utils.java.StringUtil;

import java.io.File;
import java.io.IOException;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private RadioGroup radioGroupHome;
    private RadioButton radioHome, radioFinance, radioAccount, radioMore;

    private FragmentHome homeFragment;
    private FragmentFinance financeFragment;
    private FragmentAccount accountFragment;
    private FragmentMore moreFragment;

    private int progress;
    private static final int DOWNLOADING = 1; // 表示正在下载
    private static final int DOWNLOADED = 2; // 下载完毕
    private static final int DOWNLOAD_FAILED = 3; // 下载失败
    private boolean cancelFlag = false;
    private String mUrl = "";
    private NotificationManager notificationManager;
    private Notification notification;
    RemoteViews view = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        detectUpgrade();
    }

    private void init() {
        radioGroupHome = (RadioGroup) findViewById(R.id.radio_group);
        radioHome = (RadioButton) findViewById(R.id.radiobutton_home);
        radioFinance = (RadioButton) findViewById(R.id.radiobutton_finance);
        radioAccount = (RadioButton) findViewById(R.id.radiobutton_account);
        radioMore = (RadioButton) findViewById(R.id.radiobutton_more);
        radioHome.setOnClickListener(this);
        radioFinance.setOnClickListener(this);
        radioAccount.setOnClickListener(this);
        radioMore.setOnClickListener(this);

        homeFragment = new FragmentHome();

        addFragment(homeFragment, "fHome");
        showFragment(homeFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StringUtil.isEmpty(PreferenceCache.getToken())) {// 未登录
            if (HuRongBaoApp.globalIndex == 0)
                radioHome.setChecked(true);
            else if (HuRongBaoApp.globalIndex == 1)
                radioFinance.setChecked(true);
            else if (HuRongBaoApp.globalIndex == 2)
                radioAccount.setChecked(true);
            else if (HuRongBaoApp.globalIndex == 3)
                radioMore.setChecked(true);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        if (HuRongBaoApp.globalIndex == 0) {
            invokeHomeFragment();
            showFragment(homeFragment);
            radioHome.setChecked(true);

        } else if (HuRongBaoApp.globalIndex == 1) {
            invokeFinanceFragment();
            showFragment(financeFragment);
            radioFinance.setChecked(true);

        } else if (HuRongBaoApp.globalIndex == 2) {
            invokeAccountFragment();
            showFragment(accountFragment);
            radioAccount.setChecked(true);

        } else if (HuRongBaoApp.globalIndex == 3) {
            invokeMoreFragment();
            showFragment(moreFragment);
            radioMore.setChecked(true);
        }

    }

    //解决重影问题
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        if (savedInstanceState.getInt("position") == 0) {
            invokeHomeFragment();
            showFragment(homeFragment);
            radioHome.setChecked(true);
            HuRongBaoApp.globalIndex = 0;
        } else if (savedInstanceState.getInt("position") == 1) {
            invokeFinanceFragment();
            showFragment(financeFragment);
            radioFinance.setChecked(true);
            HuRongBaoApp.globalIndex = 1;
        } else if (savedInstanceState.getInt("position") == 2) {
            invokeAccountFragment();
            showFragment(accountFragment);
            radioAccount.setChecked(true);
            HuRongBaoApp.globalIndex = 2;
        } else {
            invokeMoreFragment();
            showFragment(moreFragment);
            radioMore.setChecked(true);
            HuRongBaoApp.globalIndex = 3;
        }

        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
        outState.putInt("position", HuRongBaoApp.globalIndex);
    }


    private void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().add(R.id.fl_radio, fragment, tag).commit();
    }

    private void showFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }

        if (financeFragment != null) {
            transaction.hide(financeFragment);
        }

        if (accountFragment != null) {
            transaction.hide(accountFragment);
        }

        if (moreFragment != null) {
            transaction.hide(moreFragment);
        }
        transaction.show(fragment).commit();

    }

    long lastBackKeyDownTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastBackKeyDownTime > 2000) { // 两秒钟内双击返回键关闭主界面
            Toast.makeText(this, R.string.double_tap_to_exit, Toast.LENGTH_SHORT).show();
            lastBackKeyDownTime = System.currentTimeMillis();

        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.radiobutton_home:
                showFragment(homeFragment);// 首页
                HuRongBaoApp.globalIndex = 0;
                break;
            case R.id.radiobutton_finance:
                HuRongBaoApp.globalIndex = 1;
                invokeFinanceFragment();
                showFragment(financeFragment);// 投资专区
                break;
            case R.id.radiobutton_account:
                if (StringUtil.isEmpty(PreferenceCache.getToken())) {
                    Intent itToLogin = new Intent(MainActivity.this, ActivityLogin.class);
                    startActivity(itToLogin);
                    return;
                }

                HuRongBaoApp.globalIndex = 2;
                invokeAccountFragment();
                showFragment(accountFragment);// 我的账户
                break;
            case R.id.radiobutton_more:
                HuRongBaoApp.globalIndex = 3;
                invokeMoreFragment();
                showFragment(moreFragment);// 关于我们
                break;
        }


    }

    private void invokeHomeFragment() {

        if (homeFragment == null) {
            homeFragment = new FragmentHome();
            addFragment(homeFragment, "fHome");
        }
    }

    private void invokeFinanceFragment() {

        if (financeFragment == null) {
            financeFragment = new FragmentFinance();
            addFragment(financeFragment, "fFinance");
        }
    }

    private void invokeAccountFragment() {

        if (accountFragment == null) {
            accountFragment = new FragmentAccount();
            addFragment(accountFragment, "fAccount");
        }
    }

    private void invokeMoreFragment() {

        if (moreFragment == null) {
            moreFragment = new FragmentMore();
            addFragment(moreFragment, "fMore");
        }
    }

    VersionDescription vd;

    private void detectUpgrade() {
        new EasyLocalTask<Void, VersionDescription>() {

            @Override
            protected VersionDescription doInBackground(Void... params) {

                try {
                    return HomeBiz.detectNewVersion();

                } catch (ZYException e) {
                    return null;
                }

            }

            @Override
            protected void onPostExecute(VersionDescription result) {
                super.onPostExecute(result);

                if (result != null) {
                    if (!DeviceUtil.getVesionName(MainActivity.this).equals(result.getVersionName())) {
                        vd = result;
                        mUrl = vd.getDownloadLink();
                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                        } else {
                                new PromptOkCancel(MainActivity.this) {

                                @Override
                                protected void onOk() {
                                    LogUtil.e(MainActivity.this.getVd().getDownloadLink());
                                    if (StringUtil.isEmpty(vd.getDownloadLink())) {
                                        return;
                                    }
                                    downloadApk(vd.getDownloadLink());
                                }
                            }.show(MainActivity.this.getString(R.string.new_version_detected), MainActivity.this.getVd().getVersionDescription(),
                                    R.string.download_background, R.string.remind_me_later, MainActivity.this.getVd().getAndroidForceUpdate());// 如果给传递值就强制下载

                        }

                    }
                }
            }
        }.execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            new PromptOkCancel(MainActivity.this) {

                @Override
                protected void onOk() {
                    if (StringUtil.isEmpty(vd.getDownloadLink())) {
                        return;
                    }
                    downloadApk(vd.getDownloadLink());
                }
            }.show(MainActivity.this.getString(R.string.new_version_detected), MainActivity.this.getVd().getVersionDescription(),
                    R.string.download_background, R.string.remind_me_later, MainActivity.this.getVd().getAndroidForceUpdate());// 如果给传递值就强制下载
        }

    }

    private void downloadApk(final String url) {
        new EasyLocalTask<Void, File>() {

            @Override
            protected File doInBackground(Void... params) {
                File file = new File(HuRongBaoApp.CACHE_ROOT_CACHE_DIR + File.separator + HuRongBaoConfig.APK_NAME);
                try {
                    LogUtil.e(url);
                    notification();

                    HttpUtil.downloadFile(url, file, new HttpUtil.IDownloadCallback() {

                        int i = 0;

                        @Override
                        public void onProgress(long currentSize, long totalSize) {

                            progress = (int) (((float) currentSize / totalSize) * 100);

                            if ((int) (progress / 10) > i) {
                                i = (int) (progress / 10);
                                // 更改进度条
                                notification.contentView.setProgressBar(R.id.progress, (int) (totalSize / 1024 / 1000),
                                        (int) (currentSize / 1024 / 1000), false);
                                // 发送消息
                                notificationManager.notify(101, notification);
                            }
                        }
                    });
                    // HttpUtil.downloadFile(url, file);
                } catch (IOException e) {
                    file = null;
                }

                return file;
            }

            @Override
            protected void onPostExecute(File result) {
                super.onPostExecute(result);
                if (result != null) {
                    notificationManager.cancel(101);// notification关闭不显示
                    installApk(result);
                }
            }
        }.execute();
    }

    /**
     * @Description 安装APK
     */
    public void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private void notification() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification = new Notification(R.drawable.ic_launcher, "下载新版本", System.currentTimeMillis());
        /*
         * notification = new Notification(); notification.icon =
		 * R.drawable.ic_launcher; notification.tickerText = "下载新版本";
		 */

        if (view == null) {
            view = new RemoteViews(getPackageName(), R.layout.notification);
            notification.contentView = view;
            notification.contentView.setProgressBar(R.id.progress, 100, 0, false);
        }
        PendingIntent contentIntent = PendingIntent.getActivity(this, R.string.app_name, new Intent(),
                PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent = contentIntent;
        notification.flags |= Notification.FLAG_ONGOING_EVENT;// 滑动或者clear都不会清空
        // 获取系统当前时间
        Time t = new Time();
        t.setToNow(); // 取得系统时间。
        int hour = t.hour;
        int minute = t.minute;
        if (hour >= 12) {
            if (hour == 12) {
                notification.contentView.setTextViewText(R.id.time, "下午" + hour + ":" + minute);
            }
            notification.contentView.setTextViewText(R.id.time, "下午" + (hour - 12) + ":" + minute);
        } else {
            notification.contentView.setTextViewText(R.id.time, "上午" + hour + ":" + minute);
        }
        notificationManager.notify(101, notification);
    }

    public VersionDescription getVd() {
        return vd;
    }


}
