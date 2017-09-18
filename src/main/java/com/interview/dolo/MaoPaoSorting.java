package com.interview.dolo;

/**
 * 冒泡排序
 * 
 * @author dolojia
 *
 */
public class MaoPaoSorting {
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
}
