package com.shipparts.annotation.service.impl;

import com.shipparts.annotation.ExtResource;
import com.shipparts.annotation.ExtService;
import com.shipparts.annotation.service.OrderService;
import com.shipparts.annotation.service.UserService;


@ExtService
public class UserServiceImpl implements UserService {

    @ExtResource
    private OrderService orderServiceImpl;

    @Override
    public void add() {
        orderServiceImpl.addOrder();
        System.out.println("反射机制执行了");
    }
}
