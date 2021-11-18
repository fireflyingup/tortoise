package com.fireflying.loader;


import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * @program: tortoise
 * @author: Fire Flying
 * @create: 2021-09-13 01:42
 **/

public class FireClassLoader extends URLClassLoader {

    public FireClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public FireClassLoader(URL[] urls) {
        super(urls);
    }

    public FireClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }
}
