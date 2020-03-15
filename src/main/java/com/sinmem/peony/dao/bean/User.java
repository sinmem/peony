package com.sinmem.peony.dao.bean;

import com.google.gson.annotations.Expose;
import com.sinmem.peony.common.enums.AccountStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.dao.bean
 * @Author sinmem
 * @CreateTime 2019-12-21 23:55
 * @Description
 */
public class User extends BaseBean implements UserDetails {
    private String phoneNumber;
    private String username;
    @Expose(deserialize = false, serialize = false)
    private String password;// 密码不用于序列化和反序列化
    private List<Role> roles;
    private AccountStatus accountStatus;
    private transient boolean passwordStatus;
    private Date createTime;
    private Date updateTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {// 账号是否未过期
        return !(accountStatus == AccountStatus.EXPIRED);
    }

    @Override
    public boolean isAccountNonLocked() {// 账号是否未冻结
        return !(accountStatus == AccountStatus.LOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {// 密码是否未过期
        return passwordStatus;
    }

    @Override
    public boolean isEnabled() {// 账号是否可用
        return accountStatus == AccountStatus.ENABLED;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public boolean isPasswordStatus() {
        return passwordStatus;
    }

    public void setPasswordStatus(boolean passwordStatus) {
        this.passwordStatus = passwordStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", accountStatus=" + accountStatus +
                ", passwordStatus=" + passwordStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "} " + super.toString();
    }
}
