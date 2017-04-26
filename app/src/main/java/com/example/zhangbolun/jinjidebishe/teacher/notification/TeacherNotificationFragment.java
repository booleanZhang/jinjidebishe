package com.example.zhangbolun.jinjidebishe.teacher.notification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.zhangbolun.jinjidebishe.R;

/**
 * Created by zhangbolun on 2017/4/12.
 */

public class TeacherNotificationFragment extends Fragment {
    private String TAG="TeacherNotification";
    private DrawerLayout mDrawerLayout;
    private BottomNavigationBar bottomNavigationBar;
//    private TeacherActivity teacherActivity;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_teacher_notification,container,false);
//        teacherActivity=(TeacherActivity)getActivity();
//        Log.d(TAG, teacherActivity.getCurrentUser());
        setHasOptionsMenu(true);
        final Toolbar toolbar=(Toolbar)view.findViewById(R.id.fragment_teacher_notification_toolbar);
        toolbar.setTitle("通知/作业/缴费信息");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.iconmonstr_menu_2_16);
        }
        mDrawerLayout=(DrawerLayout)getActivity().findViewById(R.id.teacher_drawer);
        bottomNavigationBar=(BottomNavigationBar)view.findViewById(R.id.fragment_teacher_notification_bottom_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);                                                        //淡蓝色
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.iconmonstr_book_23_32,"通知").setInActiveColor(R.color.colorPrimary).setActiveColor("#1755BF")/*.setBadgeItem(numberBadgeItem)*/)
                .addItem(new BottomNavigationItem(R.mipmap.iconmonstr_task_1_32,"作业").setInActiveColor(R.color.colorPrimaryDark).setActiveColor("#560EB8")) //绿色代码
                .addItem(new BottomNavigationItem(R.mipmap.iconmonstr_coin_10_32,"缴费").setInActiveColor(R.color.colorPrimaryDark).setActiveColor("#2D94C8"))//红色
                .addItem(new BottomNavigationItem(R.mipmap.iconmonstr_email_4_32,"教师通知").setInActiveColor(R.color.colorPrimaryDark).setActiveColor("#05A754"))
                .setFirstSelectedPosition(0)
                .initialise();
        replaceFragment(new TeacherNotificationReceiverFragment());
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch(position){
                    case 0:
                        replaceFragment(new TeacherNotificationReceiverFragment());
                        break;
                    case 1:
                        replaceFragment(new TeacherNotificationReceiverFragment1());
                        break;
                    case 2:
                        replaceFragment(new TeacherNotificationReceiverFragment2());
                        break;
                    case 3:
                        replaceFragment(new TeacherNotificationReceiverFragment3());
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.teacher_notification_toolbar,menu);
        super.onCreateOptionsMenu(menu,inflater);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.teacher_notification_item1:
                break;
            case R.id.teacher_notification_item2:
                break;
            case R.id.teacher_notification_item3:
                break;
            case R.id.teacher_notification_item4:
                break;
            default:
                break;
        }
        return false;
    }

    private void replaceFragment(Fragment fragment){
//       FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentManager fragmentManager=getChildFragmentManager(); //嵌套使用fragment
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_teacher_notification_container,fragment);
        transaction.commit();
    }
}
