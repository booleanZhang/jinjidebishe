package com.example.zhangbolun.jinjidebishe.teacher.file;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.zhangbolun.jinjidebishe.login.LoginFragment.currentUrl;

/**
 * Created by zhangbolun on 2017/5/21.
 * 上传文件测试类，testUploadFile()是文件上传方法
 */

public class FileUploadTest {
    private String TAG="文件上传测试:";
    private String url="http://"+currentUrl+":8888/test_upload_file.php";


    public void testUploadFile(String url,File file,Callback callback){
        //File file = new File("/storage/emulated/0/家校通要上传的文件/啊.xls");
        File fileThis=new File(file.getPath());
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("path","http://"+currentUrl+":8888/android_connect/file/"+fileThis.getName());
        builder.addFormDataPart("jpg1",fileThis.getName(), RequestBody.create(MediaType.parse("application/octet-stream"),fileThis));
        RequestBody requestBody=builder.build();
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

//    public static void testUploadFile(String url,Callback callback) {
//        //创建OkHttpClient对象
//        OkHttpClient mOkHttpClient = new OkHttpClient();
//        File file = new File("/storage/emulated/0/家校通要上传的文件/啊.xls");
//
//        //application/octet-stream 表示类型是二进制流，不知文件具体类型
//        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
//
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addPart(Headers.of(
//                        "Content-Disposition",
//                        "form-data; name=\"username\""),
//                        RequestBody.create(null, "***"))
//                .addPart(Headers.of(
//                        "Content-Disposition",
//                        "form-data; name=\"mFile\";filename=\"啊.xls\""), fileBody)
//                .build();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(requestBody)
//                .build();
//        OkHttpClient client=new OkHttpClient();
//        client.newCall(request).enqueue(callback);
//    }
}
