package com.polarbookshop.catalogservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@Configuration
// 启用持久化实体审计
@EnableJdbcAuditing
public class DataConfig {

}
