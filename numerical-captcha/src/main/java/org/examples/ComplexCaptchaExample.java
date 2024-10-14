package org.examples;

import org.jboss.aerogear.security.otp.api.Base32;
import org.jboss.aerogear.security.otp.api.Hash;
import org.jboss.aerogear.security.otp.api.Hmac;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ComplexCaptchaExample extends JPanel {
    private BufferedImage captchaImage;
    private String captchaText;

    public ComplexCaptchaExample(String text) {
        this.captchaText = text;
        createCaptchaImage();
    }

    private void createCaptchaImage() {
        int width = 300;
        int height = 100;
        captchaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = captchaImage.createGraphics();

        // Set rendering hints for better quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Fill background with a light pattern
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillRect(0, 0, width, height);
        drawBackgroundPattern(g2d, width, height);

        // Draw weaving lines and noise
        drawWeavingLines(g2d, width, height);
        drawNoise(g2d, width, height);

        // Draw the CAPTCHA text
        drawCaptchaText(g2d, width, height);

        g2d.dispose();
    }

    private void drawBackgroundPattern(Graphics2D g2d, int width, int height) {
        g2d.setColor(new Color(220, 220, 220, 100)); // Light gray with transparency
        for (int i = 0; i < 50; i++) {
            int x1 = (int) (Math.random() * width);
            int y1 = (int) (Math.random() * height);
            int x2 = (int) (Math.random() * width);
            int y2 = (int) (Math.random() * height);
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }

    private void drawWeavingLines(Graphics2D g2d, int width, int height) {
        Random random = new Random();
        int lineCount = 10;
        g2d.setColor(new Color(100, 100, 255, 150)); // Semi-transparent blue
        for (int i = 0; i < lineCount; i++) {
            int yStart = random.nextInt(height);
            g2d.draw(new Line2D.Double(0, yStart, width, yStart + random.nextInt(20) - 10));
        }
    }

    private void drawNoise(Graphics2D g2d, int width, int height) {
        Random random = new Random();
        g2d.setColor(new Color(200, 200, 200, 150)); // Light gray noise

        for (int i = 0; i < 300; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g2d.fillRect(x, y, 3, 3); // Draw small squares for noise
        }
    }

    private void drawCaptchaText(Graphics2D g2d, int width, int height) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 36));

        // Center the text with random distortion
        FontMetrics metrics = g2d.getFontMetrics();
        int x = (width - metrics.stringWidth(captchaText)) / 2;
        int y = (height - metrics.getHeight()) / 2 + metrics.getAscent();

        // Draw text along a curve with distortion
        for (int i = 0; i < captchaText.length(); i++) {
            char c = captchaText.charAt(i);
            double angle = Math.sin(i) * 0.2; // Curve effect
            g2d.rotate(angle, x + i * 30 + randomOffset(), y + randomOffset());
            g2d.drawString(String.valueOf(c), x + i * 30 + randomOffset(), y + randomOffset());
            g2d.rotate(-angle, x + i * 30 + randomOffset(), y + randomOffset()); // Reset rotation
        }
    }

    private int randomOffset() {
        return (int) (Math.random() * 10 - 5); // Offset for distortion
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(captchaImage, 0, 0, null);
    }

    public static void main(String[] args) throws  Exception {
        String captchaText = "AB12C"; // Example CAPTCHA text
        ComplexCaptchaExample panel = new ComplexCaptchaExample(captchaText);
        JFrame frame = new JFrame("Complex CAPTCHA Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(400, 200);
        frame.setVisible(true);

    }
}