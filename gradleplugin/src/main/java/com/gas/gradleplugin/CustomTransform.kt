package com.gas.gradleplugin

import com.android.build.api.transform.*
import com.android.build.api.variant.VariantInfo
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.google.common.io.Files
import org.gradle.api.Incubating
import org.gradle.api.Project
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

class CustomTransform(project: Project): Transform() {

    override fun getName()  = "CustomTransform"

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

    override fun transform(transformInvocation: TransformInvocation) {
        super.transform(transformInvocation)

        log("Transform start, isIncremental = ${transformInvocation.isIncremental}.")

        val inputProvider = transformInvocation.inputs
        val outputProvider = transformInvocation.outputProvider

        // Delete all transform tmp files when not in incremental build.
        if (!transformInvocation.isIncremental) {
            outputProvider.deleteAll()
        }
        for (input in inputProvider) {
            for (jarInput in input.jarInputs) {
                val inputJar = jarInput.file
                val outputJar = outputProvider.getContentLocation(jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                if (transformInvocation.isIncremental) {
                    when (jarInput.status ?: Status.NOTCHANGED) {
                        Status.NOTCHANGED -> {
                            // Do nothing.
                        }
                        Status.ADDED, Status.CHANGED -> {
                            transformJar(inputJar, outputJar)
                        }
                        Status.REMOVED -> {
                            // Delete.
                            FileUtils.delete(outputJar)
                        }
                    }
                } else {
                    transformJar(inputJar, outputJar)
                }
            }
            // 4. Transform directory input.
            for (dirInput in input.directoryInputs) {
                val inputDir = dirInput.file
                val outputDir = outputProvider.getContentLocation(dirInput.name, dirInput.contentTypes, dirInput.scopes, Format.DIRECTORY)
                if (transformInvocation.isIncremental) {
                    for ((inputFile, status) in dirInput.changedFiles) {
                        val outputFile = concatOutputFilePath(outputDir, inputFile)
                        when (status ?: Status.NOTCHANGED) {
                            Status.NOTCHANGED -> {
                                // Do nothing.
                            }
                            Status.ADDED, Status.CHANGED -> {
                                // Do transform.
                                transformDirectory(inputFile, outputFile)
                            }
                            Status.REMOVED -> {
                                // Delete
                                FileUtils.delete(outputFile)
                            }
                        }
                    }
                } else {
                    for (inputFile in FileUtils.getAllFiles(inputDir)) {
                        val outputFile = concatOutputFilePath(outputDir, inputFile)
                        transformDirectory(inputFile, outputFile)
                    }
                }
            }
        }
        log("Transform end.")
    }

    /**
     * Do Transform Jar.
     */
    private fun transformJar(inputJar: File, outputJar: File) {
        Files.createParentDirs(outputJar)
        // 如果不需要处理 Jar 包下的文件使用下面代码
        FileUtils.copyFile(inputJar, outputJar)
        // 如果需要处理 Jar 包下的文件使用下面代码
//        FileInputStream(inputJar).use { fis ->
//            ZipInputStream(fis).use { zis ->
//                FileOutputStream(outputJar).use { fos ->
//                    ZipOutputStream(fos).use { zos ->
//                        var entry = zis.nextEntry
//                        while (entry != null) {
//                            if (!entry.isDirectory ) {
//                                zos.putNextEntry(ZipEntry(entry.name))
//                                zis.copyTo(zos)
//                            }
//                            entry = zis.nextEntry
//                        }
//                    }
//                }
//            }
//        }
    }

    /**
     * Do Transform Directory.
     */
    private fun transformDirectory(inputFile: File, outputFile: File) {
        // Create parent directories to hold outputFile file.
        Files.createParentDirs(outputFile)
        FileInputStream(inputFile).use { fis ->
            FileOutputStream(outputFile).use { fos ->
                if(classFilter(inputFile.name)){ // 如果是 .class 文件
                    // Apply transform function.
                    val classReader = ClassReader(fis) //对class文件进行读取与解析
                    //对class文件的写入
                    val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                    //访问class文件相应的内容，解析到某一个结构就会通知到ClassVisitor的相应方法
                    val classVisitor = CustomClassVisitor(classWriter)
                    //依次调用 ClassVisitor接口的各个方法
                    classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                    //toByteArray方法会将最终修改的字节码以 byte 数组形式返回。
                    fos.write(classWriter.toByteArray())
                }else{
                    fis.copyTo(fos)
                }
            }
        }
    }

    private fun concatOutputFilePath(outputDir: File, inputFile: File) = File(outputDir, inputFile.name)

    private fun log(logStr: String) {
        println("$name - $logStr")
    }

    private fun classFilter(className: String) = className.endsWith(SdkConstants.DOT_CLASS)

}
