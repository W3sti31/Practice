//TIP Задача 5: D (Dependency Inversion)
// Проблема: Высокоуровневая кнопка жестко привязана к конкретной лампе.
// Мы не можем использовать эту кнопку для включения вентилятора.

interface SwitchableDevice {
    void turnOn();
}
class Lamp implements SwitchableDevice {
    @Override
    public void turnOn() {
        System.out.println("Свет горит");
    }
}
class Fan implements SwitchableDevice {
    @Override
    public void turnOn() {
        System.out.println("Вентилятор работает");
    }
}
class Button {
    private final SwitchableDevice device;
    // Внедрение зависимости через конструктор (Dependency Injection)
    public Button(SwitchableDevice device) {
        this.device = device;
    }
    public void press() {
        device.turnOn();
    }
}