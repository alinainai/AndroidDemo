package com.gas.gradleplugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // 获取 Android 扩展
        val androidExtension = project.extensions.getByType(AppExtension::class.java)
        // 注册 Transform，支持额外增加依赖
        androidExtension.registerTransform(CustomTransform(project)/* 支持增加依赖*/)
    }
}