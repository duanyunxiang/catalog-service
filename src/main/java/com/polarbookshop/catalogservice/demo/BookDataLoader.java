package com.polarbookshop.catalogservice.demo;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.persistence.JdbcBookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
//testdata profile处于激活状态时，才加载该类
@Profile("testdata")
//@Profile替换成@ConditionalOnProperty控制形式
//@ConditionalOnProperty(name="polar.testdata.enabled",havingValue = "true")
@AllArgsConstructor
public class BookDataLoader {
    private final JdbcBookRepository jdbcBookRepository;

    //应用启动完成事件触发
    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData(){
        jdbcBookRepository.deleteAll();

        var book1=Book.of("1234567891","Northern Lights","Lyra Silverstar",9.90);
        var book2=Book.of("1234567892","Polar Journey","Iorek Polarson",12.90);
        jdbcBookRepository.saveAll(List.of(book1,book2));

        log.info("profile=testdata: data init end");
    }
}
