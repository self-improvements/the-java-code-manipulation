package io.github.imsejin.model;

import io.github.imsejin.MyInterface;

public class MyBook extends Book implements MyInterface {

    @Override
    public String sell() {
        return "Sale this book";
    }

}
