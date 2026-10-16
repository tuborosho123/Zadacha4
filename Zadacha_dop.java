import java.util.concurrent.atomic.AtomicInteger;


public class Zadacha_dop {


    private static final int HUNDRED_MILLION = 100_000_000;


    private final AtomicInteger stopCounter = new AtomicInteger();

    private int sharedCounter = 0;


    boolean stop() {
        return stopCounter.incrementAndGet() > HUNDRED_MILLION;
    }


    synchronized void increment() {
        sharedCounter++;
    }

    int getCounter() {
        return sharedCounter;
    }


    private void runLoop() {
        while (!stop()) {
            increment();
        }
    }

    void example() throws InterruptedException {
        Thread thread1 = new Thread(this::runLoop);
        Thread thread2 = new Thread(this::runLoop);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Expected: " + HUNDRED_MILLION);
        System.out.println("Result:   " + getCounter());

        if (getCounter() == HUNDRED_MILLION) {
            System.out.println("Гонки данных нет — счётчик корректен.");
        } else {
            System.out.println("Обнаружена гонка данных!");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Zadacha_dop().example();
    }
}