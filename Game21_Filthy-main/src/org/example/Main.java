package org.example;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
// ================== КЛАСС ИГРЫ ==================
class Game {
    // Колода карт
    private final Deck deck = new Deck();
    // Scanner для чтения ввода пользователя
    private final Scanner scanner = new Scanner(System.in);
    // Текущая сумма очков игрока
    private int totalScore = 0;
    // Основной метод запуска игры
    public void start() {
        System.out.println("=== Игра 21 ===");
        // Игрок получает две стартовые карты
        drawAndShowCard();
        drawAndShowCard();
        // Игра продолжается, пока сумма меньше 21
        while (totalScore < 21) {
            System.out.println("Очков = " + totalScore);
            System.out.print("Ещё? (y/n): ");
            // Читаем ответ пользователя
            String answer = scanner.nextLine();
            // Если игрок не хочет продолжать — выходим из цикла
            if (!answer.equalsIgnoreCase("y")) {
                break;
            }
            // Тянем новую карту
            drawAndShowCard();
            // Проверка на проигрыш
            if (totalScore > 21) {
                System.out.println("Ты проиграл!");
                return;
            }
            // Проверка на победу
            if (totalScore == 21) {
                System.out.println("Ты выиграл!");
                return;
            }
        }
        // Если игрок остановился сам
        System.out.println("Игра окончена. Итог: " + totalScore);
    }
    // Метод вытягивания карты и добавления очков
    private void drawAndShowCard() {
        // Получаем случайную карту из колоды
        Card card = deck.drawCard();
        // Если карты закончились
        if (card == null) {
            System.out.println("Колода закончилась!");
            return;
        }
        // Выводим карту
        System.out.println("Выпала карта: " + card);
        // Добавляем её значение к общей сумме
        totalScore += card.getValue();
    }
}
// ================== КЛАСС КОЛОДЫ ==================
class Deck {
    // Список всех карт в колоде
    private final List<Card> cards = new ArrayList<>();
    // Генератор случайных чисел
    private final Random random = new Random();
    // Конструктор создаёт и заполняет колоду
    public Deck() {
        // Масти
        String[] suits = {"крести", "треф", "буби", "пики"};
        // Значения карт (очки)
        int[] values = {6, 7, 8, 9, 10, 4, 3, 2, 1};
        // Названия карт
        String[] names = {"шесть", "семь", "восемь", "девять",
                "десять", "король", "дама", "валет", "туз"};
        // Создание 36 карт (4 масти × 9 значений)
        for (String suit : suits) {
            for (int i = 0; i < values.length; i++) {

                cards.add(new Card(
                        values[i] + " очков, " + names[i] + ", " + suit,
                        values[i]
                ));
            }
        }
    }
    // Метод вытягивания случайной карты
    public Card drawCard() {
        // Если колода пуста
        if (cards.isEmpty()) return null;
        // Случайный индекс
        int index = random.nextInt(cards.size());
        // Удаляем карту из колоды и возвращаем её
        return cards.remove(index);
    }
}
// ================== КЛАСС КАРТЫ ==================
class Card {
    private final String name;
    private final int value;
    public Card(String name, int value) {
        this.name = name;
        this.value = value;
    }
    // Получение значения карты
    public int getValue() {
        return value;
    }
    @Override // Переопределение метода toString, Теперь при выводе объекта будет печататься название карты
    public String toString() {
        return name;
    }
}