package com.example.zhangbolun.jinjidebishe.teacher.gradeInput;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangbolun.jinjidebishe.R;
import com.example.zhangbolun.jinjidebishe.TeacherActivity;

import java.io.File;
import java.util.List;

/**
 * Created by zhangbolun on 2017/5/24.
 */

public class GradeInputAdapter extends RecyclerView.Adapter<GradeInputAdapter.ViewHolder> {
    private Context mContext;
    private List<File> mFileList;

    private String TAG="教师文件列表";
    
    static class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        TextView name; //文件名
        TextView suffix; //文件类型（后缀名）
        public ViewHolder(View view){
            super(view);
            itemView=view;
            name=(TextView) view.findViewById(R.id.item_teacher_grade_file_name);
            suffix=(TextView) view.findViewById(R.id.item_teacher_grade_file_suffix);
        }
    }

    public GradeInputAdapter(List<File> fileList){
        mFileList=fileList;
    }

    public ViewHolder onCreateViewHolder(final ViewGroup parent,int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_teacher_input_grade_file,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        final TeacherActivity teacherActivity=(TeacherActivity)view.getContext();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                final File file=mFileList.get(position);
                Intent intent=new Intent(mContext,TeacherGradeInputActivity.class);
                intent.putExtra("FILE_PATH",file.getPath());
                intent.putExtra("USER",teacherActivity.getCurrentUser());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        File file=mFileList.get(position);
        String fileName=file.getName();
        holder.name.setText(fileName);
        holder.suffix.setText(fileName.substring(fileName.lastIndexOf("."),fileName.length())+"类型文件");
    }

    public int getItemCount(){
        return mFileList.size();
    }
}
