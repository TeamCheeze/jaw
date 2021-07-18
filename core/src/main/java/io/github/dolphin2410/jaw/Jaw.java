package io.github.dolphin2410.jaw;

import io.github.dolphin2410.jaw.util.async.Async;

import javax.swing.*;
import java.awt.*;

/**
 * Shark's jaws. Are you interested enough to look into this class?
 * @author dolphin2410
 */
public class Jaw {
    public static void main(String[] args) {
        Async.execute(() -> {
            JFrame frame = new JFrame();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            frame.setSize(new Dimension(200, 300));
            JLabel label = new JLabel("EASTER EGG!!");
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            frame.add(label);
            try {
                Thread.sleep(2000);
                System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
