/*Volodymyr Dinul
* GOIT
* Hausaufgaben 11
* Aufgabe #1
* Напишите программу, которая каждую секунду отображает на экране данные о времени, прошедшем от начала сессии (запуска программы).
* Другой ее поток выводит каждые 5 секунд сообщение "Прошло 5 секунд". Предусмотрите возможность ежесекундного оповещения потока,
* воспроизводящего сообщение, потоком, отсчитывающим время.
*
* LONG LEAVE HERBERT SCHILDT !!*/

package main.java.ua.goit.hw11;

public class Sekunden {

    public int segundos = 0;    // variable that counts the second that have elapsed since
                                // the program started running

    /*Variable state accepts two values:
    * "corriente" indicates that the execution is now in the reloj() method.
    * "cinco" indicates that the execution is in the fünfSekunden.*/
    String state;

    /*Class Sekunded contains two methods, reloj & fünfSekunden.
    * Method reloj() deals with counting out the seconds the program has been running.
    * Method fünfSekunden() checks whether the number of seconds elapsed is modulus of five.
    * reloj() and fünfSekunden() communicate with each other so that fünfSekunden() always
    * follows reloj() and vise versa.
    * */

    /*Variable running is needed to ensure a clean termination of the program.
    * The program runs until 'running' is true.
    * Correspondingly, the program ends when 'running' is false.
    * In this particular case I have set the program to run for 20 seconds.*/

    synchronized void reloj (boolean running) {
        if(!running) {              // this if block checks whether 'running' is still true
            state = "corriente";
            notify();
            return;
        }
        try {
            Thread.sleep(1000); // counting out the seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        segundos ++;
        System.out.println("The number of seconds elapsed since the program has started running is " +
                segundos);
        state = "corriente";
        notify();  // give the control of the program to fünfSekunden()

        try {
            while (!state.equals("cinco")) //wait until fünfSekunden() executes and returns the control to reloj()
                wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // the fünfSekunden() is symmetrical to reloj()
    synchronized void funfSekunden(boolean running) {
        if(!running) {
            state = "cinco";
            notify(); // notify all waiting threads
            return;
        }

        if(segundos % 5 == 0) {
            System.out.println("Five seconds have elapsed.");
        }
        state = "cinco";
        notify(); // let reloj() run

        try {
            while(!state.equals("corriente"))
                wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/*The threads are based on objects of type MyThread.
* The constructor and createAndStart method are passed the names of the tread
* which are "Reloj" and "Cinco" in our case, and a reference to the Sekunden object, seks.
* Inside the run() method if the name of the thread is "Reloj", then the call to reloj() is made.
* If the name of the thread is "Cinco", then the fünfSekunden() is called.
* The run() method makes 20 rounds equalling to 20 seconds passing 'true' to reloj() and fünfSekunden().
* The final round that passes 'false' to each method stops the execution. */

class MyThread implements Runnable {
    Thread thrd;
    Sekunden sekObj;

    // construct a new thread
    MyThread(String name, Sekunden seks) {
        thrd = new Thread(this, name);
        sekObj = seks;
    }

    // a factory method that creates and starts a thread
    public static MyThread createAndStart(String name, Sekunden seks) {
        MyThread myThrd = new MyThread(name, seks);
        myThrd.thrd.start(); // start the thread
        return myThrd;
    }

    // entry point of the thread
    public void run() {
        if(thrd.getName().compareTo("Reloj") == 0) {
            for(int i = 0; i < 20; i++) sekObj.reloj(true);
            sekObj.reloj(false);
        }
        else {
            for (int i = 0; i < 20; i++) sekObj.funfSekunden(true);
            sekObj.funfSekunden(false);
        }
    }
}

class Aufgabe_hw11_1 {
    public static void main(String[] args) {
        Sekunden seks = new Sekunden();
        MyThread mt1 = MyThread.createAndStart("Reloj", seks);
        MyThread mt2 = MyThread.createAndStart("Cinco", seks);

        try {
            mt1.thrd.join();
            mt2.thrd.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
