
//TIP Задача 2: O (Open/Closed)
// Проблема: Чтобы добавить новый способ оплаты,
// нам нужно лезть внутрь метода processPayment и
// добавлять новый if или case.
// ПЛОХО: Класс закрыт для расширения без модификации своего кода.

interface PaymentMethod {

    // Каждый способ оплаты обязан реализовать этот метод
    void pay(double amount);
}

class CreditCardPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println("Оплата картой на сумму: " + amount);
    }
}

class PayPalPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println("Оплата через PayPal на сумму: " + amount);
    }
}

class PaymentService {

    // Теперь метод принимает интерфейс, а не строку
    public void processPayment(PaymentMethod paymentMethod, double amount) {
        paymentMethod.pay(amount);
    }
}