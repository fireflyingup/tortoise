package com.fireflying.engine.transformer.visitor;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * @program: tortoise
 * @author: Fire Flying
 * @create: 2021-10-04 12:11
 **/

public class PluginMethodVisitor extends LocalVariablesSorter implements Opcodes{

    private String methodName;

    private String className;

    private boolean isMethodStatic;

    private int paramSize = 0;

    private int time;
    private int end;

    public PluginMethodVisitor(int access, String descriptor, MethodVisitor methodVisitor) {
        super(access, descriptor, methodVisitor);
    }

    protected PluginMethodVisitor(int api, int access, String descriptor, MethodVisitor methodVisitor, String methodName, String className) {
        super(api, access, descriptor, methodVisitor);
        System.out.println(methodName);
        System.out.println(descriptor);
        this.className = className;
        this.methodName = methodName;
        isMethodStatic = (access & Opcodes.ACC_STATIC) != 0;

    }


    @Override
    public void visitVarInsn(int opcode, int var) {
        super.visitVarInsn(opcode, var);
    }


    @Override
    public void visitCode() {
        super.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        time = newLocal(Type.LONG_TYPE);
        mv.visitVarInsn(Opcodes.LSTORE,  time);
        end = newLocal(Type.LONG_TYPE);

        if (paramSize > 0) {
            int start = isMethodStatic ? 0 : 1;
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            for (int i = start; i < start + paramSize; i++) {
                mv.visitVarInsn(Opcodes.ALOAD, i);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false);
            }
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }



    }

    @Override
    public void visitParameter(String name, int access) {
        super.visitParameter(name, access);
        paramSize++;
    }

    @Override
    public void visitInsn(int opcode) {

        if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW) {
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
            mv.visitVarInsn(Opcodes.LSTORE, end);

            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitVarInsn(LLOAD, end);
            mv.visitVarInsn(LLOAD, time);
            mv.visitInsn(LSUB);
            mv.visitLdcInsn(new Long(1000000000L));
//            mv.visitLdcInsn(new Long(1L));
            mv.visitInsn(LCMP);

            Label l3 = new Label();
            mv.visitJumpInsn(IFLE, l3);

            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn(className + methodName);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitLdcInsn(":");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(Opcodes.LLOAD, end);
            mv.visitVarInsn(Opcodes.LLOAD, time);
            mv.visitInsn(Opcodes.LSUB);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
            mv.visitLdcInsn("us");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

            mv.visitLabel(l3);
        }
        super.visitInsn(opcode);
    }

}
