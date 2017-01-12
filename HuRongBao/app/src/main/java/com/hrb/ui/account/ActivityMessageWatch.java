package com.hrb.ui.account;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.GetMessageInfoModel;
import com.hrb.model.MessageModelList;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;


public class ActivityMessageWatch extends BaseActivity implements OnClickListener {

    private TextView tv_msg_detail_content;
    private TextView tv_msg_detail_title;

    private MessageModelList messageModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_watch);
        messageModelList = getIntent().getParcelableExtra(ExtraConfig.IntentExtraKey.ACCOUNT_MSG);
        initView();
    }

    private void initView() {
        tv_msg_detail_content = (TextView) findViewById(R.id.tv_msg_detail_content);
        tv_msg_detail_title = (TextView) findViewById(R.id.tv_msg_detail_title);
        TextView tv_msg_detail_type = (TextView) findViewById(R.id.tv_msg_detail_type);
        TextView tv_msg_detail_time = (TextView) findViewById(R.id.tv_msg_detail_time);

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);

        tv_title.setText("消息中心");
        iv_back.setOnClickListener(this);
        tv_msg_detail_type.setText(messageModelList.getGROUP_NAME());
        tv_msg_detail_time.setText(messageModelList.getINS_DATE());

        getMessageInfoModel();
    }

    /**
     * 获取消息详细信息
     */
    private BizDataAsyncTask<GetMessageInfoModel> task;

    private void getMessageInfoModel() {
        task = new BizDataAsyncTask<GetMessageInfoModel>(getWaitingView()) {

            @Override
            protected void onExecuteSucceeded(GetMessageInfoModel result) {

                tv_msg_detail_title.setText(result.getTITLE());
                tv_msg_detail_content.setText(result.getMSG_CONTENT());
            }

            @Override
            protected GetMessageInfoModel doExecute() throws ZYException, BizFailure {
                return AccountBiz.getMessageInfo(messageModelList.getID());
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityMessageWatch.this, error);
                }
            }
        };
        task.execute();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                setResult(RESULT_OK);
                finish();
                break;

            default:
                break;
        }
    }

}
