package com.example.zhangbolun.jinjidebishe;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolidayActivity extends AppCompatActivity {
    @BindView(R.id.activity_holiday_date)TextView date;
    @BindView(R.id.activity_holiday_reason)TextView reason;
    @BindView(R.id.activity_holiday_state)TextView state;
    @BindView(R.id.activity_holiday_posttime)TextView posttime;
    @BindView(R.id.activity_holiday_toolbar)Toolbar toolbar;
    @BindView(R.id.activity_holiday_collapsingToolbar)CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday);
        Intent intent=getIntent();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        date.setText(intent.getStringExtra("date"));
        reason.setText(intent.getStringExtra("reason"));
        posttime.setText(intent.getStringExtra("posttime"));
        if(intent.getStringExtra("absence").equals("9")){
            state.setText("等待家长签字确认");
        }else if(intent.getStringExtra("absence").equals("8")){
            state.setText("等待教师确认");
        }else if(intent.getStringExtra("absence").equals("7")){
            state.setText("教师不准假");
        }else if(intent.getStringExtra("absence").equals("1")){
            state.setText("教师准假");
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
