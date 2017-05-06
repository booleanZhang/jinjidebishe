package com.example.zhangbolun.jinjidebishe.teacher.holiday;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhangbolun.jinjidebishe.R;
import com.example.zhangbolun.jinjidebishe.TeacherActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.zhangbolun.jinjidebishe.login.LoginFragment.currentUrl;

public class TeacherHolidayReceiver1Fragment extends Fragment {
    @BindView(R.id.fragment_teacher_holiday_receiver1_recyclerview)RecyclerView recyclerView;
    @BindView(R.id.fragment_teacher_holiday_receiver1_swiperefreshlayout)SwipeRefreshLayout swipeRefreshLayout;

    private String url="http://"+currentUrl+":8888/android_connect/teacher_holiday_today.php";
    private String TAG="教师请假条:";
    private TeacherActivity teacherActivity;

    private List<TeacherHolidayToday> mTeacherHolidayTodayList;
    private TeacherHolidayTodayAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_teacher_holiday_receiver1, container, false);
        ButterKnife.bind(this,view);
        teacherActivity=(TeacherActivity)getActivity();
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
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
                        mTeacherHolidayTodayList=gson.fromJson(responseData, new TypeToken<List<TeacherHolidayToday>>(){}.getType());
                        adapter=new TeacherHolidayTodayAdapter(mTeacherHolidayTodayList);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
                                mTeacherHolidayTodayList=gson.fromJson(responseData, new TypeToken<List<TeacherHolidayToday>>(){}.getType());
                                adapter=new TeacherHolidayTodayAdapter(mTeacherHolidayTodayList);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //adapter.notifyDataSetChanged();
                                        recyclerView.setAdapter(adapter);
                                        swipeRefreshLayout.setRefreshing(false);
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
        return view;
    }
}
