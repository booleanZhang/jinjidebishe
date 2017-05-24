package com.example.zhangbolun.jinjidebishe.teacher.gradeInput;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangbolun.jinjidebishe.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.zhangbolun.jinjidebishe.login.LoginFragment.currentUrl;

public class TeacherGradeDetailActivity extends AppCompatActivity {
    @BindView(R.id.activity_teacher_grade_detail_toolbar)Toolbar toolbar;
    @BindView(R.id.activity_teacher_grade_detail_courseId)TextView courseId;
    @BindView(R.id.activity_teacher_grade_detail_courseName)TextView courseName;
    @BindView(R.id.activity_teacher_grade_detail_teacherId)TextView teacherId;
    @BindView(R.id.activity_teacher_grade_detail_teacherName)TextView teacherName;
    @BindView(R.id.activity_teacher_grade_detail_studentId)TextView studentId;
    @BindView(R.id.activity_teacher_grade_detail_studentName)TextView studentName;
    @BindView(R.id.activity_teacher_grade_detail_startDate)TextView startDate;
    @BindView(R.id.activity_teacher_grade_detail_endDate)TextView endDate;
    @BindView(R.id.activity_teacher_grade_detail_test)TextView test;
    @BindView(R.id.activity_teacher_grade_detail_grade)TextView grade;
    @BindView(R.id.activity_teacher_grade_detail_comment)EditText comment;
    @BindView(R.id.activity_teacher_grade_detail_button)Button button;

    private Intent intent;

    private String url="http://"+currentUrl+":8888/android_connect/teacher_add_comment.php";
    private String commentTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_grade_detail);
        intent=getIntent();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle("教师查看成绩录入评语");
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

        if (intent.getStringExtra("comment")!=null){
            if(!intent.getStringExtra("comment").equals("")){
                comment.setText(intent.getStringExtra("comment"));
            }else{
                comment.setText("暂无");
            }
        }else{
            comment.setText("暂无");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            commentTeacher=comment.getText().toString().trim();
                            OkHttpClient client=new OkHttpClient();
                            RequestBody formBody=new FormBody.Builder()
                                    .add("student",intent.getStringExtra("studentId"))
                                    .add("course",intent.getStringExtra("courseId"))
                                    .add("startdate",intent.getStringExtra("startDate"))
                                    .add("comment",commentTeacher)
                                    .build();
                            Request request=new Request.Builder().post(formBody).url(url).build();
                            Response response=client.newCall(request).execute();
                            if(response.isSuccessful()){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TeacherGradeDetailActivity.this, "评语上传成功", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
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
