package teacher.notification;

import android.os.Bundle;
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
 * Created by zhangbolun on 2017/4/20.
 */

public class TeacherNotificationReceiverFragment3 extends Fragment{
    @BindView(R.id.fragmentteacher_notification_receiver3_rv)
    RecyclerView recyclerView;
    @BindView(R.id.fragmentteacher_notification_receiver3_srl)
    SwipeRefreshLayout srl;

    private NotificationAdapter3 adapter;
    private TeacherActivity teacherActivity;
    private String url="http://"+currentUrl+":8888/android_connect/teacher_notification3.php";
    private String TAG="NotificationReceiver3";


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_teacher_notification_receiver3,container,false);
        ButterKnife.bind(this,view);
        teacherActivity=(TeacherActivity)getActivity();
        srl.setColorSchemeResources(R.color.colorPrimary);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNotification();
            }
        });

        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),1); //试试getActivity()
        recyclerView.setLayoutManager(layoutManager);
        getNotifications();
        return view;
    }

    private void getNotifications(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder().add("post_id",teacherActivity.getCurrentUser()).add("post_mark","1").build();
                    Request request=new Request.Builder().post(formBody).url(url).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    if(response.isSuccessful()){
                        Gson gson=new Gson();
                        List<TeacherNotification3> notificationList=gson.fromJson(responseData,new TypeToken<List<TeacherNotification3>>(){}.getType());
                        adapter=new NotificationAdapter3(notificationList);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }else{

                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void refreshNotification(){
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
                            .url(url)
                            .post(formBody)
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    if (response.isSuccessful()){
                        //查询成功
                        Gson gson=new Gson();
                        List<TeacherNotification3> notificationList=gson.fromJson(responseData,new TypeToken<List<TeacherNotification3>>(){}.getType());
//                        for(TeacherNotification notification:notificationList){
//                            Log.d(TAG, notification.getNotification_sender());
//                        }
                        adapter=new NotificationAdapter3(notificationList);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                recyclerView.setAdapter(adapter);
                                srl.setRefreshing(false);
                            }
                        });
                    }else{
                        Log.d(TAG, "c");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
