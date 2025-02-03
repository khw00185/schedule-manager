package com.example.schedulemanager.user.dto;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final String id;
    private final String password;


//사용자에게 어떤 권한을 반환할것인지를 정하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((GrantedAuthority) () -> "ROLE_USER"); //이번 프로젝트에서는 role을 정의하지 않았기에 모든 사용자는 기본 권한으로 함 -> filter를 활용하면 스프링 시큐리티가 제공하는 권한 관리기능을 사용 할 수 있음.
/*        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getUserName();
            }
        });
        return authorities;*/
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public String getId() {
        return id;
    }
}
