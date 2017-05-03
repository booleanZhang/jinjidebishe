package com.example.zhangbolun.jinjidebishe.otheruser.holiday;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class OtheruserHolidayStudentFragment extends Fragment {
    @BindView(R.id.fragment_otheruser_holiday_head)EditText head;
    @BindView(R.id.fragment_otheruser_holiday_body)EditText body;
    @BindView(R.id.fragment_otheruser_holiday_btn)Button button;
    private String url="http://"+currentUrl+":8888/android_connect/otheruser_holiday_student.php";
    OtherUsersActivity otherUsersActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_otheruser_holiday_student, container, false);
        ButterKnife.bind(this,view);
        otherUsersActivity=(OtherUsersActivity)getActivity();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            OkHttpClient client=new OkHttpClient();
                            RequestBody formBody=new FormBody.Builder().add("post_id",otherUsersActivity.getCurrentUser())
                                    .add("post_head",head.getText().toString().trim())
                                    .add("post_body",body.getText().toString().trim())
                                    .build();
                            Request request=new Request.Builder().post(formBody).url(url).build();
                            Response response=client.newCall(request).execute();
                            String responseData=response.body().string();
                            if(response.isSuccessful()){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(otherUsersActivity, "发送请假条成功", Toast.LENGTH_SHORT).show();
                                        head.setText("");
                                        body.setText("");
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
