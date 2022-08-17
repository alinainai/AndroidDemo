package com.gas.gradleplugin.asm

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Opcodes

class CustomAnnotationVisitor(av: AnnotationVisitor?) : AnnotationVisitor(Opcodes.ASM6, av) {
    override fun visit(name: String, value: Any) {
        super.visit(name, value)
        println("--------AnnotationVisitor visit start-------")
        println("visit  visit----$name")
        println("visit  value----$value")
    }

    override fun visitEnum(name: String, desc: String, value: String) {
        super.visitEnum(name, desc, value)
        println("visitEnum  name----$name")
        println("visitEnum  desc----$desc")
        println("visitEnum  value----$value")
    }

    override fun visitAnnotation(name: String, desc: String): AnnotationVisitor {
        println("visitAnnotation  name----$name")
        println("visitAnnotation  desc----$desc")
        return super.visitAnnotation(name, desc)
    }

    override fun visitArray(name: String): AnnotationVisitor {
        println("visitArray  name----$name")
        return super.visitArray(name)
    }

    override fun visitEnd() {
        super.visitEnd()
        println("--------AnnotationVisitor visit end-------")
    }
}
