package com.shipparts.annotation.service.impl;

import com.shipparts.annotation.ExtService;
import com.shipparts.annotation.service.OrderService;

@ExtService
public class OrderServiceImpl implements OrderService {
    @Override
    public void addOrder() {
        System.out.println("addOrder");
    }
}
