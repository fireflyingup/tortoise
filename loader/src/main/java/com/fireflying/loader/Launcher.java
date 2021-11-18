package com.fireflying.loader;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.lang.instrument.Instrumentation;
import java.util.List;

/**
 * @program: tortoise
 * @author: Fire Flying
 * @create: 2021-09-12 21:41
 **/

/**
 * -XX:+TraceClassLoading 可以使用来打印类加载过程
 */
public class Launcher {

    public static void premain(String agentArg, Instrumentation inst) throws Exception{
        System.out.println("premain:" + agentArg);
        start(agentArg, inst);
    }

    public static void agentmain(String agentArg, Instrumentation inst) throws Exception{
        System.out.println("agentmain");
        start(agentArg, inst);
    }

    private static void start(String agentArg, Instrumentation inst) throws Exception{
//        FileLoader fileLoader = new FileLoader(inst);
//        inst.addTransformer(new CustomFileTransformer() , true);
//        new Thread(fileLoader).start();
        new Fire().start(agentArg, inst);
    }

    public static void main(String[] args) throws Exception{
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        System.out.println("into");
        for (VirtualMachineDescriptor vmd : list) {
//            System.out.println(vmd.displayName());
            if (vmd.displayName().endsWith("demo-0.0.1-SNAPSHOT.jar")) {
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                virtualMachine.loadAgent("/Users/guoying/big/myproject/tortoise/loader/target/loader.jar");
                System.out.println("加载agent，目标：" + vmd.displayName());
                virtualMachine.detach();
            }
        }
    }

}
