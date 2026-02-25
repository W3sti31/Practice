//TIP Задача 4: I (Interface Segregation)
// Проблема: Интерфейс «Умный дом» заставляет простую лампочку уметь открывать двери.

interface Switchable {
    void turnOn();
    void turnOff();
}
interface Lockable {
    void openDoor();
    void closeDoor();
}
class LightBulb implements Switchable {

    @Override
    public void turnOn() {
        System.out.println("Лампочка включена");
    }

    @Override
    public void turnOff() {
        System.out.println("Лампочка выключена");
    }
}
class SmartLock implements Lockable {

    @Override
    public void openDoor() {
        System.out.println("Дверь открыта");
    }

    @Override
    public void closeDoor() {
        System.out.println("Дверь закрыта");
    }
}
class SmartHomeHub implements Switchable, Lockable {

    @Override
    public void turnOn() {
        System.out.println("Система включена");
    }

    @Override
    public void turnOff() {
        System.out.println("Система выключена");
    }

    @Override
    public void openDoor() {
        System.out.println("Дверь открыта через хаб");
    }

    @Override
    public void closeDoor() {
        System.out.println("Дверь закрыта через хаб");
    }
}