package com.example.zhangbolun.jinjidebishe.teacher.holiday;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class TeacherHolidayTodayDeatilActivity extends AppCompatActivity {
    @BindView(R.id.activity_teacher_holiday_today_detail_absence)TextView absence;
    @BindView(R.id.activity_teacher_holiday_today_detail_date)TextView date;
    @BindView(R.id.activity_teacher_holiday_today_detail_id)TextView id;
    @BindView(R.id.activity_teacher_holiday_today_detail_name)TextView name;
    @BindView(R.id.activity_teacher_holiday_today_detail_posttime)TextView posttime;
    @BindView(R.id.activity_teacher_holiday_today_detail_reason)TextView reason;
    @BindView(R.id.activity_teacher_holiday_today_detail_agree)Button btn_agree;
    @BindView(R.id.activity_teacher_holiday_today_detail_refuse)Button btn_refuse;
    @BindView(R.id.activity_teacher_holiday_today_detail_toolbar)Toolbar toolbar;
    @BindView(R.id.activity_teacher_holiday_today_detail_appBar)AppBarLayout appBarLayout;
    @BindView(R.id.activity_teacher_holiday_today_detail_collapsingToolbarLayout)CollapsingToolbarLayout collapsingToolbarLayout;

    private String url="http://"+currentUrl+":8888/android_connect/teacher_holiday_today_operation.php";

    private String stu_id;

    private String TAG="教师请假条";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_holiday_today_deatil);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(intent.getStringExtra("name")+"本日请假条详细");

        stu_id=intent.getStringExtra("id");

        id.setText(intent.getStringExtra("id"));
        name.setText(intent.getStringExtra("name"));
        date.setText(intent.getStringExtra("date"));
        posttime.setText(intent.getStringExtra("posttime"));
        reason.setText(intent.getStringExtra("reason"));
        if(intent.getStringExtra("absence").equals("8")){
            absence.setText("待确认");
        }else if(intent.getStringExtra("absence").equals("7")){
            absence.setText("教师已拒绝请假");
            btn_agree.setVisibility(View.GONE);
            btn_refuse.setVisibility(View.GONE);
        }else if(intent.getStringExtra("absence").equals("1")){
            absence.setText("请假已同意");
            btn_agree.setVisibility(View.GONE);
            btn_refuse.setVisibility(View.GONE);
        }

        if(intent.getStringExtra("name").equals("无签到信息")){
            btn_agree.setVisibility(View.GONE);
            btn_refuse.setVisibility(View.GONE);
        }

        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Log.d(TAG, "run: agree");
                            OkHttpClient client=new OkHttpClient();
                            RequestBody formBody=new FormBody.Builder().add("post_id",stu_id).add("post_mark","agree").build();
                            Request request=new Request.Builder().post(formBody).url(url).build();
                            Response response=client.newCall(request).execute();
                            String responseData=response.body().string();
                            if(response.isSuccessful()){
                                btn_agree.setClickable(false);
                                btn_refuse.setClickable(false);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TeacherHolidayTodayDeatilActivity.this, "确认请假条成功，准假", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        btn_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Log.d(TAG, "run: refuse");
                            OkHttpClient client=new OkHttpClient();
                            RequestBody formBody=new FormBody.Builder().add("post_id",stu_id).add("post_mark","refuse").build();
                            Request request=new Request.Builder().post(formBody).url(url).build();
                            Response response=client.newCall(request).execute();
                            String responseData=response.body().string();
                            if(response.isSuccessful()){
                                btn_agree.setClickable(false);
                                btn_refuse.setClickable(false);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TeacherHolidayTodayDeatilActivity.this, "确认请假条成功，拒绝准假", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }catch(Exception e){
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
