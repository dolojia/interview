package com.interview.dolo.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

import fr.opensagres.xdocreport.converter.ConverterRegistry;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.IConverter;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;

/**
 * 描述：根据2007版world文档替换变量再装换为PDF<br>
 * 作者：dolojia <br>
 * 修改日期：2017年9月18日下午4:41:03 <br>
 * E-mail: dolojia@gmail.com<br>
 */
public class WordUtil {

    public static void generateWord(Map<String, Object> param, String fileSrc, String fileDest) {
        XWPFDocument doc = null;
        OPCPackage pack = null;
        try {
            pack = POIXMLDocument.openPackage(fileSrc);
            doc = new XWPFDocument(pack);
            if (param != null && param.size() > 0) {
                // 处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                processParagraphs(paragraphList, param, doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileOutputStream fopts = null;
        try {
            fopts = new FileOutputStream(fileDest);
            doc.write(fopts);
            pack.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(fopts);
        }
    }

    public static void closeStream(FileOutputStream fopts) {
        try {
            fopts.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理段落,替换关键字
     *
     * @param paragraphList
     * @throws InvalidFormatException
     * @throws IOException
     * @throws DocumentException
     */
    public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, Object> param, XWPFDocument doc)
            throws InvalidFormatException, DocumentException, IOException {
        if (paragraphList != null && paragraphList.size() > 0) {
            // 遍历所有段落
            for (XWPFParagraph paragraph : paragraphList) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {

                    String text = run.getText(0);
                    // System.out.println("text==" + text);
                    if (text != null) {
                        boolean isSetText = false;
                        for (Entry<String, Object> entry : param.entrySet()) {
                            String key = entry.getKey();
                            if (text.indexOf(key) != -1) {// 在配置文件中有这个关键字对应的键
                                isSetText = true;
                                Object value = entry.getValue();
                                if (value instanceof String) {// 文本替换
                                    // System.out.println("key==" + key);
                                    if (text.contains(key)) {
                                        text = text.replace(key, value.toString());
                                    }
                                }
                            }
                        }
                        if (isSetText) {
                            run.setText(text, 0);
                        }
                    }
                }
            }
        }
    }

    /**
     * 将word docx文档， 转换成pdf
     *
     * @param source 源为word文档， 必须为docx文档
     * @param target 目标输出
     * @throws Exception
     */
    public static void wordConverterToPdf(InputStream source, OutputStream target, PdfOptions subOptions)
            throws Exception {
        // 重点 重写字体方法
        subOptions.fontProvider(new IFontProvider() {
            @Override
            public Font getFont(String familyName, String encoding, float size, int style, java.awt.Color color) {
                // TODO Auto-generated method stub
                try {
                    System.out.println(familyName);
                    String filePath = WordUtil.class.getClassLoader().getResource("fonts/simsun.ttc").getPath();
                    BaseFont baseFont = BaseFont.createFont(filePath + ",1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    return new Font(baseFont, size, style, color);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        });

        Options options = Options.getFrom(fr.opensagres.xdocreport.core.document.DocumentKind.DOCX)
                .to(ConverterTypeTo.PDF).subOptions(subOptions);

        options.subOptions(subOptions);
        IConverter converter = ConverterRegistry.getRegistry().getConverter(options);
        try {
            try {
                converter.convert(source, target, options);
            } finally {
                target.close();
            }
        } finally {
            source.close();
        }
    }
}
