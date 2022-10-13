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
    env->ReleaseIntArrayElements(nums,arr,0);
}

JNIEXPORT jobjectArray JNICALL Java_com_egas_demo_JniDemoClass_processStringArray
        (JNIEnv *env, jobject, jobjectArray strs) {
    jsize size = env->GetArrayLength(strs);
    for (int i = 0; i < size; ++i) {
        auto str = (jstring) (env->GetObjectArrayElement(strs,i));
        if(str){
            const char* cstr = env->GetStringUTFChars(str,JNI_FALSE);
            LOGE("num= %d str=%s", i,cstr);
            env->ReleaseStringUTFChars(str,cstr);
            if(i==1){ // 修改 index 为 1 的元素
                jstring s = env->NewStringUTF("1");
                env->SetObjectArrayElement(strs,i,s);
            }
        }
    }
    jclass jStringClazz = env->FindClass("java/lang/String");
    jobjectArray jarr = env->NewObjectArray(size, jStringClazz, nullptr);
    for (int i = 0; i < size; ++i) {
        std::string str = std::to_string(i);
        jstring s = env->NewStringUTF(str.c_str());
        env->SetObjectArrayElement(jarr,i,s);
    }
    return jarr;
}
JNIEXPORT void JNICALL Java_com_egas_demo_JniDemoClass_modifyField
        (JNIEnv *env, jobject thiz) {
    jclass clz = env->GetObjectClass(thiz);// 获取 jclass
    jfieldID sFieldId = env->GetStaticFieldID(clz, "staticField", "Ljava/lang/String;"); // 静态字段 ID
    // 访问静态字段
    if (sFieldId) {
        // Java 方法的返回值 String 映射为 jstring
        jstring jStr = static_cast<jstring>(env->GetStaticObjectField(clz, sFieldId));
        const char *sStr = env->GetStringUTFChars(jStr, JNI_FALSE);
        env->ReleaseStringUTFChars(jStr, sStr);
        jstring newStr = env->NewStringUTF("静态字段修改");
        if (newStr) {
            // jstring 本身就是 Java String 的映射，可以直接传递到 Java 层
            env->SetStaticObjectField(clz, sFieldId, newStr);
        }
    }
    // 实例字段 ID
    jfieldID mFieldId = env->GetFieldID(clz, "strField", "Ljava/lang/String;");
    // 访问实例字段
    if (mFieldId) {
        jstring jStr = static_cast<jstring>(env->GetObjectField(thiz, mFieldId));
        const char *sStr = env->GetStringUTFChars(jStr, JNI_FALSE);
        env->ReleaseStringUTFChars(jStr, sStr);
        jstring newStr = env->NewStringUTF("实例字段修改");
        if (newStr) {
            // jstring 本身就是 Java String 的映射，可以直接传递到 Java 层
            env->SetObjectField(thiz, mFieldId, newStr);
        }
    }
}
JNIEXPORT void JNICALL Java_com_egas_demo_JniDemoClass_invokeMethod
        (JNIEnv *env, jobject thiz) {
    jclass clz = env->GetObjectClass(thiz);
    // 静态方法 ID
    jmethodID sMethodId = env->GetStaticMethodID(clz, "sLogHelloJava", "()V");
    if (sMethodId) {
        env->CallStaticVoidMethod(clz, sMethodId);
    }
    // 实例方法 ID
    jmethodID mMethodId = env->GetMethodID(clz, "logHelloJava", "()V");
    if (mMethodId) {
        env->CallVoidMethod(thiz, mMethodId);
    }
}
}
