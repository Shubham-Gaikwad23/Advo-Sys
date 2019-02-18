package advosys;

import java.awt.*;
import javax.swing.*;

public class SplashScreen extends JWindow {

    public SplashScreen() {
        int duration = 1500;
        JPanel content = (JPanel) getContentPane();
        // Set the window's bounds, centering the window
        int width = 580;
        int height = 280;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);
        // Build the splash screen
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("res/images/advosys.png")));
        content.add(label, BorderLayout.CENTER);
        Color color = new Color(0, 04, 5);
        content.setBorder(BorderFactory.createLineBorder(color, 1));
        // Display it
        setVisible(true);
        // Wait a little while, maybe while loading resources
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
        }
        this.setVisible(false);
        this.dispose();
    }
}
