package com.zero.jdbc.sharding.sphere.service;

import com.zero.jdbc.sharding.sphere.domain.entity.SystemUserEntity;

public interface SystemUserService {

    SystemUserEntity saveSystemUser(SystemUserEntity systemUserEntity);

    SystemUserEntity getSystemUser(String userId);
}
