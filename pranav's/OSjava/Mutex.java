import java.util.LinkedList;
import java.util.Queue;

class Buffer {
    private final int capacity;
    private final Queue<Integer> queue = new LinkedList<>();
    private final Object mutex = new Object(); // acts as the mutex lock

    public Buffer(int capacity) {
        this.capacity = capacity;
    }

    // Producer adds items to the buffer
    public void produce(int value) throws InterruptedException {
        synchronized (mutex) { // enter critical section
            while (queue.size() == capacity) {
                System.out.println("Buffer is full. Producer is waiting...");
                mutex.wait(); // wait if buffer is full
            }

            queue.add(value);
            System.out.println("Produced: " + value + " | Buffer: " + queue);

            mutex.notifyAll(); // notify consumer threads
        } // exit critical section
    }

    // Consumer removes items from the buffer
    public void consume() throws InterruptedException {
        synchronized (mutex) { // enter critical section
            while (queue.isEmpty()) {
                System.out.println("Buffer is empty. Consumer is waiting...");
                mutex.wait(); // wait if buffer is empty
            }

            int value = queue.poll();
            System.out.println("Consumed: " + value + " | Buffer: " + queue);

            mutex.notifyAll(); // notify producer threads
        } // exit critical section
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
                Thread.sleep(500); // simulate production time
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
                Thread.sleep(800); // simulate consumption time
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Mutex {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(3); // buffer with size 3

        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        producer.start();
        consumer.start();
    }
}
