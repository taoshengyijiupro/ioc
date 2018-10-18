package com.shipparts.service.impl;

import com.shipparts.annotation.ExtService;
import com.shipparts.service.UserService;
import org.springframework.transaction.annotation.Transactional;

@ExtService
public class UserServiceImpl implements UserService {
    @Override
    @Transactional
    public void addOrder() {
        System.out.println("反射机制执行了");
    }
}
