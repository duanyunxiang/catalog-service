package com.polarbookshop.catalogservice.domain;

import com.polarbookshop.catalogservice.config.DataConfig;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@Slf4j
//数据切片测试
@DataJdbcTest
//导入配置：启用审计功能
@Import(DataConfig.class)
//禁用依赖嵌入式数据库的默认行为，使用Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//激活profile
@ActiveProfiles("integration")
public class BookRepositoryJdbcTests {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Test
    void findBookByIsbnWhenExisting(){
        //初始化数据
        var isbn="1234561237";
        var book=Book.of(isbn,"Title","Author",12.90);
        jdbcAggregateTemplate.insert(book);

        Optional<Book> actualBook=bookRepository.findByIsbn(isbn);
        //断言存在
        Assertions.assertThat(actualBook).isPresent();
        Assertions.assertThat(actualBook.get().isbn()).isEqualTo(isbn);
        log.info("测试通过");
    }
}
