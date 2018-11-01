package views;

import controllers.ChatController;
import models.*;
import repositories.ChatMessageListener;
import util.ChatLogger;
import views.fragments.ChatDetailHeader;
import views.fragments.ChatHistoryFragment;
import views.fragments.MessageSendFragment;
import views.fragments.MessageSendListener;

import javax.swing.*;
import java.awt.*;

public class PrivateChatDetailView extends JPanel implements MessageSendListener, ChatMessageListener {
    private ChatController controller;
    private PrivateChat privateChat;
    private DefaultListModel<ChatMessage> lmPrivateMessages;
    private DefaultListModel<ChatMessage> lmNotaryMessages;

    private ChatHistoryFragment privateChatHistory;
    private ChatHistoryFragment notaryChatHistory;
    private JTabbedPane tabbedPane;
    private ChatDetailHeader detailHeader;

    public PrivateChatDetailView(ChatController controller, PrivateChat privateChat) {
        this.controller = controller;
        this.privateChat = privateChat;
        this.controller.getMessageRepository().registerListener(this);

        createView();
        updateData();
    }

    private void updateData() {
        privateChat.getPrivateMessages().forEach(m -> lmPrivateMessages.addElement(m));
    }

    private void createView() {
        setBackground(new Color(242, 242, 242));
        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(MainPanel.DETAIL_WIDTH, MainPanel.TOTAL_HEIGHT));

        detailHeader = new ChatDetailHeader(privateChat.getFriend().getName(), privateChat.getFriend().getStatusText());
        add(detailHeader, BorderLayout.NORTH);

        createChatDetails();
        createMessageSend();
    }

    private void createChatDetails() {
        tabbedPane = new JTabbedPane();

        lmPrivateMessages = new DefaultListModel<>();
        lmNotaryMessages = new DefaultListModel<>();

        privateChatHistory = new ChatHistoryFragment(lmPrivateMessages);
        notaryChatHistory = new ChatHistoryFragment(lmNotaryMessages);

        tabbedPane.addTab("Messages", null, privateChatHistory);
        tabbedPane.addTab("Notary Messages", null, notaryChatHistory);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void createMessageSend() {
        add(new MessageSendFragment(this), BorderLayout.SOUTH);
    }

    @Override
    public void onSendClicked(String message) {
        if (tabbedPane.getSelectedComponent() == privateChatHistory) {
            controller.sendPrivateMessage(privateChat.getFriend(), message);
        } else {

            controller.sendNotaryMessage(privateChat.getFriend(), message);
        }
    }

    @Override
    public void onMessageReceived(Contact c, ChatMessage m) {
        if (c.getType() == ContactType.PERSON) {
            Person sender = (Person) c;
            if (privateChat.getFriend().equals(sender)) {
                lmPrivateMessages.addElement(m);
            }
        }
    }
}
