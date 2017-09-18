package com.interview.dolo.singleton;

/**
 * 设计模式：饿汉式单例模式
 * 
 * @author dolojia
 *
 */
public class HurrySingleton {

	private final static HurrySingleton singleton = new HurrySingleton();

	/**
	 * 私有构造方法私有化，限制产生多个对象
	 */
	private HurrySingleton() {
	}

	// 通过该方法获得实例对象
	public HurrySingleton getSingleton() {
		return singleton;
	}

}
