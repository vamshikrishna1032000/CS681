package edu.umb.cs681.hw12;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ThreadSafeBankAccount2 implements BankAccount{
    private double balance = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition sufficientFundsCondition = lock.newCondition();
    private Condition belowUpperLimitFundsCondition = lock.newCondition();

    public void deposit(double amount){
        lock.lock();
        try{
            System.out.println("Lock obtained");
            System.out.println(Thread.currentThread().threadId() +
                    " (d): current balance: " + balance);
            while(balance >= 300){
                System.out.println(Thread.currentThread().threadId() +
                        " (d): await(): Balance exceeds the upper limit.");
                belowUpperLimitFundsCondition.await();
            }
            balance += amount;
            System.out.println(Thread.currentThread().threadId() +
                    " (d): new balance: " + balance);
            sufficientFundsCondition.signalAll();
        }
        catch (InterruptedException exception){
            exception.printStackTrace();
        }
        finally{
            lock.unlock();
            System.out.println("Lock released");
        }
    }

    public void withdraw(double amount){
        lock.lock();
        try{
            System.out.println("Lock obtained");
            System.out.println(Thread.currentThread().threadId() +
                    " (w): current balance: " + balance);
            while(balance <= 0){
                System.out.println(Thread.currentThread().threadId() +
                        " (w): await(): Insufficient funds");
                sufficientFundsCondition.await();
            }
            balance -= amount;
            System.out.println(Thread.currentThread().threadId() +
                    " (w): new balance: " + balance);
            belowUpperLimitFundsCondition.signalAll();
        }
        catch (InterruptedException exception){
            exception.printStackTrace();
        }
        finally{
            lock.unlock();
            System.out.println("Lock released");
        }
    }

    public double getBalance() { return this.balance; }


    public static void main(String[] args){
        ThreadSafeBankAccount2 bankAccount = new ThreadSafeBankAccount2();
        DepositRunnable deposit = new DepositRunnable(bankAccount);
        WithdrawRunnable withdraw = new WithdrawRunnable(bankAccount);
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            threads.add(new Thread(deposit));
            threads.add(new Thread(withdraw));
        }
        for(Thread t:threads){t.start();}
        try {
            Thread.sleep(3000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        deposit.stopRunning();
        withdraw.stopRunning();
        for(Thread t:threads){t.interrupt();}
        for(Thread t:threads){
            try {
                t.join();
            } catch(InterruptedException exception){
                exception.printStackTrace();
            }
        }
    }
    }
