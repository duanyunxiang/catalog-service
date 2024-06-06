package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("property")
@AllArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @GetMapping("serverPort/{type}")
    public String getServerPort(@PathVariable String type){
        return propertyService.getServerPort(type);
    }
}
