package com.zero.jdbc.sharding.sphere.service.impl;

import com.zero.jdbc.sharding.sphere.domain.entity.SystemUserEntity;
import com.zero.jdbc.sharding.sphere.jpa.SystemUserJPA;
import com.zero.jdbc.sharding.sphere.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemUserServiceImpl implements SystemUserService {

    SystemUserJPA systemUserJPA;

    @Autowired
    public SystemUserServiceImpl(SystemUserJPA systemUserJPA) {
        this.systemUserJPA = systemUserJPA;
    }

    @Override
    public SystemUserEntity saveSystemUser(SystemUserEntity systemUserEntity) {
        return systemUserJPA.save(systemUserEntity);
    }

    @Override
    public SystemUserEntity getSystemUser(String userId) {
        return systemUserJPA.findById(userId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

}
