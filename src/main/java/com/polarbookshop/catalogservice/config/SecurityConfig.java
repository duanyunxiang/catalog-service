package com.polarbookshop.catalogservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        //允许未认证读取图书
                        .mvcMatchers(HttpMethod.GET,"/","/books/**").permitAll()
                        //其它请求，不仅要认证，还需要employee角色，等效：hasAuthority("ROLE_employee")
                        .anyRequest().hasRole("employee")
                )
                //基于jwt认证启用OAuth2资源服务器
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                //因为每个请求都包含访问令牌，所以不在请求间保持用户会话
                .sessionManagement(sessionManagement->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //禁用CSRF防护，不涉及
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    //为授权用户关联GrantedAuthority对象列表
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        //定义将claim映射到GrantedAuthority对象的转换器
        var grantedConverter=new JwtGrantedAuthoritiesConverter();
        //角色使用前缀
        grantedConverter.setAuthorityPrefix("ROLE_");
        //从roles claim中提取角色列表
        grantedConverter.setAuthoritiesClaimName("roles");

        var resultConverter=new JwtAuthenticationConverter();
        resultConverter.setJwtGrantedAuthoritiesConverter(grantedConverter);
        return resultConverter;
    }
}
