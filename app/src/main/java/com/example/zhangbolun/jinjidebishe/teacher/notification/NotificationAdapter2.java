package com.example.zhangbolun.jinjidebishe.teacher.notification;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangbolun.jinjidebishe.NotificationDetailTeacher;
import com.example.zhangbolun.jinjidebishe.R;

import java.util.List;

/**
 * Created by zhangbolun on 2017/4/19.
 */

public class NotificationAdapter2 extends RecyclerView.Adapter<NotificationAdapter2.ViewHolder> {
    private Context mContext;
    private List<TeacherNotification2> mNotificationList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView sender;
        TextView date;
        TextView head;
        TextView body;
        TextView scope;
        public ViewHolder(View view) {
            super(view);
            cardView=(CardView)view;
            sender=(TextView)view.findViewById(R.id.notification_item_sender2);
            head=(TextView)view.findViewById(R.id.notification_item_head2);
            body=(TextView)view.findViewById(R.id.notification_item_body2);
            date=(TextView)view.findViewById(R.id.notification_item_date2);
            scope=(TextView)view.findViewById(R.id.notification_item_scope2);
        }
    }

    public NotificationAdapter2(List<TeacherNotification2> notificationList) {
        mNotificationList = notificationList;
    }
    public NotificationAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.notification_item2, parent, false);
        final NotificationAdapter2.ViewHolder holder = new NotificationAdapter2.ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                TeacherNotification2 notification = mNotificationList.get(position);
                Intent intent = new Intent(mContext, NotificationDetailTeacher.class);
                intent.putExtra("sender", notification.getNotification_sender());
                intent.putExtra("head", notification.getNotification_head());
                intent.putExtra("body", notification.getNotification_body());
                intent.putExtra("date", notification.getNotification_date());
                intent.putExtra("scope", notification.getNotification_scope());
                intent.putExtra("mark","2"); //2 缴费
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    public void onBindViewHolder(NotificationAdapter2.ViewHolder holder, int position) {
        TeacherNotification2 teacherNotification = mNotificationList.get(position);
        holder.sender.setText(teacherNotification.getNotification_sender());
        holder.date.setText(teacherNotification.getNotification_date());
        holder.head.setText(teacherNotification.getNotification_head());
        holder.body.setText(teacherNotification.getNotification_body());
        if (teacherNotification.getNotification_scope().equals("0")) {
            holder.scope.setText("所有班级");
        } else if (teacherNotification.getNotification_scope().equals("1")) {
            holder.scope.setText("指定班级");
        } else if (teacherNotification.getNotification_scope().equals("2")) {
            holder.scope.setText("指定个人");
        }
    }

    public int getItemCount() {
        return mNotificationList.size();
    }
}
