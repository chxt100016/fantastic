package com.chxt.fantastic.common.image;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class AwtUtils {
    @SneakyThrows
    public static byte[] toByte(List<String> data) {
        int width = 1920;
        int height = 1080;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 50));
        for (int i = 0; i < data.size(); i++) {
            String item = data.get(i);
            graphics.drawString(item, 150, 70+ (i + 1) * 50);
        }

        graphics.dispose();

//        ImageOutputStream ios = ImageIO.createImageOutputStream(new File("/Users/chenxintong/Downloads/str.jpeg"));
//        ImageIO.write(image, "jpg", ios);
//        ios.close();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", bos);
        return bos.toByteArray();


    }
}
