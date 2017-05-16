package com.example.zhangbolun.jinjidebishe.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhangbolun.jinjidebishe.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.zhangbolun.jinjidebishe.login.LoginFragment.currentUrl;

public class DynamicActivity extends AppCompatActivity {
    @BindView(R.id.activity_dynamic_collapsingToolbarLayout)CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.activity_dynamic_toolbar)Toolbar toolbar;
    @BindView(R.id.activity_dynamic_circleImageView)CircleImageView circleImageView;
    @BindView(R.id.activity_dynamic_name)TextView name;
    @BindView(R.id.activity_dynamic_content)TextView content;
    @BindView(R.id.activity_dynamic_imageView)ImageView imageView;
    @BindView(R.id.activity_dynamic_date)TextView date;
    @BindView(R.id.activity_dynamic_time)TextView time;
    @BindView(R.id.activity_dynamic_recyclerview)RecyclerView recyclerView;
    @BindView(R.id.activity_dynamic_fab)FloatingActionButton fab;
    @BindView(R.id.activity_dynamic_edittext)EditText editText;

    private String TAG="动态详细:";
    private String currentDynamicId;
    private String currentUserId;

    private String urlInit="http://"+currentUrl+":8888/android_connect/comments_init.php";
    private String urlUploadComment="http://"+currentUrl+":8888/android_connect/comments_upload.php";

    List<DynamicActivityCommentItem> dynamicActivityCommentItemList;
    DynamicActivityCommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        ButterKnife.bind(this);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent=getIntent();
        currentDynamicId=intent.getStringExtra("id");
        currentUserId=intent.getStringExtra("currentUser");
        setSupportActionBar(toolbar);
        toolbar.setTitle("动态详细");
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if(intent.getStringExtra("senderHead")!=null){
            if(!intent.getStringExtra("senderHead").isEmpty()){
                Glide.with(getApplicationContext()).load(intent.getStringExtra("senderHead")).into(circleImageView);
            }
        }
        name.setText(intent.getStringExtra("sender"));
        content.setText(intent.getStringExtra("content"));
        if(intent.getStringExtra("picture")!=null){
            if(!intent.getStringExtra("picture").isEmpty()){
                Glide.with(getApplicationContext()).load(intent.getStringExtra("picture")).into(imageView);
            }else{
                imageView.setVisibility(View.GONE);
            }
        }else{
            imageView.setVisibility(View.GONE);
        }
        date.setText(intent.getStringExtra("date"));
        time.setText(intent.getStringExtra("time"));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().trim().equals("")){
                    //上传评论
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                OkHttpClient client=new OkHttpClient();
                                RequestBody formBody=new FormBody.Builder().add("post_dynamic",currentDynamicId)
                                        .add("post_sender",currentUserId)
                                        .add("post_content",editText.getText().toString().trim()).build();
                                Request request=new Request.Builder().post(formBody).url(urlUploadComment).build();
                                Response response=client.newCall(request).execute();
                                String responseData=response.body().string();
                                if(response.isSuccessful()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            editText.setText(null);
                                        }
                                    });
                                    init();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }else {
                    Toast.makeText(DynamicActivity.this, "你并未作出评论", Toast.LENGTH_SHORT).show();
                }
            }
        });
        init();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder().add("post_id",currentDynamicId).build();
                    Log.d(TAG, currentDynamicId);
                    Request request=new Request.Builder().post(formBody).url(urlInit).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    if(response.isSuccessful()){
                        Gson gson=new Gson();
                        dynamicActivityCommentItemList=gson.fromJson(responseData,new TypeToken<List<DynamicActivityCommentItem>>(){}.getType());
                        adapter=new DynamicActivityCommentAdapter(dynamicActivityCommentItemList);
                        runOnUiThread(new Runnable() {
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
