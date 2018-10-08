package views.fragments;

import models.ChatMessage;

import javax.swing.*;

public class ChatHistoryFragment extends JScrollPane {
    private DefaultListModel<ChatMessage> listModel;

    public ChatHistoryFragment(DefaultListModel<ChatMessage> listModel) {
        this.listModel = listModel;
        createView();
    }

    private void createView() {
        JList list = new JList(listModel);
        setViewportView(list);
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }
}
