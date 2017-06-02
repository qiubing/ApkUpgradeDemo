package com.example.apkupgradedemo;

/**
 * Description:
 * Author: qiubing
 * Date: 2017-06-01 21:38
 */
public class BsPatch {
    static {
        System.loadLibrary("bsdiff");
    }

    public static native int bspatch(String oldApk,String newApk,String patch);
}
