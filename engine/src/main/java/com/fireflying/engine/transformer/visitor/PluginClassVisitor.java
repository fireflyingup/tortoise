package com.fireflying.engine.transformer.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AnalyzerAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * @program: tortoise
 * @author: Fire Flying
 * @create: 2021-10-04 11:44
 **/

public class PluginClassVisitor extends ClassVisitor {

    private String owner;

    private boolean isInterface;

    private boolean isAbstract;

    public PluginClassVisitor(int api) {
        super(api);
    }

    public PluginClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        isAbstract = (access & Opcodes.ACC_ABSTRACT) != 0;
        if (!isInterface && name != null && !name.equals("<init>") && !name.equals("<clinit>")) {
            return new PluginMethodVisitor(Opcodes.ASM5, access ,descriptor, methodVisitor, name, owner);
        }
        return methodVisitor;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        owner = name;
        isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
    }


    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}
