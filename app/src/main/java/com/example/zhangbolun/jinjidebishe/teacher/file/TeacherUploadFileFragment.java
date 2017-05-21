package com.example.zhangbolun.jinjidebishe.teacher.file;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhangbolun.jinjidebishe.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.zhangbolun.jinjidebishe.login.LoginFragment.currentUrl;

public class TeacherUploadFileFragment extends Fragment {
    @BindView(R.id.fragment_teacher_upload_file_button)Button button;
    @BindView(R.id.fragment_teacher_upload_file_edittext)EditText editText;
    @BindView(R.id.fragment_teacher_upload_file_recyclerview)RecyclerView recyclerView;

    private List<File> fileList;
    TeacherUploadFileAdapter adapter;

    private String TAG="教师上传文件:";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_teacher_upload_file, container, false);
        ButterKnife.bind(this,view);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        fileList=new ArrayList<>();
        fileList=getFilesFromDictionary("/storage/emulated/0/家校通要上传的文件/");
        if(fileList.isEmpty()){
            Toast.makeText(getContext(), "文件夹中没有内容", Toast.LENGTH_SHORT).show();
        }else{
            adapter=new TeacherUploadFileAdapter(fileList);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setAdapter(adapter);
                }
            });
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dictionary=editText.getText().toString().trim();
                String path;
                if(!dictionary.equals("")){
                    //edittext中有值
                    path="/storage/emulated/0/"+dictionary+"/";
                    //如果sPath不以文件分隔符结尾，自动添加文件分隔符
                    if (!path.endsWith(File.separator)) {
                        path = path + File.separator;
                    }
                    File dirFile=new File(path);
                    //如果dir对应的文件不存在，或者不是一个目录，则退出
                    if(!dirFile.exists()||!dirFile.isDirectory()){
                        Toast.makeText(getContext(), "您输入的文件夹名不存在或者并不是一个目录", Toast.LENGTH_SHORT).show();
                    }else{
                        fileList=getFilesFromDictionary(path);
                        if(fileList.isEmpty()){
                            Toast.makeText(getContext(), "文件夹中没有内容", Toast.LENGTH_SHORT).show();
                        }else{
                            adapter=new TeacherUploadFileAdapter(fileList);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.setAdapter(adapter);
                                }
                            });
                        }
                    }
                }else{
                    Toast.makeText(getContext(), "应该输入文件夹的路径", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new FileUploadTest().testUploadFile("http://"+currentUrl+":8888/android_connect/test_upload_file.php", new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.d(TAG, "上传失败");
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        Log.d(TAG, response.body().string());
//                    }
//                });
//            }
//        });

        return view;
    }

    /**
     * 读取sd卡上指定目录下所有文件
     * created by zhangbolun 2017/5/21 00:10
     * @param sPath 要查找的目录文件路径
     * @return fileList 返回的文件名列表
     */
    public List<File> getFilesFromDictionary(String sPath){
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        List<File> fileList=new ArrayList<>();
//        Log.d(TAG, sPath);
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
//        Log.d(TAG, sPath);
        File dirFile = new File(sPath);
//        Log.d(TAG, dirFile.toString());
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            Toast.makeText(getContext(), "文件夹不存在", Toast.LENGTH_SHORT).show();
            return null;
        }
        File[] subFiles=dirFile.listFiles();
        for(File subFile:subFiles){
            Log.d(TAG, subFile.getName());
            fileList.add(subFile);
        }
        return fileList;
    }
}
