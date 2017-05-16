package com.example.zhangbolun.jinjidebishe.teacher.dynamic;

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

import com.example.zhangbolun.jinjidebishe.R;
import com.example.zhangbolun.jinjidebishe.TeacherActivity;
import com.example.zhangbolun.jinjidebishe.dynamic.DynamicSchool;
import com.example.zhangbolun.jinjidebishe.dynamic.DynamicSchoolAdapter;
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

public class DynamicTeacherSchoolFragment extends Fragment {
    @BindView(R.id.fragment_dynamic_teacher_school_recyclerview)RecyclerView recyclerView;

    private String TAG="教师校园圈:";
    private String url="http://"+currentUrl+":8888/android_connect/dynamic_teacher.php";

    TeacherActivity teacherActivity;

    DynamicSchoolAdapter adapter;
    private List<DynamicSchool> dynamicSchoolList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dynamic_teacher_school, container, false);
        ButterKnife.bind(this,view);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        getDynamicSchool();
        return view;
    }

    private void getDynamicSchool(){
        teacherActivity=(TeacherActivity)getActivity();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder()
                            .add("post_id",teacherActivity.getCurrentUser())
                            .add("post_mark","校园圈")
                            .build();
                    Request request=new Request.Builder()
                            .post(formBody)
                            .url(url)
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    Log.d(TAG, responseData);
                    if(response.isSuccessful()){
                        Gson gson=new Gson();
                        dynamicSchoolList=gson.fromJson(responseData,new TypeToken<List<DynamicSchool>>(){}.getType());
                        adapter=new DynamicSchoolAdapter(dynamicSchoolList);
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
}
