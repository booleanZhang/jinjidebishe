package com.example.zhangbolun.jinjidebishe.otheruser.checkon;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.zhangbolun.jinjidebishe.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtheruserCheckOnDetailActivity extends AppCompatActivity {
    @BindView(R.id.otheruser_checkon_detail_date)TextView date;
    @BindView(R.id.otheruser_checkon_detail_name)TextView name;
    @BindView(R.id.otheruser_checkon_detail_absence)TextView absence;
    @BindView(R.id.otheruser_checkon_detail_intime)TextView intime;
    @BindView(R.id.otheruser_checkon_detail_outtime)TextView outtime;
    @BindView(R.id.otheruser_checkon_detail_reason)TextView reason;
    @BindView(R.id.otheruser_checkon_detail_collapsing_toolbar)CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.otheruser_checkon_detail_toolbar)Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otheruser_check_on_detail);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        date.setText(intent.getStringExtra("date"));
        name.setText(intent.getStringExtra("name"));
        if(intent.getStringExtra("absence").equals("0")){
            absence.setText("到校");
            intime.setVisibility(View.VISIBLE);
            outtime.setVisibility(View.VISIBLE);
            reason.setVisibility(View.GONE);
            intime.setText(intent.getStringExtra("intime"));
            outtime.setText(intent.getStringExtra("outtime"));
        }else if(intent.getStringExtra("absence").equals("1")){
            absence.setText("未到校");
            intime.setVisibility(View.GONE);
            outtime.setVisibility(View.GONE);
            reason.setVisibility(View.VISIBLE);
            if(intent.getStringExtra("reason").equals("")){
                reason.setText("未请假，也未到校，逃课");
            }else{
                reason.setText(intent.getStringExtra("reason"));
            }
        }
        toolbar.setTitle("学生签到详细信息");
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
