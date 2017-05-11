package com.example.zhangbolun.jinjidebishe.otheruser.personal_information;

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
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhangbolun.jinjidebishe.OtherUsersActivity;
import com.example.zhangbolun.jinjidebishe.R;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
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

public class ParentPersonalInformationActivity extends AppCompatActivity {
    @BindView(R.id.activity_parent_personal_information_collapsing_toolbar)CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.activity_parent_personal_information_appBar)AppBarLayout appBarLayout;
    @BindView(R.id.activity_parent_personal_information_toolbar)Toolbar toolbar;
    @BindView(R.id.activity_parent_personal_information_radioGroup)RadioGroup radioGroup;
    @BindView(R.id.activity_parent_personal_information_radioMan)RadioButton man;
    @BindView(R.id.activity_parent_personal_information_radioWoman)RadioButton woman;
    @BindView(R.id.activity_parent_personal_information_id)TextView id;
    @BindView(R.id.activity_parent_personal_information_name)EditText name;
    @BindView(R.id.activity_parent_personal_information_circleImageView)CircleImageView circleImageView;
    @BindView(R.id.activity_parent_personal_information_student1)EditText child1;
    @BindView(R.id.activity_parent_personal_information_student2)EditText child2;
    @BindView(R.id.activity_parent_personal_information_password)EditText password;
    @BindView(R.id.activity_parent_personal_information_password_confirm)EditText password_confirm;
    @BindView(R.id.activity_parent_personal_information_phone)EditText phone;
    @BindView(R.id.activity_parent_personal_information_btn)Button button;

    private static final int TAKE_PHOTO=1;
    private static final int CHOOSE_PHOTO=2;

    private String TAG="家长个人信息:";
    private String parentId;
    private String sex;
    private String urlInit="http://"+currentUrl+":8888/android_connect/parent_personal_information_init.php";
    private String urlPicture="http://"+currentUrl+":8888/android_connect/parent_personal_information_head.php";
    private String url="http://"+currentUrl+":8888/android_connect/parent_personal_information.php";

    ParentPersonalInformation parentPersonalInformation;

    private Uri imageUri;
    private File outputImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_personal_information);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        parentId=intent.getStringExtra("parentId");
        id.setText(parentId);
        initParentInformation();
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle("家长个人信息修改");
        toolbar.setTitle("家长个人信息修改");
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "CircleImageViewClicked");
                AlertDialog.Builder dialog=new AlertDialog.Builder(ParentPersonalInformationActivity.this);
                dialog.setTitle("修改头像");
                dialog.setMessage("选择一个方式");
                dialog.setCancelable(true);
                dialog.setPositiveButton("图库", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(ContextCompat.checkSelfPermission(ParentPersonalInformationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(ParentPersonalInformationActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
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
                        outputImage=new File(getExternalCacheDir(),"parent_head_image.jpg");
                        try{
                            if(outputImage.exists()){
                                outputImage.delete();
                            }
                            outputImage.createNewFile();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                        if(Build.VERSION.SDK_INT>=24){
                            imageUri= FileProvider.getUriForFile(ParentPersonalInformationActivity.this,"com.example.zhangbolun.jinjidebishe.fileprovider",outputImage);
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

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==man.getId()){
                    sex="男";
                }else if(checkedId==woman.getId()){
                    sex="女";
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().trim().equals(password_confirm.getText().toString().trim())){
                    if(outputImage==null){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    OkHttpClient client=new OkHttpClient();
                                    RequestBody formBody=new FormBody.Builder()
                                            .add("post_id",parentId)
                                            .add("post_name",name.getText().toString().trim())
                                            .add("post_sex",sex)
                                            .add("post_child1",child1.getText().toString().trim())
                                            .add("post_child2",child2.getText().toString().trim())
                                            .add("post_password",password.getText().toString().trim())
                                            .add("post_phone",phone.getText().toString().trim())
                                            .add("post_picture","")
                                            .build();
                                    Request request=new Request.Builder().post(formBody).url(url).build();
                                    Response response=client.newCall(request).execute();
                                    String responseData=response.body().string();
                                    if(response.isSuccessful()){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(ParentPersonalInformationActivity.this, "修改个人信息成功", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }else{
                        postHeadPicture(urlPicture, new Callback() {
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
                                            .add("post_id",parentId)
                                            .add("post_name",name.getText().toString().trim())
                                            .add("post_sex",sex)
                                            .add("post_child1",child1.getText().toString().trim())
                                            .add("post_child2",child2.getText().toString().trim())
                                            .add("post_password",password.getText().toString().trim())
                                            .add("post_phone",phone.getText().toString().trim())
                                            .add("post_picture","http://"+currentUrl+":8888/android_connect/picture/parenthead/"+outputImage.getName())
                                            .build();
                                    Request request=new Request.Builder().post(formBody).url(url).build();
                                    Response response=client.newCall(request).execute();
                                    String responseData=response.body().string();
                                    if(response.isSuccessful()){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(ParentPersonalInformationActivity.this, "修改个人信息成功", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ParentPersonalInformationActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    password.setText(null);
                    password_confirm.setText(null);
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
                        circleImageView.setImageBitmap(bitmap);
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
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
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
            circleImageView.setImageBitmap(bitmap);
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
        builder.addFormDataPart("picture_path","http://"+currentUrl+":8888/android_connect/picture/parenthead/"+outputImage.getName());
        builder.addFormDataPart("jpg1",outputImage.getName(), RequestBody.create(IMAGE_JPG,outputImage));
        builder.addFormDataPart("post_id",id.getText().toString().trim());
        RequestBody requestBody=builder.build();
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
        Log.d(TAG, "upload picture finish");
    }

    private void initParentInformation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder()
                            .add("post_id",parentId)
                            .build();
                    Request request=new Request.Builder().post(formBody).url(urlInit).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    Log.d(TAG, responseData);
                    if(response.isSuccessful()){
                        Gson gson=new Gson();
                        parentPersonalInformation=gson.fromJson(responseData,ParentPersonalInformation.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                name.setText(parentPersonalInformation.getName());
                                if(man.getText().equals(parentPersonalInformation.getSex())){
                                    man.setChecked(true);
                                    woman.setChecked(false);
                                }else if(woman.getText().equals(parentPersonalInformation.getSex())){
                                    man.setChecked(false);
                                    woman.setChecked(true);
                                }
                                child1.setText(parentPersonalInformation.getChild1());
                                if(parentPersonalInformation.getChild2()==null){
                                }else{
                                    if(!parentPersonalInformation.getChild2().isEmpty()) {
                                        child2.setText(parentPersonalInformation.getChild2());
                                    }
                                }
                                password.setText(parentPersonalInformation.getPassword());
                                password_confirm.setText(parentPersonalInformation.getPassword());
                                phone.setText(parentPersonalInformation.getPhone());
                                if(parentPersonalInformation.getPicture()==null){

                                }else{
                                    if(!parentPersonalInformation.getPicture().isEmpty()){
                                        Glide.with(getApplicationContext()).load(parentPersonalInformation.getPicture()).into(circleImageView);
                                    }
                                }
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
