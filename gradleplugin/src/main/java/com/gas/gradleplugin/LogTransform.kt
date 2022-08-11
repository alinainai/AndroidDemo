package com.gas.gradleplugin

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.variant.VariantInfo
import com.android.build.gradle.internal.pipeline.TransformManager
import com.gas.base.ExtTransform
import org.gradle.api.Incubating
import org.gradle.api.Project
import java.io.InputStream
import java.io.OutputStream

class LogTransform(project: Project):ExtTransform(true) {

    override fun getName()  = "LogTransform"


    override fun isIncremental() = true

    /**
     * 用于过滤 Variant，返回 false 表示该 Variant 不执行 Transform
     */
    @Incubating
    override fun applyToVariant(variant: VariantInfo?): Boolean {
        return "debug" == variant?.buildTypeName
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> = TransformManager.CONTENT_CLASS

    override fun getScopes(): MutableSet<QualifiedContent.ScopeType> = TransformManager.SCOPE_FULL_PROJECT

    override fun provideFunction() = { ios: InputStream, zos: OutputStream ->
        zos.write(ios.readBytes())
    }
}