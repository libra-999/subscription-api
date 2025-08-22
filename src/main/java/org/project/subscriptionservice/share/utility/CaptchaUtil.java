package org.project.subscriptionservice.share.utility;

import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.img.GraphicsUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class CaptchaUtil extends CircleCaptcha {

    public CaptchaUtil() {
        super(200, 100, 4, 500);
    }

    @Override
    public Image createImage(String code) {
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D g = ImgUtil.createGraphics(image, ObjectUtil.defaultIfNull(this.background, Color.WHITE));
        drawInterfere(g);
        drawString(g, code);
        return image;
    }

    private void drawInterfere(Graphics2D g) {
        final ThreadLocalRandom random = RandomUtil.getRandom();

        for (int i = 0; i < this.interfereCount; i++) {
            g.setColor(ImgUtil.randomColor(random));
            g.drawOval(random.nextInt(width), random.nextInt(height), random.nextInt(height >> 1), random.nextInt(height >> 1));
        }
    }

    private void drawString(Graphics2D g, String code) {
        if (null != this.textAlpha) {
            g.setComposite(this.textAlpha);
        }
        GraphicsUtil.drawString(g, code, font, Color.black, width, height);
    }
}

