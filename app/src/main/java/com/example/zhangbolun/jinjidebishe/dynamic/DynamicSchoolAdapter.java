package com.example.zhangbolun.jinjidebishe.dynamic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
 * Created by zhangbolun on 2017/5/14.
 */

public class DynamicSchoolAdapter extends RecyclerView.Adapter<DynamicSchoolAdapter.ViewHolder> {
    private Context mContext;
    private List<DynamicSchool> mDynamicSchoolList;

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
            circleImageView=(CircleImageView)view.findViewById(R.id.activity_dynamic_school_item_circleImageView);
            name=(TextView)view.findViewById(R.id.activity_dynamic_school_item_name);
            content=(TextView)view.findViewById(R.id.activity_dynamic_school_item_content);
            imageView=(ImageView)view.findViewById(R.id.activity_dynamic_school_item_imageView);
            imageViewComment=(ImageView)view.findViewById(R.id.activity_dynamic_school_item_comments);
            date=(TextView)view.findViewById(R.id.activity_dynamic_school_item_date);
            time=(TextView)view.findViewById(R.id.activity_dynamic_school_item_time);
        }
    }

    public DynamicSchoolAdapter(List<DynamicSchool> dynamicSchoolList){
        mDynamicSchoolList=dynamicSchoolList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view=LayoutInflater.from(mContext).inflate(R.layout.activity_dynamic_school_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                DynamicSchool dynamicSchool=mDynamicSchoolList.get(position);
                Intent intent=new Intent(mContext,DynamicActivity.class);
                intent.putExtra("id",dynamicSchool.getId());
                intent.putExtra("senderHead",dynamicSchool.getSenderHead());
                intent.putExtra("sender",dynamicSchool.getSender());
                intent.putExtra("mark",dynamicSchool.getMark());
                intent.putExtra("content",dynamicSchool.getContent());
                intent.putExtra("picture",dynamicSchool.getPicture());
                intent.putExtra("date",dynamicSchool.getDate());
                intent.putExtra("time",dynamicSchool.getTime());
                intent.putExtra("currentUser",dynamicSchool.getCurrent_user());
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        DynamicSchool dynamicSchool=mDynamicSchoolList.get(position);
        if(dynamicSchool.getSenderHead()!=null){
            if(!dynamicSchool.getSenderHead().isEmpty()){
                Glide.with(mContext).load(dynamicSchool.getSenderHead()).into(holder.circleImageView);
            }
        }
        holder.name.setText(dynamicSchool.getSender());
        holder.content.setText(dynamicSchool.getContent());
        if(dynamicSchool.getPicture()!=null){
            if(!dynamicSchool.getPicture().isEmpty()){
                Glide.with(mContext).load(dynamicSchool.getPicture()).into(holder.imageView);
            }else{
                holder.imageView.setVisibility(View.GONE);
            }
        }else{
            holder.imageView.setVisibility(View.GONE);
        }
        holder.date.setText(dynamicSchool.getDate());
        holder.time.setText(dynamicSchool.getTime());
    }
    
    public int getItemCount(){
        return mDynamicSchoolList.size();
    }
}
