package com.example.zhangbolun.jinjidebishe;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;


public class NotificationDetailTeacher extends AppCompatActivity {
    private String sender;
    private String head;
    private String body;
    private String scope;
    private String date;
    private String mark;
    
    private Toolbar toolbar;
    private TextView headText;
    private TextView senderText;
    private TextView bodyText;
    private TextView scopeText;
    private TextView dateText;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail_teacher);
        Intent intent=getIntent();
        sender=intent.getStringExtra("sender");
        head=intent.getStringExtra("head");
        body=intent.getStringExtra("body");
        scope=intent.getStringExtra("scope");
        date=intent.getStringExtra("date");
        mark=intent.getStringExtra("mark");
        
        toolbar=(Toolbar)findViewById(R.id.teacher_notification_detail_toolbar);
        headText=(TextView)findViewById(R.id.teacher_notification_detail_head);
        senderText=(TextView)findViewById(R.id.teacher_notification_detail_sender);
        bodyText=(TextView)findViewById(R.id.teacher_notification_detail_body);
        scopeText=(TextView)findViewById(R.id.teacher_notification_detail_scope);
        dateText=(TextView)findViewById(R.id.teacher_notification_detail_date);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.teacher_notification_detail_collapsing_toolbar);
        if(mark.equals("0")){
            collapsingToolbarLayout.setTitle("通知详细信息");
        }else if(mark.equals("1")){
            collapsingToolbarLayout.setTitle("作业详细信息");
        }else if(mark.equals("2")){
            collapsingToolbarLayout.setTitle("缴费详细信息");
        }else if(mark.equals("3")){
            collapsingToolbarLayout.setTitle("教师通知详细信息");
        }

        senderText.setText(sender);
        headText.setText(head);
        bodyText.setText(body);
        dateText.setText(date);
        if(scope.equals("0")){
            scopeText.setText("全部班级");
        }else{
            scopeText.setText("指定班级");
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }
}
