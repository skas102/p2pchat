package views;

import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        add(new MainPanel());
        showFrame();
    }

    private void showFrame() {
        setTitle("P2P Chat");
        pack();
        setResizable(false);
        setVisible(true);
        setLocation(200, 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
