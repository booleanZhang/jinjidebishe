package com.example.zhangbolun.jinjidebishe;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {
    public LocationClient mLocationClient;
    @BindView(R.id.activity_test_tv) TextView positionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        List<String> permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(TestActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(TestActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(TestActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String [] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(TestActivity.this,permissions,1);
        }else{
            requestLocation();
        }
    }

    private void requestLocation(){
        mLocationClient.start();
    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch(requestCode){
            case 1:
                if(grantResults.length>0){
                    for(int results:grantResults){
                        if(results!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else{
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener{
        public void onReceiveLocation(BDLocation location){
            final StringBuilder currentPosition=new StringBuilder();
            currentPosition.append("纬度:").append(location.getLatitude()).append("\n");
            currentPosition.append("经度:").append(location.getLongitude()).append("\n");
            currentPosition.append("定位方式:");
            if(location.getLocType()==BDLocation.TypeNetWorkLocation){
                currentPosition.append("网络定位");
            }else if(location.getLocType()==BDLocation.TypeGpsLocation){
                currentPosition.append("GPS");
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    positionText.setText(currentPosition);
                }
            });
        }

        public void onConnectHotSpotMessage(String a,int b){

        }
    }
}
