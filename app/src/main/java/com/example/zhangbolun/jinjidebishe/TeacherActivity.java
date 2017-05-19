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

import com.bumptech.glide.Glide;
import com.example.zhangbolun.jinjidebishe.instantmessage.TeacherContactFragment;
import com.example.zhangbolun.jinjidebishe.teacher.dynamic.DynamicTeacherFragment;
import com.example.zhangbolun.jinjidebishe.teacher.file.TeacherGetFileFragment;
import com.example.zhangbolun.jinjidebishe.teacher.holiday.TeacherHolidayFragment;
import com.example.zhangbolun.jinjidebishe.teacher.notification.TeacherNotificationFragment;
import com.example.zhangbolun.jinjidebishe.teacher.checkon.TeacherCheckOnFragment;
import com.example.zhangbolun.jinjidebishe.teacher.personal_information.TeacherPersonalInformationActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherActivity extends AppCompatActivity{
    private String TAG="TeacherActiviy";
    private NavigationView mNavigationView;

    public String currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        Intent intent=getIntent();
        currentUser=intent.getStringExtra("com/example/zhangbolun/jinjidebishe/currentUser");
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
                    case R.id.teacher_holiday:
                        //教师请假条
                        replaceFragment(new TeacherHolidayFragment());
                        break;
                    case R.id.teacher_dynamic:
                        replaceFragment(new DynamicTeacherFragment());
                        break;
                    case R.id.teacher_personal_information:
                        Intent intent=new Intent(TeacherActivity.this, TeacherPersonalInformationActivity.class);
                        intent.putExtra("teacherId",getCurrentUser());
                        startActivity(intent);
                        break;
                    case R.id.teacher_im:
                        replaceFragment(new TeacherContactFragment());
                        break;
                    case R.id.teacher_document:
                        replaceFragment(new TeacherGetFileFragment());
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
