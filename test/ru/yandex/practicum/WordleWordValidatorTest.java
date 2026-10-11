package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class WordleWordValidatorTest {

    @ParameterizedTest
    @CsvSource(value = {
        "Пилот;true",
        "котёл;true",
        "сапоги;false",
        "сало;false",
        "и что;false",
        "null;false",
        "demon;false"
    }, delimiter = ';')
    void isValidTest(String word, boolean result) {
        Assertions.assertEquals(result, WordleWordValidator.isValid(word));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "Пилот;пилот",
        "котёл;котел",
        "САПЁР;сапер",
        "пОчКа;почка"
    }, delimiter = ';')
    void prepareForGameTest(String word, String result) {
        Assertions.assertEquals(result, WordleWordValidator.prepareForGame(word));
    }

}