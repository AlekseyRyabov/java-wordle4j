package ru.yandex.practicum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class WordleDictionaryTest extends WordleTest {

    @AfterEach
    void refresh() {
        dictionary.refreshChars();
    }

    @ParameterizedTest
    @CsvSource(value = {
        "актер;^^+++;катер;актер",
        "топор;^+^+^;ропот;топор"
    }, delimiter = ';')
    void getClueTest(String answer, String scheme, String currentWord, String result) {
        dictionary.delete(currentWord);
        Assertions.assertEquals(result, dictionary.getClue(answer, scheme, currentWord));
    }
}