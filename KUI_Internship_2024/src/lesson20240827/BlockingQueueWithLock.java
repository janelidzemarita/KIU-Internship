package lesson20240827;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueWithLock<T> {

    private final Queue<T> items = new ArrayList<>();

    // Reentrant lock for mutual exclusion
    private final Lock lock = new ReentrantLock();

    // Condition for when the queue is full (producers should wait)
    private final Condition notFull = lock.newCondition();

    // Condition for when the queue is empty (consumers should wait)
    private final Condition notEmpty = lock.newCondition();

    public BlockingQueueWithLock(int capacity) {
        this.capacity = capacity;
    }

    // Add an item to the queue
    public void put(T item) throws InterruptedException {
        lock.lock();
        try {
            // Wait while the queue is full
            while (items.size() == capacity) {
                notFull.await();
            }

            // Add the item to the queue
            items.add(item);

            // Signal that there is now an item available for consumers
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    // Remove and return an item from the queue
    public T get() throws InterruptedException {
        lock.lock();
        try {
            // Wait while the queue is empty
            while (items.isEmpty()) {
                notEmpty.await();
            }

            // Remove the item from the queue
            T item = items.poll();

            // Signal that there is now space available for producers
            notFull.signal();

            return item;
        } finally {
            lock.unlock();
        }
    }
}
