package io.github.imsejin.service;

import io.github.imsejin.model.Book;
import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.*;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

public class BookServiceTest {

    private static final ByteBuddy byteBuddy = new ByteBuddy();

    @Test
    @DisplayName("ByteBuddy로 클래스의 프록시를 만들기")
    @SneakyThrows
    void createProxyOfClassWithByteBuddy() {
        // given
        Class<BookService> clazz = BookService.class;

        // when
        Class<? extends BookService> proxyClass = byteBuddy.subclass(clazz)
                .make().load(clazz.getClassLoader()).getLoaded();
        BookService bookService = proxyClass.getConstructor().newInstance();

        // then
        assertThat(bookService).isNotNull();
        Book book = bookService.lend();
        assertThat(book).isNotNull();
        bookService.giveBack(book);
    }

    @Test
    @DisplayName("CGlib으로 클래스의 프록시를 만들기")
    @SneakyThrows
    void createProxyOfClassWithCGlib() {
        // given
        Class<BookService> clazz = BookService.class;
        MethodInterceptor callback = new MethodInterceptor() {
            private final BookService bookService = new BookService();

            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return method.invoke(bookService, objects);
            }
        };

        // when
        BookService bookService = (BookService) Enhancer.create(clazz, callback);

        // then
        assertThat(bookService).isNotNull();
        Book book = bookService.lend();
        assertThat(book).isNotNull();
        bookService.giveBack(book);
    }

}
