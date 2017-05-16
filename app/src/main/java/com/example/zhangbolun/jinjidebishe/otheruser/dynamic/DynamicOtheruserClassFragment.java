package com.example.zhangbolun.jinjidebishe.otheruser.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhangbolun.jinjidebishe.OtherUsersActivity;
import com.example.zhangbolun.jinjidebishe.R;
import com.example.zhangbolun.jinjidebishe.dynamic.DynamicClass;
import com.example.zhangbolun.jinjidebishe.dynamic.DynamicClassAdapter;
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

public class DynamicOtheruserClassFragment extends Fragment {
    @BindView(R.id.fragment_dynamic_otheruser_class_recyclerview)RecyclerView recyclerView;
    @BindView(R.id.fragment_dynamic_otheruser_class_fab)FloatingActionButton fab;
    @BindView(R.id.fragment_dynamic_otheruser_class_swiperefreshlayout)SwipeRefreshLayout swipeRefreshLayout;

    private String TAG="家长学生班级圈";
    private String url="http://"+currentUrl+":8888/android_connect/dynamic_otheruser.php";

    OtherUsersActivity otherUsersActivity;

    private DynamicClassAdapter adapter;
    private List<DynamicClass> dynamicClassList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dynamic_otheruser_class, container, false);
        ButterKnife.bind(this,view);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        getDynamicClass();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),DynamicOtheruserPostActivity.class);
                intent.putExtra("currentUser",otherUsersActivity.getCurrentUser());
                intent.putExtra("mark","班级圈");
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDynamicClass();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });

        return view;
    }

    private void getDynamicClass(){
        otherUsersActivity=(OtherUsersActivity) getActivity();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder()
                            .add("post_id",otherUsersActivity.getCurrentUser())
                            .add("post_mark","班级圈")
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
                        dynamicClassList=gson.fromJson(responseData,new TypeToken<List<DynamicClass>>(){}.getType());
                        adapter=new DynamicClassAdapter(dynamicClassList);
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