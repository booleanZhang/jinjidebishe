package com.example.zhangbolun.jinjidebishe.teacher.gradeInput;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.zhangbolun.jinjidebishe.R;
import com.example.zhangbolun.jinjidebishe.TeacherActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.zhangbolun.jinjidebishe.login.LoginFragment.currentUrl;

public class TeacherGradeThisYearFragment extends Fragment {
    @BindView(R.id.fragment_teacher_grade_this_year_recyclerview)RecyclerView recyclerView;
    @BindView(R.id.fragment_teacher_grade_this_year_spinner)Spinner spinner;

    private TeacherActivity teacherActivity;

    private String TAG="教师得到成绩";

    private String urlGetTeahcerCourse="http://"+currentUrl+":8888/android_connect/teacher_input_grade_get_course.php";
    private String urlGetTeacherCourseGrades="http://"+currentUrl+":8888/android_connect/teacher_input_grade_get_course_grade.php";
    private String post_mark;

    List<GetCourse> getCourseList;
    List<String> courseNameList=new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    TeacherGradeThisYearAdapter adapter;
    List<TeacherGradeThisYear> teacherGradeThisYearList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_teacher_grade_this_year, container, false);
        ButterKnife.bind(this,view);
        teacherActivity=(TeacherActivity)getActivity();
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder().add("post_id",teacherActivity.getCurrentUser()).build();
                    Request request=new Request.Builder().post(formBody).url(urlGetTeahcerCourse).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    if(response.isSuccessful()){
                        Gson gson=new Gson();
                        getCourseList=gson.fromJson(responseData,new TypeToken<List<GetCourse>>(){}.getType());
                        for(int i=0;i<getCourseList.size();i++){
                            courseNameList.add(getCourseList.get(i).getCourseName());
                        }
                        Log.d(TAG, courseNameList.toString());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                arrayAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,courseNameList);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(arrayAdapter);
                                spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
                                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                                        //Log.d(TAG, arrayAdapter.getItem(arg2));
                                        post_mark=arrayAdapter.getItem(arg2);
                                        getGrades();
                                        arg0.setVisibility(View.VISIBLE);
                                    }
                                    public void onNothingSelected(AdapterView<?> arg0){
                                        arg0.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();


        return view;
    }

    private void getGrades(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder().add("post_id",teacherActivity.getCurrentUser())
                            .add("post_mark",post_mark).build();
                    Request request=new Request.Builder().post(formBody).url(urlGetTeacherCourseGrades).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    if(response.isSuccessful()){
                        Gson gson=new Gson();
                        teacherGradeThisYearList=gson.fromJson(responseData,new TypeToken<List<TeacherGradeThisYear>>(){}.getType());
                        adapter=new TeacherGradeThisYearAdapter(teacherGradeThisYearList);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
