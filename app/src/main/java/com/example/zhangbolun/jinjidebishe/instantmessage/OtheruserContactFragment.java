package com.example.zhangbolun.jinjidebishe.instantmessage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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

public class OtheruserContactFragment extends Fragment {
    @BindView(R.id.fragment_otheruser_contact_recyclerview)RecyclerView recyclerView;
    @BindView(R.id.fragment_otheruser_contact_toolbar)Toolbar toolbar;
    DrawerLayout mDrawerLayout;

    OtherUsersActivity otherUsersActivity;

    private String url="http://"+currentUrl+":8888/android_connect/otheruser_get_contact.php";
    private String TAG="教师联系人:";

    OtheruserContactAdapter adapter;
    List<OtheruserContact> otheruserContactList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_otheruser_contact, container, false);
        ButterKnife.bind(this,view);
        mDrawerLayout=(DrawerLayout)getActivity().findViewById(R.id.otheruser_drawer);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("联系人");
        ActionBar actionBar=((AppCompatActivity) getActivity()).getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        otherUsersActivity=(OtherUsersActivity)getActivity();
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        getOtheruserContact();
        return view;
    }

    private void getOtheruserContact() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder().add("post_id", otherUsersActivity.getCurrentUser()).build();
                    Request request = new Request.Builder().url(url).post(formBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d(TAG, responseData);
                    if (response.isSuccessful()) {
                        Gson gson = new Gson();
                        otheruserContactList = gson.fromJson(responseData, new TypeToken<List<OtheruserContact>>() {
                        }.getType());
                        adapter = new OtheruserContactAdapter(otheruserContactList);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }
                } catch (Exception e) {
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
