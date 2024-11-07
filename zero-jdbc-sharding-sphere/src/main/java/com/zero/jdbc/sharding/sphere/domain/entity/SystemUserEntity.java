package com.zero.jdbc.sharding.sphere.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "system_user")
public class SystemUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

//    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", length = 32)
    private String userId;

    @Column(name = "username", length = 128)
    private String username;

    @Column(name = "password", length = 128)
    private String password;

    @Column(name = "nickname", length = 128)
    private String nickname;

    @Column(name = "avatar", length = 128)
    private String avatar;

    @Column(name = "email", length = 128)
    private String email;

    @Column(name = "mobile", length = 128)
    private String mobile;

    @Column(name = "status")
    private Integer status;

    @Column(name = "age")
    private Integer age;

    @Column(name = "creator", length = 128)
    private String creator;

    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    @Column(name = "updater", length = 128)
    private String updater;

    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "remark", length = 256)
    private String remark;

    @Column(name = "deleted")
    private Boolean deleted;

}