package com.interview.dolo;

/**
 * 设计模式：单例模式
 * 
 * @author dolojia
 *
 */
public class Singleton {

	private static Singleton singleton = null;

	/**
	 * 构造方法私有化，限制产生多个对象
	 */
	private Singleton() {
	}

	// 通过该方法获得实例对象
	public Singleton getSingleton() {
		// 多线程并发情况下用关键字synchronized锁住
		synchronized (singleton) {
			if (singleton == null) {
				singleton = new Singleton();
			}
		}
		return singleton;
	}

}
