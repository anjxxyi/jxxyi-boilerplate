package com.anjxxyi.practice.boilerplate.model;

import com.anjxxyi.practice.boilerplate.model.enums.Authority;
import jakarta.persistence.*;
import lombok.*;
import org.apache.ibatis.annotations.Mapper;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Mapper
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String email;

    private String password;

    private String nickname;

    private String provider;

    @Column(length = 2000)
    private String accessToken;

    private Long accessTokenExpireIn;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAccessTokenExpireIn(Long accessTokenExpireIn) {
        this.accessTokenExpireIn = accessTokenExpireIn;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public Member update(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
        return this;
    }

    public Member update(String accessToken, Long accessTokenExpireIn, String email) {
        this.accessToken = accessToken;
        this.accessTokenExpireIn = accessTokenExpireIn;
        this.email = email;
        return this;
    }
}