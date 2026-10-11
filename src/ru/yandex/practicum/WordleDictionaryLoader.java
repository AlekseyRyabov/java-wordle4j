package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Objects;

/**
* Этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками.
 * Ему нужны методы по загрузке списка слов из файла по имени файла.
 * На выходе должен быть класс WordleDictionary.
 */
public class WordleDictionaryLoader {

    public WordleDictionaryLoader() {}

    /**
     * Загрузка словаря подходящих для игры слов из исходного файла.
     *
     * @param fileName исходный файл
     * @return заполенный словарь
     * @throws IOException ошибка чтения файла
     */
    public WordleDictionary load(String fileName) throws IOException {
        Wordle.LOG.println("Загрузка словаря");
        WordleDictionary dictionary = new WordleDictionary();
        try(BufferedReader br = new BufferedReader(new FileReader(Paths.get(fileName).toString(), StandardCharsets.UTF_8))) {
            String word;
            while (Objects.nonNull(word = br.readLine())) {
                if (WordleWordValidator.isValid(word)) {
                    Wordle.LOG.println("Добавлено в словарь - " + word);
                    dictionary.addWord(WordleWordValidator.prepareForGame(word));
                }
                Wordle.LOG.println("Не подходит для игры - " + word);
            }
        }
        dictionary.setWordsSet();
        Wordle.LOG.println("Загрузка словаря завершена");
        return dictionary;
    }
}
