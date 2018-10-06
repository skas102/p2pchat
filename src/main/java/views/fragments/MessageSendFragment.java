package views.fragments;

import views.MainPanel;

import javax.swing.*;
import java.awt.*;

public class MessageSendFragment extends JPanel {
    private JTextArea messageBox;
    private JButton btnSend;

    public MessageSendFragment() {
        createView();
    }

    private void createView() {
        setOpaque(false);
        Box panel = Box.createHorizontalBox();
        panel.setPreferredSize(new Dimension(MainPanel.DETAIL_WIDTH, 50));
        panel.add(Box.createHorizontalStrut(5));

        messageBox = new JTextArea("test");
        messageBox.setRows(2);
        messageBox.setPreferredSize(new Dimension(MainPanel.DETAIL_WIDTH - 150, 50));
        messageBox.setEditable(true);
        panel.add(messageBox);

        btnSend = new JButton("Send");
        btnSend.setPreferredSize(new Dimension(80, 50));
        btnSend.setBackground(new Color(44, 93, 152));
        btnSend.setForeground(Color.WHITE);
        btnSend.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(Box.createHorizontalStrut(5));
        panel.add(btnSend);
        panel.add(Box.createHorizontalStrut(5));

        add(panel, BorderLayout.CENTER);
    }
}
