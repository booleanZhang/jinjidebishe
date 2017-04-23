package currentUser;

/**
 * Created by zhangbolun on 2017/4/9.
 */

public class Student {
    private String student_id;
    private String student_name;
    private String student_sex;
    private String student_in_date;
    private String student_out_date;
    private String student_parent1;
    private String student_parent2;
    private String student_class;
    private String student_teacher;
    private String student_password;
    private String student_phone;

    public String getStudent_id(){return student_id;}
    public String getName(){return student_name;}
    public String getSex(){return student_sex;}
    public String getInDate(){return student_in_date;}
    public String getOutDate(){return student_out_date;}
    public String getParent1(){return student_parent1;}
    public String getParent2(){return student_parent2;}
    public String getStudent_class(){return student_class;}
    public String getStudent_teacher(){return student_teacher;}
    public String getStudent_password(){return student_password;}
    public String getStudent_phone(){return student_phone;}

    public void setStudent_id(String student_id){this.student_id=student_id;}
    public void setStudent_name(String student_name){this.student_name=student_name;}
    public void setStudent_sex(String student_sex){this.student_sex=student_sex;}
    public void setStudent_in_date(String student_in_date){this.student_in_date=student_in_date;}
    public void setStudent_out_date(String student_out_date){this.student_out_date=student_out_date;}
    public void setStudent_parent1(String student_parent1){this.student_parent1=student_parent1;}
    public void setStudent_parent2(String student_parent2){this.student_parent2=student_parent2;}
    public void setStudent_class(String student_class){this.student_class=student_class;}
    public void setStudent_teacher(String student_teacher){this.student_teacher=student_teacher;}
    public void setStudent_password(String student_password){this.student_password=student_password;}
    public void setStudent_phone(String student_phone){this.student_phone=student_phone;}
}
