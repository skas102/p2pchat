package views.fragments;

import models.ChatMessage;

import javax.swing.*;
import java.awt.event.MouseAdapter;

public class ChatHistoryFragment<T extends ChatMessage> extends JScrollPane {
    private DefaultListModel<T> listModel;
    private JList<T> list;

    public ChatHistoryFragment(DefaultListModel<T> listModel) {
        this.listModel = listModel;
        createView();
    }

    private void createView() {
        list = new JList<>(listModel);
        setViewportView(list);
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    public void addMouseListener(MouseAdapter adapter) {
        list.addMouseListener(adapter);
    }

    public JList<T> getList() {
        return list;
    }
}
