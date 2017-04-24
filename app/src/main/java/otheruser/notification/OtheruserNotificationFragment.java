package otheruser.notification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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

public class OtheruserNotificationFragment extends Fragment {
    private String TAG="OtheruserNotificationFragment";
    private DrawerLayout mDrawerLayout;
    @BindView(R.id.fragment_otheruser_notification_bottom_bar)BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.fragment_otheruser_notification_toolbar)Toolbar toolbar;

    public View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_otheruser_notification,container,false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        toolbar.setTitle("通知缴费作业信息");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.iconmonstr_menu_2_16);
        }
        mDrawerLayout=(DrawerLayout) getActivity().findViewById(R.id.otheruser_drawer);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.iconmonstr_book_23_32,"通知").setInActiveColor(R.color.colorPrimary).setActiveColor("#1755BF")/*.setBadgeItem(numberBadgeItem)*/)
                .addItem(new BottomNavigationItem(R.mipmap.iconmonstr_task_1_32,"作业").setInActiveColor(R.color.colorPrimaryDark).setActiveColor("#560EB8")) //绿色代码
                .addItem(new BottomNavigationItem(R.mipmap.iconmonstr_coin_10_32,"缴费").setInActiveColor(R.color.colorPrimaryDark).setActiveColor("#2D94C8"))//红色
                .setFirstSelectedPosition(0)
                .initialise();

        return view;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getChildFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_otheruser_notification_container,fragment);
        transaction.commit();
    }

}
