package com.shipparts.annotation;


import com.shipparts.annotation.service.UserService;

public class test {
    public static void main(String[] args) throws Exception {
        ExtClassPathXmlApplicationContext app = new ExtClassPathXmlApplicationContext("com.shipparts.annotation.service.impl");
        UserService userService = (UserService) app.getBean("userServiceImpl");
        userService.add();
        System.out.println(userService);
    }
}
