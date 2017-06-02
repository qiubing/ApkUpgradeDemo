package com.example.apkupgradedemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Process;
import android.util.Log;

import java.io.File;

/**
 * Description:
 * Author: qiubing
 * Date: 2017-06-01 21:32
 */
public class ApkExtract {
    public static String extract(Context context){
        context = context.getApplicationContext();
        ApplicationInfo info = context.getApplicationInfo();
        String apkPath = info.sourceDir;
        Log.e("bing","the old apk source: " + apkPath);
        return apkPath;
    }

    public static void installApk(Context context,String apkPath){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(apkPath)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
        android.os.Process.killProcess(Process.myPid());
    }
}
