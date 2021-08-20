package com.yd.mofanghuanyuans.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xzq.module_base.adapter.BaseRecyclerAdapter;
import com.xzq.module_base.bean.MofangBean;
import com.yd.mofanghuanyuans.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MfrmAdapter extends RecyclerView.Adapter {
    private ArrayList<MofangBean.ListDTO> list;
    private Context context;

    public MfrmAdapter(ArrayList<MofangBean.ListDTO> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_rm, parent, false);
       MyHolder myHolder = new MyHolder(inflate);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
     MyHolder myHolder=(MyHolder) holder;
        Glide.with(context).load(list.get(position).getCoverImgUrl()).into(myHolder.image);

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

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ia);

        }
    }
    public interface  Jiekouhuidiao{
        void OnClike(int position);
    }
    Jiekouhuidiao jiekouhuidiao;
    public void setJiekouhuidiaos(MfrmAdapter.Jiekouhuidiao jiekouhuidiao) {
        this.jiekouhuidiao = jiekouhuidiao;
    }
}
