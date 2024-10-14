/*
Copyright 2009-(CURRENT YEAR) Igor Polevoy

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0 

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, 
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
See the License for the specific language governing permissions and 
limitations under the License. 
*/
package org.examples.func;

import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * This is a simple captcha class, use it to generate a random string and then to create an image of it.
 *
 * @author Igor Polevoy
 */
public class Captcha {
    private static final String FONT_PATH = "classpath:font/XBZarIt-Persian-Number.ttf";
    private static final Font arabicFont;

    static {
        try {
            arabicFont = Font.createFont(Font.PLAIN, ResourceUtils.getFile(FONT_PATH)).deriveFont(48f);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a PNG image of text 180 pixels wide, 40 pixels high with white background.
     *
     * @param text expects string size eight (8) characters.
     * @return byte array that is a PNG image generated with text displayed.
     */
    public static byte[] generateImage(String text) {
        int width = 200, height = 60;
        Color backgroundColor = Color.WHITE;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.setColor(backgroundColor);
        g2.fillRect(0, 0, width, height);

        g2.setFont(arabicFont);
        //g2.setFont(new Font("Serif", Font.PLAIN, 26));
        g2.setColor(Color.blue);
        int start = 10;
        byte[] bytes = text.getBytes();

        Random random = new Random();
        for (int i = 0; i < bytes.length; i++) {
            g2.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            g2.drawString(new String(new byte[]{bytes[i]}), start + (i * 25), (int) (Math.random() * 30 + 30));
        }
        g2.setColor(backgroundColor);
        for (int i = 0; i < 8; i++) {
            g2.drawOval((int) (Math.random() * 160), (int) (Math.random() * 10), 30, 30);
        }
        //Draw Patterns
        drawWeavingLines(g2,width,height);
        drawBackgroundPattern(g2, width, height);
        // Draw weaving lines and noise
        drawNoise(g2, width, height);
        g2.dispose();
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            ImageIO.write(applyBlur(image), "png", bout);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bout.toByteArray();
    }

    private static BufferedImage applyBlur(BufferedImage src) {
        // Define a kernel for the blur effect
        float[] kernel = {
                1f / 16, 1f / 8, 1f / 16,
                1f / 8, 1f / 4, 1f / 8,
                1f / 16, 1f / 8, 1f / 16
        };
        Kernel blurKernel = new Kernel(3, 3, kernel);
        ConvolveOp blurOp = new ConvolveOp(blurKernel, ConvolveOp.EDGE_NO_OP, null);
        return blurOp.filter(src, null);
    }

    private static void drawBackgroundPattern(Graphics2D g2d, int width, int height) {
        g2d.setColor(new Color(220, 220, 220, 150)); // Light gray with transparency
        for (int i = 0; i < 50; i++) {
            int x1 = (int) (Math.random() * width);
            int y1 = (int) (Math.random() * height);
            int x2 = (int) (Math.random() * width);
            int y2 = (int) (Math.random() * height);
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }

    private static void drawWeavingLines(Graphics2D g2d, int width, int height) {
        Random random = new Random();
        int lineCount = 6;
        for (int i = 0; i < lineCount; i++) {
            int yStart = random.nextInt(height);
            g2d.setColor(new Color(100, 100, 255, 150)); // Semi-transparent blue
            g2d.draw(new Line2D.Double(0, yStart, width, yStart + random.nextInt(20) - 10));
        }
    }

    private static void drawNoise(Graphics2D g2d, int width, int height) {
        Random random = new Random();
        g2d.setColor(new Color(200, 200, 200, 150)); // Light gray noise
        for (int i = 0; i < 300; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g2d.fillRect(x, y, 3, 3); // Draw small squares for noise
        }
    }

}