package com.yd.mofanghuanyuans.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xzq.module_base.bean.MarketPkgsBean;
import com.yd.mofanghuanyuans.R;

import java.util.ArrayList;

public class GwhpPopupwindowAdapter extends BaseAdapter {
    private ArrayList<MarketPkgsBean> mList;
    private Context mContext;

    public GwhpPopupwindowAdapter(Context mContext, ArrayList<MarketPkgsBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.size() > 0 ? mList.get(position) : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gwhp_popupwindowr_item, null);

            viewHolder.ll_xz = convertView.findViewById(R.id.ll_xz);
            viewHolder.img_icon = convertView.findViewById(R.id.img_icon);
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MarketPkgsBean marketPkgsBean = mList.get(position);
        viewHolder.img_icon.setImageDrawable(marketPkgsBean.getIcon());
        viewHolder.tv_name.setText(marketPkgsBean.getAppName());

        return convertView;
    }

    static class ViewHolder {

        LinearLayout ll_xz;
        ImageView img_icon;
        TextView tv_name;
    }
}
