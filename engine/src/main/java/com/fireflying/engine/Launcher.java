package com.fireflying.engine;

import com.fireflying.engine.transformer.PluginTransformer;

import java.lang.instrument.Instrumentation;

/**
 * @program: tortoise
 * @author: Fire Flying
 * @create: 2021-09-12 21:32
 **/

public class Launcher {

    public static void start(Instrumentation inst) {
        PluginTransformer pluginTransformer = new PluginTransformer(inst);
        System.out.println("start...");
    }
}
