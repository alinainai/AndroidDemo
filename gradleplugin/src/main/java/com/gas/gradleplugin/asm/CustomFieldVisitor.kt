package com.gas.gradleplugin.asm

import com.gas.gradleplugin.log
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Opcodes

class CustomFieldVisitor(av: AnnotationVisitor?) : AnnotationVisitor(Opcodes.ASM6, av) {
    override fun visit(name: String, value: Any) {
        super.visit(name, value)
        log(
            "AnnotationVisitor # visit: name =$name value = $value"
        )
    }

    override fun visitEnum(name: String, desc: String, value: String) {
        super.visitEnum(name, desc, value)
        log(
            "AnnotationVisitor # visitEnum: name =$name desc=$desc value = $value"
        )
    }

    override fun visitAnnotation(name: String, desc: String): AnnotationVisitor {
        log(
            "AnnotationVisitor # visitAnnotation: name =$name desc=$desc"
        )
        return super.visitAnnotation(name, desc)
    }

    override fun visitArray(name: String): AnnotationVisitor {
        log("AnnotationVisitor # visitArray  name=$name")
        return super.visitArray(name)
    }

    override fun visitEnd() {
        super.visitEnd()
        log("AnnotationVisitor # visitEnd")
    }
}
