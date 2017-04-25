package com.example.zhangbolun.jinjidebishe.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.zhangbolun.jinjidebishe.R;
import com.hyphenate.chat.EMClient;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.zhangbolun.jinjidebishe.login.LoginFragment.url_login_register;
import static com.example.zhangbolun.jinjidebishe.login.LoginFragment.TAG;

/**
 * Created by zhangbolun on 2017/3/30.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{
    private ProgressDialog mDialog;
    private EditText username;
    private EditText pwd;
    private EditText name;
    private EditText phone;
    private EditText sex;
    private EditText child1;
    private EditText child2;
    private Button btn;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_register,container,false);
        username=(EditText)view.findViewById(R.id.fragmentregister_username);
        pwd=(EditText)view.findViewById(R.id.fragmentregister_pwd);
        name=(EditText)view.findViewById(R.id.fragmentregister_name);
        phone=(EditText)view.findViewById(R.id.fragmentregister_phone);
        sex=(EditText)view.findViewById(R.id.fragmentregister_sex);
        child1=(EditText)view.findViewById(R.id.fragmentregister_child1);
        child2=(EditText)view.findViewById(R.id.fragmentregister_child2);
        btn=(Button)view.findViewById(R.id.fragmentregister_btn);

        btn.setOnClickListener(this);
        return view;
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.fragmentregister_btn:
                sendRegisterInformation();
                break;
            default:
                break;
        }
    }

    public void sendRegisterInformation(){
        mDialog=new ProgressDialog(getContext());
        mDialog.setMessage("注册中...........");
        mDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    EMClient.getInstance().createAccount(username.getText().toString().trim(),pwd.getText().toString().trim());
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody;
                    if(child2.getText().toString().equals("")){
                        formBody=new FormBody.Builder()
                                .add("post_mark","1")
                                .add("post_identity","parent")
                                .add("post_register_id",username.getText().toString().trim())
                                .add("post_register_password",pwd.getText().toString().trim())
                                .add("post_register_name",name.getText().toString().trim())
                                .add("post_register_phone",phone.getText().toString().trim())
                                .add("post_register_sex",sex.getText().toString().trim())
                                .add("post_register_student1",child1.getText().toString().trim())
                                .add("post_register_student2","").build();
                        Log.d(TAG, "child1==================>"+child1.getText().toString().trim());
                    }else{
                        formBody=new FormBody.Builder()
                                .add("post_mark","1")
                                .add("post_identity","parent")
                                .add("post_register_id",username.getText().toString().trim())
                                .add("post_register_password",pwd.getText().toString().trim())
                                .add("post_register_name",name.getText().toString().trim())
                                .add("post_register_phone",phone.getText().toString().trim())
                                .add("post_register_sex",sex.getText().toString().trim())
                                .add("post_register_student1",child1.getText().toString().trim())
                                .add("post_register_student2",child2.getText().toString().trim()).build();
                    }
                    Request request=new Request.Builder()
                            .url(url_login_register)
                            .post(formBody)
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    if(response.isSuccessful()){
                        mDialog.dismiss();
                        Log.d(TAG,"服务器返回的信息再看");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
