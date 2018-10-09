package views;

import controllers.ChatController;
import models.ChatMessage;
import models.Contact;
import models.GroupChat;
import repositories.ChatMessageListener;
import views.fragments.ChatDetailHeader;
import views.fragments.ChatHistoryFragment;
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

    public GroupChatDetailView(ChatController controller, GroupChat groupChat){
        this.controller = controller;
        this.groupChat = groupChat;
        this.controller.getMessageRepository().registerListener(this);

        createView();
    }

    private void createView() {
        setBackground(new Color(242, 242, 242));
        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(MainPanel.DETAIL_WIDTH, MainPanel.TOTAL_HEIGHT));

        detailHeader = new ChatDetailHeader(groupChat.getGroup().getName(),
                groupChat.getGroup().getMembers().size() + " members");
        add(detailHeader, BorderLayout.NORTH);
    }

    @Override
    public void onMessageReceived(Contact c, ChatMessage m) {

    }

    @Override
    public void onMessageSent(String message) {

    }
}
