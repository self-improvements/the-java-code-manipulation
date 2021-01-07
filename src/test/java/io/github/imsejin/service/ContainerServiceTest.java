package io.github.imsejin.service;

import io.github.imsejin.repo.BookRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContainerServiceTest {

    @Test
    void getBookRepository() {
        // given
        BookRepository bookRepository = ContainerService.getObject(BookRepository.class);

        // then
        assertThat(bookRepository).isNotNull();
    }

    @Test
    void getBookService() {
        // given
        BookService bookService = ContainerService.getObject(BookService.class);

        // then
        assertThat(bookService).isNotNull();
        assertThat(bookService.getRepository()).isNotNull();
    }

}