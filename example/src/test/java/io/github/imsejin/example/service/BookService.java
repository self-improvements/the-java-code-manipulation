package io.github.imsejin.example.service;

import io.github.imsejin.example.annotation.Inject;
import io.github.imsejin.example.model.Book;
import io.github.imsejin.example.repo.BookRepository;

public class BookService {

    @Inject
    private BookRepository repository;

    public BookRepository getRepository() {
        return repository;
    }

    public Book lend() {
        return new Book();
    }

    public void giveBack(Book book) {
        System.out.println("Successfully returned!");
    }

}
