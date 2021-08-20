package com.yd.mofanghuanyuans.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xzq.module_base.bean.MofangBean;
import com.xzq.module_base.views.DrawableTextView;
import com.yd.mofanghuanyuans.R;

import java.util.ArrayList;

public class YIkanAdapter extends RecyclerView.Adapter {
    private ArrayList<MofangBean.ListDTO> list;
    private Context context;

    public YIkanAdapter(ArrayList<MofangBean.ListDTO> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_recycvideo, parent, false);
        YIkanAdapter.MyHolder myHolder = new YIkanAdapter.MyHolder(inflate);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder=(MyHolder) holder;
        Glide.with(context).load(list.get(position).getCoverImgUrl()).into(myHolder.image);
        myHolder.spbt.setText(list.get(position).getTags());
        myHolder.rs.setText(list.get(position).getName());
      myHolder.mRead.setText(list.get(position).getViewTimes()+"人观看");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jiekouhuidiao.OnClike(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView spbt;
        TextView rs;
       TextView mRead;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_img);
            spbt = itemView.findViewById(R.id.tv_jichu);
            rs = itemView.findViewById(R.id.titlej);
            mRead = itemView.findViewById(R.id.tv_read);
        }
    }
    public interface  Jiekouhuidiao{
        void OnClike(int position);
    }
    Jiekouhuidiao jiekouhuidiao;
    public void setJiekouhuidiaos(Jiekouhuidiao jiekouhuidiao) {
        this.jiekouhuidiao = jiekouhuidiao;
    }
}
