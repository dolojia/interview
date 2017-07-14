package com.interview.dolo.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Excel导出
 */
// maven依赖<dependency><groupId>org.apache.poi</groupId><artifactId>poi</artifactId><version>3.16</version></dependency>
public class ExcelCreateUtil {
	/**
	 * Excel空间
	 */
	private HSSFWorkbook hfWorkbook = null;
	/**
	 * 工作表
	 */
	private HSSFSheet hfSheet = null;

	/**
	 * 构造Excel表空间
	 */
	public ExcelCreateUtil() {
		hfWorkbook = new HSSFWorkbook();
	}

	/**
	 * 旧合并行
	 */
	private HSSFRow oldRow = null;

	/**
	 * 行索引
	 */
	private int rowIndex = 0;

	/**
	 * 创建一个Sheet页
	 * 
	 * @param sheetName
	 *            Sheet页的名称
	 */
	public void createSheet(String sheetName) {
		hfSheet = hfWorkbook.createSheet(sheetName);
		// 设置单元格默认长、宽
		hfSheet.setDefaultRowHeightInPoints(20);
		hfSheet.setDefaultColumnWidth(8);
	}

	/**
	 * 添加一行表头
	 * 
	 * @param headValues
	 *            单元格内容
	 */
	public void addHeader(List<String> headValues) {
		this.addHeader(headValues, null, true, "微软雅黑", (short) 14, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER,
				new HSSFColor.BLACK(), new HSSFColor.GREY_25_PERCENT());
	}

	/**
	 * 添加一行表头
	 * 
	 * @param headValues
	 *            单元格内容
	 */
	public void addHeader(List<String> headValues, List<Integer> headWidths, boolean isBackground) {
		this.addHeader(headValues, null, isBackground, "微软雅黑", (short) 10, CellStyle.ALIGN_CENTER,
				CellStyle.VERTICAL_CENTER, new HSSFColor.BLACK(), new HSSFColor.GREY_80_PERCENT());
	}

	/**
	 * 添加一行表头
	 * 
	 * @param headValues
	 *            单元格内容
	 */
	public void addHeader(List<String> headValues, List<Integer> headWidths) {
		this.addHeader(headValues, headWidths, true, "微软雅黑", (short) 10, CellStyle.ALIGN_CENTER,
				CellStyle.VERTICAL_CENTER, new HSSFColor.BLACK(), new HSSFColor.GREY_25_PERCENT());
	}

	/**
	 * 添加一行表头
	 * 
	 * @param headValues
	 *            单元格内容
	 * @param isBackground
	 *            是否含有背景色
	 * @param backgroundColor
	 *            表头背景色填充
	 */
	public void addHeader(List<String> headValues, List<Integer> headWidths, boolean isBackground,
			HSSFColor backgroundColor) {
		this.addHeader(headValues, headWidths, isBackground, "微软雅黑", (short) 14, CellStyle.ALIGN_CENTER,
				CellStyle.VERTICAL_CENTER, new HSSFColor.BLACK(), backgroundColor);
	}

	/**
	 * 添加一行表头
	 * 
	 * @param headValues
	 *            单元格内容
	 * @param isBackground
	 *            是否含有背景色
	 * @param fontName
	 *            表头字体名称
	 * @param fontSize
	 *            表头字体大小
	 * @param borderColor
	 *            边框颜色
	 * @param backgroundColor
	 *            表头背景色填充
	 */
	public void addHeader(List<String> headValues, List<Integer> headWidths, boolean isBackground, String fontName,
			short fontSize, HSSFColor backgroundColor) {
		this.addHeader(headValues, headWidths, isBackground, fontName, fontSize, CellStyle.ALIGN_CENTER,
				CellStyle.VERTICAL_CENTER, new HSSFColor.BLACK(), backgroundColor);
	}

	/**
	 * 添加一行表头
	 * 
	 * @param headValues
	 *            单元格内容
	 * @param isBackground
	 *            是否含有背景色
	 * @param fontName
	 *            表头字体名称
	 * @param fontSize
	 *            表头字体大小
	 * @param align
	 *            表头对齐方式 HSSFCellStyle.ALIGN_CENTER 居中对齐
	 * @param Vertica
	 *            垂直居中
	 * @param borderColor
	 *            边框颜色
	 * @param backgroundColor
	 *            表头背景色填充
	 */
	public void addHeader(List<String> headValues, List<Integer> headWidths, boolean isBackground, String fontName,
			short fontSize, short align, short Vertica, HSSFColor borderColor, HSSFColor backgroundColor) {
		HSSFFont hfFont = hfWorkbook.createFont();
		if (fontName != null && !fontName.equals(""))
			hfFont.setFontName(fontName);
		else
			hfFont.setFontName("微软雅黑");
		if (fontSize > 1)
			hfFont.setFontHeightInPoints(fontSize);
		else
			hfFont.setFontHeightInPoints((short) 14);
		// 加粗
		hfFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// 单元格颜色
		HSSFCellStyle hdCellStyle = createCellStyle(borderColor, align, backgroundColor, Vertica);
		hdCellStyle.setFont(hfFont);

		HSSFRow dtRow = hfSheet.createRow(rowIndex);
		int cellIndex = 0;
		if (isBackground) {
			for (String s : headValues) {
				setHSSFCellValue(dtRow.createCell(cellIndex), s, hdCellStyle);
				if (headWidths != null && cellIndex < headWidths.size()) {
					hfSheet.setColumnWidth(cellIndex, headWidths.get(cellIndex));
				}
				cellIndex++;
			}
		} else {
			for (String s : headValues) {
				setHSSFCellValue(dtRow.createCell(cellIndex), s, null);
				if (headWidths != null && cellIndex < headWidths.size()) {
					hfSheet.setColumnWidth(cellIndex, headWidths.get(cellIndex));
				}
				cellIndex++;
			}
		}
		// MergedCell(dtRow);
		if (oldRow != null)
			MergedRow(dtRow);
		else
			oldRow = dtRow;
		rowIndex++;
	}

	/**
	 * 设置单元格的内容
	 */
	private void setHSSFCellValue(HSSFCell cell, String s, HSSFCellStyle CellStyle) {
		HSSFRichTextString value = new HSSFRichTextString(s);
		cell.setCellValue(value);
		if (CellStyle != null)
			cell.setCellStyle(CellStyle);
	}

	/**
	 * 横向合并单元格
	 * 
	 * @param newRow
	 *            待合并的行
	 */
	private void MergedCell(HSSFRow newRow) {
		int mergedCount = 0;
		HSSFCell oldCell = newRow.getCell(mergedCount);
		int rowNum = newRow.getRowNum();
		for (int i = mergedCount + 1; i < newRow.getLastCellNum(); i++) {
			HSSFCell newCell = newRow.getCell(i);
			if (!getCellValue(oldCell).equals(getCellValue(newCell))) {
				CellRangeAddress cellrange = new CellRangeAddress(rowNum, rowNum, mergedCount, (i - 1));

				hfSheet.addMergedRegion(cellrange);
				oldCell = newCell;
				mergedCount = i;
			} else {
				newCell.setCellValue("");
			}
			if (i == (newRow.getLastCellNum() - 1) && mergedCount < (newRow.getLastCellNum() - 1)) {
				CellRangeAddress cellrange = new CellRangeAddress(rowNum, rowNum, mergedCount, i);
				hfSheet.addMergedRegion(cellrange);
			}
		}
	}

	/**
	 * 纵向向合并单元格
	 * 
	 * @param newRow
	 *            待合并的行
	 */
	private void MergedRow(HSSFRow newRow) {
		if (oldRow != null) {
			int oldNum = oldRow.getRowNum();
			int rowNum = newRow.getRowNum();
			for (int i = 0; i < newRow.getLastCellNum(); i++) {
				if (getCellValue(oldRow.getCell(i)).equals(getCellValue(newRow.getCell(i)))) {
					CellRangeAddress cellrange = new CellRangeAddress(oldNum, rowNum, i, i);
					// newRow.getCell(i).setCellValue("");
					hfSheet.addMergedRegion(cellrange);
				}
			}
			oldRow = newRow;
		}
	}

	/**
	 * 获取单元格内容
	 * 
	 * @param cell
	 *            待获取内容的单元格
	 */
	private Object getCellValue(HSSFCell cell) {
		Object object = null;
		try {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				object = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				object = cell.getNumericCellValue();
				break;
			default:
				object = cell.getStringCellValue();
				break;
			}
		} catch (Exception e) {

		}
		return object;
	}

	/**
	 * 返回单元格样式
	 * 
	 * @param borderColor
	 *            边框颜色
	 * @param align
	 *            表头对齐方式 HSSFCellStyle.ALIGN_CENTER 居中对齐
	 * 
	 * @param backgroundColor
	 *            背景色
	 * @param Vertica
	 *            垂直居中
	 */
	private HSSFCellStyle createCellStyle(HSSFColor borderColor, short align, HSSFColor backgroundColor,
			short Vertica) {
		// 表头样式及背景色
		HSSFCellStyle CellStyle = hfWorkbook.createCellStyle();
		// 底部
		CellStyle.setBorderBottom(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
		CellStyle.setBottomBorderColor(borderColor.getIndex());
		// 左侧
		CellStyle.setBorderLeft(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
		CellStyle.setLeftBorderColor(borderColor.getIndex());
		// 上
		CellStyle.setBorderTop(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
		CellStyle.setTopBorderColor(borderColor.getIndex());
		// 右侧
		CellStyle.setBorderRight(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
		CellStyle.setRightBorderColor(borderColor.getIndex());

		// 字体位置模式 左 居中 右
		CellStyle.setAlignment(align);

		// 垂直居中
		CellStyle.setVerticalAlignment(Vertica);

		if (backgroundColor != null) {
			// 背景色填充
			CellStyle.setFillForegroundColor(backgroundColor.getIndex());
			CellStyle.setFillPattern(org.apache.poi.ss.usermodel.CellStyle.SOLID_FOREGROUND);
		}
		return CellStyle;
	}

	/**
	 * 添加一个行数据
	 * 
	 * @param rowvalues
	 *            一行数据List
	 */
	public void addRow(List<Object> rowvalues) {
		HSSFRow dtRow = hfSheet.createRow(rowIndex++);
		for (int j = 0; j < rowvalues.size(); j++) {
			Object cell_data = rowvalues.get(j);
			HSSFCell cell = dtRow.createCell(j);
			// 正文格式
			if (cell_data instanceof String) {
				cell.setCellValue((String) cell_data);
			} else if (cell_data instanceof Double) {
				cell.setCellValue((Double) cell_data);
			} else if (cell_data instanceof Integer) {
				cell.setCellValue(Double.valueOf(String.valueOf(cell_data)));
			} else if (cell_data instanceof Date) {
				cell.setCellValue((Date) cell_data);
			} else if (cell_data instanceof Boolean) {
				cell.setCellValue((Boolean) cell_data);
			} else if (cell_data instanceof Float) {
				cell.setCellValue((Float) cell_data);
			}
		}
	}

	/**
	 * 具体文件生成的路径
	 * 
	 * @param file
	 *            文件生成路径
	 * @throws Exception
	 *             生成错误
	 */
	public void exportExcel(String file) throws Exception {
		FileOutputStream fileOut = new FileOutputStream(file);
		hfWorkbook.write(fileOut);
		fileOut.close();
	}

	/**
	 * 具体文件生成的文件
	 * 
	 * @param file
	 *            文件流
	 * @throws Exception
	 *             生成错误
	 */
	public void exportExcel(File file) throws Exception {
		FileOutputStream fileOut = new FileOutputStream(file);
		hfWorkbook.write(fileOut);
		fileOut.close();
	}

	/**
	 * 具体文件生成的文件
	 * 
	 * @param file
	 *            输出流
	 * @throws Exception
	 *             生成错误
	 */
	public void exportExcel(OutputStream outputstream) throws Exception {
		BufferedOutputStream buffout = new BufferedOutputStream(outputstream);
		hfWorkbook.write(buffout);
		buffout.flush();
		buffout.close();
	}
}
