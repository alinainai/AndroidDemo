package com.gas.gradleplugin.asm

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class CustomClassVisitor (cv: ClassVisitor) : ClassVisitor(Opcodes.ASM6, cv) {

    private var className: String? = null
    override fun visit(
        version: Int,
        access: Int,
        name: String,
        signature: String?,
        superName: String?,
        interfaces: Array<String?>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name
    }

    override fun visitMethod(
        access: Int,
        name: String,
        desc: String?,
        signature: String?,
        exceptions: Array<String?>?
    ): MethodVisitor {
        println("ClassVisitor visitMethod name-------$name")
        val mv: MethodVisitor = cv.visitMethod(access, name, desc, signature, exceptions)
        return if (name.startsWith("on")) {
            //处理onXX()方法
           CustomMethodVisitor(mv, className!!, name)
        } else mv
    }

    override fun visitEnd() {
        super.visitEnd()
    }
}