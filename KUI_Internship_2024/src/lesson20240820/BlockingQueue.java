package lesson20240820;

import java.util.ArrayList;
import java.util.List;

/**
 * A thread-safe blocking queue that allows multiple threads to safely
 * add and remove elements.
 *
 * @param <T> the type of elements held in this queue
 */
public class BlockingQueue<T> {

	List<T> items = new ArrayList<>();
	/**
	 * Mutex object used for synchronizing access to the queue.
	 */
	Object mutex = new Object();

	/**
	 * Adds an element to the queue. If any threads are waiting to
	 * retrieve an element, one of them is notified.
	 *
	 * @param item the element to add to the queue
	 */
	public void put(T item) {
		synchronized (mutex) {
			items.add(item);
			mutex.notifyAll();
		}
	}

	/**
	 * Retrieves and removes the head of the queue, blocking if the
	 * queue is empty until an element becomes available.
	 *
	 * @return the head of the queue
	 */
	public T get() {
		synchronized (mutex) {
			while (items.isEmpty()) {
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt(); // Restore interrupt status
					e.printStackTrace();
				}
			}
			return items.remove(0);
		}
	}
}
