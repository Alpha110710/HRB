package com.hrb.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.HuRongBaoApp;
import com.hrb.MainActivity;
import com.hrb.R;
import com.hrb.biz.HomeBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

/**
 * Created by Kindling on 2016/10/28 13:52.
 */

public class ActivityRealNameAuth extends BaseActivity {
    ImageView iv_back;
    TextView tv_title;
    Button btn_confirm;

    EditText et_name;
    EditText et_id_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realnameauth);
        initView();
    }
    private void initView(){
        et_name = (EditText) findViewById(R.id.et_name);
        et_id_number = (EditText) findViewById(R.id.et_id_number);

        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("实名认证");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realNameAuth();
            }
        });
    }


    private void realNameAuth() {

        //realNameAuth(String token,String realName,String cardId)

        BizDataAsyncTask<String> realNameAuth = new BizDataAsyncTask<String>(getWaitingView()) {
            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return HomeBiz.realNameAuth(et_name.getText().toString(),et_id_number.getText().toString());
            }

            @Override
            protected void onExecuteSucceeded(String openAccountModel) {
                AlertUtil.t(ActivityRealNameAuth.this, "实名成功!");
                Intent intent = new Intent(ActivityRealNameAuth.this, MainActivity.class);
                HuRongBaoApp.globalIndex=2;
                startActivity(intent);
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityRealNameAuth.this, error);
                }
            }
        };
        realNameAuth.execute();
    }
}
