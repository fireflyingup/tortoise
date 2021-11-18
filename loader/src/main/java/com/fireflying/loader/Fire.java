package com.fireflying.loader;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * @program: tortoise
 * @author: Fire Flying
 * @create: 2021-10-04 02:34
 **/

public class Fire {

    public void start(String agentArg, Instrumentation inst) throws Exception {
        File file = new File("/Users/guoying/big/myproject/tortoise/engine/target/engine-1.0.0.jar");
        FireClassLoader fireClassLoader = new FireClassLoader(new URL[]{file.toURI().toURL()});
        Class<?> aClass = fireClassLoader.loadClass("com.fireflying.engine.Launcher");
        Method start = aClass.getMethod("start", Instrumentation.class);
        start.invoke(null, inst);
    }
}
