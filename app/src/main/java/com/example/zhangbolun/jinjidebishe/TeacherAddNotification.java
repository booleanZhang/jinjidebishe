package com.example.zhangbolun.jinjidebishe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.bind.ArrayTypeAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static login.LoginFragment.currentUrl;

public class TeacherAddNotification extends AppCompatActivity {
    private String url=currentUrl;
    private String get_teacher_class="http://"+url+":8888/android_connect/get_teacher_class.php";
    private String url_teacher_notification="http://"+url+":8888/android_connect/teacher_navigation.php";
    private String url_teacher_notification1="http://"+url+":8888/android_connect/teacher_notification1.php";
    private String url_teacher_notification2="http://"+url+":8888/android_connect/teacher_notification2.php";
    private String TAG="TeacherAddNotification";
    private String scope="";

    private TextView title;
    private Spinner spinner;
    private EditText head;
    private EditText body;
    private Button send;
    private List<String> dataList;
    private ArrayAdapter<String> arrayAdapter;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_add_notification);

        spinner=(Spinner)findViewById(R.id.addnotification_spinner);
        head=(EditText)findViewById(R.id.addnotification_head);
        body=(EditText)findViewById(R.id.addnotification_body);
        send=(Button)findViewById(R.id.addnotification_btn);
        title=(TextView)findViewById(R.id.addnotification_title);
        mContext=this;
        dataList=new ArrayList<String>();

        Intent intent=getIntent();
        final String currentUser=intent.getStringExtra("currentUser");
        final String type=intent.getStringExtra("type");
        if(type.equals("0")){
            title.setText("发布通知");
        }else if(type.equals("1")){
            title.setText("发布作业");
        }else if(type.equals("2")){
            title.setText("发布缴费信息");
        }
        Log.d(TAG, currentUser+"+"+type);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder().add("post_id",currentUser).build();
                    Request request=new Request.Builder().post(formBody).url(get_teacher_class).build();
                    Response response=client.newCall(request).execute();
                    String responseDate=response.body().string();
                    Log.d(TAG, "返回的数据"+responseDate);
                    if(response.isSuccessful()){
                        Gson gson=new Gson();
                        GetTeacherClass teacherClass=gson.fromJson(responseDate,GetTeacherClass.class);
                        dataList.add("所有班级");
                        dataList.add(teacherClass.getClass1());
                        dataList.add(teacherClass.getClass2());
                        dataList.add(teacherClass.getClass3());
                        dataList.add(teacherClass.getClass4());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                arrayAdapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,dataList);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(arrayAdapter);
                                spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
                                    public void onItemSelected(AdapterView<?> arg0,View arg1,int arg2,long arg3){
                                        Log.d(TAG, arrayAdapter.getItem(arg2));
                                        switch(arrayAdapter.getItem(arg2)){
                                            case "所有班级":
                                                scope=arrayAdapter.getItem(arg2);
                                                break;
                                            case "1304101":
                                                scope=arrayAdapter.getItem(arg2);
                                                break;
                                            case "1304102":
                                                scope=arrayAdapter.getItem(arg2);
                                                break;
                                            case "1304103":
                                                scope=arrayAdapter.getItem(arg2);
                                                break;
                                            case "1304104":
                                                scope=arrayAdapter.getItem(arg2);
                                                break;
                                        }
                                        arg0.setVisibility(View.VISIBLE);
                                    }
                                    public void onNothingSelected(AdapterView<?> arg0){
                                        arg0.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(head.getText().equals("")){
                    Toast.makeText(TeacherAddNotification.this,"请输入通知标题",Toast.LENGTH_SHORT).show();
                }else{
                    if(body.getText().equals("")){
                        Toast.makeText(TeacherAddNotification.this,"请输入通知正文",Toast.LENGTH_SHORT).show();
                    }else{
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                OkHttpClient client=new OkHttpClient();
                                SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                                String date=sDateFormat.format(new java.util.Date());
                               if(scope.equals("所有班级")){
                                    try{
                                        RequestBody formBody=new FormBody.Builder()
                                                .add("post_id",currentUser)
                                                .add("post_head",head.getText().toString().trim())
                                                .add("post_body",body.getText().toString().trim())
                                                .add("post_scope",scope)
                                                .add("post_type",type)
                                                .add("post_date",date)
                                                .add("post_mark","0").build();
                                        Request request=new Request.Builder().url(url_teacher_notification).build();
                                        if(type.equals("0")){
                                            request=new Request.Builder()
                                                    .post(formBody)
                                                    .url(url_teacher_notification)
                                                    .build();
                                        }else if(type.equals("1")){
                                            request=new Request.Builder()
                                                    .post(formBody)
                                                    .url(url_teacher_notification1)
                                                    .build();
                                        }else if(type.equals("2")){
                                            request=new Request.Builder()
                                                    .post(formBody)
                                                    .url(url_teacher_notification2)
                                                    .build();
                                        }
                                        Response response=client.newCall(request).execute();
                                        if(response.isSuccessful()){
                                            finish();
                                        }
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                               }else{
                                    try{
                                        RequestBody formBody=new FormBody.Builder()
                                                .add("post_id",currentUser)
                                                .add("post_head",head.getText().toString().trim())
                                                .add("post_body",body.getText().toString().trim())
                                                .add("post_scope",scope)
                                                .add("post_type",type)
                                                .add("post_date",date)
                                                .add("post_mark","0")
                                                .add("post_receiver",scope).build();
                                        Request request=new Request.Builder().url(url_teacher_notification).build();
                                        if(type.equals("0")){
                                            request=new Request.Builder()
                                                    .post(formBody)
                                                    .url(url_teacher_notification)
                                                    .build();
                                        }else if(type.equals("1")){
                                            request=new Request.Builder()
                                                    .post(formBody)
                                                    .url(url_teacher_notification1)
                                                    .build();
                                        }else if(type.equals("2")){
                                            request=new Request.Builder()
                                                    .post(formBody)
                                                    .url(url_teacher_notification2)
                                                    .build();
                                        }
                                        Response response=client.newCall(request).execute();
                                        if(response.isSuccessful()){
                                            finish();
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                               }
                            }
                        }).start();
                    }
                }
            }
        });
    }
}
