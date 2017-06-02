package com.example.apkupgradedemo;

import android.annotation.NonNull;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    private Button mPatchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPatchBtn = (Button)findViewById(R.id.patch);
        mPatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                }else {
                    //请求合并patch
                    bsPatch();
                }
            }
        });
    }

    private void bsPatch(){
        //定义新合成的apk文件名，以及保存在外置存储中
        final File newApk = new File(Environment.getExternalStorageDirectory(),"new.apk");
        //将生成的差分patch文件放置在外置存储中，并且命名为diff.patch，利用bsdiff工具生成差分patch
        final File patch = new File(Environment.getExternalStorageDirectory(),"diff.patch");
        Log.e("bing", "path exist : " + patch.exists() + " patch path: " + patch.getAbsolutePath());
        BsPatch.bspatch(ApkExtract.extract(this), newApk.getAbsolutePath(), patch.getAbsolutePath());

        Log.e("bing", "newAPk exist :" + newApk.exists() + " newApk path: " + newApk.getAbsolutePath());
        if (newApk.exists()){
            ApkExtract.installApk(this,newApk.getAbsolutePath());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bsPatch();
            }
        }
    }
}
