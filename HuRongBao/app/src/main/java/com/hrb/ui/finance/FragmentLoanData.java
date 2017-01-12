package com.hrb.ui.finance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrb.HuRongBaoApp;
import com.hrb.R;
import com.hrb.biz.FinanceBiz;
import com.hrb.biz.exception.BizFailure;
import com.hrb.biz.exception.ZYException;
import com.hrb.biz.task.BizDataAsyncTask;
import com.hrb.model.GetBorrowBaseInfoModel;
import com.hrb.photo.ShowBigImage;
import com.hrb.ui.base.BaseFragment;
import com.hrb.ui.widget.NoScorllGridview;
import com.hrb.utils.java.AlertUtil;
import com.hrb.utils.java.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ls on 2016/10/20.
 */

public class FragmentLoanData extends BaseFragment {

    private NoScorllGridview gridView;
    private MyadapterBottom myadapterBottom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loan_data, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = (NoScorllGridview) view.findViewById(R.id.loan_data_gv);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myadapterBottom = new MyadapterBottom(getActivity());
        gridView.setAdapter(myadapterBottom);
        getBorrowBaseInfo();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(getActivity(), ShowBigImage.class);
                intent.putStringArrayListExtra("images", myadapterBottom.getList());
                intent.putExtra("currentItem", arg2);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        HuRongBaoApp.buildCacheDir();

    }

    private void getBorrowBaseInfo() {

        BizDataAsyncTask<List<GetBorrowBaseInfoModel>> getBorrowBaseData = new
                BizDataAsyncTask<List<GetBorrowBaseInfoModel>>(getWaitingView()) {
                    @Override
                    protected List<GetBorrowBaseInfoModel> doExecute() throws ZYException, BizFailure {
                        return FinanceBiz.getBorrowBaseData(((ActivityInvestDetailMore) getActivity()).getBorrowId());
                    }

                    @Override
                    protected void onExecuteSucceeded(List<GetBorrowBaseInfoModel> getBorrowBaseInfoModel) {
                        myadapterBottom.setList(getBorrowBaseInfoModel);
                    }

                    @Override
                    protected void OnExecuteFailed(String error) {
                        if (!StringUtil.isEmpty(error)) {
                            AlertUtil.t(getActivity(), error);
                        }
                    }
                };

        getBorrowBaseData.execute();
    }

    /**
     * 资质证照
     *
     * @author Administrator
     */
    class MyadapterBottom extends BaseAdapter {
        public List<GetBorrowBaseInfoModel> list = new ArrayList<>();
        private Context context;


        public MyadapterBottom(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        // 添加数据
        public void addItem(GetBorrowBaseInfoModel url) {
            list.add(url);
        }

        public void setList(List<GetBorrowBaseInfoModel> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        public ArrayList<String> getList() {
            ArrayList<String> imgStringArray = new ArrayList<>();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    imgStringArray.add(list.get(i).getFILE_ID());
                }
            }
            return imgStringArray;
        }

        // 移除所有数据
        public void removeAll() {
            if (list != null && list.size() > 0) {
                for (int i = list.size() - 1; i >= 0; i--) {
                    list.remove(i);
                }
            }
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
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHodler;
            if (convertView == null) {
                viewHodler = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.item_loan_data, null);
                viewHodler.img = (ImageView) convertView
                        .findViewById(R.id.loan_image);
                viewHodler.tv = (TextView) convertView.findViewById(R.id.loan_title);
                convertView.setTag(viewHodler);
            } else {
                viewHodler = (ViewHolder) convertView.getTag();
            }
            viewHodler.tv.setText(list.get(position).getIMG_DESCRIPTION());
            ImageLoader.getInstance().displayImage(list.get(position).getFILE_ID(), viewHodler.img);

            return convertView;
        }

        class ViewHolder {
            ImageView img;
            TextView tv;
        }
    }
}
