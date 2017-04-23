package teacher.notification;

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

import com.example.zhangbolun.jinjidebishe.R;
import com.example.zhangbolun.jinjidebishe.TeacherActivity;
import com.example.zhangbolun.jinjidebishe.TeacherAddNotification;
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

import static login.LoginFragment.currentUrl;

/**
 * Created by zhangbolun on 2017/4/14.
 */

public class TeacherNotificationReceiverFragment extends Fragment {
    @BindView(R.id.fragmentteacher_notification_receiver_rv)
    RecyclerView recyclerView;
    @BindView(R.id.fragmentteacher_notification_receiver_fab)
    FloatingActionButton fab;
    @BindView(R.id.fragmentteacher_notification_receiver_srl)
    SwipeRefreshLayout srl;

    private NotificationAdapter adapter;
    private String TAG="TeacherNotification";
    private TeacherActivity teacherActivity;
    private String url_teacher_notification="http://"+currentUrl+":8888/android_connect/teacher_navigation.php";
    private String responseData;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_teacher_notification_receiver,container,false);
        ButterKnife.bind(this,view);
        teacherActivity=(TeacherActivity) getActivity();
//        Log.d(TAG, "==============================");
//        Log.d(TAG, teacherActivity.getCurrentUser());

        srl.setColorSchemeResources(R.color.colorPrimary);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNotification();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification();
            }
        });

        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),1); //试试getActivity()
        recyclerView.setLayoutManager(layoutManager);
        getNotifications();
        return view;
    }

    public void refreshNotification(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder()
                            .add("post_id",teacherActivity.getCurrentUser())
                            .add("post_mark","1")
                            .build();
                    Request request=new Request.Builder()
                            .url(url_teacher_notification)
                            .post(formBody)
                            .build();
                    Response response=client.newCall(request).execute();
                    responseData=response.body().string();
                    if (response.isSuccessful()){
                        //查询成功
                        Gson gson=new Gson();
                        List<TeacherNotification> notificationList=gson.fromJson(responseData,new TypeToken<List<TeacherNotification>>(){}.getType());
//                        for(TeacherNotification notification:notificationList){
//                            Log.d(TAG, notification.getNotification_sender());
//                        }
                        adapter=new NotificationAdapter(notificationList);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                recyclerView.setAdapter(adapter);
                                srl.setRefreshing(false);
                            }
                        });
                    }else{
                        Log.d(TAG, "从服务器查询失败");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getNotifications(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder()
                            .add("post_id",teacherActivity.getCurrentUser())
                            .add("post_mark","1")
                            .build();
                    Request request=new Request.Builder()
                            .url(url_teacher_notification)
                            .post(formBody)
                            .build();
                    Response response=client.newCall(request).execute();
                    responseData=response.body().string();
                    if (response.isSuccessful()){
                        //查询成功
                        Gson gson=new Gson();
                        List<TeacherNotification> notificationList=gson.fromJson(responseData,new TypeToken<List<TeacherNotification>>(){}.getType());
//                        for(TeacherNotification notification:notificationList){
//                            Log.d(TAG, notification.ge tNotification_sender());
//                        }
                        adapter=new NotificationAdapter(notificationList);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }else{
                        Log.d(TAG, "从服务器查询失败");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void addNotification(){
        Intent intent=new Intent(getActivity(), TeacherAddNotification.class);
        intent.putExtra("currentUser",teacherActivity.getCurrentUser());
        intent.putExtra("type","0"); //0 通知
        startActivity(intent);
    }

}
