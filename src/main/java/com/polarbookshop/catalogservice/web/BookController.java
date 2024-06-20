package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public Iterable<Book> get(){
        log.info("查询book列表");
        return bookService.viewBookList();
    }

    @GetMapping("{isbn}")
    public Book getByIsbn(@PathVariable String isbn){
        log.info("查询book详情，isbn：{}",isbn);
        return bookService.viewBookDetails(isbn);
    }

    /** 新增 */
    @PostMapping
    //新增成功，返回201
    @ResponseStatus(HttpStatus.CREATED)
    //@Valid注解用于触发record中校验逻辑
    public Book post(@RequestBody @Valid Book book){
        log.info("新增book：{}",book);
        return bookService.addBookToCatalog(book);
    }

    @DeleteMapping("{isbn}")
    //删除成功，返回204
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String isbn){
        log.info("删除book，isbn：{}",isbn);
        bookService.removeBookFromCatalog(isbn);
    }

    /**
     * 更新或新增
     */
    @PutMapping("{isbn}")
    public Book put(@PathVariable String isbn,@RequestBody @Valid Book book){
        log.info("保存book，isbn：{}，book：{}",isbn,book);
        return bookService.editBookDetails(isbn,book);
    }
}
