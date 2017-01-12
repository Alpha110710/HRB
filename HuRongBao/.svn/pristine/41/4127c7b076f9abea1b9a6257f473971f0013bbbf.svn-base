package com.hrb.ui.account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.ExtraConfig;
import com.hrb.R;
import com.hrb.biz.AccountBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.dialog.AreaPopup;
import com.hrb.dialog.BankPopup;
import com.hrb.model.BankModel;
import com.hrb.model.MyAccountModel;
import com.hrb.model.ProvinceModel;
import com.hrb.ui.base.BaseActivity;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

import java.util.List;

/**
 * Created by Ls on 2016/10/14.
 */

public class ActivityAddBankCard extends BaseActivity implements View.OnClickListener {

    private Button add_bank_card_confirm_btn;
    private TextView add_bank_card_bank_name_tv;
    private TextView add_bank_card_city_name_tv;
    private TextView add_bank_card_province_name_tv;
    private EditText add_bank_card_num_et;
    private EditText add_bank_card_fen_hang_et;
    private EditText add_bank_card_tele_num_et;
    private TextView add_bank_card_user_name_tv;
    private EditText add_bank_card_zhi_hang_et;

    private String bankId = "icbc";
    private String provinceId = "10", cityId = "162";
    private int pos = -1;
    private int post = -1;
    private MyAccountModel myAccountModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_card);
        myAccountModel = getIntent().getParcelableExtra(ExtraConfig.IntentExtraKey.MY_ACCOUNT);
        initView();

    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        add_bank_card_confirm_btn = (Button) findViewById(R.id.add_bank_card_confirm_btn);
        add_bank_card_bank_name_tv = (TextView) findViewById(R.id.add_bank_card_bank_name_tv);
        add_bank_card_city_name_tv = (TextView) findViewById(R.id.add_bank_card_city_name_tv);
        add_bank_card_fen_hang_et = (EditText) findViewById(R.id.add_bank_card_fen_hang_et);
        add_bank_card_num_et = (EditText) findViewById(R.id.add_bank_card_num_et);
        add_bank_card_province_name_tv = (TextView) findViewById(R.id.add_bank_card_province_name_tv);
        add_bank_card_tele_num_et = (EditText) findViewById(R.id.add_bank_card_tele_num_et);
        add_bank_card_user_name_tv = (TextView) findViewById(R.id.add_bank_card_user_name_tv);
        add_bank_card_zhi_hang_et = (EditText) findViewById(R.id.add_bank_card_zhi_hang_et);


        tv_title.setText("添加银行卡");
        iv_back.setOnClickListener(this);
        add_bank_card_bank_name_tv.setOnClickListener(this);
        add_bank_card_province_name_tv.setOnClickListener(this);
        add_bank_card_city_name_tv.setOnClickListener(this);
        add_bank_card_confirm_btn.setOnClickListener(this);
        add_bank_card_user_name_tv.setText(myAccountModel.getUSER_NAME_CONCEAL());

    }

    //获取银行列表
    private void getBankList() {
        BizDataAsyncTask<List<BankModel>> getBankList = new BizDataAsyncTask<List<BankModel>>(getWaitingView()) {
            @Override
            protected List<BankModel> doExecute() throws ZYException, BizFailure {
                return AccountBiz.getSiteBankList();
            }

            @Override
            protected void onExecuteSucceeded(final List<BankModel> bankModels) {

                final BankPopup bp = new BankPopup(ActivityAddBankCard.this, add_bank_card_bank_name_tv, bankModels);
                bp.setPos(new BankPopup.GetPosition() {
                    @Override
                    public void getPos(int position) {
                        add_bank_card_bank_name_tv.setText(bankModels.get(position).getNAME());
                        bankId = bankModels.get(position).getID();
                        bp.dismiss();
                    }
                });

                bp.showAsDropDown(add_bank_card_bank_name_tv);
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityAddBankCard.this, error);
                }
            }
        };
        getBankList.execute();
    }

    //获取省市列表
    private void getArea(final String parentId) {
        BizDataAsyncTask<List<ProvinceModel>> getArea = new BizDataAsyncTask<List<ProvinceModel>>(getWaitingView()) {
            @Override
            protected List<ProvinceModel> doExecute() throws ZYException, BizFailure {
                return AccountBiz.getAreaList(parentId);
            }

            @Override
            protected void onExecuteSucceeded(final List<ProvinceModel> provinceModels) {
                if (parentId.equals("")) {
                    final AreaPopup popup = new AreaPopup(ActivityAddBankCard.this, add_bank_card_province_name_tv,
                            provinceModels);
                    popup.setPosition(new AreaPopup.GetAreaPosition() {
                        @Override
                        public void getPosition(int position) {
                            //设置选择省时, 市清空
                            if (pos != position) {
                                pos = position;
                                add_bank_card_province_name_tv.setText(provinceModels.get(position).getNAME());
                                provinceId = provinceModels.get(position).getID();
                                add_bank_card_city_name_tv.setText("");
                            }
                            popup.dismiss();
                        }
                    });
                    popup.showAsDropDown(add_bank_card_province_name_tv);
                } else {
                    final AreaPopup popup = new AreaPopup(ActivityAddBankCard.this, add_bank_card_city_name_tv,
                            provinceModels);
                    popup.setPosition(new AreaPopup.GetAreaPosition() {
                        @Override
                        public void getPosition(int position) {
                            //设置选择省时, 市清空
                            if (post != position) {
                                post = position;
                                add_bank_card_city_name_tv.setText(provinceModels.get(position).getNAME());
                                cityId = provinceModels.get(position).getID();
                            }
                            popup.dismiss();
                        }
                    });
                    popup.showAsDropDown(add_bank_card_city_name_tv);
                }
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityAddBankCard.this, error);
                }
            }
        };

        getArea.execute();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.add_bank_card_bank_name_tv:
                //所属银行
                getBankList();
                break;
            case R.id.add_bank_card_province_name_tv:
                //所属省
                getArea("");
                break;
            case R.id.add_bank_card_city_name_tv:
                //市
                if (provinceId.equals("")) {
                    return;
                }
                getArea(provinceId);
                break;
            case R.id.add_bank_card_confirm_btn:
                if (invoke()) {
                    bindBank();
                }
                break;

        }
    }

    private boolean invoke() {
        if (add_bank_card_bank_name_tv.getText().toString().equals("")) {
            AlertUtil.t(this, "请选择所属银行");
            return false;
        }
        if (add_bank_card_province_name_tv.getText().toString().equals("")) {
            AlertUtil.t(this, "请选择所在省份");
            return false;
        }
        if (add_bank_card_city_name_tv.getText().toString().equals("")) {
            AlertUtil.t(this, "请选择所在城市");
            return false;
        }

        if (add_bank_card_fen_hang_et.getText().toString().equals("")) {
            AlertUtil.t(this, "请输入开户行分行名称");
            return false;
        }
        if (add_bank_card_zhi_hang_et.getText().toString().equals("")) {
            AlertUtil.t(this, "请输入开户行分支行名称");
            return false;
        }
        if (add_bank_card_num_et.getText().toString().equals("")) {
            AlertUtil.t(this, "请输入银行卡号");
            return false;
        }
        if (add_bank_card_tele_num_et.getText().toString().equals("")) {
            AlertUtil.t(this, "请输入银行预留手机号");
            return false;
        }
        return true;
    }

    // 绑定银行卡
    private void bindBank() {
        BizDataAsyncTask<String> bindBank = new BizDataAsyncTask<String>(getWaitingView()) {
            @Override
            protected String doExecute() throws ZYException, BizFailure {
                return AccountBiz.bindBank(bankId, provinceId, cityId, add_bank_card_fen_hang_et.getEditableText().toString()
                        , add_bank_card_zhi_hang_et.getEditableText().toString(), add_bank_card_num_et.getEditableText().toString(),
                        add_bank_card_tele_num_et.getEditableText().toString());
            }

            @Override
            protected void onExecuteSucceeded(String s) {
                AlertUtil.t(ActivityAddBankCard.this, "银行卡绑定成功");
                finish();
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(ActivityAddBankCard.this, error);
                }
            }
        };
        bindBank.execute();
    }

}
