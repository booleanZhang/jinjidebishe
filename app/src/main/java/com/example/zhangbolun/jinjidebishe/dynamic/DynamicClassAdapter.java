package com.example.zhangbolun.jinjidebishe.dynamic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhangbolun.jinjidebishe.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhangbolun on 2017/5/13.
 */

public class DynamicClassAdapter extends RecyclerView.Adapter<DynamicClassAdapter.ViewHolder> {
    private Context mContext;
    private List<DynamicClass> mDynamicClassList;

    private String TAG="adapter";

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        CircleImageView circleImageView;
        TextView name;
        TextView content;
        ImageView imageView;
        ImageView imageViewComment;
        TextView date;
        TextView time;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            circleImageView=(CircleImageView) view.findViewById(R.id.activity_dynamic_class_item_circleImageView);
            name=(TextView)view.findViewById(R.id.activity_dynamic_class_item_name);
            content=(TextView)view.findViewById(R.id.activity_dynamic_class_item_content);
            imageView=(ImageView) view.findViewById(R.id.activity_dynamic_class_item_imageView);
            imageViewComment=(ImageView)view.findViewById(R.id.activity_dynamic_class_item_comments);
            date=(TextView)view.findViewById(R.id.activity_dynamic_class_item_date);
            time=(TextView)view.findViewById(R.id.activity_dynamic_class_item_time);
        }
    }

    public DynamicClassAdapter(List<DynamicClass> dynamicClassList){
        mDynamicClassList=dynamicClassList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.activity_dynamic_class_item,parent,false);

        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                DynamicClass dynamicClass=mDynamicClassList.get(position);
                Intent intent=new Intent(mContext,DynamicActivity.class);
                intent.putExtra("id",dynamicClass.getId());
                intent.putExtra("senderHead",dynamicClass.getSenderHead());
                intent.putExtra("sender",dynamicClass.getSender());
                intent.putExtra("mark",dynamicClass.getMark());
                intent.putExtra("content",dynamicClass.getContent());
                intent.putExtra("picture",dynamicClass.getPicture());
                intent.putExtra("date",dynamicClass.getDate());
                intent.putExtra("time",dynamicClass.getTime());
                intent.putExtra("currentUser",dynamicClass.getCurrent_user());
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        DynamicClass dynamicClass=mDynamicClassList.get(position);
        if(dynamicClass.getSenderHead()!=null){
            if(!dynamicClass.getSenderHead().isEmpty()){
                Glide.with(mContext).load(dynamicClass.getSenderHead()).into(holder.circleImageView);
            }
        }
        holder.name.setText(dynamicClass.getSender());
        holder.content.setText(dynamicClass.getContent());
        if(dynamicClass.getPicture()!=null){
            if(!dynamicClass.getPicture().isEmpty()){
                Log.d(TAG, dynamicClass.getPicture());
                holder.imageView.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(dynamicClass.getPicture()).into(holder.imageView);
            }else{
                holder.imageView.setVisibility(View.GONE);
            }
        }else{
            holder.imageView.setVisibility(View.GONE);
        }
        holder.date.setText(dynamicClass.getDate());
        holder.time.setText(dynamicClass.getTime());
    }

    public int getItemCount(){
        return mDynamicClassList.size();
    }
}
