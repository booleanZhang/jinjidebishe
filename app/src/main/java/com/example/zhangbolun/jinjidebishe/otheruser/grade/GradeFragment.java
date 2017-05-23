package com.example.zhangbolun.jinjidebishe.otheruser.grade;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangbolun.jinjidebishe.OtherUsersActivity;
import com.example.zhangbolun.jinjidebishe.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PieChartView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.zhangbolun.jinjidebishe.login.LoginFragment.currentUrl;

public class GradeFragment extends Fragment {
    @BindView(R.id.fragment_grade_textview1)TextView textView1;
    @BindView(R.id.fragment_grade_textview2)TextView textView2;
    @BindView(R.id.fragment_grade_recyclerview1)RecyclerView recyclerView1;
    @BindView(R.id.fragment_grade_recyclerview2)RecyclerView recyclerView2;
    @BindView(R.id.fragment_grade_toolbar)Toolbar toolbar;
    @BindView(R.id.chart)PieChartView pieChart;
    DrawerLayout mDrawerLayout;

    private OtherUsersActivity otherUsersActivity;

    private String urlThisYear="http://"+currentUrl+":8888/android_connect/otheruser_grade_this_year.php";
    private String urlAll="http://"+currentUrl+":8888/android_connect/otheruser_grade_all.php";
    private String TAG="学生/家长查看成绩:";
    private int markThisYear=0;
    private int markAll=0;

    private List<GradeAll> gradeAllList;
    private List<GradeThisYear> gradeThisYearList;

    private GradeAllAdapter gradeAllAdapter;
    private GradeThisYearAdapter gradeThisYearAdapter;

    private PieChartData pieChartData;
    private PieChartData pieChartDataAll;
    private List<SliceValue> values=new ArrayList<SliceValue>();
    private List<SliceValue> valuesAll=new ArrayList<SliceValue>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_grade, container, false);
        ButterKnife.bind(this,view);
        mDrawerLayout=(DrawerLayout)getActivity().findViewById(R.id.otheruser_drawer);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("成绩查看");
        ActionBar actionBar=((AppCompatActivity) getActivity()).getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        otherUsersActivity=(OtherUsersActivity)getActivity();
        GridLayoutManager layoutManager1=new GridLayoutManager(getContext(),1);
        GridLayoutManager layoutManager2=new GridLayoutManager(getContext(),1);
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView2.setLayoutManager(layoutManager2);
        getAllGrade();
        getThisYearGrade();
        recyclerView1.setVisibility(View.GONE);
        recyclerView2.setVisibility(View.GONE);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (markThisYear){
                    case 0:{
                        textView1.setTextSize(20f);
                        recyclerView1.setVisibility(View.VISIBLE);
                        getThisYearGrade();
                        markThisYear=1;
                    }
                        break;
                    case 1:{
                        textView1.setTextSize(40f);
                        recyclerView1.setVisibility(View.GONE);
                        markThisYear=0;
                    }
                        break;
                    default:
                        break;
                }
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (markAll){
                    case 0:{
                        textView2.setTextSize(20f);
                        recyclerView2.setVisibility(View.VISIBLE);
                        getAllGrade();
                        markAll=1;
                    }
                        break;
                    case 1:{
                        textView2.setTextSize(40f);
                        recyclerView2.setVisibility(View.GONE);
                        markAll=0;
                    }
                        break;
                    default:
                        break;
                }
            }
        });
        pieChart.setOnValueTouchListener(new PieChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int arcIndex, SliceValue value) {
                //value.setLabel(Float.toString(value.getValue()));
                Toast.makeText(getContext(),"分数为:"+value.getValue(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {

            }
        });
        setPieChartData();

        return view;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllGrade(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder()
                            .add("post_id",otherUsersActivity.getCurrentUser().substring(0,9))
                            .build();
                    Request request=new Request.Builder().post(formBody).url(urlAll).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    if(response.isSuccessful()){
                        Gson gson=new Gson();
                        gradeAllList=gson.fromJson(responseData,new TypeToken<List<GradeAll>>(){}.getType());
                        gradeAllAdapter=new GradeAllAdapter(gradeAllList);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView2.setAdapter(gradeAllAdapter);
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getThisYearGrade(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder()
                            .add("post_id",otherUsersActivity.getCurrentUser().substring(0,9))
                            .build();
                    Request request=new Request.Builder().post(formBody).url(urlThisYear).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    if(response.isSuccessful()){
                        Gson gson=new Gson();
                        gradeThisYearList=gson.fromJson(responseData,new TypeToken<List<GradeThisYear>>(){}.getType());
                        gradeThisYearAdapter=new GradeThisYearAdapter(gradeThisYearList);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView1.setAdapter(gradeThisYearAdapter);
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private int getRandomColor(){
        int[] customizedColors = getActivity().getResources().getIntArray(R.array.customizedColors);
        int customizedColor = customizedColors[new Random().nextInt(customizedColors.length)];
        Log.d(TAG, Integer.toString(customizedColor));
        return customizedColor;
    }

    private void setPieChartData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    List<GradeThisYear> gradeThisYears;
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder()
                            .add("post_id",otherUsersActivity.getCurrentUser().substring(0,9))
                            .build();
                    Request request=new Request.Builder().post(formBody).url(urlThisYear).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    if(response.isSuccessful()){
                        Gson gson=new Gson();
                        gradeThisYears=gson.fromJson(responseData,new TypeToken<List<GradeThisYear>>(){}.getType());
                        for(int i=0;i<gradeThisYearList.size();i++){
                            SliceValue sliceValue = new SliceValue(Float.valueOf(gradeThisYears.get(i).getGrade()),getRandomColor()).setLabel(gradeThisYearList.get(i).getCourseName()+":"+gradeThisYearList.get(i).getGrade());//这里的颜色是我写了一个工具类 是随机选择颜色的
                            values.add(sliceValue);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pieChartData = new PieChartData();
                                    pieChartData.setHasLabels(true);//显示表情
                                    pieChartData.setHasLabelsOnlyForSelected(false);//不用点击显示占的百分比
                                    pieChartData.setHasLabelsOutside(false);//占的百分比是否显示在饼图外面
                                    pieChartData.setHasCenterCircle(true);//是否是环形显示
                                    pieChartData.setValues(values);//填充数据
                                    pieChartData.setCenterCircleColor(Color.WHITE);//设置环形中间的颜色
                                    pieChartData.setCenterCircleScale(0.5f);//设置环形的大小级别
                                    pieChartData.setCenterText1("本学期成绩图表");//环形中间的文字1
                                    pieChartData.setCenterText1Color(Color.BLACK);//文字颜色
                                    pieChartData.setCenterText1FontSize(18);//文字大小
                                    pieChart.setPieChartData(pieChartData);
                                    pieChart.setValueSelectionEnabled(true);//选择饼图某一块变大
                                    pieChart.setAlpha(0.9f);//设置透明度
                                    pieChart.setCircleFillRatio(1f);//设置饼图大小
                                }
                            });
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
