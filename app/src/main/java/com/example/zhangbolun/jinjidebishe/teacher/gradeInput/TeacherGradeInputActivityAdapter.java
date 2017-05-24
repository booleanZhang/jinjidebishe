package com.example.zhangbolun.jinjidebishe.teacher.gradeInput;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangbolun.jinjidebishe.R;

import java.util.List;

/**
 * Created by zhangbolun on 2017/5/24.
 */

public class TeacherGradeInputActivityAdapter extends RecyclerView.Adapter<TeacherGradeInputActivityAdapter.ViewHolder> {
    private Context mContext;
    private List<ExcelItem> mExcelItemList;

    private String TAG="教师取得的成绩:";

    static class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        TextView course;
        TextView student;
        TextView teacher;
        TextView grade;
        public ViewHolder(View view){
            super(view);
            itemView=view;
            course=(TextView) view.findViewById(R.id.item_teacher_grade_input_activity_adapter_course);
            student=(TextView) view.findViewById(R.id.item_teacher_grade_input_activity_adapter_student);
            teacher=(TextView) view.findViewById(R.id.item_teacher_grade_input_activity_adapter_teacher);
            grade=(TextView) view.findViewById(R.id.item_teacher_grade_input_activity_adapter_grade);
        }
    }

    public TeacherGradeInputActivityAdapter(List<ExcelItem> excelItemList){
        mExcelItemList=excelItemList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_teacher_grade_input_activity_adapter,parent,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        ExcelItem excelItem=mExcelItemList.get(position);
        holder.student.setText(excelItem.getStudent());
        holder.teacher.setText(excelItem.getTeacher());
        holder.course.setText(excelItem.getCourse());
        holder.grade.setText(excelItem.getGrade());
        if(Integer.valueOf(excelItem.getGrade())<60){
            holder.grade.setTextColor(Color.parseColor("#DE0000"));
        }else{
            holder.grade.setTextColor(Color.parseColor("#1C8106"));
        }
    }

    public int getItemCount(){
        return mExcelItemList.size();
    }
}
