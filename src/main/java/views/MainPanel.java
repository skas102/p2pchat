package views;

import controllers.ChatController;
import models.*;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel implements MainPanelCallback {
    public static final int TOTAL_WIDTH = 800;
    public static final int TOTAL_HEIGHT = 600;

    public static final int LIST_WIDTH = 240;
    public static final int DETAIL_WIDTH = TOTAL_WIDTH - LIST_WIDTH;

    private EmptyDetailView emptyDetailView;
    private PrivateChatDetailView privateChatView;
    private GroupChatDetailView groupChatView;

    private ChatController controller;

    public MainPanel(ChatController controller) {
        this.controller = controller;
        setPreferredSize(new Dimension(TOTAL_WIDTH, TOTAL_HEIGHT));
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        emptyDetailView = new EmptyDetailView();
        add(new ContactListView(controller, this), BorderLayout.LINE_START);
        add(emptyDetailView, BorderLayout.CENTER);
    }

    @Override
    public void ShowContactDetail(Contact contact) {
        if (contact == null) {
            return;
        }
        removeCurrentDetailView();

        if (contact.getType() == ContactType.PERSON) {
            Person p = (Person) contact;
            PrivateChat privateChat = controller.getMessageRepository().getPrivateChat(p);
            privateChatView = new PrivateChatDetailView(controller, privateChat);
            add(privateChatView, BorderLayout.CENTER);
        } else if (contact.getType() == ContactType.GROUP) {
            Group g = (Group) contact;
            GroupChat groupChat = controller.getMessageRepository().getGroupChat(g);
            groupChatView = new GroupChatDetailView(controller, groupChat);
            add(groupChatView, BorderLayout.CENTER);
        }
        revalidate();
        repaint();
    }

    private void removeCurrentDetailView() {
        BorderLayout lm = (BorderLayout) getLayout();
        remove(lm.getLayoutComponent(BorderLayout.CENTER));
    }
}
