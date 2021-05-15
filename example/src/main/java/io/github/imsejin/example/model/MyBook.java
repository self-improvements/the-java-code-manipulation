package io.github.imsejin.example.model;

import io.github.imsejin.example.MyInterface;

public class MyBook extends Book implements MyInterface {

    @Override
    public String sell() {
        return "Sale this book";
    }

}
