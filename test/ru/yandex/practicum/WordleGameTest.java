package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class WordleGameTest extends WordleTest {

    private static final WordleGame game = new WordleGame();

    @BeforeAll
    static void initGame() throws InitGameException {
        game.init(dictionary);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "рокот;true",
        "actor;false",
        "небо;false",
        "жирафы;false",
        "питер;false"
    }, delimiter = ';')
    void existsTest(String word, boolean result) throws WordNotFoundException {
        if (result) {
            Assertions.assertEquals(result, game.exists(word));
        } else {
            Assertions.assertThrows(WordNotFoundException.class, () -> game.exists(word));
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
        "актер;5;false",
        "топор;4;false"
    }, delimiter = ';')
    void doStep(String word, int resultSteps) throws GameSessionException {
        game.doStep(word);
        Assertions.assertEquals(resultSteps, game.getSteps());
        Assertions.assertThrows(WordNotFoundException.class, () -> game.exists(word));
    }

    @Test
    void initThrowTest() {
        Assertions.assertThrows(InitGameException.class, () -> game.init(null));
    }
}