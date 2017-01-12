package com.hrb.ui.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.R;
import com.hrb.ui.base.BaseFragment;

import static com.hrb.R.id.more_about_us_ll;
import static com.hrb.R.id.more_contact_us_ll;
import static com.hrb.R.id.more_news_center_ll;

/**
 * Created by Ls on 2016/10/9.
 */

public class FragmentMore extends BaseFragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(more_contact_us_ll).setOnClickListener(this);
        view.findViewById(more_about_us_ll).setOnClickListener(this);
        view.findViewById(more_news_center_ll).setOnClickListener(this);

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.INVISIBLE);
        tv_title.setText("更多");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case more_contact_us_ll:
                //联系我们
                Intent intent = new Intent(getActivity(), ActivityContactUs.class);
                startActivity(intent);
                break;
            case more_about_us_ll:
                //关于我们
                Intent intent1 = new Intent(getActivity(), ActivityCompanyProfile.class);
                startActivity(intent1);
                break;
            case more_news_center_ll:
                //新闻中心
                Intent intent2 = new Intent(getActivity(), ActivityNewsCenter.class);
                startActivity(intent2);
                break;
        }
    }
}
