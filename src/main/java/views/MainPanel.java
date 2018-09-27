package views;

import controllers.ChatController;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel(ChatController controller) {
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        add(new ContactListView(controller), BorderLayout.LINE_START);
//        add(new JButton("adf"), BorderLayout.CENTER);
    }
}
