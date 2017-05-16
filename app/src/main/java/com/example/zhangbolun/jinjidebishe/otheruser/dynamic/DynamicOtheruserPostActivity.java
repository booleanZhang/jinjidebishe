package com.example.zhangbolun.jinjidebishe.otheruser.dynamic;

import android.Manifest;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhangbolun.jinjidebishe.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.zhangbolun.jinjidebishe.login.LoginFragment.currentUrl;

public class DynamicOtheruserPostActivity extends AppCompatActivity {
    @BindView(R.id.activity_dynamic_otheruser_post_edittext)EditText editText;
    @BindView(R.id.activity_dynamic_otheruser_post_imageview)ImageView imageView;
    @BindView(R.id.activity_dynamic_otheruser_post_toolbar)Toolbar toolbar;
    @BindView(R.id.activity_dynamic_otheruser_post_fab)FloatingActionButton fab;
    @BindView(R.id.activity_dynamic_otheruser_post_button)Button button;

    private static final int TAKE_PHOTO=1;
    private static final int CHOOSE_PHOTO=2;

    private String TAG="动态发布:";
    private String sender;
    private String mark;
    private String urlDynamicPost="http://"+currentUrl+":8888/android_connect/otheruser_dynamic_post.php";
    private String urlDynamicPostPicture="http://"+currentUrl+":8888/android_connect/otheruser_dynamic_post_picture.php";

    private Uri imageUri;
    private File outputImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_otheruser_post);
        ButterKnife.bind(this);
        final Intent intent=getIntent();
        sender=intent.getStringExtra("currentUser");
        mark=intent.getStringExtra("mark");
        setSupportActionBar(toolbar);
        toolbar.setTitle("发布动态");
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "CircleImageViewClicked");
                AlertDialog.Builder dialog=new AlertDialog.Builder(DynamicOtheruserPostActivity.this);
                dialog.setTitle("修改头像");
                dialog.setMessage("选择一个方式");
                dialog.setCancelable(true);
                dialog.setPositiveButton("图库", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(ContextCompat.checkSelfPermission(DynamicOtheruserPostActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(DynamicOtheruserPostActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                        }else{
                            openAlbum();
                        }
                    }
                });

                dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.setNegativeButton("相机", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String date=simpleDateFormat.format(new java.util.Date());
                        outputImage=new File(getExternalCacheDir(),date+intent.getStringExtra("mark")+"dynamic.jpg");
                        try{
                            if(outputImage.exists()){
                                outputImage.delete();
                            }
                            outputImage.createNewFile();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                        if(Build.VERSION.SDK_INT>=24){
                            imageUri= FileProvider.getUriForFile(DynamicOtheruserPostActivity.this,"com.example.zhangbolun.jinjidebishe.fileprovider",outputImage);
                        }else{
                            imageUri=Uri.fromFile(outputImage);
                        }
                        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                        startActivityForResult(intent,TAKE_PHOTO);
                    }
                });
                dialog.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().equals("")){
                    if(outputImage==null){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    OkHttpClient client=new OkHttpClient();
                                    RequestBody formBody=new FormBody.Builder()
                                            .add("post_sender",sender)
                                            .add("post_mark",mark)
                                            .add("post_content",editText.getText().toString().trim())
                                            .add("post_picture","").build();
                                    Request request=new Request.Builder().post(formBody).url(urlDynamicPost).build();
                                    Response response=client.newCall(request).execute();
                                    if(response.isSuccessful()){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(DynamicOtheruserPostActivity.this, "发布动态成功", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        });
                                    }

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }else{
                        postHeadPicture(urlDynamicPostPicture, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                            }
                        });
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    OkHttpClient client=new OkHttpClient();
                                    RequestBody formBody=new FormBody.Builder()
                                            .add("post_sender",sender)
                                            .add("post_mark",mark)
                                            .add("post_content",editText.getText().toString().trim())
                                            .add("post_picture","http://"+currentUrl+":8888/android_connect/picture/dynamic_picture/"+outputImage.getName()).build();
                                    Request request=new Request.Builder().post(formBody).url(urlDynamicPost).build();
                                    Response response=client.newCall(request).execute();
                                    if(response.isSuccessful()){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(DynamicOtheruserPostActivity.this, "发布动态成功", Toast.LENGTH_SHORT).show();
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
                }else{
                    Toast.makeText(DynamicOtheruserPostActivity.this, "可以没有图片但不能没有内容呀", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    try{
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageView.setImageBitmap(bitmap);
                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data);
                    }else{
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this, "你有拒绝的权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void handleImageOnKitKat(Intent data){
        String imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath=uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data){
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri,String selection){
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if(imagePath!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            outputImage=new File(imagePath);
            imageView.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this, "获取图像失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void postHeadPicture(String address, Callback callback){
        Log.d(TAG, "start to upload picture");
        MediaType IMAGE_JPG = MediaType.parse("image/jpg;charset=utf-8");
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        Log.d(TAG, outputImage.getName());
        builder.addFormDataPart("picture_path","http://"+currentUrl+":8888/android_connect/picture/dynamic_picture/"+outputImage.getName());
        builder.addFormDataPart("jpg1",outputImage.getName(), RequestBody.create(IMAGE_JPG,outputImage));
        RequestBody requestBody=builder.build();
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
        Log.d(TAG, "upload picture finish");
    }
}
