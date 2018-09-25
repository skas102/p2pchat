package views;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel() {
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        add(new ContactListView(), BorderLayout.LINE_START);
//        add(new JButton("adf"), BorderLayout.CENTER);
    }
}
