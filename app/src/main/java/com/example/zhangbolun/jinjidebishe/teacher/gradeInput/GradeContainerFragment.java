package com.example.zhangbolun.jinjidebishe.teacher.gradeInput;

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

public class GradeContainerFragment extends Fragment {
    @BindView(R.id.fragment_grade_container_bottomnavigationbar)BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.fragment_grade_container_toolbar)Toolbar toolbar;
    DrawerLayout mDrawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_grade_container, container, false);
        ButterKnife.bind(this,view);
        mDrawerLayout=(DrawerLayout) getActivity().findViewById(R.id.teacher_drawer);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar=((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle("教师文件操作");
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);                                                        //淡蓝色
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_people_outline_black_24dp,"教师批量录入成绩").setInActiveColor(R.color.colorPrimary).setActiveColor("#1755BF"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_school_black_24dp,"教师查看成绩录入评语").setInActiveColor(R.color.colorPrimaryDark).setActiveColor("#05A754"))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position){
                    case 0:
                        toolbar.setTitle("教师批量录入成绩");
                        replaceFragment(new GradeInputFragment());
                        break;
                    case 1:
                        toolbar.setTitle("教师查看成绩录入评语");
                        replaceFragment(new TeacherGradeThisYearFragment());
                        break;
                    default:
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
        replaceFragment(new GradeInputFragment());
        return view;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getChildFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_grade_container_container,fragment);
        transaction.commit();
    }
}
