package com.example.zhangbolun.jinjidebishe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import teacher.notification.TeacherNotificationFragment;
import teacher.checkon.TeacherCheckOnFragment;

public class TeacherActivity extends AppCompatActivity{
    private String TAG="TeacherActiviy";
    private NavigationView mNavigationView;

    public String currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        Intent intent=getIntent();
        currentUser=intent.getStringExtra("currentUser");
        replaceFragment(new TeacherNotificationFragment());
        mNavigationView=(NavigationView)findViewById(R.id.teacher_nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.teacher_notification:
                        replaceFragment(new TeacherNotificationFragment());
                        break;
                    case R.id.teacher_checkon:
                        replaceFragment(new TeacherCheckOnFragment());
                        break;

                }
                return false;
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentMananger=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentMananger.beginTransaction();
        transaction.replace(R.id.activity_teacher_framelayout,fragment);
        transaction.commit();
    }

    public String getCurrentUser(){
        return currentUser;
    }
}
