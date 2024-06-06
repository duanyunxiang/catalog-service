package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.BookNotFoundException;
import com.polarbookshop.catalogservice.domain.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 切片测试（加载需要的上下文）
 */
@WebMvcTest(BookController.class)
public class BookControllerMvcTests {
    //用于测试web端点，而不必加载tomcat服务器
    @Autowired
    private MockMvc mockMvc;
    //@MockBean注解创建的bean会包含到应用上下文中，所以会自动注入BookController中
    @MockBean
    private BookService bookService;

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn="73737313940";
        //定义mockBean的预期行为
        BDDMockito.given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException.class);

        //使用mockMvc执行http get请求，断言响应码为404
        mockMvc.perform(MockMvcRequestBuilders.get("/books/"+isbn)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
