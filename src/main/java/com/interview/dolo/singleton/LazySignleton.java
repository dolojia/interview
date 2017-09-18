package com.interview.dolo.singleton;

/**
 * 设计模式：懒汉式单例模式
 * 
 * @author dolojia
 *
 */
public class LazySignleton {

	private static LazySignleton singleton = null;

	/**
	 * 私有构造方法私有化，限制产生多个对象
	 */
	private LazySignleton() {
	}

	// 通过该方法获得实例对象
	public LazySignleton getSingleton() {
		// 多线程并发情况下用关键字synchronized锁住
		synchronized (singleton) {
			if (singleton == null) {
				singleton = new LazySignleton();
			}
		}
		return singleton;
	}

}
