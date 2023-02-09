package com.andywang.jms.decorator;

import java.util.Arrays;
import java.util.StringJoiner;

public class ToStringDecorator<T> {

    public ToStringDecorator(T object) {
        this.object = object;
    }

    T object;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "{", "}");
        Arrays.stream(object.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            try {
                sj.add(field.getName() + ":" + field.get(object));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        return object.getClass().getSimpleName() + sj;
    }
}
