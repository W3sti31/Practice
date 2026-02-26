import java.util.*;

public class Game2 {
    public static final int TARGET_SCORE = 21;
    public static List<String> deck = new ArrayList<>();
    public static List<Integer> deckValues = new ArrayList<>();

    public static int player1Score = 0;
    public static int player2Score = 0;
    public static String player1Cards = "";
    public static String player2Cards = "";

    private static Scanner scanner;

    //ИНИЦИАЛИЗАЦИЯ И ЗАПУСК
    public static void main(String[] args) {
        initDeck(); // Подготовка колоды
        scanner = new Scanner(System.in);
        dealInitialCards(); // Раздача первых карт

        // Запуск ходов для каждого игрока
        System.out.println("ХОД ПЕРВОГО ИГРОКА");
        processPlayer1Turn();

        System.out.println("\nХОД ВТОРОГО ИГРОКА");
        processPlayer2Turn();

        printResult(); // Итоги
    }

    //ЛОГИЧЕСКИЕ БЛОКИ (МЕТОДЫ)

    // Блок 1: Работа с колодой
    private static void initDeck() {
        String[] suits = {"♥️", "♦️", "♣️", "♠️"};
        String[] ranks = {"6", "7", "8", "9", "10", "В", "Д", "К", "Т"};
        int[] values = {6, 7, 8, 9, 10, 2, 3, 4, 11};

        for (String s : suits) {
            for (int i = 0; i < ranks.length; i++) {
                deck.add(ranks[i] + " " + s);
                deckValues.add(values[i]);
            }
        }
        Collections.shuffle(deck);
    }

    // Блок 2: Раздача карт
    private static void dealInitialCards() {
        for(int i = 0; i < 2; i++) {
            drawCardForPlayer1();
            drawCardForPlayer2();
        }
    }

    // Блок 3: Игровая логика (Ходы)
    // Разделил на конкретные методы, так как в Java примитивы (int) передаются по значению,
    // и изменение player1Score внутри общего метода не меняло бы глобальную переменную.
    private static void processPlayer1Turn() {
        while (player1Score <= TARGET_SCORE) {
            System.out.println("Игрок 1: " + player1Cards + "(" + player1Score + "). Еще? 1-да");
            if (scanner.nextLine().equals("1")) {
                drawCardForPlayer1();
            } else break;
        }
    }

    private static void processPlayer2Turn() {
        while (player2Score <= TARGET_SCORE) {
            System.out.println("Игрок 2: " + player2Cards + "(" + player2Score + "). Еще? 1-да");
            if (scanner.nextLine().equals("1")) {
                drawCardForPlayer2();
            } else break;
        }
    }

    // Блок 4: Вспомогательные действия
    private static void drawCardForPlayer1() {
        int r = new Random().nextInt(deck.size());
        player1Cards += deck.remove(r) + " ";
        player1Score += deckValues.remove(r);
    }

    private static void drawCardForPlayer2() {
        int r = new Random().nextInt(deck.size());
        player2Cards += deck.remove(r) + " ";
        player2Score += deckValues.remove(r);
    }

    // Блок 5: Вывод результата
    public static void printResult() {
        System.out.println("\nИТОГИ");
        if (player1Score > TARGET_SCORE) System.out.println("Игрок 1 перебор! П2 победил");
        else if (player2Score > TARGET_SCORE || player1Score > player2Score) System.out.println("П1 победил!");
        else if (player1Score == player2Score) System.out.println("Ничья!");
        else System.out.println("П2 победил!");
    }
}
