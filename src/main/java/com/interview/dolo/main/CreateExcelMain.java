package com.interview.dolo.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.interview.dolo.util.ExcelCreateUtil;

/**描述：<br>
 * 作者：dolo <br>
 * 修改日期：2017年7月14日下午4:08:25 <br>
 */
public class CreateExcelMain {
	public static void main(String[] args) {
		System.out.println("hello world");
		List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "1");
		map.put("creditAmount", "2");
		map.put("askFree", "3");
		map.put("creditModel", "4");
		map.put("annuity", "5");
		map.put("endureAccount", "6");
		map.put("cahe", "7");
		map.put("idProduct", "8");
		map.put("newProductId", "9");
		map.put("isProductNo", "10");
		datalist.add(map);
		createExcle(datalist);
	}

	private static void createExcle(List<Map<String, Object>> datalist) {
		ExcelCreateUtil create = new ExcelCreateUtil();
		create.createSheet("统计日报表");
		List<String> headerList = new LinkedList<String>();
		List<Integer> headWidths = new ArrayList<Integer>();
		headerList = new LinkedList<String>();
		headerList.add("合同id ");
		headerList.add("贷款金额");
		headerList.add("咨询费");
		headerList.add("合同模式");
		headerList.add("原期款");
		headerList.add("新期款");
		headerList.add("差额");
		headerList.add("原产品id ");
		headerList.add("新产品id");
		headerList.add("产品ID是否一致");
		for (int i = 0; i < headerList.size(); i++) {
			headWidths.add(3000);
		}
		create.addHeader(headerList, headWidths);

		List<Object> rowList = null;
		for (int i = 0, n = datalist.size(); i < n; i++) {
			rowList = new ArrayList<Object>();
			HashMap<String, Object> map = (HashMap<String, Object>) datalist.get(i);
			rowList.add(String.valueOf(map.get("id")));
			rowList.add(String.valueOf(map.get("creditAmount")));
			rowList.add(String.valueOf(map.get("askFree")));
			rowList.add(String.valueOf(map.get("creditModel")));
			rowList.add(String.valueOf(map.get("annuity")));
			rowList.add(String.valueOf(map.get("endureAccount")));
			rowList.add(String.valueOf(map.get("cahe")));
			rowList.add(String.valueOf(map.get("idProduct")));
			rowList.add(String.valueOf(map.get("newProductId")));
			rowList.add(String.valueOf(map.get("isProductNo")));
			create.addRow(rowList);
		}

		String fileName = "C:/Users/100196/Desktop/" + "数据表.xls";
		try {
			create.exportExcel(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
