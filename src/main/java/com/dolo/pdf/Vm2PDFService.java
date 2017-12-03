package com.dolo.pdf;


import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * 文件名称: Vm2PDFService.java
 * 描述：vm模板生成PDF并拼接其他PDF文件
 * 作者：j1deng
 * 修改日期：2017-12-3
 * E-mail: dolojia@gmail.com<br>
 */
public class Vm2PDFService {
    //模版文件夹路径
    private String reportTemplatePath = Vm2PDFService.class.getClassLoader().getResource("pdfTemplate").getPath();

    String pdfFile = "";

    public void createPdf() throws Exception {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, reportTemplatePath);
        byte[] pdf = null;
        //加入电子模板
        Template template = null;

        //vm单模名称
        String reportTemplateName = "pdfTemp.vm";
        //最终PDF文件名
        String lastFileName = "1000990.pdf";
        //临时文件存储路径
        String randomPath = "/Users/dolojia/Documents/";

        ByteArrayOutputStream newFileOutPutStream = null;
        PdfReader pdfReader = null;
        PdfReader jsnPdfReader = null;
        OutputStream reportOutputStream = null;
        InputStream jsnInputStream = null;
        String randomFileName = lastFileName;
        //临时文件
        File randomFile = new File(randomPath + randomFileName);
        InputStream oldFileinInputStream = null;
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
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(sw.toString());

            /**
             * 初始化字体
             */
            FlyingSaucerUtils.initFlyingSaucerFonts(renderer, reportTemplatePath + "/fonts");
            renderer.layout();
            reportOutputStream = new FileOutputStream(randomFile);
            renderer.createPDF(reportOutputStream);
            reportOutputStream.flush();
            newFileOutPutStream = new ByteArrayOutputStream();
            File file = null;
            //自动获取要拼接的PDF文件
            String pdfName = "/pdfname.pdf";

            //业务处理，选择拼接内容
            file = new File(reportTemplatePath + pdfName);

            oldFileinInputStream = new FileInputStream(randomFile);
            pdfReader = new PdfReader(oldFileinInputStream);
            Document document = new Document(pdfReader.getPageSize(1));
            PdfCopy pdfCopy = new PdfCopy(document, newFileOutPutStream);
            document.open();
            jsnInputStream = new FileInputStream(file);
            jsnPdfReader = new PdfReader(jsnInputStream);
            int oldPages = pdfReader.getNumberOfPages();
            //vm生成的PDF
            for (int i = 1; i <= oldPages; i++) {
                document.newPage();
                PdfImportedPage page = pdfCopy.getImportedPage(pdfReader, i);
                pdfCopy.addPage(page);
            }
            //待拼接PDF文件
            int pages = jsnPdfReader.getNumberOfPages();
            for (int i = 1; i <= pages; i++) {
                document.newPage();
                PdfImportedPage page = pdfCopy.getImportedPage(jsnPdfReader, i);
                pdfCopy.addPage(page);
            }


            newFileOutPutStream.flush();
            document.close();
            pdf = newFileOutPutStream.toByteArray();
            pdfFile = reportTemplatePath + "/" + lastFileName;
            FileOutputStream ops = new FileOutputStream(new File(pdfFile));
            ops.write(pdf);
            ops.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reportOutputStream != null) {
                reportOutputStream.close();
            }
            if (newFileOutPutStream != null) {
                newFileOutPutStream.close();
            }
            if (pdfReader != null) {
                pdfReader.close();
            }
            if (jsnPdfReader != null) {
                jsnPdfReader.close();
            }
            if (jsnInputStream != null) {
                jsnInputStream.close();
            }
            if (oldFileinInputStream != null) {
                oldFileinInputStream.close();
            }
            if (randomFile.exists()) {
                randomFile.delete();
            }
        }

    }


}