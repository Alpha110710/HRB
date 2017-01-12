package com.hrb.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

/**
 * Created by Ls on 2016/10/21.
 */

public class ActivityResetTeleNumTwo extends BaseActivity implements View.OnClickListener {

    private Button btn_reset_num_two_go_next;
    private EditText et_reset_num_two_id_num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_tele_num_two);
        initView();
    }

    private void initView() {
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        btn_reset_num_two_go_next = (Button) findViewById(R.id.btn_reset_num_two_go_next);
        et_reset_num_two_id_num = (EditText) findViewById(R.id.et_reset_num_two_id_num);

        tv_title.setText("修改手机");
        iv_back.setOnClickListener(this);
        btn_reset_num_two_go_next.setOnClickListener(this);

    }

    private void checkCardId() {
        BizDataAsyncTask<String> checkCardId = new BizDataAsyncTask<String>(getWaitingView()) {
            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return AccountBiz.checkCardId(et_reset_num_two_id_num.getEditableText().toString());
            }

            @Override
            protected void onExecuteSucceeded(String s) {
                Intent intent = new Intent();
                intent.setClass(ActivityResetTeleNumTwo.this, ActivityResetTeleNumThree.class);
                startActivity(intent);
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityResetTeleNumTwo.this, error);
                }
            }
        };
        checkCardId.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_reset_num_two_go_next:
                // 下一步
                checkCardId();
                break;
        }
    }
}
