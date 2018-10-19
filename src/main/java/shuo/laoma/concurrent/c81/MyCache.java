package shuo.laoma.concurrent.c81;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyCache {

	//数据存这
	private Map<String, Object> map = new HashMap<>();

	private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private Lock readLock = readWriteLock.readLock();
	private Lock writeLock = readWriteLock.writeLock();

	//读要得读锁
	public Object get(String key) {
		readLock.lock();
		try {
			return map.get(key);
		} finally {
			readLock.unlock();
		}
	}

	//写要写锁
	public Object put(String key, Object value) {
		writeLock.lock();
		try {
			return map.put(key, value);
		} finally {
			writeLock.unlock();
		}
	}

	public void clear() {
		writeLock.lock();
		try {
			map.clear();
		} finally {
			writeLock.unlock();
		}
	}

}
