package com.polarbookshop.catalogservice.domain;

import com.polarbookshop.catalogservice.config.PolarProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * 属性获取方式
 */
@Service
public class PropertyService {
    @Autowired
    private Environment environment;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private PolarProperties polarProperties;

    public String getServerPort(String type){
        return switch (type) {
            case "1" -> environment.getProperty("server.port");
            case "2" -> serverPort;
            case "3" -> polarProperties.getGreeting();
            default -> throw new RuntimeException("不支持的type：" + type);
        };
    }
}
