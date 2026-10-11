package ru.yandex.practicum;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;

class WordleTest {

    protected static WordleDictionary dictionary;
    private static final WordleDictionaryLoader loader =  new WordleDictionaryLoader();

    @BeforeAll
    static void init() throws IOException {
        dictionary = loader.load("test/ru/yandex/practicum/words_ru_test.txt");
    }

}
