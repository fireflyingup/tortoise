package com.fireflying.plugin;


import com.fireflying.enums.FireMethod;

/**
 * @program: tortoise
 * @author: Fire Flying
 * @create: 2021-09-13 14:02
 **/

public class Handler {

    @FireMethod(
            value = {"com.fireflying.demo.test.TestApi.test()"}
    )
    public static void handler() {
        System.out.println("init handler");
    }

    public void test() {

    }

}
