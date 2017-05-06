package com.example.zhangbolun.jinjidebishe.teacher.holiday;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangbolun.jinjidebishe.R;

import java.util.List;

/**
 * Created by zhangbolun on 2017/5/4.
 */

public class TeacherHolidayMonthAdapter extends RecyclerView.Adapter<TeacherHolidayMonthAdapter.ViewHolder> {
    private Context mContext;
    private List<TeacherHolidayMonth> mTeacherHolidayMonthList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        CardView cardView1;
        TextView name;
        TextView id;
        TextView date;
        TextView reason;
        TextView posttime;
        TextView state;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            cardView1=(CardView)view.findViewById(R.id.holiday_teacher_item_cardview);
            name=(TextView)view.findViewById(R.id.holiday_teacher_item_name);
            id=(TextView)view.findViewById(R.id.holiday_teacher_item_id);
            date=(TextView)view.findViewById(R.id.holiday_teacher_item_date);
            reason=(TextView)view.findViewById(R.id.holiday_teacher_item_reason);
            posttime=(TextView)view.findViewById(R.id.holiday_teacher_item_posttime);
            state=(TextView)view.findViewById(R.id.holiday_teacher_item_state);
        }
    }

    public TeacherHolidayMonthAdapter(List<TeacherHolidayMonth> teacherHolidayMonthList){
        mTeacherHolidayMonthList=teacherHolidayMonthList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.holiday_teacher_item,parent,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        TeacherHolidayMonth teacherHolidayMonth=mTeacherHolidayMonthList.get(position);
        if (null==teacherHolidayMonth.getAbsence()){
            holder.cardView1.setVisibility(View.GONE);
            holder.cardView.setVisibility(View.GONE);
            Toast.makeText(mContext, "无请假信息", Toast.LENGTH_SHORT).show();
        }else{
            holder.name.setText(teacherHolidayMonth.getName());
            holder.id.setText(teacherHolidayMonth.getId());
            holder.date.setText(teacherHolidayMonth.getDate());
            holder.reason.setText("\u3000\u3000"+teacherHolidayMonth.getReason());
            holder.posttime.setText(teacherHolidayMonth.getPosttime());
            if(teacherHolidayMonth.getAbsence().equals("9")){
                holder.cardView.setBackgroundColor(android.graphics.Color.parseColor("#B9B900"));
                holder.state.setText("等待家长签字");
            }else if(teacherHolidayMonth.getAbsence().equals("8")){
                holder.cardView.setBackgroundColor(android.graphics.Color.parseColor("#B9B900"));
                holder.state.setText("等待教师确认");
            }else if(teacherHolidayMonth.getAbsence().equals("7")){
                holder.cardView.setBackgroundColor(android.graphics.Color.parseColor("#CD0000"));
                holder.state.setText("家长拒绝签字");
            }else if(teacherHolidayMonth.getAbsence().equals("6")){
                holder.cardView.setBackgroundColor(android.graphics.Color.parseColor("#CD0000"));
                holder.state.setText("教师拒绝批准");
            }else if(teacherHolidayMonth.getAbsence().equals("1")){
                holder.cardView.setBackgroundColor(android.graphics.Color.parseColor("#005800"));
                holder.state.setText("教师准假");
            }else{
                holder.cardView1.setVisibility(View.GONE);
                holder.cardView.setVisibility(View.GONE);
            }
        }
    }

    public int getItemCount(){
        return mTeacherHolidayMonthList.size();
    }
}
