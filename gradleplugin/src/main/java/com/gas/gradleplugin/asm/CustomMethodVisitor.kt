package com.gas.gradleplugin.asm

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class CustomMethodVisitor(
    methodVisitor: MethodVisitor, private val className: String,
    private val methodName: String
) : MethodVisitor(Opcodes.ASM6, methodVisitor) {

    override fun visitAnnotation(desc: String, visible: Boolean): AnnotationVisitor {
        println("MethodVisitor visitAnnotation desc------$desc")
        println("MethodVisitor visitAnnotation visible------$visible")
        val annotationVisitor: AnnotationVisitor = mv.visitAnnotation(desc, visible)
        return if (desc.contains("CheckLogin")) {
            CustomAnnotationVisitor(annotationVisitor)
        } else annotationVisitor
    }


    //方法执行前插入
    override fun visitCode() {
        super.visitCode()
        println("MethodVisitor visitCode------")
        mv.visitLdcInsn("TAG")
        mv.visitLdcInsn("$className------->$methodName")
        mv.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            "android/util/Log",
            "e",
            "(Ljava/lang/String;Ljava/lang/String;)I",
            false
        )
        mv.visitInsn(Opcodes.POP)
    }

    //方法执行后插入
    override fun visitInsn(opcode: Int) {
        super.visitInsn(opcode)
        println("MethodVisitor visitInsn------")
    }

    override fun visitEnd() {
        super.visitEnd()
        println("MethodVisitor visitEnd------")
    }
}
