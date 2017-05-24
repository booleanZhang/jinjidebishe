package com.example.zhangbolun.jinjidebishe.teacher.gradeInput;

/**
 * Created by zhangbolun on 2017/5/20.
 * 要和excel中的标题匹配
 */

public class ExcelItem {
    private String Student;
    private String Course;
    private String Startdate;
    private String Enddate;
    private String Testdate;
    private String Grade;
    private String Teacher;
    private String Testtime;

    public ExcelItem(String student, String course, String startdate, String enddate, String testdate, String grade, String teacher, String testtime) {
        Student = student;
        Course = course;
        Startdate = startdate;
        Enddate = enddate;
        Testdate = testdate;
        Grade = grade;
        Teacher = teacher;
        Testtime = testtime;
    }

    public String getStudent() {
        return Student;
    }

    public void setStudent(String student) {
        Student = student;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getStartdate() {
        return Startdate;
    }

    public void setStartdate(String startdate) {
        Startdate = startdate;
    }

    public String getEnddate() {
        return Enddate;
    }

    public void setEnddate(String enddate) {
        Enddate = enddate;
    }

    public String getTestdate() {
        return Testdate;
    }

    public void setTestdate(String testdate) {
        Testdate = testdate;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }

    public String getTesttime() {
        return Testtime;
    }

    public void setTesttime(String testtime) {
        Testtime = testtime;
    }
}
