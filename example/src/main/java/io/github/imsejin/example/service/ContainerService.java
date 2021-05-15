package io.github.imsejin.example.service;

import io.github.imsejin.example.annotation.Inject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ContainerService {

    public static <T> T getObject(Class<T> type) {
        T rootInstant = instantiate(type);
        for (Field field : type.getDeclaredFields()) {
            Inject annotation = field.getAnnotation(Inject.class);
            if (annotation == null) continue;

            Object childInstant = instantiate(field.getType());
            field.setAccessible(true);
            try {
                field.set(rootInstant, childInstant);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return rootInstant;
    }

    private static <T> T instantiate(Class<T> type) {
        try {
            return type.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
