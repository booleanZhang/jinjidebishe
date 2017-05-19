package com.example.zhangbolun.jinjidebishe.otheruser.file;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class OtheruserGetFileFragment extends Fragment {
    @BindView(R.id.fragment_otheruser_get_file_recyclerview)RecyclerView recyclerView;
    @BindView(R.id.fragment_otheruser_get_file_toolbar)Toolbar toolbar;
    @BindView(R.id.fragment_otheruser_get_file_edittext)EditText editText;
    @BindView(R.id.fragment_otheruser_get_file_button)Button button;
    @BindView(R.id.fragment_otheruser_get_file_swiperefreshlayout)SwipeRefreshLayout swipeRefreshLayout;
    DrawerLayout mDrawerLayout;

    private String TAG="学生/家长文件下载:";
    private String url="http://"+currentUrl+":8888/android_connect/file_get.php";
    private String urlSearch="http://"+currentUrl+":8888/android_connect/file_search.php";

    private List<FileItem> fileItemList;
    private FileAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_otheruser_get_file, container, false);
        ButterKnife.bind(this,view);
        mDrawerLayout=(DrawerLayout)getActivity().findViewById(R.id.otheruser_drawer);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("文件列表");
        ActionBar actionBar=((AppCompatActivity) getActivity()).getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        init();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
                adapter.notifyDataSetChanged();;
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().equals(null)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                OkHttpClient client=new OkHttpClient();
                                RequestBody formBody=new FormBody.Builder().add("post_name",editText.getText().toString().trim()).build();
                                Request request=new Request.Builder().post(formBody).url(urlSearch).build();
                                Response response=client.newCall(request).execute();
                                String responseData=response.body().string();
                                if(response.isSuccessful()){
                                    Gson gson=new Gson();
                                    fileItemList=gson.fromJson(responseData,new TypeToken<List<FileItem>>(){}.getType());
                                    adapter=new FileAdapter(fileItemList);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            recyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }else{
                    Toast.makeText(getContext(), "请输入要查询的文件名", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder().url(url).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    if(response.isSuccessful()){
                        Log.d(TAG, responseData);
                        Gson gson=new Gson();
                        fileItemList=gson.fromJson(responseData,new TypeToken<List<FileItem>>(){}.getType());
                        adapter=new FileAdapter(fileItemList);
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

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
