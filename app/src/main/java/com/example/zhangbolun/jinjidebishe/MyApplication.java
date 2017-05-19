package com.example.zhangbolun.jinjidebishe;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

//import com.hyphenate.chat.EMClient;
//import com.hyphenate.chat.EMOptions;

/**
 * Created by zhangbolun on 2017/3/30.
 */

public class MyApplication extends Application {
    public void onCreate(){
        super.onCreate();
//        EMOptions emp=new EMOptions();
//        emp.setAutoLogin(false);
//        EMClient.getInstance().init(this,emp);
        EMOptions emp=new EMOptions();
        emp.setAutoLogin(false);
        EaseUI.getInstance().init(this,emp);
//        EMClient.getInstance().setDebugMode(true);
    }
}
