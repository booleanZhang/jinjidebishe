package currentUser;

/**
 * Created by zhangbolun on 2017/4/10.
 */

public class CurrentUser {
    private String current_id;
    private String returnCode;

    public void setCurrent_id(String current_id){this.current_id=current_id;}
    public void setReturnCode(String returnCode){this.returnCode=returnCode;}

    public String getCurrent_id(){return current_id;}
    public String getReturnCode(){return returnCode;}
}
