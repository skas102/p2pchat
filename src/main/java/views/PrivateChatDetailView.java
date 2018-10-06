package views;

import controllers.ChatController;
import models.Person;
import views.fragments.MessageSendFragment;

import javax.swing.*;
import java.awt.*;

public class PrivateChatDetailView extends JPanel {
    private ChatController controller;
    private Person person;
    private DefaultListModel<String> messageListModel;

    public PrivateChatDetailView(ChatController controller, Person person) {
        this.controller = controller;
        this.person = person;

        createView();
        updateData();
    }

    private void updateData() {
        for (int i = 0; i < 10; i++) {
            messageListModel.addElement(String.format("Hello " + i));
        }
    }

    private void createView() {
        setBackground(new Color(27, 27, 27));
        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(MainPanel.DETAIL_WIDTH, MainPanel.TOTAL_HEIGHT));
        createHeader();
        createChatDetails();
        createMessageSend();
    }

    private void createHeader() {
        Box titlePanel = Box.createHorizontalBox();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setOpaque(true);
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titlePanel.setPreferredSize(new Dimension(MainPanel.DETAIL_WIDTH, 39));
        titlePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0,
                new Color(74, 126, 187)));

        JLabel title = new JLabel(person.getName());
        title.setFont(new Font(title.getName(), Font.PLAIN, 18));
        titlePanel.add(Box.createHorizontalStrut(20));
        titlePanel.add(title);

        // todo - set data dynmaically
        JLabel onlineStatus = new JLabel(person.getStatusText());
        titlePanel.add(Box.createHorizontalStrut(8));
        titlePanel.add(onlineStatus, BorderLayout.EAST);

        add(titlePanel, BorderLayout.NORTH);
    }

    private void createChatDetails() {
        JTabbedPane tabbedPane = new JTabbedPane();

        messageListModel = new DefaultListModel<>();
        JList list = new JList(messageListModel);
        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        tabbedPane.addTab("Messages", null, listScroll);
        tabbedPane.addTab("Notary Messages", null, new JLabel("Hello 2"));

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void createMessageSend() {
        add(new MessageSendFragment(), BorderLayout.SOUTH);
    }

}
