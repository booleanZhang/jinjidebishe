package com.example.zhangbolun.jinjidebishe.teacher.gradeInput;

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
import com.example.zhangbolun.jinjidebishe.otheruser.grade.GradeDetailActivity;

import java.util.List;

/**
 * Created by zhangbolun on 2017/5/24.
 */

public class TeacherGradeThisYearAdapter extends RecyclerView.Adapter<TeacherGradeThisYearAdapter.ViewHolder> {
    private Context mContext;
    private List<TeacherGradeThisYear> mTeacherGradeThisYearList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView courseName;
        TextView courseId;
        TextView testTime;
        TextView grade;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            courseName=(TextView) view.findViewById(R.id.item_teacher_grade_this_year_courseName);
            courseId=(TextView) view.findViewById(R.id.item_teacher_grade_this_year_courseId);
            testTime=(TextView) view.findViewById(R.id.item_teacher_grade_this_year_testTime);
            grade=(TextView) view.findViewById(R.id.item_teacher_grade_this_year_grade);
        }
    }

    public TeacherGradeThisYearAdapter(List<TeacherGradeThisYear> teacherGradeThisYearList){
        mTeacherGradeThisYearList=teacherGradeThisYearList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_teacher_grade_this_year,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                TeacherGradeThisYear gradeThisYear=mTeacherGradeThisYearList.get(position);
                Intent intent=new Intent(mContext,TeacherGradeDetailActivity.class);
                intent.putExtra("courseId",gradeThisYear.getCourseId());        //
                intent.putExtra("studentId",gradeThisYear.getStudentId());      //
                intent.putExtra("startDate",gradeThisYear.getStartDate());      //
                intent.putExtra("endDate",gradeThisYear.getEndDate());          //
                intent.putExtra("testDate",gradeThisYear.getTestDate());        //
                intent.putExtra("grade",gradeThisYear.getGrade());
                intent.putExtra("comment",gradeThisYear.getComment());
                intent.putExtra("teacherId",gradeThisYear.getTeacherId());      //
                intent.putExtra("testTime",gradeThisYear.getTestTime());        //
                intent.putExtra("teacherName",gradeThisYear.getTeacherName());  //
                intent.putExtra("studentName",gradeThisYear.getStudentName());  //
                intent.putExtra("courseName",gradeThisYear.getCourseName());    //
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        TeacherGradeThisYear gradeThisYear=mTeacherGradeThisYearList.get(position);
        holder.courseName.setText(gradeThisYear.getCourseName());
        holder.courseId.setText(gradeThisYear.getCourseId());
        holder.testTime.setText(gradeThisYear.getTestDate()+" "+gradeThisYear.getTestTime());
        holder.grade.setText(gradeThisYear.getGrade());
        if(Integer.valueOf(gradeThisYear.getGrade())>60||Integer.valueOf(gradeThisYear.getGrade())==60){
            holder.grade.setTextColor(Color.parseColor("#1C8106"));
        }else{
            holder.grade.setTextColor(Color.parseColor("#DE0000"));
        }
    }

    public int getItemCount(){
        return mTeacherGradeThisYearList.size();
    }
}
