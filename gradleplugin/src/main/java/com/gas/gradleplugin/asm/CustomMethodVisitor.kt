package com.gas.gradleplugin.asm

import com.gas.gradleplugin.log
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class CustomMethodVisitor(
    methodVisitor: MethodVisitor, private val className: String,
    private val methodName: String
) : MethodVisitor(Opcodes.ASM6, methodVisitor) {

    override fun visitAnnotation(desc: String, visible: Boolean): AnnotationVisitor {
        log(
            "MethodVisitor # visitAnnotation: desc =$desc visible = $visible"
        )
        return CustomAnnotationVisitor(mv.visitAnnotation(desc, visible))
    }

    //方法执行前插入
    override fun visitCode() {
        super.visitCode()
        log("MethodVisitor # visitCode")
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
        log(
            "MethodVisitor # visitInsn: opcode =$opcode"
        )
    }

    override fun visitEnd() {
        super.visitEnd()
        log(
            "MethodVisitor # visitEnd"
        )
    }
}
