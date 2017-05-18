package com.example.zhangbolun.jinjidebishe.instantmessage;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhangbolun.jinjidebishe.R;
import com.google.gson.Gson;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.zhangbolun.jinjidebishe.login.LoginFragment.currentUrl;

public class TeacherContactItemActivity extends AppCompatActivity {
    @BindView(R.id.activity_teacher_contact_item_appbarlayout)AppBarLayout appBarLayout;
    @BindView(R.id.activity_teacher_contact_item_collapsinttoolbarlayout)CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.activity_teacher_contact_item_toolbar)Toolbar toolbar;
    @BindView(R.id.activity_teacher_contact_item_circleImageView)CircleImageView imageView;
    @BindView(R.id.activity_teacher_contact_item_id)TextView id;
    @BindView(R.id.activity_teacher_contact_item_name)TextView name;
    @BindView(R.id.activity_teacher_contact_item_class)TextView student_class;
    @BindView(R.id.activity_teacher_contact_item_phone)TextView phone;
    @BindView(R.id.activity_teacher_contact_item_parent1)TextView parent1;
    @BindView(R.id.activity_teacher_contact_item_parent2)TextView parent2;

    private Intent intent;

    private String url="http://"+currentUrl+":8888/android_connect/teacher_im_init.php";
    private String TAG="联系人详细:";

    private TeacherContactItem teacherContactItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_contact_item);
        ButterKnife.bind(this);
        intent=getIntent();
        if (intent.getStringExtra("PICTURE")!=null){
            if(!intent.getStringExtra("PICTURE").equals("")){
                String headPicture=intent.getStringExtra("PICTURE");
                Glide.with(getApplicationContext()).load(headPicture).into(imageView);
            }
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle(intent.getStringExtra("NAME")+"详细信息");
        collapsingToolbarLayout.setTitle(intent.getStringExtra("NAME")+"详细信息");
        init();

        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                AlertDialog.Builder dialog=new AlertDialog.Builder(TeacherContactItemActivity.this);
                dialog.setTitle("即时通讯");
                dialog.setMessage("选择一种方式");
                dialog.setCancelable(true);
                dialog.setPositiveButton("打电话", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doMakePhoneTo(phone.getText().toString().trim());
                    }
                });

                dialog.setNegativeButton("发短信", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doSendSMSTo(phone.getText().toString().trim(),"");
                    }
                });

                dialog.setNeutralButton("App内通讯", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent1=new Intent(TeacherContactItemActivity.this, IMActivity.class);
                        intent1.putExtra(EaseConstant.EXTRA_USER_ID,id.getText().toString().trim());
                        intent1.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                        startActivity(intent1);
                    }
                });
                dialog.show();
            }
        });

        parent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(TeacherContactItemActivity.this);
                dialog.setTitle("即时通讯");
                dialog.setMessage("选择一种方式");
                dialog.setCancelable(true);
                dialog.setPositiveButton("打电话", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doMakePhoneTo(teacherContactItem.getPhone1());
                    }
                });

                dialog.setNegativeButton("发短信", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doSendSMSTo(teacherContactItem.getPhone1(),"");
                    }
                });

                dialog.setNeutralButton("App内通讯", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent1=new Intent(TeacherContactItemActivity.this, IMActivity.class);
                        intent1.putExtra(EaseConstant.EXTRA_USER_ID,parent1.getText().toString().trim());
                        intent1.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                        startActivity(intent1);
                    }
                });
                dialog.show();
            }
        });

        parent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teacherContactItem.getParent2()!=null){
                    if(!teacherContactItem.getParent2().equals("")){
                        AlertDialog.Builder dialog=new AlertDialog.Builder(TeacherContactItemActivity.this);
                        dialog.setTitle("即时通讯");
                        dialog.setMessage("选择一种方式");
                        dialog.setCancelable(true);
                        dialog.setPositiveButton("打电话", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doMakePhoneTo(teacherContactItem.getPhone2());
                            }
                        });

                        dialog.setNegativeButton("发短信", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doSendSMSTo(teacherContactItem.getPhone2(),"");
                            }
                        });

                        dialog.setNeutralButton("App内通讯", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1=new Intent(TeacherContactItemActivity.this, IMActivity.class);
                                intent1.putExtra(EaseConstant.EXTRA_USER_ID,parent2.getText().toString().trim());
                                intent1.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                                startActivity(intent1);
                            }
                        });
                        dialog.show();
                    }else{
                        Toast.makeText(TeacherContactItemActivity.this, "无用户", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(TeacherContactItemActivity.this, "无用户", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody formBody=new FormBody.Builder().add("post_id",intent.getStringExtra("ID")).build();
                    Request request=new Request.Builder().post(formBody).url(url).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    Log.d(TAG, "run: ");
                    if(response.isSuccessful()){
                        Gson gson=new Gson();
                        teacherContactItem=gson.fromJson(responseData,TeacherContactItem.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                parent1.setText(teacherContactItem.getParent1());
                                if(teacherContactItem.getParent2()!=null){
                                    if(!teacherContactItem.getParent2().equals("")){
                                        parent2.setText(teacherContactItem.getParent2());
                                    }else{
                                        parent2.setText("");
                                        parent2.setClickable(false);
                                    }
                                }else{
                                    parent2.setText("");
                                    parent2.setClickable(false);
                                }
                                phone.setText(intent.getStringExtra("PHONE"));
                                student_class.setText(intent.getStringExtra("ID").substring(0,7));
                                name.setText(intent.getStringExtra("NAME"));
                                id.setText(intent.getStringExtra("ID"));
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void doSendSMSTo(String phoneNumber,String message){
        if(PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)){
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phoneNumber));
            intent.putExtra("sms_body", message);
            startActivity(intent);
        }
    }

    private void doMakePhoneTo(String phoneNumber){
        Intent intent=new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phoneNumber));
        startActivity(intent);
    }
}
