package com.example.zhangbolun.jinjidebishe.teacher.checkon;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangbolun.jinjidebishe.R;

import java.util.List;


/**
 * Created by zhangbolun on 2017/4/22.
 */

public class CheckOnAdapter extends RecyclerView.Adapter<CheckOnAdapter.ViewHolder> {
    private Context mContext;
    private List<CheckOnDetail> mCheckOnList;
    private String TAG="k";

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView id;
        TextView name;
        TextView absence;
        TextView intime;
        TextView outtime;
        TextView reason;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            id = (TextView) view.findViewById(R.id.checkon_item_id);
            name = (TextView) view.findViewById(R.id.checkon_item_name);
            absence = (TextView) view.findViewById(R.id.checkon_item_absence);
            intime = (TextView) view.findViewById(R.id.checkon_item_intime);
            outtime = (TextView) view.findViewById(R.id.checkon_item_outtime);
            reason = (TextView) view.findViewById(R.id.checkon_item_reason);
        }
    }

    public CheckOnAdapter(List<CheckOnDetail> checkOnList) {
        mCheckOnList = checkOnList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.checkon_item, parent, false);

        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        CheckOnDetail checkOn = mCheckOnList.get(position);
        if (null == checkOn.getId()) {
            holder.absence.setVisibility(View.GONE);
            holder.intime.setVisibility(View.GONE);
            holder.outtime.setVisibility(View.GONE);
            holder.id.setVisibility(View.GONE);
            holder.name.setVisibility(View.GONE);
            holder.reason.setVisibility(View.GONE);
            Toast.makeText(mContext, "无当日签到信息", Toast.LENGTH_SHORT).show();
        } else {
            holder.id.setText(checkOn.getId());
            holder.name.setText(checkOn.getName());
            if (checkOn.getAbsence().equals("0")) {
                //学生到校上课
                holder.absence.setText("学生到校上课");
                holder.intime.setVisibility(View.VISIBLE);
                holder.outtime.setVisibility(View.VISIBLE);
                holder.intime.setText(checkOn.getIntime());
                holder.outtime.setText(checkOn.getOuttime());
                holder.reason.setVisibility(View.GONE);
            } else if (checkOn.getAbsence().equals("1")) {
                //学生未到校
                holder.absence.setText("学生未到校上课");
                holder.intime.setVisibility(View.GONE);
                holder.outtime.setVisibility(View.GONE);
                holder.reason.setVisibility(View.VISIBLE);
                holder.reason.setText("请假信息：" + "\r\n     " + checkOn.getReason());
            }
        }
    }

    public int getItemCount() {
        return mCheckOnList.size();
    }
}
