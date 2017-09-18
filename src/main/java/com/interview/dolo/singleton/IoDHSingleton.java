package com.interview.dolo.singleton;

/**
 * 描述：内部类形式的单例模式，Initialization Demand Holder (IoDH)的技术<br>
 * 作者：dolojia <br>
 * 修改日期：2017年7月26日上午11:11:53 <br>
 * E-mail: dolojia@gmail.com<br>
 */
public class IoDHSingleton {

	private IoDHSingleton() {
	}

	private static class InnerClass {
		private final static IoDHSingleton singleton1 = new IoDHSingleton();
	}

	/**
	 * 方法名称: getInstance<br>
	 * 描述：此处final为防止子类重写getInstance方法 作者: dolojia 修改日期：2017年7月26日上午11:12:12
	 * 
	 * @return
	 */
	public final static IoDHSingleton getInstance() {
		return InnerClass.singleton1;
	}

}
