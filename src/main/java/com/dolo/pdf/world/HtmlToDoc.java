package com.dolo.pdf.world;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.AltChunkType;

import com.dolo.pdf.PDFContextMaker;


/**
 * 文件名称: HtmlToDoc.java
 * 描述：html转化为word文档doc或docx格式、using docx4j's XHTMLImporter instead
 * 作者：j1deng
 * 修改日期：2017-12-3
 * E-mail: dolojia@gmail.com<br>
 */
public class HtmlToDoc {
    //模版文件夹路径
    private String reportTemplatePath = HtmlToDoc.class.getClassLoader().getResource("pdfTemplate").getPath();

    public void createDoc() throws Exception {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, reportTemplatePath);
        //加入电子模板
        Template template = null;

        //vm单模名称
        String reportTemplateName = "pdfTemp.html";
        //文件输出路径
        String docxName = "/Users/dolojia/Documents/temp2.docx";

        //临时文件
        ByteArrayInputStream bais = null;
        try {
            template = ve.getTemplate(reportTemplateName, "GBK");

            //这里处理context(动态为PDF模板赋值)
            PDFContextMaker contextMaker = new PDFContextMaker();
            Map<String, String> map = new HashMap<String, String>();
            VelocityContext context = contextMaker.makeContext(map);
            map.put("name", "dolo");
            map.put("phone", "12345678912");
            map.put("address", "北京的金山上光芒照四方");
            map.put("nowTime", "2017-12-01");

            StringWriter sw = new StringWriter();
            template.merge(context, sw);

            byte b[] = sw.toString().getBytes("UTF-8");
            bais = new ByteArrayInputStream(b);

            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
            wordMLPackage.getMainDocumentPart().addAltChunk(AltChunkType.Html, sw.toString().getBytes());
            wordMLPackage.save(new File(docxName));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bais.close();
        }

    }

    public static void main(String[] args) throws Exception {
        HtmlToDoc vm2PDFService = new HtmlToDoc();
        vm2PDFService.createDoc();
    }


}