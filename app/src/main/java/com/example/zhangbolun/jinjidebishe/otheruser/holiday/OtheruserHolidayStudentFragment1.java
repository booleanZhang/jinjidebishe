package com.example.zhangbolun.jinjidebishe.otheruser.holiday;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhangbolun.jinjidebishe.OtherUsersActivity;
import com.example.zhangbolun.jinjidebishe.R;
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

public class OtheruserHolidayStudentFragment1 extends Fragment {
    @BindView(R.id.fragment_otheruser_holiday_student1_recyclerview)RecyclerView recyclerView;
    OtherUsersActivity otherUsersActivity;
    private String url="http://"+currentUrl+":8888/android_connect/otheruser_holiday_student1.php";
    private String TAG="";

    List<StudentHoliday> studentHolidayList;
    StudentHolidayAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_otheruser_holiday_student1, container, false);
        ButterKnife.bind(this,view);
        otherUsersActivity=(OtherUsersActivity)getActivity();
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder()
                            .add("post_id",otherUsersActivity.getCurrentUser())
                            .build();
                    Request request=new Request.Builder().post(formBody).url(url).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    Log.d(TAG, "run: "+responseData);
                    if(response.isSuccessful()){
                        Gson gson=new Gson();
                        studentHolidayList=gson.fromJson(responseData,new TypeToken<List<StudentHoliday>>(){}.getType());
                        adapter=new StudentHolidayAdapter(studentHolidayList);
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
        return view;
    }
}
