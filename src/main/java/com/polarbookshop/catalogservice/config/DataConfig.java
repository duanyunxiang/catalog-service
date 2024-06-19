package com.polarbookshop.catalogservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
// 启用持久化实体审计
@EnableJdbcAuditing
public class DataConfig {
    //返回当前认证用户
    @Bean
    AuditorAware<String> auditorAware(){
        return ()-> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                //用户已认证
                .filter(Authentication::isAuthenticated)
                //返回用户名
                .map(Authentication::getName);
    }
}
