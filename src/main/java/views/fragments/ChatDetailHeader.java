package views.fragments;

import views.MainPanel;

import javax.swing.*;
import java.awt.*;

public class ChatDetailHeader extends Box{
    private JLabel lblTitle;
    private JLabel lblSubtitle;

    public ChatDetailHeader(String title, String subtitle){
        super(BoxLayout.X_AXIS);
        createView(title, subtitle);
    }

    private void createView(String title, String subtitle) {
        setBackground(Color.WHITE);
        setOpaque(true);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setPreferredSize(new Dimension(MainPanel.DETAIL_WIDTH, 39));
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0,
                new Color(74, 126, 187)));

        lblTitle = new JLabel(title);
        lblTitle.setFont(new Font(lblTitle.getName(), Font.PLAIN, 18));
        add(Box.createHorizontalStrut(20));
        add(lblTitle);

        lblSubtitle = new JLabel(subtitle);
        lblSubtitle.add(Box.createHorizontalStrut(8));
        add(lblSubtitle, BorderLayout.EAST);
    }

    public void updateText(String title, String subtitle){
        lblTitle.setText(title);
        lblSubtitle.setText(subtitle);
    }
}
