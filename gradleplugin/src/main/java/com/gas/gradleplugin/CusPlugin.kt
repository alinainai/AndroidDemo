package com.gas.gradleplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("Hello CustomPlugin")
    }
}