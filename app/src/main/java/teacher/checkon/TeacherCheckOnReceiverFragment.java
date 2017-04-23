package teacher.checkon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangbolun.jinjidebishe.R;
import com.example.zhangbolun.jinjidebishe.TeacherActivity;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import currentUser.Teacher;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static login.LoginFragment.currentUrl;
import static login.LoginFragment.url_login_register;

/**
 * Created by zhangbolun on 2017/4/21.
 */

public class TeacherCheckOnReceiverFragment extends Fragment{
    @BindView(R.id.fragmentteacher_checkon_receiver_sum)TextView sum;
    @BindView(R.id.fragmentteacher_checkon_receiver_holiday)TextView holiday;
    @BindView(R.id.fragmentteacher_checkon_receiver_late)TextView late;
    @BindView(R.id.fragmentteacher_checkon_receiver_early)TextView early;

    public String currenturl=currentUrl;
    private String url="http://"+currenturl+":8888/android_connect/teacher_checkon.php";
    private String TAG="heckOnReceiver";
    private CheckOnInformation checkOnInformation;
    TeacherActivity teacherActivity;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_teacher_checkon_receiver,container,false);
        ButterKnife.bind(this,view);
        teacherActivity=(TeacherActivity)getActivity();
        getNumbers();

        return view;
    }

    public void getNumbers(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                    String date=sDateFormat.format(new Date());
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder()
                            .add("post_id",teacherActivity.getCurrentUser())
                            .add("post_date",date)
                            .build();
                    Request request=new Request.Builder().post(formBody).url(url).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    if(response.isSuccessful()){
                        Gson gson=new Gson();
                        checkOnInformation=gson.fromJson(responseData,CheckOnInformation.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sum.setText(checkOnInformation.getSum());
                                holiday.setText(checkOnInformation.getHoliday());
                                late.setText(checkOnInformation.getLate());
                                early.setText(checkOnInformation.getEarly());
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
