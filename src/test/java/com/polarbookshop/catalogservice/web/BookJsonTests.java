package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
public class BookJsonTests {
    //json序列化和反序列化的工具类
    @Autowired
    private JacksonTester<Book> jsonTool;

    private final Book book=Book.of("1234567890","Title","Author",9.90);

    @Test
    void testSerialize() throws Exception {
        var jsonContent= jsonTool.write(book);
        //@.isbn为jsonPath格式取属性
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        Assertions.assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
    }

    @Test
    void testDeserialize() throws Exception {
        var content= """
                {
                "isbn":"1234567890",
                "title":"Title",
                "author":"Author",
                "price":9.90
                }
                """;
        Assertions.assertThat(jsonTool.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(book);
    }
}
