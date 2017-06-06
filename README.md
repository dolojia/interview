## 漫漫面试路

#### 算法
##### 冒泡排序
* 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
* 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
* 针对所有的元素重复以上的步骤，除了最后一个。
* 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
```
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int sort[] = { 67, 69, 75, 87, 89, 90, 99, 100 };
		for (int i = 0; i < sort.length; i++) {
			// j的范围很关键，这个范围是在逐步缩小的
			for (int j = 0; j < sort.length - i - 1; j++) {
				if (sort[j] < sort[j + 1]) {
					// 把小的交换到后面
					int tempSort = sort[j];
					sort[j] = sort[j + 1];
					sort[j + 1] = tempSort;
				}
			}
			System.out.println("第" + (i + 1) + "次排序结果：");
			for (int a = 0; a < sort.length; a++) {
				System.out.print(sort[a] + "\t");
			}
			System.out.println("");
		}
		System.out.println("最终排序结果：");
		for (int a = 0; a < sort.length; a++) {
			System.out.print(sort[a] + "\t");
		}
	}

```


#### LinkLiat & ArrayList比较
* ArrayList是实现了基于动态数组的数据结构，LinkedList基于链表的数据结构。 
* 对于随机访问get和set，ArrayList觉得优于LinkedList，因为LinkedList要移动指针。 
* 对于新增和删除操作add和remove，LinedList比较占优势，因为ArrayList要移动数据。 
 
######` 总结`：当操作是在一列数据的后面添加数据而不是在前面或中间,并且需要随机地访问其中的元素时,使用ArrayList会提供比较好的性能；当你的操作是在一列数据的前面或中间添加或删除数据,并且按照顺序访问其中的元素时,就应该使用LinkedList了。

#### 数据源 
* [Druid](https://github.com/alibaba/druid/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)
* Druid连接池统计功能过滤器 web.xml

[线程池的原理及实现](http://blog.csdn.net/hsuxu/article/details/8985931)


#### 并发
高并发量网站解决方案

#### [事务隔离级别](http://www.cnblogs.com/younggun/archive/2013/07/16/3193800.html)

* Read uncommitted
 * 就是一个事务可以读取另一个未提交事务的数据   脏读
 * 事例：老板要给程序员发工资，程序员的工资是3.6万/月。但是发工资时老板不小心按错了数字，按成3.9万/月，该钱已经打到程序员的户口，但是事务还没有提交，就在这时，程序员去查看自己这个月的工资，发现比往常多了3千元，以为涨工资了非常高兴。但是老板及时发现了不对，马上回滚差点就提交了的事务，将数字改成3.6万再提交。

* Read committed
 * 就是一个事务要等另一个事务提交后才能读取数据。 幻读
 * 事例：程序员拿着信用卡去享受生活（卡里当然是只有3.6万），当他埋单时（程序员事务开启），收费系统事先检测到他的卡里有3.6万，就在这个时候！！程序员的妻子要把钱全部转出充当家用，并提交。当收费系统准备扣款时，再检测卡里的金额，发现已经没钱了（第二次检测金额当然要等待妻子转出金额事务提交完）。程序员就会很郁闷，明明卡里是有钱的…
 * 在这个事例中，出现了一个事务范围内两个相同的查询却返回了不同数据，这就是不可重复读。

* Repeatable read
 * 重复读，就是在开始读取数据（事务开启）时，不再允许修改操作
 * 事例：程序员拿着信用卡去享受生活（卡里当然是只有3.6万），当他埋单时（事务开启，不允许其他事务的UPDATE修改操作），收费系统事先检测到他的卡里有3.6万。这个时候他的妻子不能转出金额了。接下来收费系统就可以扣款了。
 * 分析：重复读可以解决不可重复读问题。写到这里，应该明白的一点就是，不可重复读对应的是修改，即UPDATE操作。但是可能还会有幻读问题。因为幻读问题对应的是插入INSERT操作，而不是UPDATE操作。

* Serializable
 * Druid
 * Serializable 是最高的事务隔离级别，在该级别下，事务串行化顺序执行，可以避免脏读、不可重复读与幻读。但是这种事务隔离级别效率低下，比较耗数据库性能，一般不使用。
* 值得一提的是：大多数数据库默认的事务隔离级别是Read committed，比如Sql Server , Oracle。MySQL的默认隔离级别是Repeatable read。

Spring事务的传播行为，并说说每个传播行为的区别

[Spring事务机制](http://blog.csdn.net/pingnanlee/article/details/11488695)

#### 分布式事务

#### 死锁原理
##### 产生死锁的原因主要是：
* 因为系统资源不足。
* 进程运行推进的顺序不合适。
* 资源分配不当等。
##### 产生死锁的四个必要条件：　　
* 互斥使用（资源独占）    一个资源每次只能给一个进程使用
* 不可强占（不可剥夺）    资源申请者不能强行的从资源占有者手中夺取资源，资源只能由占有者自愿释放
* 请求和保持（部分分配，占有申请）   一个进程在申请新的资源的同时保持对原有资源的占有（只有这样才是动态申请，动态分配）
* 循环等待 

#### [23种设计模式](http://zz563143188.iteye.com/blog/1847029)
[设计模式：使用场景、比如单例模式、工厂模式（为什么使用）代理模式]
* [单例模式的七种写法](http://cantellow.iteye.com/blog/838473)
* [java 单例模式及运用](http://damon-zhang.iteye.com/blog/1913596)

#### spring：工作原理、源码阅读、ico、aop，事务原理
#### SpringMVC：工作原理、注解、mvc、流程
#### mybatis :工作原理、一对多,与hibernate比较、#与$区别、分页查询
#### redis: 工作原理、集群、操作、应用场景

#### 图片服务器：fastDFS
#### [nginx:配置](http://www.cnblogs.com/xiaogangqq123/archive/2011/03/02/1969006.html)

[web如何项目优化](http://bbs.csdn.net/topics/391849317)

#### 高并发解决方案：nginx+tomcat多节点
#### tomcat:配置、jvm配置（session会话同步Tomcat-Redis-Cluster-manager，tomcat插件）

java：虚拟机、拆封箱integer(128)==128 false integer(127)==127 引发java缓存机制int(-128 — 127)
项目架构：
数据库：函数跟储存过程的区别


redis集群部署
jenkins:持续集成
git：命令

[消息队列的原理和实现](http://blog.csdn.net/blade2001/article/details/5193464)

[深入浅出 消息队列 ActiveMQ](http://blog.csdn.net/jwdstef/article/details/17380471)

[Java高级工程师常见面试题](http://www.itmayiedu.com/front/articleinfo/63.html)



## 关于作者

```javascript
  var ihubo = {
    nickName  : "dolo",
    site : "https://github.com/DoloXing"
  }
```
