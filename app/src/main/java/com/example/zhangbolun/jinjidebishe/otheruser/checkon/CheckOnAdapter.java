package com.example.zhangbolun.jinjidebishe.otheruser.checkon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangbolun.jinjidebishe.R;

import java.util.List;


/**
 * Created by zhangbolun on 2017/4/26.
 */

public class CheckOnAdapter extends RecyclerView.Adapter<CheckOnAdapter.ViewHolder> {
    private Context mContext;
    private List<CheckOnDetail> mCheckOnList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView date;
        TextView absence;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            absence=(TextView)view.findViewById(R.id.checkon_item_otheruser_absence);
            date=(TextView)view.findViewById(R.id.checkon_item_otheruser_date);
        }
    }

    public CheckOnAdapter(List<CheckOnDetail> checkOnList){
        mCheckOnList= checkOnList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.checkon_item_otheruser,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                CheckOnDetail checkOnDetail=mCheckOnList.get(position);
                Intent intent=new Intent(mContext,OtheruserCheckOnDetailActivity.class);
                intent.putExtra("name",checkOnDetail.getName());
                intent.putExtra("date",checkOnDetail.getDate());
                intent.putExtra("absence",checkOnDetail.getAbsence());
                intent.putExtra("intime",checkOnDetail.getIntime());
                intent.putExtra("outtime",checkOnDetail.getOuttime());
                intent.putExtra("reason",checkOnDetail.getReason());
                intent.putExtra("id",checkOnDetail.getIntime());
                mContext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        CheckOnDetail checkOn=mCheckOnList.get(position);
        holder.date.setText(checkOn.getDate());
        if(checkOn.getAbsence().equals("0")){
            //学生到校上课
            holder.absence.setText("到校");
        }else if(checkOn.getAbsence().equals("1")){
            //学生未到校
            holder.absence.setText("缺勤");
        }else{
            holder.absence.setText("缺勤");
        }
    }

    public int getItemCount(){
        return mCheckOnList.size();
    }
}
