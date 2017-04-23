package teacher.notification;

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
 * Created by zhangbolun on 2017/4/18.
 */

public class NotificationAdapter1 extends RecyclerView.Adapter<NotificationAdapter1.ViewHolder> {
    private Context mContext;
    private List<TeacherNotification1> mNotificationList;

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
            sender=(TextView)view.findViewById(R.id.notification_item_sender1);
            head=(TextView)view.findViewById(R.id.notification_item_head1);
            body=(TextView)view.findViewById(R.id.notification_item_body1);
            date=(TextView)view.findViewById(R.id.notification_item_date1);
            scope=(TextView)view.findViewById(R.id.notification_item_scope1);
        }
    }

    public NotificationAdapter1(List<TeacherNotification1> notificationList) {
        mNotificationList = notificationList;
    }
    public NotificationAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.notification_item1, parent, false);
        final NotificationAdapter1.ViewHolder holder = new NotificationAdapter1.ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                TeacherNotification1 notification = mNotificationList.get(position);
                Intent intent = new Intent(mContext, NotificationDetailTeacher.class);
                intent.putExtra("sender", notification.getNotification_sender());
                intent.putExtra("head", notification.getNotification_head());
                intent.putExtra("body", notification.getNotification_body());
                intent.putExtra("date", notification.getNotification_date());
                intent.putExtra("scope", notification.getNotification_scope());
                intent.putExtra("mark","1"); //1 作业
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    public void onBindViewHolder(NotificationAdapter1.ViewHolder holder, int position) {
        TeacherNotification1 teacherNotification = mNotificationList.get(position);
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
