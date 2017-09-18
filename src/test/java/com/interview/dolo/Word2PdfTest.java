package com.interview.dolo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.converter.pdf.PdfOptions;

import com.interview.dolo.util.WordUtil;

public class Word2PdfTest {

	public static void main(String[] args) throws Exception {
		// 获取配置文件所有键值对
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("${name}", "我是乙方");

		// 替换文档关键字
		String fileSrc = Word2PdfTest.class.getClassLoader().getResource("fonts/template.docx").getPath();// 源模板文件
		String fileDest = Word2PdfTest.class.getClassLoader().getResource("fonts").getPath() + "/new.docx"; // 替换内容后的新文件

		WordUtil.generateWord(param, fileSrc, fileDest);

		// word 转 PDF
		String fileName = System.currentTimeMillis() + ".PDF";
		String outpath = Word2PdfTest.class.getClassLoader().getResource("fonts").getPath() + "/" + fileName;// 生成PDF文件
		InputStream source = new FileInputStream(fileDest);
		OutputStream target = new FileOutputStream(outpath);
		PdfOptions options = PdfOptions.create();
		WordUtil.wordConverterToPdf(source, target, options);

	}

}
