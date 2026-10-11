package ru.yandex.practicum;

import java.io.Serial;

public class InitGameException extends Exception {

    @Serial
    private static final long serialVersionUID = -6213555210686014389L;

    public InitGameException(String message) {
        super(message);
    }

}
