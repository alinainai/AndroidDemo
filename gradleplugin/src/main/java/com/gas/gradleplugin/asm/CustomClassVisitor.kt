package com.gas.gradleplugin.asm

import com.gas.gradleplugin.log
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class CustomClassVisitor(cv: ClassVisitor) : ClassVisitor(Opcodes.ASM6, cv) {

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
        log(
            "ClassVisit # visit: version = $version "
                    + "access= $access"
                    + "name= $name"
                    + "signature= ${signature ?: "null"}"
                    + "superName= ${superName ?: "null"}"
        )
    }

    override fun visitField(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        value: Any?
    ): FieldVisitor {
        log(
            "ClassVisit # visitField:"
                    + "access= $access"
                    + "name= $name"
                    + "descriptor= ${descriptor ?: "null"}"
                    + "signature= ${signature ?: "null"}"
                    + "value= ${value ?: "null"}"
        )
        return super.visitField(access, name, descriptor, signature, value)
    }

    override fun visitMethod(
        access: Int,
        name: String,
        desc: String?,
        signature: String?,
        exceptions: Array<String?>?
    ): MethodVisitor {
        log(
            "ClassVisit # visitMethod:"
                    + "access= $access"
                    + "name= $name"
                    + "desc= ${desc ?: "null"}"
                    + "signature= ${signature ?: "null"}"
        )
        val mv: MethodVisitor = cv.visitMethod(access, name, desc, signature, exceptions)
        return if (name.startsWith("on")) {  //处理onXX()方法
            CustomMethodVisitor(mv, className!!, name)
        } else mv
    }

    override fun visitEnd() {
        super.visitEnd()
        log(
            "ClassVisit # visitEnd"
        )
    }
}