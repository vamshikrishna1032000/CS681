package edu.umb.cs681.hw12;

public class WithdrawRunnable implements Runnable {
    private ThreadSafeBankAccount2 bankAccount;
    private volatile boolean running = true;

    public WithdrawRunnable(ThreadSafeBankAccount2 bankAccount) {this.bankAccount = bankAccount;}

    public void stopRunning() {
        running = false;
    }

    public void run() {
        while(running) {
            bankAccount.withdraw(350);
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                System.out.println(e);
                return;
            }
        }
    }
}
