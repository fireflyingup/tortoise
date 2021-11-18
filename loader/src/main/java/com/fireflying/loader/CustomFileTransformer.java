package com.fireflying.loader;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @program: tortoise
 * @author: Fire Flying
 * @create: 2021-09-12 22:09
 **/

public class CustomFileTransformer implements ClassFileTransformer {


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className != null && (className.startsWith("com/fireflying"))) {
            System.out.println(className);
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
            ClassVisitor cv = new CustomClassVisitor(Opcodes.ASM5, cw, className, loader);
            cr.accept(cv, ClassReader.EXPAND_FRAMES);
            return cw.toByteArray();
        }
        return classfileBuffer;
    }


}
