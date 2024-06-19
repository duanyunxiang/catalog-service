package com.polarbookshop.catalogservice.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Iterable<Book> viewBookList(){
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn){
        return bookRepository.findByIsbn(isbn).orElseThrow(()->new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book){
        if(bookRepository.existsByIsbn(book.isbn())){
            throw new BookAlreadyExistsException(book.isbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn){
        bookRepository.deleteByIsbn(isbn);
    }

    /**
     * 编辑图书
     */
    public Book editBookDetails(String isbn,Book book){
        return bookRepository.findByIsbn(isbn)
                //修改
                .map(existBook ->{
                    var bookToUpdate=new Book(existBook.id(),
                            existBook.isbn(), book.title(), book.author(), book.price(),book.publisher(),
                            existBook.createDate(), existBook.lastModifiedDate(),
                            existBook.createdBy(), existBook.lastModifiedBy(), existBook.version());
                    return bookRepository.save(bookToUpdate);
                })
                //新增
                .orElseGet(()->addBookToCatalog(book));
    }
}
