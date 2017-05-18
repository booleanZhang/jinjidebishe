package com.example.zhangbolun.jinjidebishe.instantmessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zhangbolun.jinjidebishe.R;
import com.hyphenate.easeui.ui.EaseChatFragment;

public class IMActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);

        EaseChatFragment chatFragment=new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.activity_im_container,chatFragment).commit();
    }
}
