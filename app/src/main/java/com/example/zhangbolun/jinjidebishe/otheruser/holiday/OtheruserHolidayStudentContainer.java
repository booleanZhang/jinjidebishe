package com.example.zhangbolun.jinjidebishe.otheruser.holiday;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.zhangbolun.jinjidebishe.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtheruserHolidayStudentContainer extends Fragment {
    @BindView(R.id.fragment_otheruser_holiday_container_bottomNavigationBar)BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.fragment_otheruser_holiday_container_toolbar)Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_otheruser_holiday_student_container, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        toolbar.setTitle("学生请假条");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.iconmonstr_menu_2_16);
        }
        mDrawerLayout=(DrawerLayout)getActivity().findViewById(R.id.otheruser_drawer);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);                                                        //淡蓝色
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.iconmonstr_book_23_32,"往日请假条").setInActiveColor(R.color.colorPrimary).setActiveColor("#1755BF")/*.setBadgeItem(numberBadgeItem)*/)
                .addItem(new BottomNavigationItem(R.mipmap.iconmonstr_task_1_32,"发送请假条").setInActiveColor(R.color.colorPrimaryDark).setActiveColor("#560EB8")) //绿色代码
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch(position){
                    case 0:
                        replaceFragment(new OtheruserHolidayStudentFragment1());
                        break;
                    case 1:
                        replaceFragment(new OtheruserHolidayStudentFragment());
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
        replaceFragment(new OtheruserHolidayStudentFragment1());
        return view;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return false;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getChildFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_otheruser_holiday_container_container,fragment);
        transaction.commit();

    }
}
