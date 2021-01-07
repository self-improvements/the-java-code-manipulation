package io.github.imsejin.service;

import io.github.imsejin.annotation.Inject;
import io.github.imsejin.model.Book;
import io.github.imsejin.repo.BookRepository;

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
