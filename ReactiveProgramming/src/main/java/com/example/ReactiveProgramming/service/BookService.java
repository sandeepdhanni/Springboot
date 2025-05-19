package com.example.ReactiveProgramming.service;

import com.example.ReactiveProgramming.entity.Book;
import com.example.ReactiveProgramming.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class BookService {


    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Mono<Book> create(Book book){
        return bookRepository.save(book);
    }

    public Mono<Book> findByName(String name){
        return bookRepository.findByName(name);
    }

    public Flux<Book> getAllBooksByAuthor(String name,String author){
        return bookRepository.getAllBooksByAuthor(name,author);
    }

    public Flux<Book> getAll(){
        return bookRepository
                .findAll()
                .delayElements(Duration.ofSeconds(2))
                .log()
                .map(book -> {
                    book.setName(book.getName().toUpperCase());
                    return book;
                });
    }

    public Mono<Book> get(int bookId){
        Mono<Book> item = bookRepository.findById(bookId);
        return item;
    }

    public Mono<Book> update(Book book, int bookId){
        Mono<Book> oldBook = bookRepository.findById(bookId);
        return oldBook.flatMap(book1 -> {
            book1.setName(book.getName());
            book1.setPublisher(book.getPublisher());
            book1.setAuthor(book.getAuthor());
            book1.setDescription(book.getDescription());
            return bookRepository.save(book1);
        });
    }

    public Mono<Void> delete(int bookId){
        return bookRepository.findById(bookId).flatMap(book -> bookRepository.delete(book));

    }

    public Flux<Book> search(String query){
        return null;
    }

    public Flux<Book> searchBooks(String titleKeyword) {
        return this.bookRepository.searchBookByTitle("%" + titleKeyword + "%");
    }
}
