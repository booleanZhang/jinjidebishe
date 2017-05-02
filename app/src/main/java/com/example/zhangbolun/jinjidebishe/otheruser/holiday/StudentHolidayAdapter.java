package com.example.zhangbolun.jinjidebishe.otheruser.holiday;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangbolun.jinjidebishe.HolidayActivity;
import com.example.zhangbolun.jinjidebishe.R;

import java.util.List;

/**
 * Created by zhangbolun on 2017/5/2.
 */

public class StudentHolidayAdapter extends RecyclerView.Adapter<StudentHolidayAdapter.ViewHolder> {
    private Context mContext;
    private List<StudentHoliday> mStudentHolidayList;
    private String TAG="";

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView date;
        TextView state;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            date=(TextView)view.findViewById(R.id.holiday_student_item_date);
            state=(TextView)view.findViewById(R.id.holiday_student_item_state);
        }
    }

    public StudentHolidayAdapter(List<StudentHoliday> studentHolidayList){
        mStudentHolidayList=studentHolidayList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.holiday_student_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Log.d(TAG, "======"+Integer.toString(position));
                StudentHoliday studentHoliday=mStudentHolidayList.get(position+1);
                Intent intent=new Intent(mContext, HolidayActivity.class);
                intent.putExtra("date",studentHoliday.getDate());
                intent.putExtra("student",studentHoliday.getStudent());
                intent.putExtra("absence",studentHoliday.getAbsence());
                intent.putExtra("posttime",studentHoliday.getPosttime());
                intent.putExtra("reason",studentHoliday.getReason());
                mContext.startActivity(intent);
            }
        });
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        StudentHoliday studentHoliday=mStudentHolidayList.get(position);
        holder.date.setText(studentHoliday.getDate());
        if(studentHoliday.getAbsence().equals("9")){
            //等待家长确认
            holder.date.setBackgroundColor(android.graphics.Color.parseColor("#B9B900"));
            holder.state.setBackgroundColor(android.graphics.Color.parseColor("#B9B900"));
            holder.state.setText("当前状态：等待家长确认");
        }else if(studentHoliday.getAbsence().equals("8")){
            //家长签字完成,等待教师准假
            holder.date.setBackgroundColor(android.graphics.Color.parseColor("#B9B900"));
            holder.state.setBackgroundColor(android.graphics.Color.parseColor("#B9B900"));
            holder.state.setText("当前状态：等待教师准假");
        }else if(studentHoliday.getAbsence().equals("7")){
            //教师不准假，请假完成
            holder.date.setBackgroundColor(android.graphics.Color.parseColor("#CD0000"));
            holder.state.setBackgroundColor(android.graphics.Color.parseColor("#CD0000"));
            holder.state.setText("当前状态：教师不准假");
        }else if(studentHoliday.getAbsence().equals("1")){
            //教师准假，请假完成
            holder.date.setBackgroundColor(android.graphics.Color.parseColor("#005800"));
            holder.state.setBackgroundColor(android.graphics.Color.parseColor("#005800"));
            holder.state.setText("当前状态：教师准假");
        }
    }

    public int getItemCount(){
        return mStudentHolidayList.size();
    }
}
