package com.fireflying.demo.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: tortoise
 * @author: Fire Flying
 * @create: 2021-09-12 23:36
 **/
@RestController
public class TestApi {

    @GetMapping("/test")
    public Object test() throws Exception{
        System.out.println("into test");
        return "success";
    }

    @GetMapping("/test1")
    public void test1() throws Exception{
        System.out.println("into test1");
    }

    @GetMapping("/test2")
    public void test2() throws Exception{
        try {
            System.out.println("into test1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } catch (Throwable e) {
            throw e;
        }
    }

    @GetMapping("/test3")
    public String test3(String name, String age) {
        String app = "aa";
        System.out.println(name + age);
        return test4(name, age);
    }

    @GetMapping("/test4")
    public String test4(String name, String age) {
        System.out.println(name + age);
        return name;
    }

}
