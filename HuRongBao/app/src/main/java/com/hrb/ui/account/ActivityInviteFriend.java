package com.hrb.ui.account;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.InviteInfoModel;
import com.hrb.ui.base.BaseActivity;
import com.hrb.ui.widget.WaterView;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import static android.R.attr.action;

/**
 * Created by Ls on 2016/11/8.
 */

public class ActivityInviteFriend extends BaseActivity implements View.OnClickListener, PlatformActionListener, Callback {

    private ImageView invite_friend_round_iv;
    private TextView invite_award_tv;
    private TextView invite_people_num_tv;
    private ImageView invite_qq_iv;
    private ImageView invite_sinna_iv;
    private ImageView invite_weixin_iv;
    private WaterView waterview;

    private String link = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        ShareSDK.initSDK(this);
        initView();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        TextView tv_right = (TextView) findViewById(R.id.tv_right);
        invite_friend_round_iv = (ImageView) findViewById(R.id.invite_friend_round_iv);
        invite_award_tv = (TextView) findViewById(R.id.invite_award_tv);
        invite_people_num_tv = (TextView) findViewById(R.id.invite_people_num_tv);
        invite_qq_iv = (ImageView) findViewById(R.id.invite_qq_iv);
        invite_sinna_iv = (ImageView) findViewById(R.id.invite_sinna_iv);
        invite_weixin_iv = (ImageView) findViewById(R.id.invite_weixin_iv);
        waterview = (WaterView) findViewById(R.id.waterview);

        iv_back.setOnClickListener(this);
        tv_title.setText("邀请好友");
        tv_right.setText("邀请记录");
        tv_right.setOnClickListener(this);
        invite_friend_round_iv.setOnClickListener(this);
        invite_qq_iv.setOnClickListener(this);
        invite_sinna_iv.setOnClickListener(this);
        invite_weixin_iv.setOnClickListener(this);

        getInviteInfo();
    }


    private void getInviteInfo() {
        BizDataAsyncTask<InviteInfoModel> getInviteInfoTask = new BizDataAsyncTask<InviteInfoModel>(getWaitingView()) {

            @Override
            protected void onExecuteSucceeded(InviteInfoModel result) {

                invite_people_num_tv.setText(result.getFRIEND_COUNT() + "人");
                invite_award_tv.setText(result.getRECEIVED_AMOUNT().replace("￥", ""));
                link = result.getINVITE_FRIEND_URL();
                waterview.start();
                waterview.setProcess(0.4f);
            }

            @Override
            protected InviteInfoModel doExecute() throws ZYException, BizFailure {

                return AccountBiz.getInviteInfo();
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityInviteFriend.this, error);
                }
            }
        };
        getInviteInfoTask.execute();
    }


    @Override
    public void onClick(View view) {
        ShareParams sp = new ShareParams();
        Platform plat = null;
        Resources res = this.getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.ic_launcher);
        switch (view.getId()) {
            case R.id.invite_qq_iv:
                //qq
                sp.setTitle("虎融宝");
                sp.setTitleUrl(link);
                sp.setText("虎融宝最专业的P2P服务平台。");
                sp.setImageUrl("https://www.0080.cn/iloan/images/hrb.png");
                plat = ShareSDK.getPlatform(QQ.NAME);
                plat.setPlatformActionListener(this);
                plat.share(sp);
                break;
            case R.id.invite_friend_round_iv:
                //朋友圈
                sp.setShareType(Platform.SHARE_WEBPAGE);
                sp.setTitle("虎融宝");
                sp.setUrl(link);
                //sp.setImageData(bmp);
                sp.setImageUrl("https://www.0080.cn/iloan/images/hrb.png");
                plat = ShareSDK.getPlatform(WechatMoments.NAME);
                plat.setPlatformActionListener(this);
                plat.share(sp);
                break;
            case R.id.invite_sinna_iv:
                //新浪
                sp.setText("虎融宝专业的P2P服务平台。" + link);
                sp.setImageUrl("https://www.0080.cn/iloan/images/hrb.png");
                plat = ShareSDK.getPlatform(SinaWeibo.NAME);
                plat.setPlatformActionListener(this); // 设置分享事件回调
                // 执行图文分享
                plat.share(sp);
                break;
            case R.id.invite_weixin_iv:
                //微信
                //sp.setText("虎融宝" + "" + link);
                sp.setShareType(Platform.SHARE_WEBPAGE);
                sp.setTitle("虎融宝");
                sp.setUrl(link);
                //sp.setImageData(bmp);
                sp.setImageUrl("https://www.0080.cn/iloan/images/hrb.png");
                sp.setText("虎融宝最专业的P2P服务平台。");
                plat = ShareSDK.getPlatform(Wechat.NAME);
                plat.setPlatformActionListener(this);
                plat.share(sp);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                //邀请记录
                Intent intent = new Intent(this, ActivityAwardDetail.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message msg = new Message();
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        throwable.printStackTrace();
        Message msg = new Message();
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = throwable;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Message msg = new Message();
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public boolean handleMessage(Message message) {
        String text = "";
        switch (message.arg1) {
            case 1: {
                // 成功
                Platform plat = (Platform) message.obj;
                // text = plat.getName() + "share completed ";
                text = "分享成功 ";
            }
            break;
            case 2: {
                // 失败
                if ("WechatClientNotExistException".equals(message.obj.getClass().getSimpleName())) {
                    text = this.getString(R.string.wechat_client_inavailable);
                } else if ("WechatTimelineNotSupportedException".equals(message.obj.getClass().getSimpleName())) {
                    text = this.getString(R.string.wechat_client_inavailable);
                } else {
                    text = this.getString(R.string.share_failed);
                }
            }
            break;
            case 3: {
                // 取消
                Platform plat = (Platform) message.obj;
                // text = plat.getName() + " canceled ";
                text = "取消分享 ";
            }
            break;
        }

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        return false;
    }
}
