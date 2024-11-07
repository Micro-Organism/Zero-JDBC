package com.zero.jdbc.sharding.sphere.jpa;

import com.zero.jdbc.sharding.sphere.domain.entity.SystemUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemUserJPA extends JpaRepository<SystemUserEntity, Object> {

}
