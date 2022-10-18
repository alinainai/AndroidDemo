package com.egas.demo;

import android.util.Log;

public class JniDemoClass {

    static {
        System.loadLibrary("demo");
        initCacheMethodId();
    }

    private static final String TAG = "JniDemoClass";

    public static String staticField = "0";
    public String strField = "11";

    public void logHelloJava() {
        Log.e(TAG, "hello java");
    }

    public static void sLogHelloJava() {
        Log.e(TAG, "hello java static");
    }

    public native String stringFromJNI(int num, double price, String owner); // 从 Jni 获取String

    public native static String stringFromStaticJNI(int num, double price, String owner);// 通过静态方法 从 Jni 获取String

    public native void processIntArray(int[] strs);

    public native String[] processStringArray(String[] strs);

    public native void modifyField();

    public native void invokeMethod();
    
    public static native void initCacheMethodId();

    public native void verifyInitCacheMethodId();

}
