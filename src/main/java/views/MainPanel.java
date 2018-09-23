package views;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel(){
        setPreferredSize(new Dimension(720, 720));
        setLayout(new BorderLayout());
        add(new JButton("hhelo"), BorderLayout.EAST);
        add(new JButton("adf"), BorderLayout.CENTER);
    }
}
