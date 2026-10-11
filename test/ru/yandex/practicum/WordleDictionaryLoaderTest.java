package ru.yandex.practicum;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class WordleDictionaryLoaderTest {

    private final WordleDictionaryLoader loader = new WordleDictionaryLoader();

    @ParameterizedTest
    @CsvSource(value = {
        "test/ru/yandex/practicum/words_ru_test_1.txt;1",
        "test/ru/yandex/practicum/words_ru_test_2.txt;1",
    }, delimiter = ';')
    void loadTest(String filename, int resultSize) throws IOException {
        Assertions.assertEquals(resultSize, loader.load(filename).getWordsSet().size());
    }

    @Test
    void exceptionTest() {
        Assertions.assertThrows(IOException.class, () -> loader.load("test/ru/yandex/practicum/words_ru_test_3"));
    }

}