package teacher.notification;

/**
 * Created by zhangbolun on 2017/4/18.
 */

public class TeacherNotification1 {
    private String notification_head;
    private String notification_body;
    private String notification_date;
    private String notification_scope;
    private String notification_sender;

    public void setNotification_head(String notification_head){this.notification_head=notification_head;}
    public void setNotification_body(String notification_body){this.notification_body=notification_body;}
    public void setNotification_date(String notification_date){this.notification_date=notification_date;}
    public void setNotification_scope(String notification_scope){this.notification_scope=notification_scope;}
    public void setNotification_sender(String notification_sender){this.notification_sender=notification_sender;}

    public String getNotification_head(){return notification_head;}
    public String getNotification_body(){return notification_body;}
    public String getNotification_date(){return notification_date;}
    public String getNotification_scope(){return notification_scope;}
    public String getNotification_sender(){return notification_sender;}
}
