package com.example.zhangbolun.jinjidebishe.otheruser.grade;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.zhangbolun.jinjidebishe.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GradeDetailActivity extends AppCompatActivity {
    @BindView(R.id.activity_grade_detail_toolbar)Toolbar toolbar;
    @BindView(R.id.activity_grade_detail_courseId)TextView courseId;
    @BindView(R.id.activity_grade_detail_courseName)TextView courseName;
    @BindView(R.id.activity_grade_detail_teacherId)TextView teacherId;
    @BindView(R.id.activity_grade_detail_teacherName)TextView teacherName;
    @BindView(R.id.activity_grade_detail_studentId)TextView studentId;
    @BindView(R.id.activity_grade_detail_studentName)TextView studentName;
    @BindView(R.id.activity_grade_detail_startDate)TextView startDate;
    @BindView(R.id.activity_grade_detail_endDate)TextView endDate;
    @BindView(R.id.activity_grade_detail_test)TextView test;
    @BindView(R.id.activity_grade_detail_grade)TextView grade;
    @BindView(R.id.activity_grade_detail_comment)TextView comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_detail);
        Intent intent=getIntent();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle("成绩详细");
        courseId.setText(intent.getStringExtra("courseId"));
        courseName.setText(intent.getStringExtra("courseName"));
        teacherId.setText(intent.getStringExtra("teacherId"));
        teacherName.setText(intent.getStringExtra("teacherName"));
        studentId.setText(intent.getStringExtra("studentId"));
        studentName.setText(intent.getStringExtra("studentName"));
        startDate.setText(intent.getStringExtra("startDate"));
        if(intent.getStringExtra("endDate")!=null){
            if(!intent.getStringExtra("endDate").equals("")){
                endDate.setText(intent.getStringExtra("endDate"));
            }else{
                endDate.setText("暂无");
            }
        }else{
            endDate.setText("暂无");
        }

        if(intent.getStringExtra("testDate")!=null){
            if(!intent.getStringExtra("testDate").equals("")){
                test.setText(intent.getStringExtra("testDate")+" "+intent.getStringExtra("testTime"));
            }else{
                test.setText("暂无");
            }
        }else{
            test.setText("暂无");
        }

        if (intent.getStringExtra("grade")!=null){
            if(!intent.getStringExtra("grade").equals("")){
                grade.setText(intent.getStringExtra("grade"));
                if(Integer.valueOf(intent.getStringExtra("grade"))<60){
                    grade.setTextColor(Color.parseColor("#DE0000"));
                }else{
                    grade.setTextColor(Color.parseColor("#1C8106"));
                }
            }else{
                grade.setText("暂无");
            }
        }else{
            grade.setText("暂无");
        }

        if(intent.getStringExtra("comment")!=null){
            if(!intent.getStringExtra("comment").equals("")){
                comment.setText(intent.getStringExtra("comment"));
            }else{
                comment.setText("暂无");
            }
        }else{
            comment.setText("暂无");
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
