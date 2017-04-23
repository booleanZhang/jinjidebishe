package teacher.checkon;

/**
 * Created by zhangbolun on 2017/4/22.
 */

public class CheckOn {
    private String id;
    private String name;

    public CheckOn(String id,String name){
        this.name=name;
        this.id=id;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
