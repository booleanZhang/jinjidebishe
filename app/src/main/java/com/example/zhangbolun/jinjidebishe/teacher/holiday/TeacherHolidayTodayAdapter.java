package com.example.zhangbolun.jinjidebishe.teacher.holiday;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangbolun.jinjidebishe.R;
import com.example.zhangbolun.jinjidebishe.currentUser.Teacher;

import java.util.List;

/**
 * Created by zhangbolun on 2017/5/4.
 */

public class TeacherHolidayTodayAdapter extends RecyclerView.Adapter<TeacherHolidayTodayAdapter.ViewHolder> {
    private Context mContext;
    private List<TeacherHolidayToday> mTeacherHolidayTodayList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView name;
        TextView posttime;
        TextView reason;
        TextView state;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            name=(TextView)view.findViewById(R.id.holiday_teacher_today_item_name);
            posttime=(TextView)view.findViewById(R.id.holiday_teacher_today_item_posttime);
            reason=(TextView)view.findViewById(R.id.holiday_teacher_today_item_reason);
            state=(TextView)view.findViewById(R.id.holiday_teacher_today_item_state);
        }
    }

    public TeacherHolidayTodayAdapter(List<TeacherHolidayToday> teacherHolidayTodayList){
        mTeacherHolidayTodayList=teacherHolidayTodayList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view=LayoutInflater.from(mContext).inflate(R.layout.holiday_teacher_today_item,parent,false);
        final ViewHolder holder =new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                TeacherHolidayToday teacherHolidayToday=mTeacherHolidayTodayList.get(position);
                Intent intent=new Intent(mContext,TeacherHolidayTodayDeatilActivity.class);
                if(null==teacherHolidayToday.getAbsence()){
                    intent.putExtra("name","无签到信息");
                    intent.putExtra("id","");
                    intent.putExtra("date","");
                    intent.putExtra("reason","");
                    intent.putExtra("posttime","");
                    intent.putExtra("absence","");
                    mContext.startActivity(intent);
                }else{
                    intent.putExtra("name",teacherHolidayToday.getName());
                    intent.putExtra("id",teacherHolidayToday.getId());
                    intent.putExtra("date",teacherHolidayToday.getDate());
                    intent.putExtra("reason",teacherHolidayToday.getReason());
                    intent.putExtra("posttime",teacherHolidayToday.getPosttime());
                    intent.putExtra("absence",teacherHolidayToday.getAbsence());
                    mContext.startActivity(intent);
                }
            }
        });
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        TeacherHolidayToday teacherHolidayToday=mTeacherHolidayTodayList.get(position);
        if(null==teacherHolidayToday.getAbsence()){
            holder.cardView.setVisibility(View.GONE);
        }else{
            if(teacherHolidayToday.getAbsence().equals("8")){
                //等待教师确认
                holder.cardView.setBackgroundColor(android.graphics.Color.parseColor("#B9B900"));
                holder.name.setText(teacherHolidayToday.getName());
                holder.posttime.setText(teacherHolidayToday.getPosttime());
                holder.reason.setText("\u3000\u3000"+teacherHolidayToday.getReason());
                holder.state.setText("等待教师确认");
            }else if(teacherHolidayToday.getAbsence().equals("7")){
                //教师不准假
                holder.cardView.setBackgroundColor(android.graphics.Color.parseColor("#CD0000"));
                holder.name.setText(teacherHolidayToday.getName());
                holder.posttime.setText(teacherHolidayToday.getPosttime());
                holder.reason.setText("\u3000\u3000"+teacherHolidayToday.getReason());
                holder.state.setText("未批准请假");
            }else if(teacherHolidayToday.getAbsence().equals("1")){
                //教师准假
                holder.cardView.setBackgroundColor(android.graphics.Color.parseColor("#005800"));
                holder.name.setText(teacherHolidayToday.getName());
                holder.posttime.setText(teacherHolidayToday.getPosttime());
                holder.reason.setText("\u3000\u3000"+teacherHolidayToday.getReason());
                holder.state.setText("请假已被批准");
            }else if(teacherHolidayToday.getAbsence().equals("9")){
                holder.itemView.setVisibility(View.GONE);
                holder.cardView.setVisibility(View.GONE);
                holder.state.setVisibility(View.GONE);
                holder.name.setVisibility(View.GONE);
                holder.posttime.setVisibility(View.GONE);
                holder.reason.setVisibility(View.GONE);
            }else if(teacherHolidayToday.getAbsence().equals("6")){
                holder.itemView.setVisibility(View.GONE);
                holder.cardView.setVisibility(View.GONE);
                holder.state.setVisibility(View.GONE);
                holder.name.setVisibility(View.GONE);
                holder.posttime.setVisibility(View.GONE);
                holder.reason.setVisibility(View.GONE);
            }
        }
    }

    public int getItemCount(){
        return mTeacherHolidayTodayList.size();
    }
}
