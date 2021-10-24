package com.example.project_1081741;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ImageView imgPlay;
    private GoogleMap mMap;

    private VideoView vidVideo;
    private final String sdPath="sdcard/"; //SD路徑
    private String fname=""; //影片檔名稱
    private Button btnView,btnView1,btnImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        btnView = (Button) findViewById(R.id.btnView);
        btnView1 = (Button) findViewById(R.id.btnView1);
        btnImage = (Button) findViewById(R.id.btnImage);
        btnView.setOnClickListener(myListener);
        btnView1.setOnClickListener(myListener);
        btnImage.setOnClickListener(myListener);

        imgPlay=(ImageView)findViewById(R.id.imgPlay);
        imgPlay.setOnClickListener(listener);

        vidVideo=(VideoView)findViewById(R.id.vidVideo);
        requestStoragePermission();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //檢查驗證
    private void requestStoragePermission(){
        if(Build.VERSION.SDK_INT>=23) {//Android 6.0以上
            //判斷是否已經取得驗證
            int hasPermission=checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) { //取得驗證
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                return;
            }
            int hasPermission1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            if (hasPermission1 != PackageManager.PERMISSION_GRANTED) { //未取得授權
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        }
    }
    //RequestPermissions觸發的事件
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        if(requestCode==1){
            if(!(grantResults[0]==PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this,"未取得權限!",Toast.LENGTH_SHORT).show();
                finish(); //結束應用程式
            }
        }else {
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }
    private Button.OnClickListener myListener=new Button.OnClickListener(){
        public void onClick(View v){
            switch (v.getId()){
                case R.id.btnView:{
                    Uri uri=Uri.parse("https://www.cdc.gov.tw/");
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                    break;
                }
                case R.id.btnView1:{
                    Uri uri=Uri.parse("tel:0223959825");
                    Intent intent=new Intent(Intent.ACTION_DIAL,uri);
                    startActivity(intent);
                    break;
                }
                case R.id.btnImage: {
                    Intent intent1 = new Intent();
                    intent1.setClass(MapsActivity.this, Second.class);
                    startActivity(intent1);
                    break;
                }
            }
        }
    };
    private ImageView.OnClickListener listener=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v){
            switch (v.getId())
            {
                case R.id.imgPlay:
                    fname="covid.3gp";
                    playVidco(sdPath+fname);
                    break;
            }
        }
    };
    private void playVidco(String filePath){ //filePath是影片路徑
        if(filePath!="")
        {
            vidVideo.setVideoPath(filePath);//設定影片路徑
            //加入播放控制軸
            vidVideo.setMediaController(new MediaController(MapsActivity.this));
            vidVideo.start();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(25.056824947423483, 121.52454762877221);
        mMap.addMarker(new MarkerOptions().position(sydney).title("衛生福利部疾病管制署"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);       // 一般地圖
        mMap.getUiSettings().setCompassEnabled(true);     // 顯示指南針
        mMap.getUiSettings().setZoomControlsEnabled(true);// 顯示縮放圖示

    }
}