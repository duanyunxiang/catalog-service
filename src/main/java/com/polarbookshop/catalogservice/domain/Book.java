package com.polarbookshop.catalogservice.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.Instant;

/**
 * record Book
 * 领域实体和持久化实体未分开
 */
public record Book(
        // 逻辑主键
        @Id
        Long id,

        @NotBlank(message = "The book ISBN must be defined.")
        @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$",message = "The ISBN format must be valid.")
        String isbn,

        @NotBlank(message = "The book title must be defined.")
        String title,

        @NotBlank(message = "The book author must be defined.")
        String author,

        @NotNull(message = "The book price must be defined.")
        @Positive(message = "The book price must be grater than zero.")
        Double price,

        String publisher,

        //审计类字段；Instant是java时间戳类型，jdk11引入
        @CreatedDate
        Instant createDate,
        @LastModifiedDate
        Instant lastModifiedDate,

        @CreatedBy
        String createdBy,
        @LastModifiedBy
        String lastModifiedBy,

        //实体的版本号，用于实现乐观锁
        @Version
        int version
) {
        public static Book of(String isbn,String title,String author,Double price){
                return new Book(null,isbn,title,author,price,null,null,null,null,null,0);
        }
        public static Book of(String isbn,String title,String author,Double price,String publisher){
                //id和version由框架负责赋值
                return new Book(null,isbn,title,author,price,publisher,null,null,null,null,0);
        }
}
