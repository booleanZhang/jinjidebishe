package teacher.checkon;

/**
 * Created by zhangbolun on 2017/4/23.
 */

public class CheckOnInformation {
    private String sum;
    private String late;
    private String early;
    private String holiday;

    public void setSum(String sum){this.sum=sum;}
    public void setLate(String late){this.late=late;}
    public void setEarly(String early){this.early=early;}
    public void setHoliday(String holiday){this.holiday=holiday;}

    public String getSum(){return sum;}
    public String getLate(){return late;}
    public String getEarly(){return early;}
    public String getHoliday(){return holiday;}
}
