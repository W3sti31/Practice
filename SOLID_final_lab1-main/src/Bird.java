//TIP Задача 3: L (Liskov Substitution)
// Проблема: У нас есть базовый класс «Птица».
// Мы создаем наследника «Страус», но страусы не летают.

class Bird {
    public void eat() {
        System.out.println("Птица ест");
    }
}
interface Flyable {
    void fly();
}
class Sparrow extends Bird implements Flyable {

    @Override
    public void fly() {
        System.out.println("Воробей летит!");
    }
}
class Ostrich extends Bird {
    public void run() {
        System.out.println("Страус бежит!");
    }
}