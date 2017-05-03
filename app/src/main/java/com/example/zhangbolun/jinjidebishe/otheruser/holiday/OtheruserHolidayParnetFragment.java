package com.example.zhangbolun.jinjidebishe.otheruser.holiday;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zhangbolun.jinjidebishe.OtherUsersActivity;
import com.example.zhangbolun.jinjidebishe.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.zhangbolun.jinjidebishe.login.LoginFragment.currentUrl;

public class OtheruserHolidayParnetFragment extends Fragment {
    @BindView(R.id.fragment_otheruser_holiday_parent_recyclerview)RecyclerView recyclerView;
    @BindView(R.id.fragment_otheruser_holiday_parent_title)TextView textView_title;
    @BindView(R.id.fragment_otheruser_holiday_parent_checkbox)ImageButton imageButton;
    @BindView(R.id.fragment_otheruser_holiday_parent_agree)Button btn_agree;
    @BindView(R.id.fragment_otheruser_holiday_parent_refuse)Button btn_refuse;
    @BindView(R.id.fragment_otheruser_holiday_parent_date)TextView textView_date;
    @BindView(R.id.fragment_otheruser_holiday_parent_reason)TextView textView_reason;
    @BindView(R.id.fragment_otheruser_holiday_parent_posttime)TextView textView_posttime;

    private OtherUsersActivity otherUsersActivity;
    private String currentUser;
    private String childId;

    private String url1="http://"+currentUrl+":8888/android_connect/otheruser_holiday_parent1.php";
    private String url2="http://"+currentUrl+":8888/android_connect/otheruser_holiday_parent2.php";

    private int flag=1; //0 显示 1 不显示
    
    public String TAG="请假条_家长";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_otheruser_holiday_parnet, container, false);
        ButterKnife.bind(this,view);
        otherUsersActivity=(OtherUsersActivity)getActivity();
        currentUser=otherUsersActivity.getCurrentUser();
        childId=currentUser.substring(0,9);
        recyclerView.setVisibility(View.GONE);

        textView_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (flag){
                    case 0:
                        recyclerView.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        recyclerView.setVisibility(View.GONE);
                        break;
                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (flag){
                            case 0:
                                recyclerView.setVisibility(View.VISIBLE);
                                break;
                            case 1:
                                recyclerView.setVisibility(View.GONE);
                                break;
                        }
                    }
                });
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder().add("post_id",childId).build();
                    Request request=new Request.Builder().post(formBody).url(url1).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    Log.d(TAG, responseData);
                    if(response.isSuccessful()){
                        
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();


        return view;
    }
}
