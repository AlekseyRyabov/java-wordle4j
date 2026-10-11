package ru.yandex.practicum;

import java.io.Serial;

public class GameSessionException extends Exception {

    @Serial
    private static final long serialVersionUID = -2274551987175721242L;

    public GameSessionException(String message) {
        super(message);
    }

}
