package views;

import controllers.ChatController;
import models.*;
import repositories.ChatMessageListener;
import views.fragments.ChatDetailHeader;
import views.fragments.ChatHistoryFragment;
import views.fragments.MessageSendFragment;
import views.fragments.MessageSendListener;

import javax.swing.*;
import java.awt.*;

public class GroupChatDetailView extends JPanel implements MessageSendListener, ChatMessageListener {
    private ChatController controller;
    private GroupChat groupChat;
    private DefaultListModel<ChatMessage> lmGroupMessages;
    private ChatHistoryFragment groupChatHistory;
    private JTabbedPane tabbedPane;

    private ChatDetailHeader detailHeader;

    public GroupChatDetailView(ChatController controller, GroupChat groupChat) {
        this.controller = controller;
        this.groupChat = groupChat;
        this.controller.getMessageRepository().registerListener(this);

        createView();
        updateData();
    }

    private void updateData() {
        groupChat.getMessages().forEach(m -> lmGroupMessages.addElement(m));
    }

    private void createView() {
        setBackground(new Color(242, 242, 242));
        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(MainPanel.DETAIL_WIDTH, MainPanel.TOTAL_HEIGHT));

        detailHeader = new ChatDetailHeader(groupChat.getGroup().getName(),
                groupChat.getGroup().getMembers().size() + " members");
        add(detailHeader, BorderLayout.NORTH);

        createChatDetails();
        createMessageSend();
    }

    private void createChatDetails() {
        tabbedPane = new JTabbedPane();

        lmGroupMessages = new DefaultListModel<>();
        groupChatHistory = new ChatHistoryFragment(lmGroupMessages);

        tabbedPane.addTab("Messages", null, groupChatHistory);
        tabbedPane.addTab("Members", null, new JLabel("Coming soon"));

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void createMessageSend() {
        add(new MessageSendFragment(this), BorderLayout.SOUTH);
    }

    @Override
    public void onMessageReceived(Contact c, ChatMessage m) {
        if (c.getType() == ContactType.GROUP) {
            Group g = (Group) c;
            if (groupChat.getGroup().equals(g)) {
                lmGroupMessages.addElement(m);
            }
        }
    }

    @Override
    public void onSendClicked(String message) {
        controller.sendGroupMessage(groupChat.getGroup(), message);
    }
}
