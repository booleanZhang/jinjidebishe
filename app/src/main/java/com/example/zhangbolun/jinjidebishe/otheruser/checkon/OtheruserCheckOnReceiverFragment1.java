package com.example.zhangbolun.jinjidebishe.otheruser.checkon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

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

/**
 * Created by zhangbolun on 2017/4/26.
 */

public class OtheruserCheckOnReceiverFragment1 extends Fragment {
    private String TAG = "booleanZhang";
    private String date = "";
    private OtherUsersActivity otheruserActivity;
    private String url = "http://" + currentUrl + ":8888/android_connect/otheruser_checkon1.php";
    private String user;

    private CheckOnAdapter adapter;

    @BindView(R.id.fragmentotheruser_checkon_receiver1_rv)
    RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otheruser_checkon_receiver1, container, false);
        ButterKnife.bind(this, view);
        otheruserActivity = (OtherUsersActivity) getActivity();
        if (otheruserActivity.getCurrentUser().length() == 10) {
            user = otheruserActivity.getCurrentUser().substring(0, 9);
            Log.d(TAG, user);
        }else{
            user=otheruserActivity.getCurrentUser();
        }

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1); //试试getActivity()
        recyclerView.setLayoutManager(layoutManager);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("post_id", user)
                            .build();
                    Request request = new Request.Builder().post(requestBody).url(url).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    if (response.isSuccessful()) {
                        Gson gson = new Gson();
                        List<CheckOnDetail> checkOnDetailList = gson.fromJson(responseData, new TypeToken<List<CheckOnDetail>>() {
                        }.getType());
                        adapter = new CheckOnAdapter(checkOnDetailList);
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

        return view;
    }
}
