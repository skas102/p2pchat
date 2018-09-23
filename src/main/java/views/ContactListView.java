package views;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ContactListView extends JPanel {
    private final int WIDTH = 240;

    public ContactListView() {
        setPreferredSize(new Dimension(WIDTH, 600));
        setLayout(new BorderLayout());

        createView();
    }

    private void createView() {
        setBackground(Color.white);
        createTitleBox();
    }

    private void createTitleBox() {
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
        titlePanel.setPreferredSize(new Dimension(WIDTH, 40));
        titlePanel.setBackground(Color.white);
        titlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                new Color(74, 126, 187)));

        JLabel title = new JLabel("Contact List");
        title.setFont(new Font(title.getName(), Font.PLAIN, 18));
        title.setBackground(Color.red);
        titlePanel.add(title);

        ImageIcon addIcon = new ImageIcon(getClass().getClassLoader().getResource("images/add-friend2.png"));
        JLabel lblAdd = new JLabel(addIcon);
        titlePanel.add(lblAdd);

        add(titlePanel, BorderLayout.NORTH);
        add(lblAdd, BorderLayout.CENTER);
    }
}
