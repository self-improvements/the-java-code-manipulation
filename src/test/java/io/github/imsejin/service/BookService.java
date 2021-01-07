package io.github.imsejin.service;

import io.github.imsejin.annotation.Inject;
import io.github.imsejin.repo.BookRepository;

public class BookService {

    @Inject
    private BookRepository repository;

    public BookRepository getRepository() {
        return repository;
    }

}
