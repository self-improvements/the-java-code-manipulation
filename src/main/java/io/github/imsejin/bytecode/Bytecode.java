package io.github.imsejin.bytecode;

import io.github.imsejin.bytecode.model.Book;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;

import java.io.File;
import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * 바이트코드를 조작하는 여러가지 라이브러리
 *
 * @see <a href="https://asm.ow2.io/">ASM</a>
 * @see <a href="https://www.javassist.org/">Javassist</a>
 * @see <a href="https://bytebuddy.net/">ByteBuddy</a>
 */
public class Bytecode {

    public void play() throws IOException {
        Book book = new Book();

        ByteBuddy byteBuddy = new ByteBuddy();
        byteBuddy.redefine(Book.class).method(named("getContent"))
                .intercept(FixedValue.value("This is a content."))
                .make().saveIn(new File("E:/Dropbox/repositories/study/the-java-code-manipulation", "target/classes/"));

        System.out.println(book.getContent());
    }

}
