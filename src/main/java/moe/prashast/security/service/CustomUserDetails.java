package moe.prashast.security.service;

import lombok.Data;
import moe.prashast.entity.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class CustomUserDetails implements UserDetails {

    private String mobile;
    private String fullName;
    private String udiseCode;
    private short role;
    private Short status;
    private String password;
    private Long userId;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user, String role) {
        if(user!=null){
            this.mobile = user.getPhoneMobile();
            this.fullName = user.getName();
            this.udiseCode = user.getUdiseSchCode();
            this.role = Short.parseShort(role);
            this.status = user.getIsActive();
            this.password = user.getUserPassword();
            this.userId=user.getUserId();
            this.authorities =List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserRole()));
        }

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return mobile;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return status == 1; }
}
