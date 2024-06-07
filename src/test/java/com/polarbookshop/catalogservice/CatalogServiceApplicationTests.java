package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * 集成测试（加载应用上下文）
 * RANDOM_PORT：监听随机端口
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//激活profile，使用Testcontainers
@ActiveProfiles("integration")
class CatalogServiceApplicationTests {
    //由webflux提供
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenPostRequestThenBookCreated(){
        var expectedBook=Book.of("1231231231","Title","Author",9.90);

        webTestClient
                //发送post请求
                .post().uri("/books").bodyValue(expectedBook)
                //发送
                .exchange()
                //期望响应码=201
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook->{
                    log.info("actualBook={}",actualBook);
                    //校验创建的对象符合预期
                    Assertions.assertThat(actualBook).isNotNull();
                    Assertions.assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
                });
        log.info("测试完成");
    }
}
