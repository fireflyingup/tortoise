package com.fireflying.loader;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @program: tortoise
 * @author: Fire Flying
 * @create: 2021-09-12 22:17
 **/

public class CustomClassVisitor extends ClassVisitor {

    private int maxLine = 0;

    public static final Map<String, ClassLoader> loaderMap = new HashMap<>();

    private String className;

    public CustomClassVisitor(int i, ClassVisitor classVisitor, String className, ClassLoader classLoader) {
        super(i, classVisitor);
        loaderMap.put(className.replace("/", "."), classLoader);
        this.className = className.replace("/", ".");
    }

    @Override
    public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
        System.out.println(i + " " + s + " " + s1 + " " + s2 + " " + strings);
        MethodVisitor methodVisitor = super.visitMethod(i, s, s1, s2, strings);
        Set<Map.Entry<String, String>> entries = FileLoader.map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            if (key.equals(this.className)) {
                return new CustomMethodVisitor(Opcodes.ASM5, methodVisitor);
            }
        }
        return methodVisitor;
    }

    @Override
    public void visitEnd() {
        System.out.println("最大行数:" + maxLine);
        super.visitEnd();
    }

    public class CustomMethodVisitor extends MethodVisitor {

        public CustomMethodVisitor(int i, MethodVisitor methodVisitor) {
            super(i, methodVisitor);
        }

        @Override
        public void visitCode() {
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/fireflying/plugin/Handler", "handler", "()V", false);
            super.visitCode();
        }

        @Override
        public void visitLineNumber(int line, Label start) {
            maxLine = Math.max(maxLine, line);
            super.visitLineNumber(line, start);
        }
    }

}
