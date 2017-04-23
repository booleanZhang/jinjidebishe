package com.example.zhangbolun.jinjidebishe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import login.LoginFragment;
import login.RegisterFragment;

public class Login extends AppCompatActivity {
    private BottomNavigationBar mBottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        assignsViews();
    }

    private void assignsViews(){
        BadgeItem numberBadgeItem=new BadgeItem()
                .setBorderWidth(1)
                .setBackgroundColorResource(R.color.colorPrimary)
                .setHideOnSelect(true);
        mBottomNavigationBar=(BottomNavigationBar)findViewById(R.id.activitylogin_bottom_bar);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);                                                        //深情的蓝色
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.lock_unlock_48px,"用户登录").setInActiveColor(R.color.colorPrimary).setActiveColor("#2E86C1")/*.setBadgeItem(numberBadgeItem)*/)
                .addItem(new BottomNavigationItem(R.mipmap.new_file_48px,"家长注册").setInActiveColor(R.color.colorPrimaryDark).setActiveColor("#DBA901")) //屎黄色代码
                .setFirstSelectedPosition(0)
                .initialise();
        replaceFragment(new LoginFragment());
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position){
                    case 0:
                        replaceFragment(new LoginFragment());
                        break;
                    case 1:
                        replaceFragment(new RegisterFragment());
                        break;
                    default:
                        Toast.makeText(Login.this, "GG", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.activitylogin_framelayout,fragment);
        transaction.commit();
    }
}
