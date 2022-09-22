package com.gas.gradleplugin

import Outer
import org.gradle.api.Plugin
import org.gradle.api.Project

const val UPLOAD_EXTENSION_NAME = "outer"

class CustomPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("Hello CustomPlugin")
        project.extensions.create(UPLOAD_EXTENSION_NAME, Outer::class.java)
        project.afterEvaluate {
            val outer = project.extensions.findByName(UPLOAD_EXTENSION_NAME) as Outer?
            outer?.let {
                println("outer.name =${it.name?:"null"} outer.inner.name =${it.inner?.name?:"null"}")
            }
        }
    }
}