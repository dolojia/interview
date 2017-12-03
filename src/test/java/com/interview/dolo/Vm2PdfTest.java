package com.interview.dolo;


import com.dolo.pdf.Vm2PDFService;

/**
 * 文件名称: Vm2PdfTest.java
 * 描述：vm生成pdf并拼接文件测试类
 * 作者：j1deng
 * 修改日期：2017-12-3
 * E-mail: dolojia@gmail.com<br>
 */
public class Vm2PdfTest {
    public static void main(String[] args) throws Exception {

        Vm2PDFService vm2PDFService = new Vm2PDFService();
        vm2PDFService.createPdf();

    }

}
