Для того чтобы код стал по-настоящему «чистым», мы разделим его на три логические части: **Данные** (Карта), **Хранилище** (Колода) и **Процесс** (Логика игры).
---

### 1. Класс `Card` (Модель данных)

Этот класс заменяет разрозненные массивы. Теперь имя и значение карты связаны неразрывно.

```java
public class Card {
    private final String name;
    private final int value;

    public Card(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name; // Теперь карту можно просто напечатать
    }
}

```

---

### 2. Класс `Deck` (Управление колодой)

Этот класс инкапсулирует в себе создание карт и логику их выдачи. Обрати внимание, как решается проблема «вытянутой карты» через список.

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        String[] suits = {"крести", "треф", "буби", "пики"};
        String[] ranks = {"шесть", "семь", "восемь", "девять", "десять", "король", "дама", "валет", "туз"};
        int[] values = {6, 7, 8, 9, 10, 4, 3, 2, 11}; // Исправил Туз на 11 (классика 21)

        // Автоматическое заполнение колоды вместо ручного ввода 36 строк
        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(ranks[i] + " " + suit, values[i]));
            }
        }
        // Перемешиваем колоду сразу — это избавляет от сложных циклов с Random позже
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) return null;
        return cards.remove(0); // Берем верхнюю карту и удаляем её из колоды
    }
}

```

---

### 3. Класс `BlackjackGame` (Логика процесса)

Здесь мы убираем бесконечные вложенные `if` и заменяем их на аккуратный цикл.

```java
import java.util.Scanner;

public class BlackjackGame {
    private final Deck deck = new Deck();
    private final Scanner scanner = new Scanner(System.in);
    private int totalScore = 0;

    public void start() {
        // Начальная раздача: две карты
        drawNextCard();
        drawNextCard();

        while (totalScore < 21) {
            System.out.println("Ваши очки: " + totalScore + ". Хотите ещё? (y/n)");
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("y")) {
                drawNextCard();
            } else {
                break;
            }
        }

        checkResult();
    }

    private void drawNextCard() {
        Card card = deck.drawCard();
        if (card != null) {
            totalScore += card.getValue();
            System.out.println("Выпало: " + card);
        }
    }

    private void checkResult() {
        System.out.println("Итог: " + totalScore);
        if (totalScore == 21) {
            System.out.println("ТЫ выиграл!");
        } else if (totalScore > 21) {
            System.out.println("Ты проиграл (Перебор)!");
        } else {
            System.out.println("Вы остановились на " + totalScore);
        }
    }
}

```

---

### Основные изменения, которые увидит студент:

1. **Масштабируемость**: Теперь неважно, сколько карт тянет игрок — 3 или 10. Цикл `while` обработает любую ситуацию.
2. **Безопасность**: Больше нет риска получить `ArrayIndexOutOfBoundsException`, потому что мы работаем со списком объектов, а не с индексами массивов.
3. **Читаемость**: Метод `main` теперь состоит всего из двух строк:
```java
public static void main(String[] args) {
    new BlackjackGame().start();
}

```
