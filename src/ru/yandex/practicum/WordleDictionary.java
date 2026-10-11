package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Этот класс содержит в себе список слов List<String>.
 * Его методы похожи на методы списка, но учитывают особенности игры.
 * Также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final List<String> words = new ArrayList<>();
    private final Set<String> wordsSet = new HashSet<>();
    private Set<Character> includeChars = new HashSet<>();
    private Set<Character> excludeChars = new HashSet<>();

    public WordleDictionary() {
    }

    /**
     * Добавление подходящего слова в словарь.
     * @param word слово
     */
    public void addWord(String word) {
        words.add(word);
    }

    public void setWordsSet() {
        wordsSet.addAll(words);
    }

    public Set<String> getWordsSet() {
        return wordsSet;
    }

    /**
     * Проверка, что введенное игроком слово существует в словаре.
     * @param word введенное слово
     * @return true - если присутствует
     */
    public boolean contains(String word) {
        return wordsSet.contains(word);
    }

    /**
     * Выбор случайного слова для игровой сессии.
     * @return случайное слово из словаря
     */
    public String chooseWord() {
        Collections.shuffle(words);
        return words.getFirst();
    }

    /**
     * Удаление использованных слов из текущего состояния словаря.
     * @param word удаленное слово
     */
    public void delete(String word) {
        Wordle.LOG.println("Удалено слово - " + word);
        wordsSet.remove(word);
    }

    /**
     * Метод, находящий подсказку для текущей схемы из текущего словаря.
     * @param answer загаданное слово
     * @param scheme текущая схема-подсказка
     * @param currentWord текущее введенное игроком слово
     * @return подсказка
     */
    public String getClue(String answer, String scheme, String currentWord) {
        List<String> clues = wordsSet.stream()
            .filter(word -> findWordByScheme(word, answer, scheme, currentWord))
            .collect(Collectors.toList());
        Collections.shuffle(clues);
        String result = clues.getFirst();
        Wordle.LOG.println("Слово подсказка - " + result);
        return result;
    }

    //Создание паттерна для фильтрации по словарю на основе текущего состояния игры
    private boolean findWordByScheme(String word, String answer, String scheme, String currentWord) {
        if (Objects.isNull(scheme)) {
            return true;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < answer.length(); i++) {
            char schemeChar = scheme.charAt(i);
            if (schemeChar == '+') {
                result.append(answer.charAt(i));
            } else if (schemeChar == '^') {
                result.append(".");
                includeChars.add(currentWord.charAt(i));
            } else {
                result.append(".");
                excludeChars.add(currentWord.charAt(i));
            }
        }
        //Здесь можно улучшить, учесть что нельзя ставить несовпавшие, но присутствующие буквы на уже использованные
        //места, но это сильно упростит решение. Также можно учесть повторяющиеся в слове буквы.
        return word.matches(result.toString())
            && includeChars.stream().allMatch(character -> word.indexOf(character) != -1)
            && excludeChars.stream().noneMatch(character -> word.indexOf(character) != -1);
    }

    public void refreshChars() {
        includeChars = new HashSet<>();
        excludeChars = new HashSet<>();
    }
}
