package com.hrb.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hrb.R;
import com.hrb.biz.FinanceBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.RedPacketModel;
import com.hrb.ui.finance.ActivityInvestDetailImmediately;
import com.hrb.utils.android.DialogInterface;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;

import java.util.ArrayList;
import java.util.List;

/***
 * 红包进入该页面
 *
 * @author Administrator
 */
public class RedSelectDialog extends Dialog implements View.OnClickListener {
    private ImageButton close;// 关闭
    private Button selectRed;// 使用红包
    private ListView dialog_list;
    private MyAdapter adapter;
    private Context context;
    private String money;
    DialogInterface dialogInterface;
    private String productId;//标的id
    private List<RedPacketModel> list = new ArrayList<>();

    public RedSelectDialog(Context context, int theme, String money, String productId, DialogInterface dialogInterface) {
        super(context, theme);
        this.context = context;
        this.money = money;
        this.productId = productId;
        this.dialogInterface = dialogInterface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_use_red_packet);
        initView();
        initHoldingList();
    }

    // 访问接口获取数据
    private void initHoldingList() {
        BizDataAsyncTask<List<RedPacketModel>> getRedList = new BizDataAsyncTask<List<RedPacketModel>>() {
            @Override
            protected List<RedPacketModel> doExecute() throws ZYException, BizFailure {
                return FinanceBiz.getRedPacket(productId);
            }

            @SuppressWarnings("deprecation")
            @Override
            protected void onExecuteSucceeded(List<RedPacketModel> result) {

                for (int i = 0; i < result.size(); i++) {
                    adapter.addItem(result.get(i));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            protected void OnExecuteFailed(String error) {
                if (!StringUtil.isEmpty(error)) {
                    AlertUtil.t(context, error);
                }
            }

        };

        getRedList.setWaitingView(((ActivityInvestDetailImmediately) context).getWaitingView());
        getRedList.execute();

    }


    private void initView() {
        adapter = new MyAdapter();
        close = (ImageButton) findViewById(R.id.close);
        close.setOnClickListener(this);
        selectRed = (Button) findViewById(R.id.select_red);
        selectRed.setOnClickListener(this);
        dialog_list = (ListView) findViewById(R.id.dialog_list);
        dialog_list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:// 关闭
                dismiss();
                break;
            case R.id.select_red:// 使用红包
                String position = adapter.getNum();
                if (!"".equals(position)) {
                    dialogInterface.selectRedPacket(true, list.get(Integer.parseInt(position)));
                    dismiss();
                } else {
                    AlertUtil.t(context, "您没有选中任何红包");
                }
            default:
                break;
        }
    }

    class MyAdapter extends BaseAdapter {
        private String num = "";

        @Override
        public int getCount() {
            return list.size();
        }

        // 添加数据
        public void addItem(RedPacketModel model) {
            list.add(model);
        }

        public String getNum() {

            return num;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHodler;
            if (convertView == null) {
                viewHodler = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_dialog, null);
                viewHodler.endTime = (TextView) convertView.findViewById(R.id.dialog_end_time);
                viewHodler.useCondition = (TextView) convertView.findViewById(R.id.dialog_use_condition);
                viewHodler.money = (TextView) convertView.findViewById(R.id.dialog_money);
                viewHodler.checkBox = (CheckBox) convertView.findViewById(R.id.dialog_checkbox);
                viewHodler.layout_dialog = (LinearLayout) convertView.findViewById(R.id.layout_dialog);
                convertView.setTag(viewHodler);
            } else {
                viewHodler = (ViewHolder) convertView.getTag();
            }
            if (!"".equals(num)) {
                if ((position + "").equals(num))
                    viewHodler.checkBox.setChecked(true);
                else
                    viewHodler.checkBox.setChecked(false);
            } else {
                viewHodler.checkBox.setChecked(false);
            }

            viewHodler.endTime.setText(list.get(position).getEnd_Time());
            viewHodler.money.setText(list.get(position).getRed_Money());
            viewHodler.useCondition.setText("投资满" + list.get(position).getMinItender() + "元使用");

            if (Integer.parseInt(money) < Integer.parseInt(list.get(position).getMinItender())) {
                viewHodler.layout_dialog.setBackgroundColor(Color.parseColor("#f0f0f0"));
            } else {
                viewHodler.layout_dialog.setBackgroundColor(Color.parseColor("#ffffff"));
            }


            viewHodler.layout_dialog.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(money) >= Integer.parseInt(list.get(position).getMinItender())) {
                        if ("".equals(num)) {
                            num = position + "";
                        } else {
                            if ((position + "").equals(num))
                                num = "";
                            else
                                num = position + "";
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        AlertUtil.t(context, "当前红包不可用，请重新选择");
                    }
                }
            });

            return convertView;
        }

        class ViewHolder {
            // * 是否选中但红包
            CheckBox checkBox;
            // * 有效期
            TextView endTime;
            // *使用条件
            TextView useCondition;
            // *金额
            TextView money;
            LinearLayout layout_dialog;
        }
    }

}
