package ru.yandex.practicum;

/**
 * В этом классе хранится словарь и состояние игры:
 *     текущий шаг,
 *     всё, что пользователь вводил,
 *     правильный ответ.
 * В этом классе нужны методы, которые проанализируют совпадение слова с ответом,
 * предложат слово-подсказку с учётом всего, что вводил пользователь ранее.
 * Не забудьте про специальные типы исключений для игровых и неигровых ошибок.
 */
public class WordleGame {

    /**
     * Переменная для поиска совпадения букв в слове с ответом.
     */
    private static final StringBuilder ANSWER_TEMP = new StringBuilder();

    /**
     * Загаданное слово.
     */
    private String answer;

    /**
     * Текущее кол-во шагов.
     */
    private int steps;

    /**
     * Словарь.
     */
    private WordleDictionary dictionary;

    /**
     * Схема, сформированная по текущему шагу.
     */
    private String currentWordScheme;

    /**
     * Слово, проверяемое на текущем шаге.
     */
    private String currentWord;

    public WordleGame() {
    }

    public String getAnswer() {
        return answer;
    }

    public boolean hasSteps() {
        return steps != 0;
    }

    public int getSteps() {
        return steps;
    }

    /**
     * Метод, позволяющий добавить попыток к игре.
     * @param newSteps кол-во попыток
     */
    public void addSteps(int newSteps) {
         steps += newSteps;
         Wordle.LOG.println("Добавлено шагов к игре - " + newSteps);
    }

    /**
     * Метод, проверяющий валидность слова и его существование в словаре.
     * @param word проверяемое слово
     * @return true - если слово существует
     */
    public boolean exists(String word) throws WordNotFoundException {
        Wordle.LOG.println("Проверяем существование слова - " + word);
        if (WordleWordValidator.isValid(word) && dictionary.contains(word)) {
            return true;
        }
        Wordle.LOG.println("Введенное слово отсутствует в словаре - " + word);
        throw new WordNotFoundException("Введенное слово отсутствует в словаре");
    }

    /**
     * Основная обработка шага, преобразование текущего слова в схему подсказку.
     * @param word текущее слово
     * @return схема подсказка
     */
    public String doStep(String word) throws GameSessionException {
        try {
            currentWord = word;
            printCurrentCondition();
            StringBuilder result = new StringBuilder();
            ANSWER_TEMP.delete(0, Math.max(ANSWER_TEMP.length() - 1, 0));
            ANSWER_TEMP.append(answer);
            for (int i = 0; i < word.length(); i++) {
                char answerChar = answer.charAt(i);
                char wordChar = word.charAt(i);
                if (answerChar == wordChar) {
                    result.append("+");
                    ANSWER_TEMP.deleteCharAt(ANSWER_TEMP.toString().indexOf(answerChar));
                } else if (answer.indexOf(wordChar) != -1) {
                    result.append("^");
                    ANSWER_TEMP.deleteCharAt(ANSWER_TEMP.toString().indexOf(answerChar));
                } else {
                    result.append("-");
                }
            }
            steps--;
            dictionary.delete(word);
            currentWordScheme = result.toString();
            Wordle.LOG.println("Текущая схема - " + currentWordScheme);
            return currentWordScheme;
        } catch (Exception e) {
            e.printStackTrace(Wordle.LOG);
            throw new GameSessionException("Произошла ошибка при преобразовании слова в схему подсказку");
        }
    }

    public void printCurrentCondition() {
        Wordle.LOG.println("Текущее кол-во ходов - " + steps);
        Wordle.LOG.println("Текущее слово - " + currentWord);
        Wordle.LOG.println("Текущее кол-во слов в словаре - " + dictionary.getWordsSet().size());
    }

    /**
     * Инициализация игровой сессии
     * @param dictionary словарь
     */
    public void init(WordleDictionary dictionary) throws InitGameException {
        try {
            this.dictionary = dictionary;
            this.steps = 6;
            this.answer = dictionary.chooseWord();
            Wordle.LOG.println("Загаданное слово - " + answer);
        } catch (Exception e) {
            e.printStackTrace(Wordle.LOG);
            throw new InitGameException("Произошла ошибка при инициализации игровой сессии");
        }
    }

    /**
     * Метод, возвращающий подсказку
     * @return подсказка
     */
    public String getClue() throws GameSessionException {
        try {
            Wordle.LOG.println("Поиск подсказки по текущей схеме - " + currentWordScheme);
            return dictionary.getClue(answer, currentWordScheme, currentWord);
        } catch (Exception e) {
            e.printStackTrace(Wordle.LOG);
            throw new GameSessionException("Произошла ошибка при поиске подсказки");
        }
    }

}
