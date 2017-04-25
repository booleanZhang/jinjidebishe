package otheruser.notification;

import android.os.Bundle;
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
import teacher.notification.*;

import static login.LoginFragment.currentUrl;

/**
 * Created by zhangbolun on 2017/4/25.
 */

public class OtheruserNotificationReceiverFragment1 extends Fragment {
    @BindView(R.id.fragmentotheruser_notification_receiver1_rv)RecyclerView recyclerView;
    @BindView(R.id.fragmentotheruser_notification_receiver1_srl)SwipeRefreshLayout swipeRefreshLayout;

    OtherUsersActivity otheruserActivity;
    private String url="http://"+currentUrl+":8888/android_connect/otheruser_notification1.php";
    private String currentClass; //当前用户所处的班级
    private String TAG="userNotiRec1";
    private NotificationAdapter1 adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_otheruser_notification_receiver1,container,false);
        ButterKnife.bind(this,view);
        otheruserActivity=(OtherUsersActivity)getActivity();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNotification();
            }
        });
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),1); //试试getActivity()
        recyclerView.setLayoutManager(layoutManager);
        getNotifications();
        int idLength=otheruserActivity.getCurrentUser().length();
        currentClass=otheruserActivity.getCurrentUser().substring(0,7);
        //Log.d(TAG, "onCreateView: 当前用户所在班级:"+currentClass);
        return view;
    }

    public void refreshNotification(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder()
                            .add("post_id",currentClass)
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
                        List<OtheruserNotification1> notificationList=gson.fromJson(responseData,new TypeToken<List<OtheruserNotification1>>(){}.getType());
//                        for(TeacherNotification notification:notificationList){
//                            Log.d(TAG, notification.getNotification_sender());
//                        }
                        adapter=new NotificationAdapter1(notificationList);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                recyclerView.setAdapter(adapter);
                                swipeRefreshLayout.setRefreshing(false);
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
                            .add("post_id",currentClass)
                            .add("post_type","0")
                            .build();
                    Request request=new Request.Builder()
                            .post(formBody)
                            .url(url)
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    if(response.isSuccessful()){
                        Gson gson=new Gson();
                        List<OtheruserNotification1> notificationList=gson.fromJson(responseData,new TypeToken<List<OtheruserNotification1>>(){}.getType());
                        adapter=new NotificationAdapter1(notificationList);
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
