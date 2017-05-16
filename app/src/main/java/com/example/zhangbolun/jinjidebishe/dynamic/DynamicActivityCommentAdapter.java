package com.example.zhangbolun.jinjidebishe.dynamic;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangbolun.jinjidebishe.R;

import java.util.List;

/**
 * Created by zhangbolun on 2017/5/15.
 */

public class DynamicActivityCommentAdapter extends RecyclerView.Adapter<DynamicActivityCommentAdapter.ViewHolder> {
    private Context mContext;
    private List<DynamicActivityCommentItem> mDynamicActivityItemList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView name;
        TextView content;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            name=(TextView)view.findViewById(R.id.activity_dynamic_comment_item_name);
            content=(TextView)view.findViewById(R.id.activity_dynamic_comment_item_content);
        }
    }

    public DynamicActivityCommentAdapter(List<DynamicActivityCommentItem> dynamicActivityCommentItemList){
        mDynamicActivityItemList=dynamicActivityCommentItemList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view=LayoutInflater.from(mContext).inflate(R.layout.activity_dynamic_comment_item,parent,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        DynamicActivityCommentItem dynamicActivityCommentItem=mDynamicActivityItemList.get(position);
        holder.name.setText(dynamicActivityCommentItem.getName()+":");
        holder.content.setText(dynamicActivityCommentItem.getContent());
    }

    public int getItemCount(){
        return mDynamicActivityItemList.size();
    }
}
