package com.polarbookshop.catalogservice.persistence;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

//@Repository
@ConditionalOnProperty(name="polar.enableMemRepo",havingValue = "true")
public class InMemoryBookRepository implements BookRepository {
    //将图书存在内存中，便于测试
    private static final Map<String,Book> books=new ConcurrentHashMap<>();

    @Override
    public Iterable<Book> findAll() {
        return books.values();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return this.existsByIsbn(isbn)?Optional.of(books.get(isbn)):Optional.empty();
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return books.get(isbn)!=null;
    }

    @Override
    public Book save(Book book) {
        books.put(book.isbn(),book);
        return book;
    }

    @Override
    public void deleteByIsbn(String isbn) {
        books.remove(isbn);
    }
}
