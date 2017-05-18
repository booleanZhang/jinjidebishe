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
 * Created by zhangbolun on 2017/5/18.
 */

public class OtheruserContactAdapter extends RecyclerView.Adapter<OtheruserContactAdapter.ViewHolder>{
    private Context mContext;
    private List<OtheruserContact> motheruserContactList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        CircleImageView circleImageView;
        TextView id;
        TextView name;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            circleImageView=(CircleImageView)view.findViewById(R.id.fragment_otheruser_contact_item_picture);
            id=(TextView)view.findViewById(R.id.fragment_otheruser_contact_item_id);
            name=(TextView)view.findViewById(R.id.fragment_otheruser_contact_item_name);
        }
    }
    
    public OtheruserContactAdapter(List<OtheruserContact> otheruserContactList){
        motheruserContactList=otheruserContactList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.fragment_otheruser_contact_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                OtheruserContact otheruserContact=motheruserContactList.get(position);
                Intent intent=new Intent(mContext,TeacherContactItemActivity.class);
                intent.putExtra("ID",otheruserContact.getId());
                intent.putExtra("NAME",otheruserContact.getName());
                intent.putExtra("PICTURE",otheruserContact.getPicture());
                intent.putExtra("PHONE",otheruserContact.getPhone());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        OtheruserContact otheruserContact=motheruserContactList.get(position);
        if(otheruserContact.getPicture()!=null){
            if(!otheruserContact.getPicture().equals("")){
                Glide.with(mContext).load(otheruserContact.getPicture()).into(holder.circleImageView);
            }
        }
        holder.id.setText(otheruserContact.getId());
        holder.name.setText(otheruserContact.getMark()+":"+otheruserContact.getName());
    }

    public int getItemCount(){
        return motheruserContactList.size();
    }
}
