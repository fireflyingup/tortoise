package com.fireflying.engine.transformer;

import com.fireflying.engine.transformer.visitor.PluginClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @program: tortoise
 * @author: Fire Flying
 * @create: 2021-10-04 11:15
 **/

public class PluginTransformer implements ClassFileTransformer {

    private Instrumentation inst;

    public PluginTransformer(Instrumentation inst) {
        this.inst = inst;
        inst.addTransformer(this, true);
    }


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className != null && !className.startsWith("sun/") && !className.startsWith("java/") && className.startsWith("com/fireflying/demo/test")) {
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
            ClassVisitor cv = new PluginClassVisitor(Opcodes.ASM5, cw);
            cr.accept(cv, ClassReader.EXPAND_FRAMES);
            byte[] bytes = cw.toByteArray();
//            addToFile(bytes, className);
            return bytes;
        }
        return classfileBuffer;
    }

    private void addToFile(byte[] bytes, String className) {
        FileOutputStream fout = null;
        try {

            File f = new File("/opt/class/" + className + ".class");
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }

//            File f = new File(project.getBasedir() + "/target/classes/com/example/demo/service/UserServie.class");
            fout = new FileOutputStream(f);
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
}
