#include <jni.h>
#include <string>
#include <cstdio>
#include "androidlog.h"


extern "C" {

JNIEXPORT jstring JNICALL
Java_com_egas_demo_JniDemoClass_stringFromJNI(
        JNIEnv *env,
        jobject /* this */,
        jint num,
        jdouble price,
        jstring owner) {
    std::string hello = "Hello from C++ ";
    LOGE("num= %d price=%f", num, price);
    const char *javaStr = env->GetStringUTFChars(owner, JNI_FALSE);
    hello.append(javaStr);
    env->ReleaseStringUTFChars(owner, javaStr);
    return env->NewStringUTF(hello.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_egas_demo_JniDemoClass_stringFromStaticJNI(
        JNIEnv *env,
        jclass /* this */,
        jint num,
        jdouble price,
        jstring owner) {
    printf("static num= %d price=%f", num, price);
    std::string hello = "Hello from C++ ";
    const char *javaStr = env->GetStringUTFChars(owner, JNI_FALSE);
    hello.append(javaStr);
    env->ReleaseStringUTFChars(owner, javaStr);
    return env->NewStringUTF(hello.c_str());
}

JNIEXPORT void JNICALL Java_com_egas_demo_JniDemoClass_processIntArray
        (JNIEnv *env, jobject, jintArray nums) {
    jsize size = env->GetArrayLength(nums);
    jint *arr = env->GetIntArrayElements(nums, JNI_FALSE);
    for (int i = 0; i < size; ++i) {
        arr[i] = size-i;
    }
    env->ReleaseIntArrayElements(nums,arr,JNI_ABORT);
//    jintArray jarr = env->NewIntArray(size);
}

JNIEXPORT void JNICALL Java_com_egas_demo_JniDemoClass_processStringArray
        (JNIEnv *env, jobject, jobjectArray strs) {


}
}