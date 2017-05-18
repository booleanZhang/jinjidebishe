package com.example.zhangbolun.jinjidebishe.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.zhangbolun.jinjidebishe.OtherUsersActivity;
import com.example.zhangbolun.jinjidebishe.R;
import com.example.zhangbolun.jinjidebishe.TeacherActivity;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import com.example.zhangbolun.jinjidebishe.currentUser.CurrentUser;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhangbolun on 2017/4/5.
 */

public class LoginFragment extends Fragment {
    public static CurrentUser currentUser=null;
    public static String TAG="this";

    public final static String currentUrl="172.20.10.7";
//    public final static String currentUrl="172.20.10.8";
    public static String url_login_register="http://"+currentUrl+":8888/android_connect/login_register.php"; //注册和登录时所需的url
    private String identity="teacher"; //记录当前要登录的用户身份

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private ProgressDialog mProgressDialog;
    private EditText username;
    private EditText pwd;
    private CheckBox remember_pwd;
    private RadioGroup mRadioGroup;
    private RadioButton radio_teacher=null;
    private RadioButton radio_student=null;
    private RadioButton radio_parent=null;
    private Button btn;
    private ImageButton imageButton;
    private int imageButtonTemp=0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_login,container,false);
        username=(EditText) view.findViewById(R.id.fragmentlogin_username);
        pwd=(EditText)view.findViewById(R.id.fragmentlogin_pwd);
        btn=(Button)view.findViewById(R.id.fragmentlogin_btn);
        remember_pwd=(CheckBox)view.findViewById(R.id.fragmentlogin_remember_pwd);
        mRadioGroup=(RadioGroup)view.findViewById(R.id.fragmentlogin_radio_group);
        radio_teacher=(RadioButton)view.findViewById(R.id.fragmentlogin_radio_teacher);
        radio_student=(RadioButton)view.findViewById(R.id.fragmentlogin_radio_student);
        radio_parent=(RadioButton)view.findViewById(R.id.fragmentlogin_radio_parent);
        imageButton=(ImageButton)view.findViewById(R.id.fragmentlogin_imagebtn) ;
        currentUser=new CurrentUser();

        pref= PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean isRemember=pref.getBoolean("remember_password",false);
        if(isRemember){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            username.setText(account);
            pwd.setText(password);
            remember_pwd.setChecked(true);
        }

        mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==radio_teacher.getId()){
                    //选择教师登录
                    identity="teacher";
                    Log.d(TAG, identity);
                }else if(checkedId==radio_student.getId()){
                    //选择学生登录
                    identity="student";
                    Log.d(TAG, identity);
                }else if(checkedId==radio_parent.getId()){
                    //选择家长登录
                    identity="parent";
                    Log.d(TAG, identity);
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //要实现登陆部分的逻辑代码。
                mProgressDialog=new ProgressDialog(getContext());
                mProgressDialog.setMessage("登录中...........");
                mProgressDialog.show();
                EMClient.getInstance().login(username.getText().toString(), pwd.getText().toString(),
                        new EMCallBack() { //EMCallBack中的方法执行在子线程中
                            @Override
                            public void onSuccess() {
                                EMClient.getInstance().groupManager().loadAllGroups();
                                EMClient.getInstance().chatManager().loadAllConversations();
                                Log.d(TAG, "登录聊天服务器成功");
                                loginToLocalSever(url_login_register,username.getText().toString(),pwd.getText().toString());
                            }

                            @Override
                            public void onError(int i, String s) {
                                //s的值为返回的错误信息
                                mProgressDialog.dismiss();
                                Log.d(TAG, "登录聊天服务器失败");
                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (imageButtonTemp){
                    case 0:{
                        pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        imageButtonTemp=1;
                    }
                        break;
                    case 1:{
                        pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        imageButtonTemp=0;
                    }
                        break;
                }
            }
        });
        return view;
    }

    public void loginToLocalSever(String url,final String id,final String pwd){
        //登录和注册向同一个php文件中发，添加标识
        try{
            OkHttpClient client=new OkHttpClient();
            RequestBody formBody=new FormBody.Builder()
                    .add("post_id",id)
                    .add("post_password",pwd)
                    .add("post_mark","0")
                    .add("post_identity",identity)
                    .build();
            Request request=new Request.Builder()
                    .url(url_login_register)
                    .post(formBody)
                    .build();
            Response response=client.newCall(request).execute();
            String responseData=response.body().string();
            Log.d(TAG, response.toString());
            if(response.isSuccessful()){
                //根据本地服务器返回的数据来决定
                Gson gson=new Gson();
                currentUser=gson.fromJson(responseData,CurrentUser.class);
                Log.d(TAG, currentUser.getReturnCode());
                if (currentUser.getReturnCode().equals("00")) {
                    Log.d(TAG, "登陆成功");
                    Log.d(TAG, "============="+identity);
                    editor=pref.edit();
                    if(remember_pwd.isChecked()){
                        editor.putBoolean("remember_password",true);
                        editor.putString("account",username.getText().toString().trim());
                        editor.putString("password",this.pwd.getText().toString().trim());
                    }else{
                        editor.clear();
                    }
                    editor.apply();
                    switch(identity){
                        case "teacher":
                            //跳转到教师操作界面
                            mProgressDialog.dismiss();
                            Intent intent=new Intent(getActivity(), TeacherActivity.class);
                            intent.putExtra("com/example/zhangbolun/jinjidebishe/currentUser",currentUser.getCurrent_id());
                            startActivity(intent);
                            getActivity().finish();
                            break;
                        case "student":
                            mProgressDialog.dismiss();
                            Intent intentOtherUser=new Intent(getActivity(), OtherUsersActivity.class);
                            intentOtherUser.putExtra("com/example/zhangbolun/jinjidebishe/currentUser",currentUser.getCurrent_id());
                            startActivity(intentOtherUser);
                            getActivity().finish();
                            break;
                        case "parent":
                            mProgressDialog.dismiss();
                            Intent intentOtherUsers=new Intent(getActivity(), OtherUsersActivity.class);
                            intentOtherUsers.putExtra("com/example/zhangbolun/jinjidebishe/currentUser",currentUser.getCurrent_id());
                            startActivity(intentOtherUsers);
                            getActivity().finish();
                            break;
                        default:
                            break;
                    }
                }else if(currentUser.getReturnCode().equals("01")){
                    Log.d(TAG, "密码错误");
                    username.setText("");
                    this.pwd.setText("");
                }else if(currentUser.getReturnCode().equals("02")){
                    Log.d(TAG, "没有找到用户名");
                    username.setText("");
                    this.pwd.setText("");
                }else if(currentUser.getReturnCode().equals("-1")){
                    Log.d(TAG, "数据库连接失败");
                }
            }else{
                mProgressDialog.dismiss();
                Log.d(TAG, "登录本地服务器失败");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
