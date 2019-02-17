/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-10-29 23:20 创建
 */
package com.acooly.module.security.captche;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.util.Configurable;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author qiubo
 */
public class WaterRender extends Configurable implements GimpyEngine {
    @Override
    public BufferedImage getDistortedImage(BufferedImage baseImage) {
        BufferedImage distortedImage =
                new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), 2);
        Graphics2D graphics = (Graphics2D) distortedImage.getGraphics();
        graphics.drawImage(baseImage, 0, 0, null, null);
        graphics.dispose();
        return distortedImage;
    }
}
