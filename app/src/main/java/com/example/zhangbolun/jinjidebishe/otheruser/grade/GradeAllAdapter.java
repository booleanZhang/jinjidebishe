package com.example.zhangbolun.jinjidebishe.otheruser.grade;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangbolun.jinjidebishe.R;

import java.util.List;

/**
 * Created by zhangbolun on 2017/5/22.
 */

public class GradeAllAdapter extends RecyclerView.Adapter<GradeAllAdapter.ViewHolder> {
    private Context mContext;
    private List<GradeAll> mGradeAllList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView courseName;
        TextView courseId;
        TextView testTime;
        TextView grade;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            courseName=(TextView) view.findViewById(R.id.item_grade_all_courseName);
            courseId=(TextView) view.findViewById(R.id.item_grade_all_courseId);
            testTime=(TextView) view.findViewById(R.id.item_grade_all_testTime);
            grade=(TextView) view.findViewById(R.id.item_grade_all_grade);
        }
    }

    public GradeAllAdapter(List<GradeAll> gradeAllList){
        mGradeAllList=gradeAllList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_grade_all,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                GradeAll gradeAll=mGradeAllList.get(position);
                Intent intent=new Intent(mContext,GradeDetailActivity.class);
                intent.putExtra("courseId",gradeAll.getCourseId());
                intent.putExtra("studentId",gradeAll.getStudentId());
                intent.putExtra("startDate",gradeAll.getStartDate());
                intent.putExtra("endDate",gradeAll.getEndDate());
                intent.putExtra("testDate",gradeAll.getTestDate());
                intent.putExtra("grade",gradeAll.getGrade());
                intent.putExtra("comment",gradeAll.getComment());
                intent.putExtra("teacherId",gradeAll.getTeacherId());
                intent.putExtra("testTime",gradeAll.getTestTime());
                intent.putExtra("teacherName",gradeAll.getTeacherName());
                intent.putExtra("studentName",gradeAll.getStudentName());
                intent.putExtra("courseName",gradeAll.getCourseName());
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        GradeAll gradeAll=mGradeAllList.get(position);
        holder.courseName.setText(gradeAll.getCourseName());
        holder.courseId.setText(gradeAll.getCourseId());
        holder.testTime.setText(gradeAll.getTestDate()+" "+gradeAll.getTestTime());
        holder.grade.setText(gradeAll.getGrade());
        if(Integer.valueOf(gradeAll.getGrade())>60||Integer.valueOf(gradeAll.getGrade())==60){
            holder.grade.setTextColor(Color.parseColor("#1C8106"));
        }else{
            holder.grade.setTextColor(Color.parseColor("#DE0000"));
        }
    }

    public int getItemCount(){
        return mGradeAllList.size();
    }
}
