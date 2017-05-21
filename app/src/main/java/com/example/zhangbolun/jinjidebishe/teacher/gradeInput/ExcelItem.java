package com.example.zhangbolun.jinjidebishe.teacher.gradeInput;

/**
 * Created by zhangbolun on 2017/5/20.
 * 要和excel中的标题匹配
 */

public class ExcelItem {
    private String Id;
    private String Name;

    public ExcelItem(String id,String name){
        this.Id=id;
        this.Name=name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
