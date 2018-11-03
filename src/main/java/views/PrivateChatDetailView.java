package views;

import controllers.ChatController;
import models.*;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import repositories.ChatMessageListener;
import util.ChatLogger;
import views.fragments.ChatDetailHeader;
import views.fragments.ChatHistoryFragment;
import views.fragments.MessageSendFragment;
import views.fragments.MessageSendListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.CompletableFuture;

public class PrivateChatDetailView extends JPanel implements MessageSendListener, ChatMessageListener {
    private ChatController controller;
    private PrivateChat privateChat;
    private DefaultListModel<ChatMessage> lmPrivateMessages;
    private DefaultListModel<NotaryMessage> lmNotaryMessages;

    private ChatHistoryFragment<ChatMessage> privateChatHistory;
    private ChatHistoryFragment<NotaryMessage> notaryChatHistory;
    private JTabbedPane tabbedPane;
    private ChatDetailHeader detailHeader;

    private Person self;

    public PrivateChatDetailView(ChatController controller, PrivateChat privateChat) {
        this.controller = controller;
        this.privateChat = privateChat;
        this.controller.getMessageRepository().registerListener(this);
        this.self = controller.getSelf();

        createView();
        updateData();
    }

    private void updateData() {
        privateChat.getPrivateMessages().forEach(m -> lmPrivateMessages.addElement(m));
        privateChat.getNotaryMessages().forEach(m -> lmNotaryMessages.addElement(m));
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

        privateChatHistory = new ChatHistoryFragment<>(lmPrivateMessages);
        notaryChatHistory = new ChatHistoryFragment<>(lmNotaryMessages);

        tabbedPane.addTab("Messages", null, privateChatHistory);
        tabbedPane.addTab("Notary Messages", null, notaryChatHistory);

        add(tabbedPane, BorderLayout.CENTER);

        notaryChatHistory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if( e.getButton() == MouseEvent.BUTTON3) { // right click
                    int index = notaryChatHistory.getList().getSelectedIndex();
                    NotaryMessage m = notaryChatHistory.getList().getSelectedValue();

                    if (m.getState() == NotaryState.PENDING) {
                        getNotaryMessagePopup(m, index).show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });
    }

    private JPopupMenu getNotaryMessagePopup(NotaryMessage m, int index) {
        JPopupMenu popup = new JPopupMenu();

        if (m.getSender().equals(self.getName())) {
            JMenuItem checkItem = new JMenuItem("Check state");
            popup.add(checkItem);
        } else {
            JMenuItem acceptItem = new JMenuItem("Accept Message");
            popup.add(acceptItem);
            JMenuItem rejectItem = new JMenuItem("Reject Message");
            popup.add(rejectItem);

//            acceptItem.addActionListener(e -> confirmFriend(requester, index));
//            rejectItem.addActionListener(e -> rejectFriend(requester, index));
        }

        return popup;
    }

    private void createMessageSend() {
        add(new MessageSendFragment(this), BorderLayout.SOUTH);
    }

    @Override
    public void onSendClicked(String message) {
        if (tabbedPane.getSelectedComponent() == privateChatHistory) {
            controller.sendPrivateMessage(privateChat.getFriend(), message);
        } else {
            try {
                CompletableFuture<TransactionReceipt> txFuture = controller
                        .sendNotaryMessage(privateChat.getFriend(), message);

                txFuture.thenAccept(tx -> {
                            ChatLogger.info(String.format("addMessageHash Transaction completed, hash=%s",
                                    tx.getTransactionHash()));
                            JOptionPane.showMessageDialog(null, "Message hash is successfully added to the contract");
                        }
                ).exceptionally(ex -> {
                    ChatLogger.error(ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Transaction failed");
                    return null;
                });
            } catch (Exception e) {
                ChatLogger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMessageReceived(Contact c, ChatMessage m) {
        if (c.getType() == ContactType.PERSON) {
            Person sender = (Person) c;
            if (privateChat.getFriend().equals(sender)) {
                if (m instanceof NotaryMessage) {
                    lmNotaryMessages.addElement((NotaryMessage) m);
                } else {
                    lmPrivateMessages.addElement(m);
                }
            }
        }
    }
}
