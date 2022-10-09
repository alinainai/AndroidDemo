//
// Created by 李佳星 on 2022/10/9.
//
#ifndef ANDROIDDEMO_LOG_H
#define ANDROIDDEMO_LOG_H
#include <android/log.h>

#define  LOGI(...) __android_log_print(ANDROID_LOG_INFO, "========= Info =========   ", __VA_ARGS__)

#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, "========= Error =========   ", __VA_ARGS__)

#define  LOGD(...)  __android_log_print(ANDROID_LOG_INFO, "========= Debug =========   ", __VA_ARGS__)

#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN, "========= Warn =========   ", __VA_ARGS__)


#endif //ANDROIDDEMO_LOG_H
