package com.dolo.pdf;

import org.apache.velocity.VelocityContext;

import java.util.Map;

/**
 * 文件名称: PDFContextMaker.java
 * 描述：vm模板变量替换处理类
 * 作者：j1deng
 * 修改日期：2017-12-3
 * E-mail: dolojia@gmail.com<br>
 */
public class PDFContextMaker {

    public VelocityContext makeContext(Map<String, String> map) {
        VelocityContext context = new VelocityContext();
        context.put("map", map);
        return context;
    }
}
