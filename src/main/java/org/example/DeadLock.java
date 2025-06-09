package org.example;

/**
 * Класс вызывает взаимную блокировку.
 * thread1 блокирует fromAccount, thread2 блокирует toAccount.
 * Оба потока бесконечно ждут друг друга, что приводит к deadlock.
 */
public class DeadLock {
    private static Account fromAccount = new Account(1000);
    private static Account toAccount = new Account(2000);

    public static void main(String[] args) throws InterruptedException {
        DeadLock deadLock = new DeadLock();

        Thread thread1 = new Thread(() -> {
            synchronized (fromAccount) {
                deadLock.transferMoney(fromAccount, toAccount, 500);
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (toAccount) {
                deadLock.transferMoney(toAccount, fromAccount, 500);
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Баланс аккаунта, с которого вывели денежные средства: " + fromAccount.getBalance());
        System.out.println("Баланс аккаунта, куда поступили денежные средства: " + toAccount.getBalance());
    }

    public void transferMoney(Account fromAccount, Account toAccount, double amount) {
        if (fromAccount.getBalance() < amount) {
            throw new NotEnoughFundsException();
        }
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }
}