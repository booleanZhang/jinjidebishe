package com.example.zhangbolun.jinjidebishe.otheruser.holiday;

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
 * Created by zhangbolun on 2017/5/3.
 */

public class ParentHolidayAdapter extends RecyclerView.Adapter<ParentHolidayAdapter.ViewHolder> {
    private Context mContext;
    private List<HolidayOlddayParent> mHolidayOlddayParentList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView date;
        TextView state;
        TextView posttime;
        TextView reason;

        public ViewHolder(View view) {
            super(view);
            cardView=(CardView)view;
            date=(TextView) view.findViewById(R.id.holiday_parent_item_date);
            state=(TextView)view.findViewById(R.id.holiday_parent_item_state);
            posttime=(TextView) view.findViewById(R.id.holiday_parent_item_posttime);
            reason=(TextView) view.findViewById(R.id.holiday_parent_item_reason);

        }
    }

    public ParentHolidayAdapter(List<HolidayOlddayParent> holidayOlddayParentList){
        mHolidayOlddayParentList=holidayOlddayParentList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.holiday_parent_item,parent,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        HolidayOlddayParent holidayOlddayParent=mHolidayOlddayParentList.get(position);
        if(holidayOlddayParent.getAbsence().equals("9")){
            holder.cardView.setBackgroundColor(android.graphics.Color.parseColor("#B9B900"));
            holder.date.setText(holidayOlddayParent.getDate());
            holder.state.setText("家长未确认");
            holder.posttime.setText(holidayOlddayParent.getPosttime());
            holder.reason.setText(holidayOlddayParent.getReason());
        }else if(holidayOlddayParent.getAbsence().equals("8")){
            holder.cardView.setBackgroundColor(android.graphics.Color.parseColor("#B9B900"));
            holder.date.setText(holidayOlddayParent.getDate());
            holder.state.setText("教师未确认");
            holder.posttime.setText(holidayOlddayParent.getPosttime());
            holder.reason.setText(holidayOlddayParent.getReason());
        }else if(holidayOlddayParent.getAbsence().equals("7")){
            holder.cardView.setBackgroundColor(android.graphics.Color.parseColor("#CD0000"));
            holder.date.setText(holidayOlddayParent.getDate());
            holder.state.setText("教师未批准");
            holder.posttime.setText(holidayOlddayParent.getPosttime());
            holder.reason.setText(holidayOlddayParent.getReason());
        }else if(holidayOlddayParent.getAbsence().equals("6")){
            holder.cardView.setBackgroundColor(android.graphics.Color.parseColor("#CD0000"));
            holder.date.setText(holidayOlddayParent.getDate());
            holder.state.setText("家长未批准");
            holder.posttime.setText(holidayOlddayParent.getPosttime());
            holder.reason.setText(holidayOlddayParent.getReason());
        }else if(holidayOlddayParent.getAbsence().equals("1")){
            holder.cardView.setBackgroundColor(android.graphics.Color.parseColor("#005800"));
            holder.date.setText(holidayOlddayParent.getDate());
            holder.state.setText("请假被批准");
            holder.posttime.setText(holidayOlddayParent.getPosttime());
            holder.reason.setText(holidayOlddayParent.getReason());
        }
    }

    public int getItemCount(){
        return mHolidayOlddayParentList.size();
    }
}
