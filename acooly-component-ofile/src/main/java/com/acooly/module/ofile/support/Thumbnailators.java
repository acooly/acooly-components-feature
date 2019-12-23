/**
 * create by zhangpu date:2015年7月13日
 */
package com.acooly.module.ofile.support;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author zhangpu
 */
public class Thumbnailators {

    public static void resize(String srcFile, String outFile, int width, int height) {
        try {
            double ratio = 1; // 缩放比例
            File f = new File(srcFile);
            BufferedImage bi = ImageIO.read(f);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
            }
            Thumbnails.of(srcFile).scale(ratio).toFile(outFile);
        } catch (Exception e) {
            throw new RuntimeException("缩略图生产失败:" + e.getMessage());
        }
    }
}
