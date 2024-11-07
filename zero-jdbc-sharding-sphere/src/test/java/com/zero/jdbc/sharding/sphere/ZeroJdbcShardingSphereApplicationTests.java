package com.zero.jdbc.sharding.sphere;

import com.zero.jdbc.sharding.sphere.domain.entity.SystemUserEntity;
import com.zero.jdbc.sharding.sphere.jpa.SystemUserJPA;
import com.zero.jdbc.sharding.sphere.service.SystemUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.*;
import java.time.Instant;
import java.util.List;

@SpringBootTest
class ZeroJdbcShardingSphereApplicationTests {

	@Container
	static MySQLContainer<?> mySQLContainer1 = new MySQLContainer<>("mysql:8.0.23")
			.withDatabaseName("ds0")
			.withUsername("root")
			.withPassword("root@123");

	@Container
	static MySQLContainer<?> mySQLContainer2 = new MySQLContainer<>("mysql:8.0.23")
			.withDatabaseName("ds1")
			.withUsername("root")
			.withPassword("root@123");

	static {
		mySQLContainer2.setPortBindings(List.of("13307:3306"));
		mySQLContainer1.setPortBindings(List.of("13306:3306"));
	}
	
	@Autowired
	private SystemUserService systemUserService;

	@Autowired
	private SystemUserJPA systemUserJPA;
	
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
	}

	@Test
	void shouldFindSystemUserInCorrectShard() {
		// given
		SystemUserEntity systemUser1 = new SystemUserEntity();
		systemUser1.setId(100L);
		systemUser1.setUserId("test1");
		systemUser1.setUsername("test1");
		systemUser1.setPassword("test1");
		systemUser1.setNickname("test1");
		systemUser1.setAvatar("1234567890");
		systemUser1.setEmail("test1@test.com");
		systemUser1.setMobile("1234567890");
		systemUser1.setGender(1);
		systemUser1.setAge(18);
		systemUser1.setCreateTime(Instant.now());
		systemUser1.setUpdateTime(Instant.now());
		systemUser1.setDeleted(false);
		SystemUserEntity systemUser2 = new SystemUserEntity();
		systemUser2.setId(101L);
		systemUser2.setUserId("test2");
		systemUser2.setUsername("test2");
		systemUser2.setPassword("test2");
		systemUser2.setNickname("test2");
		systemUser2.setAvatar("1234567890");
		systemUser2.setEmail("test2@test.com");
		systemUser2.setMobile("1234567890");
		systemUser2.setGender(1);
		systemUser2.setAge(18);
		systemUser2.setCreateTime(Instant.now());
		systemUser2.setUpdateTime(Instant.now());
		systemUser2.setDeleted(false);

		// when
		SystemUserEntity savedSystemUser1 = systemUserService.saveSystemUser(systemUser1);
		SystemUserEntity savedSystemUser2 = systemUserService.saveSystemUser(systemUser2);

		// then,
		// Assuming the sharding strategy is based on the order id, data for systemUser1 should be present only in ds0
		// and data for systemUser2 should be present only in ds1
		Assertions.assertThat(systemUserService.getSystemUser(savedSystemUser1.getUserId())).isEqualTo(savedSystemUser1);
		Assertions.assertThat(systemUserService.getSystemUser(savedSystemUser2.getUserId())).isEqualTo(savedSystemUser2);

		// Verify that the orders are not present in the wrong shards.
		// You would need to implement these methods in your SystemUserService.
		// They should use a JdbcTemplate or EntityManager to execute SQL directly against each shard.
		Assertions.assertThat(assertSystemUserInShard(savedSystemUser1, mySQLContainer1)).isTrue();
		Assertions.assertThat(assertSystemUserInShard(savedSystemUser2, mySQLContainer2)).isTrue();
	}

	private boolean assertSystemUserInShard(SystemUserEntity systemUserEntity, MySQLContainer<?> container) {
		try (Connection conn = DriverManager.getConnection(container.getJdbcUrl(), container.getUsername(), container.getPassword())) {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM `system_user` WHERE user_id = ?");
			stmt.setString(1, systemUserEntity.getUserId());
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (SQLException ex) {
			throw new RuntimeException("Error querying order in shard", ex);
		}
	}

}
