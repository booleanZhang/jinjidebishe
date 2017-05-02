package com.example.zhangbolun.jinjidebishe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.zhangbolun.jinjidebishe.otheruser.checkon.OtheruserCheckonFragment;
import com.example.zhangbolun.jinjidebishe.otheruser.holiday.OtheruserHolidayParnetFragment;
import com.example.zhangbolun.jinjidebishe.otheruser.holiday.OtheruserHolidayStudentContainer;
import com.example.zhangbolun.jinjidebishe.otheruser.holiday.OtheruserHolidayStudentFragment;
import com.example.zhangbolun.jinjidebishe.otheruser.notification.OtheruserNotificationFragment;

public class OtherUsersActivity extends AppCompatActivity {
    @BindView(R.id.otheruser_nav_view)NavigationView mNavigationView;
    private String TAG="OtheruserActivity";
    public String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_users);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        currentUser=intent.getStringExtra("com/example/zhangbolun/jinjidebishe/currentUser");
        Log.d(TAG, currentUser);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.otheruser_notification:
                        replaceFragment(new OtheruserNotificationFragment());
                        break;
                    case R.id.otheruser_checkon:
                        replaceFragment(new OtheruserCheckonFragment());
                        break;
                    case R.id.otheruser_holiday:
                        //学生请假条
                    {
                        if(getCurrentUser().length()==9){
                            replaceFragment(new OtheruserHolidayStudentContainer());
                        }else if(getCurrentUser().length()==10){
                            //家长登录
                            replaceFragment(new OtheruserHolidayParnetFragment());
                        }
                    }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        replaceFragment(new OtheruserNotificationFragment());
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_otheruser_framelayout,fragment);
        transaction.commit();

    }

    public String getCurrentUser(){
        return currentUser;
    }
}
