package ru.yandex.practicum;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * В главном классе нам нужно:
 * создать лог-файл (он должен передаваться во все классы),
 * создать загрузчик словарей WordleDictionaryLoader,
 * загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader,
 * создать игру WordleGame и передать ей словарь,
 * вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру,
 * вывести состояние игры и конечный результат.
 */
public class Wordle {

    public static final PrintWriter LOG;

    //Создаем лог файл
    static {
        try {
            Path path = Paths.get("log.txt");
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
            LOG = new PrintWriter(path.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        WordleGame game = new WordleGame();
        try (LOG) {
            LOG.println("Старт приложения Wordle");
            LOG.println("Инициализация загрузчика словаря");
            WordleDictionaryLoader loader = new WordleDictionaryLoader();
            LOG.println("Инициализация загрузчика словаря завершена");
            WordleDictionary dictionary = loader.load("words_ru.txt");
            LOG.println("Инициализация игоровой сессии");
            game.init(dictionary);
            LOG.println("Инициализация игоровой сессии завершена");
            startGame(game);
        } catch (InitGameException e) {
            LOG.println("Произошла неигровая ошибка!" + e.getMessage());
            System.out.println("Игра будет прервана!");
        } catch (GameSessionException e) {
            LOG.println("Произошла игровая ошибка! " + e.getMessage());
            LOG.println("Текущее состояние игры - ");
            game.printCurrentCondition();
            System.out.println("Игра будет прервана!");
        }   catch (IOException e) {
            LOG.println("Ошибка чтения файла со словарем");
            System.out.println("Игра будет прервана!");
        }
    }

    private static void startGame(WordleGame game) throws GameSessionException {
        Wordle.LOG.println("Загадывание слова");
        String chosenWord = game.getAnswer();
        Wordle.LOG.println("Загаданное слов - " + chosenWord);
        System.out.println("Попробуйте угадать загаданное слово");
        while (game.hasSteps()) {
            Scanner scanner = new Scanner(System.in);
            String in = scanner.nextLine();
            String word;
            if (in.isEmpty()) {
                word = game.getClue();
                System.out.println(word);
            } else {
                word = WordleWordValidator.prepareForGame(in);
            }
            try {
                if (game.exists(word)) {
                    if (chosenWord.equals(word)) {
                        System.out.println("Вы угадали!");
                        return;
                    }
                    System.out.println(game.doStep(word));
                }
            } catch (WordNotFoundException e) {
                System.out.println("Предложенное слово некорректно или отсутствует в словаре. Попытка не засчитана");
            }
            if (!game.hasSteps()) {
                System.out.println("Сколько хотите добавить попыток?");
                int steps = scanner.nextInt();
                game.addSteps(steps);
            }
        }
        System.out.println("К сожалению вы проиграли, загаданное слово - " + game.getAnswer());
    }

}
