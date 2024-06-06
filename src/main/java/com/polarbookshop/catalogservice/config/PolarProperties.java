package com.polarbookshop.catalogservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 定义自定义属性
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "polar")
public class PolarProperties {
    private String greeting;

    private Testdata testdata;

    @Getter
    @Setter
    static class Testdata {
        private Boolean enabled;
    }
}
