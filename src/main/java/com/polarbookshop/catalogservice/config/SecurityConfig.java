package com.polarbookshop.catalogservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        //允许未认证读取图书
                        .mvcMatchers(HttpMethod.GET,"/","/books/**").permitAll()
                        .anyRequest().authenticated()
                )
                //基于jwt认证启用OAuth2资源服务器
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                //因为每个请求都包含访问令牌，所以不在请求间保持用户会话
                .sessionManagement(sessionManagement->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //禁用CSRF防护，不涉及
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
