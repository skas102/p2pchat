package views;

import controllers.ChatController;
import models.Person;
import sun.tools.jstat.Alignment;

import javax.swing.*;
import java.awt.*;

public class PrivateChatDetailView extends JPanel {
    private ChatController controller;
    private Person person;

    public PrivateChatDetailView(ChatController controller, Person person) {
        this.controller = controller;
        this.person = person;

        createView();
    }

    private void createView(){
        setBackground(new Color(242,242, 242));
        setPreferredSize(new Dimension(MainPanel.DETAIL_WIDTH, MainPanel.TOTAL_HEIGHT));
        createHeader();
    }

    private void createHeader() {
        JPanel titlePanel = new JPanel(new BorderLayout(20, 20));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titlePanel.setPreferredSize(new Dimension(MainPanel.DETAIL_WIDTH, 30));
        titlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                new Color(74, 126, 187)));

        JLabel title = new JLabel(person.getName());
        title.setFont(new Font(title.getName(), Font.PLAIN, 18));
        titlePanel.add(title, BorderLayout.WEST);

        // todo - set data dynmaically
//        JLabel onlineStatus = new JLabel("online");
//        Box controlPanel = Box.createHorizontalBox();
//        controlPanel.add(Box.createHorizontalStrut(3));
//        controlPanel.add(title);
//        controlPanel.add(Box.createHorizontalStrut(3));
//        titlePanel.add(controlPanel, BorderLayout.EAST);

        add(titlePanel, BorderLayout.NORTH);
    }

}
