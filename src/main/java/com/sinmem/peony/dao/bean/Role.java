package com.sinmem.peony.dao.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.dao.bean
 * @Author sinmem
 * @CreateTime 2019-12-22 00:09
 * @Description 用户角色
 */
public class Role extends BaseBean implements GrantedAuthority {
    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_USER = 2;
    public static final int ROLE_VIP = 3;
    public static final int ROLE_SVIP = 4;
    private String role;

    public Role() {
    }

    public Role(String role) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    public Role(Long id, String role) {
        super(id);
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof SimpleGrantedAuthority ? this.role.equals(((Role)obj).role) : false;
        }
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }
}
