package com.example.zhangbolun.jinjidebishe.teacher.file;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangbolun.jinjidebishe.R;
import com.example.zhangbolun.jinjidebishe.TeacherActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.zhangbolun.jinjidebishe.login.LoginFragment.currentUrl;

/**
 * Created by zhangbolun on 2017/5/21.
 */

public class TeacherUploadFileAdapter extends RecyclerView.Adapter<TeacherUploadFileAdapter.ViewHolder> {
    private Context mContext;
    private List<File> mfileList;

    private String TAG="获得文件名Adapter:";

    static class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        TextView name; //文件名
        TextView suffix; //文件类型（后缀名）
        public ViewHolder(View view){
            super(view);
            itemView=view;
            name=(TextView) view.findViewById(R.id.teacher_upload_file_item_name);
            suffix=(TextView) view.findViewById(R.id.teacher_upload_file_item_suffix);
        }
    }

    public TeacherUploadFileAdapter(List<File> fileList){
        mfileList=fileList;
    }

    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.teacher_upload_file_item,parent,false);

        final TeacherActivity teacherActivity=(TeacherActivity)view.getContext();

        final ViewHolder holder=new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                final File file=mfileList.get(position);
                Log.d(TAG, teacherActivity.getCurrentUser());
                Log.d(TAG, file.getName());
                Log.d(TAG, "path:"+file.getPath().toString());
                Log.d(TAG, "absolutePath:"+file.getAbsolutePath().toString());
                try{
                    Log.d(TAG, "canonicalPath:"+file.getCanonicalPath().toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            new FileUploadTest().testUploadFile("http://" + currentUrl + ":8888/android_connect/test_upload_file.php",file, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {

                                }
                            });
                        }
                    }).start();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                OkHttpClient client=new OkHttpClient();
                                RequestBody formBody=new FormBody.Builder()
                                        .add("post_sender",teacherActivity.getCurrentUser())
                                        .add("post_name",file.getName())
                                        .add("post_path","http://"+currentUrl+":8888/android_connect/file/"+file.getName())
                                        .build();
                                Request request=new Request.Builder().post(formBody).url("http://"+currentUrl+":8888/android_connect/upload_file.php").build();
                                Response response=client.newCall(request).execute();
                                if(response.isSuccessful()){
                                    teacherActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(mContext, "文件上传成功", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        File file=mfileList.get(position);
        String fileName=file.getName();
        holder.name.setText(fileName);
        holder.suffix.setText(fileName.substring(fileName.lastIndexOf("."),fileName.length())+"类型文件");
    }

    public int getItemCount(){
        return mfileList.size();
    }
}
