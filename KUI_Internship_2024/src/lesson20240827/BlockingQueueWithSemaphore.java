package lesson20240827;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class BlockingQueueWithSemaphore<T> {

	private final Queue<T> items = new ArrayList<>();

	// Semaphore to track available items (initially 0)
	private final Semaphore itemsSemaphore;

	// Semaphore to track available spaces (initially equal to capacity)
	private final Semaphore spacesSemaphore;

	// Mutex for synchronized access to the queue
	private final Semaphore mutex = new Semaphore(1);

	public BlockingQueueWithSemaphore(int capacity) {
		this.capacity = capacity;
		this.itemsSemaphore = new Semaphore(0); // Initially, the queue is empty
		this.spacesSemaphore = new Semaphore(capacity); // Initially, all spaces are available
	}

	// Add an item to the queue
	public void put(T item) throws InterruptedException {
		// Acquire a space semaphore to ensure there's room to add
		spacesSemaphore.acquire();

		// Lock the queue (mutex) to add the item
		mutex.acquire();
		try {
			items.add(item);
		} finally {
			mutex.release();
		}

		// Signal that there is a new item available
		itemsSemaphore.release();
	}

	// Remove and return an item from the queue
	public T get() throws InterruptedException {
		// Acquire an item semaphore to ensure there's an item to remove
		itemsSemaphore.acquire();

		// Lock the queue (mutex) to remove the item
		mutex.acquire();
		T item;
		try {
			item = items.poll();
		} finally {
			mutex.release();
		}

		// Signal that a space is now available
		spacesSemaphore.release();

		return item;
	}
}
