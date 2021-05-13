package main.java.ua.goit.hw11;

public class FizzBuzz {

    void fizz(int nummer) {
        if (nummer % 3 == 0 && nummer % 5 != 0) System.out.print("fizz" + ", ");
    }

    void buzz(int nummer) {
        if (nummer % 5 == 0 && nummer % 3 != 0) System.out.print("buzz" + ", ");
    }

    void fizzbuzz(int nummer) {
        if (nummer % 3 == 0 && nummer % 5 == 0) System.out.print("fizzbuzz" + ", ");
    }

    void number(int nummer) {
        if (nummer % 3 != 0) {
            if(nummer % 5 != 0)
                System.out.print(Integer.toString(nummer) + ", ");
        }
    }
}

class MyThread1 implements Runnable {
    Thread thrd;
    FizzBuzz fbObj;

    // construct a new thread
    MyThread1(String name, FizzBuzz fb) {
        thrd = new Thread(this, name);
        fbObj = fb;
    }

    // a factory method to create and start a thread
    public static MyThread1 createAndStart(String name, FizzBuzz fb) {
        MyThread1 myThrd = new MyThread1(name, fb);
        myThrd.thrd.start();
        return myThrd;
    }

    // entry point of thread
    public void run() {
        for (int i = 1; i < 15; i++) {
            synchronized (fbObj) {
                if (thrd.getName().compareTo("Number") == 0) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    fbObj.number(i);}
                if (thrd.getName().compareTo("Buzz") == 0) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    fbObj.buzz(i);}
                if (thrd.getName().compareTo("FizzBuzz") == 0) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    fbObj.fizzbuzz(i);}
                if (thrd.getName().compareTo("Fizz") == 0) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    fbObj.fizz(i);}
            }
        }
    }
}

class Aufgabe_hw11_2 {
    public static void main(String[] args) {
        FizzBuzz fb = new FizzBuzz();
        MyThread1 mt4 = MyThread1.createAndStart("Number", fb);
        MyThread1 mt1 = MyThread1.createAndStart("Fizz", fb);
        MyThread1 mt2 = MyThread1.createAndStart("Buzz", fb);
        MyThread1 mt3 = MyThread1.createAndStart("FizzBuzz", fb);


        try {
            mt1.thrd.join();
            mt2.thrd.join();
            mt3.thrd.join();
            mt4.thrd.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
