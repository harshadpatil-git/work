import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

class Buffer {
    private final int capacity;
    private final Queue<Integer> queue = new LinkedList<>();

    // Semaphores
    private final Semaphore mutex = new Semaphore(1);   // acts as mutual exclusion lock
    private final Semaphore empty;  // counts available empty slots
    private final Semaphore full;   // counts filled slots

    public Buffer(int capacity) {
        this.capacity = capacity;
        this.empty = new Semaphore(capacity); // initially all slots are empty
        this.full = new Semaphore(0);         // initially no item is produced
    }

    // Producer method
    public void produce(int value) throws InterruptedException {
        empty.acquire(); // wait if buffer is full
        mutex.acquire(); // enter critical section

        queue.add(value);
        System.out.println("Produced: " + value + " | Buffer: " + queue);

        mutex.release(); // exit critical section
        full.release();  // signal that buffer has one more filled slot
    }

    // Consumer method
    public void consume() throws InterruptedException {
        full.acquire();  // wait if buffer is empty
        mutex.acquire(); // enter critical section

        int value = queue.poll();
        System.out.println("Consumed: " + value + " | Buffer: " + queue);

        mutex.release(); // exit critical section
        empty.release(); // signal that one slot is now free
    }
}

class Producer extends Thread {
    private final Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        int value = 1;
        try {
            while (true) {
                buffer.produce(value++);
                Thread.sleep(500); // simulate production delay
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer extends Thread {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        try {
            while (true) {
                buffer.consume();
                Thread.sleep(800); // simulate consumption delay
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Semaphore1 {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(3); // buffer capacity = 3

        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        producer.start();
        consumer.start();
    }
}
