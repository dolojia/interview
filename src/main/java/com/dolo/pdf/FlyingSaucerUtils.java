package com.dolo.pdf;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * 文件名称: FlyingSaucerUtils.java
 * 描述：字体文件处理类
 * 作者：j1deng
 * 修改日期：2017-12-3
 * E-mail: dolojia@gmail.com<br>
 */
public class FlyingSaucerUtils {

    public static void initFlyingSaucerFonts(ITextRenderer renderer, String fontDir) {
        try {
            ITextFontResolver fontResolver = renderer.getFontResolver();
            File f = new File(fontDir);

            if (f.isDirectory()) {

                File[] files = f.listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        System.out.println(name);
                        String lower = name.toLowerCase();
                        return lower.endsWith(".otf") || lower.endsWith(".ttf") || lower.endsWith(".ttc");
                    }
                });
                for (int i = 0; i < files.length; i++) {
                    fontResolver.addFont(files[i].getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}