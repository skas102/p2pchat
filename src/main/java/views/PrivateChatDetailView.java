package views;

import controllers.ChatController;
import models.*;
import repositories.ChatMessageListener;
import util.ChatLogger;
import views.fragments.ChatHistoryFragment;
import views.fragments.MessageSendFragment;
import views.fragments.MessageSendListener;

import javax.swing.*;
import java.awt.*;

public class PrivateChatDetailView extends JPanel implements MessageSendListener, ChatMessageListener {
    private ChatController controller;
    private PrivateChat privateChat;
    private DefaultListModel<ChatMessage> lmPrivateMessages;
    private ChatHistoryFragment privateChatHistory;
    private JTabbedPane tabbedPane;

    public PrivateChatDetailView(ChatController controller, PrivateChat privateChat) {
        this.controller = controller;
        this.privateChat = privateChat;
        this.controller.getMessageRepository().registerListener(this);

        createView();
        updateData();
    }

    private void updateData() {
        privateChat.getPrivateMessges().forEach(m -> lmPrivateMessages.addElement(m));
    }

    private void createView() {
        setBackground(new Color(242, 242, 242));
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

        JLabel title = new JLabel(privateChat.getFriend().getName());
        title.setFont(new Font(title.getName(), Font.PLAIN, 18));
        titlePanel.add(Box.createHorizontalStrut(20));
        titlePanel.add(title);

        // todo - set data dynmaically
        JLabel onlineStatus = new JLabel(privateChat.getFriend().getStatusText());
        titlePanel.add(Box.createHorizontalStrut(8));
        titlePanel.add(onlineStatus, BorderLayout.EAST);

        add(titlePanel, BorderLayout.NORTH);
    }

    private void createChatDetails() {
        tabbedPane = new JTabbedPane();

        lmPrivateMessages = new DefaultListModel<>();
        privateChatHistory = new ChatHistoryFragment(lmPrivateMessages);

        tabbedPane.addTab("Messages", null, privateChatHistory);
        tabbedPane.addTab("Notary Messages", null, new JLabel("Hello 2"));

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void createMessageSend() {
        add(new MessageSendFragment(this), BorderLayout.SOUTH);
    }

    @Override
    public void onMessageSent(String message) {
        if (tabbedPane.getSelectedComponent() == privateChatHistory) {
            controller.sendPrivateMessage(privateChat.getFriend(), message);
        } else {
            ChatLogger.error("Notary messages not implemented");
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
