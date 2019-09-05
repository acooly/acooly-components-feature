/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-10-29 23:19 创建
 */
package com.acooly.module.security.captche;

import com.google.code.kaptcha.text.WordRenderer;
import com.google.code.kaptcha.util.Configurable;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author qiubo
 */
public class WordRender extends Configurable implements WordRenderer {

    public BufferedImage renderWord(String word, int width, int height) {
        int fontSize = getConfig().getTextProducerFontSize();
        Font[] fonts = getConfig().getTextProducerFonts(fontSize);
        Color color = getConfig().getTextProducerFontColor();
        int charSpace = getConfig().getTextProducerCharSpace();
        char[] wordChars = word.toCharArray();
        BufferedImage image = new BufferedImage(width, height, 2);
        Graphics2D g2D = image.createGraphics();
        RenderingHints hints =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.add(
                new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2D.setRenderingHints(hints);
        draw(width, height, fontSize, wordChars, charSpace, fonts, color, g2D);
        Random random = ThreadLocalRandom.current();
        image = twistImage(image, random);
        return image;
    }

    protected void draw(
            int width,
            int height,
            int fontSize,
            char[] wordChars,
            int charSpace,
            Font[] fonts,
            Color color,
            Graphics2D g2D) {
        Random random = ThreadLocalRandom.current();
        FontRenderContext frc = g2D.getFontRenderContext();
        Font[] chosenFonts = new Font[wordChars.length];
        int[] charWidths = new int[wordChars.length];
        int heigthSpace = height - fontSize;
        int startPosY =
                heigthSpace <= 0 ? heigthSpace / 2 + fontSize : random.nextInt(heigthSpace) + fontSize;
        int widthNeeded = 0;
        for (int i = 0; i < wordChars.length; ++i) {

            chosenFonts[i] = fonts[random.nextInt(fonts.length)];
            char[] charToDraw = {wordChars[i]};
            GlyphVector gv = chosenFonts[i].createGlyphVector(frc, charToDraw);
            charWidths[i] = (int) gv.getVisualBounds().getWidth();
            if (i > 0) {
                widthNeeded += 2;
            }
            widthNeeded += charWidths[i];
        }
        int widthSpace = width - widthNeeded;
        int startPosX = widthSpace / 2 <= 0 ? widthSpace / 2 : random.nextInt(widthSpace);
        g2D.setColor(color);
        for (int i = 0; i < wordChars.length; ++i) {
            // g2D.setColor(color[random.nextInt(5)]);
            char[] charToDraw = {wordChars[i]};
            g2D.setFont(chosenFonts[i]);
            int count = 0;
            if (i == 0) {
                count = getRandomDegree(true, 10);
            } else {
                count = getRandomDegree(false, 15);
            }
            double tempX = startPosX + charWidths[i] / 2;
            g2D.rotate(count * Math.PI / 120, tempX, height / 2);
            if (random.nextInt(2) == 1) { //画空心字体
                TextLayout tl = new TextLayout(new String(charToDraw), chosenFonts[i], frc);
                Shape shape =
                        tl.getOutline(
                                AffineTransform.getTranslateInstance((double) startPosX, (double) startPosY));
                g2D.draw(shape);
            } else {
                g2D.drawString(new String(charToDraw), startPosX, startPosY);
            }
            startPosX = startPosX + charWidths[i] + charSpace;
            g2D.rotate(-count * Math.PI / 210, tempX, height / 2);
        }
    }

    protected int getRandomDegree(Boolean b, int max) {
        int count = ThreadLocalRandom.current().nextInt(max) + 3;
        if (b) {
            if (count > 0) {
                count = -count;
            }
        }
        return count;
    }

    private BufferedImage twistImage(BufferedImage image, Random random) {
        BufferedImage image2 =
                new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        //
        double dMultValue = random.nextInt(5) + 2; // 波形的幅度倍数，越大扭曲的程序越高，一般为3
        double dPhase = random.nextInt(6); // 波形的起始相位，取值区间（0-2＊PI）

        for (int i = 0; i < image2.getWidth(); i++) {
            for (int j = 0; j < image2.getHeight(); j++) {
                int nOldX = getXPosition4Twist(dPhase, dMultValue, image2.getHeight(), i, j);
                int nOldY = j;
                if (nOldX >= 0 && nOldX < image2.getWidth() && nOldY >= 0 && nOldY < image2.getHeight()) {
                    image2.setRGB(nOldX, nOldY, image.getRGB(i, j));
                }
            }
        }
        return image2;
    }

    //
    private int getXPosition4Twist(
            double dPhase, double dMultValue, int height, int xPosition, int yPosition) {
        //double PI = 4; // 此值越大，扭曲程度越大
        double dx = Math.PI * yPosition / height + dPhase;
        double dy = Math.sin(dx);
        return xPosition + (int) (dy * dMultValue);
    }
}
