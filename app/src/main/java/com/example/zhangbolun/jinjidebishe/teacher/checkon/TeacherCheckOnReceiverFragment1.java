package com.example.zhangbolun.jinjidebishe.teacher.checkon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

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

/**
 * Created by zhangbolun on 2017/4/21.
 */

public class TeacherCheckOnReceiverFragment1 extends Fragment {
    private String TAG="booleanZhang";
    private String date="";
    private TeacherActivity teacherActivity;
    private String url="http://"+currentUrl+":8888/android_connect/teacher_checkon1.php";

    private CheckOnAdapter adapter;
    
    @BindView(R.id.fragmentteacher_checkon_receiver1_dp)DatePicker datePicker;
    @BindView(R.id.fragmentteacher_checkon_receiver1_rv)RecyclerView recyclerView;
    @BindView(R.id.fragmentteacher_checkon_receiver1_btn)Button button;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_teacher_checkon_receiver1,container,false);
        ButterKnife.bind(this,view);
        teacherActivity=(TeacherActivity)getActivity();

        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),1); //试试getActivity()
        recyclerView.setLayoutManager(layoutManager);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date=Integer.toString(datePicker.getYear())+"-"+Integer.toString(datePicker.getMonth()+1)+"-"+Integer.toString(datePicker.getDayOfMonth());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            OkHttpClient client=new OkHttpClient();
                            RequestBody requestBody=new FormBody.Builder()
                                    .add("post_id",teacherActivity.getCurrentUser())
                                    .add("post_date",date)
                                    .build();
                            Request request=new Request.Builder().post(requestBody).url(url).build();
                            Response response=client.newCall(request).execute();
                            String responseData=response.body().string();

                            if(response.isSuccessful()){
                                Gson gson=new Gson();
                                List<CheckOnDetail> checkOnDetailList=gson.fromJson(responseData,new TypeToken<List<CheckOnDetail>>(){}.getType());
                                adapter=new CheckOnAdapter(checkOnDetailList);
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
            }
        });
        
        
        return view;
    }
}
