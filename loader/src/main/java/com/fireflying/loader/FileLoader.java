package com.fireflying.loader;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

/**
 * @program: tortoise
 * @author: Fire Flying
 * @create: 2021-09-13 00:53
 **/

public class FileLoader implements Runnable{

    private static final String BASE_DIR = "/opt/word/jar";

    public static final Map<String, String > map = new HashMap<>();

    private Instrumentation inst;

    private static URLClassLoader urlClassLoader = null;

    public FileLoader(Instrumentation inst) {
        this.inst = inst;
    }


    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(5000);
                File file = new File(BASE_DIR);
                File[] files = file.listFiles();
                for (File temp : files) {
                    if (temp.getName().endsWith(".jar")) {
                        if (urlClassLoader == null) {
                            urlClassLoader = new URLClassLoader(new URL[]{temp.toURI().toURL()});
                            urlClassLoader.loadClass("com.fireflying.plugin.Handler");
                            map.put("com.fireflying.demo.test.TestApi", "aaa");
                            inst.appendToBootstrapClassLoaderSearch(new JarFile(temp));
//                            inst.retransformClasses(CustomClassVisitor.loaderMap.get("com.fireflying.demo.test.TestApi").loadClass("com.fireflying.demo.test.TestApi"));
                            Class[] allLoadedClasses = inst.getAllLoadedClasses();
                            for (Class allLoadedClass : allLoadedClasses) {
                                if ("com.fireflying.demo.test.TestApi".equals(allLoadedClass.getName())) {
                                    System.out.println("in");
                                    inst.retransformClasses(allLoadedClass);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception{
        File file = new File("/Users/guoying/big/myproject/tortoise/plugin/target/plugin-1.0.0.jar");
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
        Class<?> aClass = urlClassLoader.loadClass("com.fireflying.plugin.Handler");
//        Constructor<?> constructor = aClass.getConstructor();
//        Object o = constructor.newInstance();
        Method handler = aClass.getMethod("handler");
        handler.invoke(null);
    }

}
