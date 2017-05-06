package com.example.zhangbolun.jinjidebishe.teacher.holiday;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangbolun.jinjidebishe.R;
import com.example.zhangbolun.jinjidebishe.TeacherActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.zhangbolun.jinjidebishe.login.LoginFragment.currentUrl;

public class TeacherHolidayReceiver2Fragment extends Fragment {
    @BindView(R.id.fragment_teacher_holiday_receiver2_yesterday)TextView yesterday;
    @BindView(R.id.fragment_teacher_holiday_receiver2_yesterday_recyclerview)RecyclerView recyclerView_yes;
    @BindView(R.id.fragment_teacher_holiday_receiver2_month)TextView month;
    @BindView(R.id.fragment_teacher_holiday_receiver2_month_recyclerview)RecyclerView recyclerView_mon;
    @BindView(R.id.fragment_teacher_holiday_receiver2_ago)TextView ago;
    @BindView(R.id.fragment_teacher_holiday_receiver2_ago_recyclerview)RecyclerView recyclerView_ago;

    private TeacherActivity teacherActivity;

    private String TAG="教师往日假条";
    private String url_yes="http://"+currentUrl+":8888/android_connect/teacher_holiday_yesterday.php";
    private String url_month="http://"+currentUrl+":8888/android_connect/teacher_holiday_month.php";
    private String url_ago="http://"+currentUrl+":8888/android_connect/teacher_holiday_ago.php";

    private int yesterday_flag=0;//0不显示 1显示
    private int month_flag=0;
    private int ago_flag=0;

    private List<TeacherHolidayYesterday> teacherHolidayYesterdayList=new ArrayList<>();
    private TeacherHolidayYesterdayAdapter adapter_yes;
    private List<TeacherHolidayMonth> teacherHolidayMonthList=new ArrayList<>();
    private TeacherHolidayMonthAdapter adapter_month;
    private List<TeacherHolidayAgo> teacherHolidayAgoList=new ArrayList<>();
    private TeacherHolidayAgoAdapter adapter_ago;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_teacher_holiday_receiver2, container, false);
        ButterKnife.bind(this,view);
        teacherActivity=(TeacherActivity)getActivity();
        GridLayoutManager layoutManager_yes=new GridLayoutManager(getContext(),1);
        GridLayoutManager layoutManager_ago=new GridLayoutManager(getContext(),1);
        GridLayoutManager layoutManager_mon=new GridLayoutManager(getContext(),1);
        recyclerView_ago.setLayoutManager(layoutManager_ago);
        recyclerView_mon.setLayoutManager(layoutManager_mon);
        recyclerView_yes.setLayoutManager(layoutManager_yes);
        yesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (yesterday_flag){
                    case 0:
                        recyclerView_yes.setVisibility(View.VISIBLE);
                        getHolidaies(url_yes,"y");
                        yesterday_flag=1;
                        break;
                    case 1:
                        recyclerView_yes.setVisibility(View.GONE);
                        yesterday_flag=0;
                        break;
                }
            }
        });

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (month_flag){
                    case 0:
                        recyclerView_mon.setVisibility(View.VISIBLE);
                        getHolidaies(url_month,"m");
                        month_flag=1;
                        break;
                    case 1:
                        recyclerView_mon.setVisibility(View.GONE);
                        month_flag=0;
                        break;
                }
            }
        });

        ago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(ago_flag){
                    case 0:
                        recyclerView_ago.setVisibility(View.VISIBLE);
                        getHolidaies(url_ago,"a");
                        ago_flag=1;
                        break;
                    case 1:
                        recyclerView_ago.setVisibility(View.GONE);
                        ago_flag=0;
                        break;
                }
            }
        });
        return view;
    }

    private void getHolidaies(final String url,final String flag){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder().add("post_id",teacherActivity.getCurrentUser()).build();
                    Request request=new Request.Builder().post(formBody).url(url).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    if(response.isSuccessful()){
                        Gson gson=new Gson();
                        switch (flag){
                            case "y":
                                teacherHolidayYesterdayList=gson.fromJson(responseData,new TypeToken<List<TeacherHolidayYesterday>>(){}.getType());
                                adapter_yes=new TeacherHolidayYesterdayAdapter(teacherHolidayYesterdayList);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        recyclerView_yes.setAdapter(adapter_yes);
                                    }
                                });
                                break;
                            case "m":
                                teacherHolidayMonthList=gson.fromJson(responseData,new TypeToken<List<TeacherHolidayMonth>>(){}.getType());
                                adapter_month=new TeacherHolidayMonthAdapter(teacherHolidayMonthList);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        recyclerView_mon.setAdapter(adapter_month);
                                    }
                                });
                                break;
                            case "a":
                                teacherHolidayAgoList=gson.fromJson(responseData,new TypeToken<List<TeacherHolidayAgo>>(){}.getType());
                                adapter_ago=new TeacherHolidayAgoAdapter(teacherHolidayAgoList);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        recyclerView_ago.setAdapter(adapter_ago);
                                    }
                                });
                                break;
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
