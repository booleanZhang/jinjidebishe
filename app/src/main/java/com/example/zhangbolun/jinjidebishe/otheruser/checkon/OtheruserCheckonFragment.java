package com.example.zhangbolun.jinjidebishe.otheruser.checkon;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.zhangbolun.jinjidebishe.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbolun on 2017/4/24.
 */

public class OtheruserCheckonFragment extends Fragment {
    @BindView(R.id.fragment_otheruser_checkon_toolbar)Toolbar toolbar;
    @BindView(R.id.fragment_otheruser_checkon_bottombar)BottomNavigationBar bottomNavigationBar;
    private DrawerLayout mDrawerLayout;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_otheruser_checkon,container,false);
        ButterKnife.bind(this,view);
        mDrawerLayout=(DrawerLayout)getActivity().findViewById(R.id.otheruser_drawer);
        setHasOptionsMenu(true);
        toolbar.setTitle("签到功能");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.iconmonstr_menu_2_16);
        }
        replaceFragment(new OtheruserCheckOnReceiverFragment());
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.iconmonstr_pen_3_32,"今日签到").setInActiveColor(R.color.colorPrimaryDark).setActiveColor("#00629B"))
                .addItem(new BottomNavigationItem(R.mipmap.iconmonstr_chrome_5_32,"签到记录浏览").setInActiveColor(R.color.colorPrimaryDark).setActiveColor("#BEC400"))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position){
                    case 1:
                        replaceFragment(new OtheruserCheckOnReceiverFragment1());
                        break;
                    case 0:
                        replaceFragment(new OtheruserCheckOnReceiverFragment());
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
        replaceFragment(new OtheruserCheckOnReceiverFragment());

        return view;
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

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getChildFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_othersuer_checkon_container,fragment);
        transaction.commit();
    }
}
