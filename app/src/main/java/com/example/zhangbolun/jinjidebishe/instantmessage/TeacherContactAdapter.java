package com.example.zhangbolun.jinjidebishe.instantmessage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhangbolun.jinjidebishe.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhangbolun on 2017/5/17.
 */

public class TeacherContactAdapter extends RecyclerView.Adapter<TeacherContactAdapter.ViewHolder> {
    private Context mContext;
    private List<TeacherContact> mteacherContactList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        CircleImageView circleImageView;
        TextView id;
        TextView name;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            circleImageView=(CircleImageView)view.findViewById(R.id.fragment_teacher_contact_item_picture);
            id=(TextView)view.findViewById(R.id.fragment_teacher_contact_item_id);
            name=(TextView)view.findViewById(R.id.fragment_teacher_contact_item_name);
        }
    }

    public TeacherContactAdapter(List<TeacherContact> teacherContactList){
        mteacherContactList=teacherContactList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.fragment_teacher_contact_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                TeacherContact teacherContact=mteacherContactList.get(position);
                Intent intent=new Intent(mContext,TeacherContactItemActivity.class);
                intent.putExtra("ID",teacherContact.getId());
                intent.putExtra("NAME",teacherContact.getName());
                intent.putExtra("PICTURE",teacherContact.getPicture());
                intent.putExtra("PHONE",teacherContact.getPhone());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        TeacherContact teacherContact=mteacherContactList.get(position);
        if(teacherContact.getPicture()!=null){
            if(!teacherContact.getPicture().equals("")){
                Glide.with(mContext).load(teacherContact.getPicture()).into(holder.circleImageView);
            }
        }
        holder.id.setText(teacherContact.getId());
        holder.name.setText(teacherContact.getName());
    }

    public int getItemCount(){
        return mteacherContactList.size();
    }
}
