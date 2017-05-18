package com.example.zhangbolun.jinjidebishe.otheruser.checkon;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.zhangbolun.jinjidebishe.OtherUsersActivity;
import com.example.zhangbolun.jinjidebishe.R;
import com.example.zhangbolun.jinjidebishe.TestActivity;

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

/**
 * Created by zhangbolun on 2017/4/26.
 */

public class OtheruserCheckOnReceiverFragment extends Fragment {
    private Double latitude;
    private Double longitude;
    private String url="http://"+currentUrl+":8888/android_connect/otheruser_checkon.php";
    private String url1="http://"+currentUrl+":8888/android_connect/otheruser_checkon_out.php";
    private String user;
    private OtherUsersActivity otheruserActivity;
    private String TAG="签到";
    public LocationClient mLocationClient;
    private BaiduMap baiduMap;
    private boolean isFirstLocate=true;
    @BindView(R.id.fragment_otheruser_checkon_receiver_mapView)MapView mapView;
//    @BindView(R.id.fragment_otheruser_checkon_receiver_tv)TextView positionText;
    @BindView(R.id.fragment_otheruser_checkon_receiver_in)Button inButton;
    @BindView(R.id.fragment_otheruser_checkon_receiver_out)Button outButton;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        SDKInitializer.initialize(getActivity().getApplication());
        View view=inflater.inflate(R.layout.fragment_otheruser_checkon_receiver,container,false);
        mLocationClient=new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        ButterKnife.bind(this,view);
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        otheruserActivity=(OtherUsersActivity)getActivity();
        if (otheruserActivity.getCurrentUser().length() == 10) {
            user = otheruserActivity.getCurrentUser().substring(0, 9);
            Log.d(TAG, user);
        }else{
            user=otheruserActivity.getCurrentUser();
        }
        List<String> permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String [] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(),permissions,1);
        }else{
            requestLocation();
        }

        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
                dialog.setTitle("确认签到");
                dialog.setMessage("本日仅可以签到一次，签过后不能在签。");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确认签到", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    OkHttpClient client=new OkHttpClient();
                                    RequestBody formBody= new FormBody.Builder()
                                            .add("post_id",user)
                                            .add("latitude",latitude.toString())
                                            .add("longitude",longitude.toString())
                                            .build();
                                    Request request=new Request.Builder().post(formBody).url(url).build();
                                    Response response=client.newCall(request).execute();
                                    String responseData=response.body().string();
                                    Log.d(TAG, responseData);
                                    if(response.isSuccessful()){
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                inButton.setVisibility(View.GONE);
                                                outButton.setVisibility(View.VISIBLE);
                                            }
                                        });
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });
                dialog.show();
            }
        });

        outButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
                dialog.setTitle("确认签到");
                dialog.setMessage("本日仅可以签到一次，签过后不能在签。");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确认签到", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    OkHttpClient client=new OkHttpClient();
                                    RequestBody formBody= new FormBody.Builder()
                                            .add("post_id",user)
                                            .add("latitude",latitude.toString())
                                            .add("longtitude",longitude.toString())
                                            .build();
                                    Request request=new Request.Builder().post(formBody).url(url1).build();
                                    Response response=client.newCall(request).execute();
                                    String responseData=response.body().string();
                                    Log.d(TAG, responseData);
                                    if(response.isSuccessful()){
                                        outButton.setClickable(false);
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });
                dialog.show();
            }
        });

        return view;
    }

    private void navigateTo(BDLocation location){
        if(isFirstLocate){
            LatLng ll=new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update= MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update=MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate=false;
        }
        MyLocationData.Builder locationBuilder=new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData=locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch(requestCode){
            case 1:
                if(grantResults.length>0){
                    for(int results:grantResults){
                        if(results!=PackageManager.PERMISSION_GRANTED){
                            Log.d(TAG, "必须同意所有权限才能使用本程序");
                            getActivity().finish();
                            return;
                        }
                    }
                    requestLocation();
                }else{
                    Log.d(TAG, "发生未知错误");
                    getActivity().finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener {
        public void onReceiveLocation(BDLocation location){
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            Log.d(TAG, latitude.toString());
            Log.d(TAG, longitude.toString());

//            final StringBuilder currentPosition=new StringBuilder();
//            currentPosition.append("纬度:").append(location.getLatitude()).append("\n");
//            currentPosition.append("经度:").append(location.getLongitude()).append("\n");
//            currentPosition.append("国家:").append(location.getCountry()).append("\n");
//            currentPosition.append("省:").append(location.getProvince()).append("\n");
//            currentPosition.append("市:").append(location.getCity()).append("\n");
//            currentPosition.append("区:").append(location.getDistrict()).append("\n");
//            currentPosition.append("定位方式:");
//            if(location.getLocType()==BDLocation.TypeNetWorkLocation){
//                currentPosition.append("网络定位");
//            }else if(location.getLocType()==BDLocation.TypeGpsLocation){
//                currentPosition.append("GPS");
//            }
            if(location.getLocType()==BDLocation.TypeGpsLocation||location.getLocType()==BDLocation.TypeNetWorkLocation){
                navigateTo(location);
            }
//            getParentFragment().getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    positionText.setText(currentPosition);
//                }
//            });
            //Log.d(TAG, currentPosition.toString());
        }

        public void onConnectHotSpotMessage(String a,int b){

        }
    }

    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        //option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        mLocationClient.setLocOption(option);
    }

    public void onDestroy(){
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    public void onResume(){
        super.onResume();
        mapView.onResume();
    }

    public void onPause(){
        super.onPause();
        mapView.onPause();
    }
}
