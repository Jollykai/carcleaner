package com.jollykai.carcleaner;

/**
 * Project: CarCleaner
 *
 * @author Dmitry Istomin
 * https://github.com/Jollykai
 */
public enum Role {
    ADMIN ("ADMIN");

    private String role;

    Role(String roleName) {
        this.role = roleName;
    }

    public String getName() {
        return role;
    }
}

