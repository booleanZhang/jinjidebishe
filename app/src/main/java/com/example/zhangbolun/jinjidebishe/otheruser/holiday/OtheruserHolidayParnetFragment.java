package com.example.zhangbolun.jinjidebishe.otheruser.holiday;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhangbolun.jinjidebishe.R;

import butterknife.BindView;

public class OtheruserHolidayParnetFragment extends Fragment {
    @BindView(R.id.fragment_otheruser_holiday_parent_recyclerView)RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_otheruser_holiday_parnet, container, false);

        return view;
    }
}
