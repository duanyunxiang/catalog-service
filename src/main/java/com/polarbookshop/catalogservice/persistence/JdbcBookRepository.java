package com.polarbookshop.catalogservice.persistence;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * spring data负责实现
 */
public interface JdbcBookRepository extends BookRepository, CrudRepository<Book,Long> {
    // spring data在运行时要实现的方法
    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);

    // 标识该方法在事务中执行
    @Transactional
    @Modifying
    @Query("delete from book where isbn=:isbn")
    void deleteByIsbn(String isbn);
}
