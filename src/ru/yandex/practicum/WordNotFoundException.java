package ru.yandex.practicum;

import java.io.Serial;

public class WordNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 513302186335061222L;

    public WordNotFoundException(String message) {
        super(message);
    }

}
