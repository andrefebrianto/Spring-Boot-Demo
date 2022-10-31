package com.example.demo.common.constant;

import lombok.Getter;

@Getter
public enum UserConstant {
    ANONYMOUS_USER(-1L, "anonymousUser"),
    SYSTEM_USER(0L, "systemUser");

    private final Long id;

    private final String name;

    UserConstant(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
