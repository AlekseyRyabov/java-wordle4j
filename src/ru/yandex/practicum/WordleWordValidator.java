package ru.yandex.practicum;

import java.util.regex.Pattern;
import org.junit.platform.commons.util.StringUtils;

/**
 * Валидатор для выбора подходящих для игры слов.
 */
public class WordleWordValidator {

    private WordleWordValidator() {

    }

    /**
     * Длина слова.
     */
    private static final int WORD_LENGTH = 5;

    /**
     * Регекс для русских слов.
     */
    private static final Pattern RUSSIAN_WORD = Pattern.compile("[А-Яа-яЁё]+");

    /**
     * Проверяем, что слово подходит для игры.
     *
     * @param word слово из словаря
     * @return true - если подходит
     */
    public static boolean isValid(String word) {
        return StringUtils.isNotBlank(word)
            && StringUtils.doesNotContainWhitespace(word)
            && word.length() == WORD_LENGTH
            && RUSSIAN_WORD.matcher(word).matches();
    }

    /**
     * Приведение подходящего слова к требованиям игры.
     * @param word подходящее слово из словаря
     * @return приведенное к требованиям игры слово
     */
    public static String prepareForGame(String word) {
        return word.trim().toLowerCase().replace("ё", "е");
    }

}
