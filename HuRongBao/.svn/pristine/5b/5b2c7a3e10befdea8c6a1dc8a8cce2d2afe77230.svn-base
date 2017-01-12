package com.hrb.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hrb.R;
import com.hrb.model.ProvinceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/18.
 */

public class AreaPopup extends PopupWindow implements AdapterView.OnItemClickListener {

    private ArrayAdapter<String> adapter;
    private View view;

    public AreaPopup(Context context, TextView textView, List<ProvinceModel> provinceModels) {
        super(context);

        view = LayoutInflater.from(context).inflate(R.layout.popup_bind_bank_card, null);
        setContentView(view);
        setWidth(textView.getWidth());
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        List<String> bankName = new ArrayList<>();
        for (ProvinceModel provinceModel : provinceModels) {
            bankName.add(provinceModel.getNAME());
        }

        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, bankName);
        ListView lvBank = (ListView) view.findViewById(R.id.lv_popup_bind_bank_card);
        lvBank.setAdapter(adapter);

        // 设置popupWindow点击任意地址不可见需设置这个方法
        setFocusable(true);
        setBackgroundDrawable(context.getResources().getDrawable(R.color.grey_deep));
        setOutsideTouchable(true);
        lvBank.setOnItemClickListener(this);
    }

    private GetAreaPosition position1;

    public void setPosition(GetAreaPosition position) {
        this.position1 = position;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position1 != null) {
            position1.getPosition(position);
        }
    }


    public interface GetAreaPosition {
        void getPosition(int position);
    }
}
