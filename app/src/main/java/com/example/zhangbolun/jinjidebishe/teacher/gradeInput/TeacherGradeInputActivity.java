package com.example.zhangbolun.jinjidebishe.teacher.gradeInput;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhangbolun.jinjidebishe.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.zhangbolun.jinjidebishe.login.LoginFragment.currentUrl;

public class TeacherGradeInputActivity extends AppCompatActivity {
    @BindView(R.id.activity_teacher_grade_input_recyclerview)RecyclerView recyclerView;
    @BindView(R.id.activity_teacher_grade_input_toolbar)Toolbar toolbar;
    @BindView(R.id.activity_teacher_grade_input_edittext)EditText editText;
    @BindView(R.id.activity_teacher_grade_input_button)Button button;
    @BindView(R.id.activity_teacher_grade_input_buttonConfirm)Button buttonConfirm;

    private String url="http://"+currentUrl+":8888/android_connect/teacher_input_grade.php";
    private String filePath;
    private String TAG="教师成绩录入活动";
    private int number=0;
    private List<ExcelItem> realDatas=new ArrayList<>();

    private TeacherGradeInputActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_grade_input);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        filePath=intent.getStringExtra("FILE_PATH");
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle("请检查要输入的成绩内容");
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number=Integer.valueOf(editText.getText().toString());
                new ImportDataFromExcel().importExcelData(filePath);
            }
        });
        
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Gson gson=new Gson();
                            String json=gson.toJson(realDatas);
                            OkHttpClient client=new OkHttpClient();
                            RequestBody formBody=new FormBody.Builder().add("jsonData",json).build();
                            Request request=new Request.Builder().post(formBody).url(url).build();
                            Response response=client.newCall(request).execute();
                            String responseData=response.body().string();
                            if(response.isSuccessful()){
                                Log.d(TAG, responseData);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TeacherGradeInputActivity.this, "成绩上传成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class ImportDataFromExcel {
        //将excel文件导入到内存中
        private List<ExcelItem> datas; //需要一个对应excel表中列的类，同recyclerView的Item类一样  2333
        public String importExcelData(String filePath){
            datas = new ArrayList<>();
            realDatas=new ArrayList<>();
            Workbook workbook = null;
            try {
                Log.d(TAG, filePath);
                workbook = Workbook.getWorkbook(new File(filePath));
                Sheet sheet = workbook.getSheet(0);
                int rows = sheet.getRows();
                int columns = sheet.getColumns();
                //遍历excel文件的每行每列
                for (int i=0; i < rows ;i++){
                    //遍历行
                    List<String> li = new ArrayList<>();
                    for (int j = 0 ; j < columns ; j++ ){
                        Cell cell = sheet.getCell(j,i);
                        String result = cell.getContents();
                        if (i!=0){
                            li.add(result);
                        }
                    }
                    if (li.size()>0){
                        datas.add(new ExcelItem(li.get(0),li.get(1),li.get(2),li.get(3),li.get(4),li.get(5),li.get(6),li.get(7)));
                    }
                    li=null;
                }
                Gson gson = new Gson();
                for(int i=1;i<=number;i++){
                    realDatas.add(datas.get(i));
                }
                Log.d(TAG, gson.toJson(realDatas));
                adapter=new TeacherGradeInputActivityAdapter(realDatas);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BiffException e) {
                e.printStackTrace();
            }
            return "error";
        }
    }
}
