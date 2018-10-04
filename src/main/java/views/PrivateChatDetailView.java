package views;

import controllers.ChatController;

import javax.swing.*;

public class PrivateChatDetailView extends JPanel {
    private ChatController controller;

    public PrivateChatDetailView(ChatController controller){
        this.controller = controller;
        this.add(new JButton("Hello"));
    }


}
