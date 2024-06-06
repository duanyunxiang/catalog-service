package com.polarbookshop.catalogservice.demo;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
//testdata profile处于激活状态时，才加载该类
@Profile("testdata")
//@Profile替换成@ConditionalOnProperty控制形式
//@ConditionalOnProperty(name="polar.testdata.enabled",havingValue = "true")
@AllArgsConstructor
public class BookDataLoader {
    private final BookRepository bookRepository;

    //应用启动完成事件触发
    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData(){
        var book1=new Book("1234567891","Northern Lights","Lyra Silverstar",9.90);
        var book2=new Book("1234567892","Polar Journey","Iorek Polarson",12.90);

        bookRepository.save(book1);
        bookRepository.save(book2);
        log.info("testdata环境：初始数据加载完成");
    }
}
