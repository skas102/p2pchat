package views;

import controllers.ChatController;
import models.Person;

import javax.swing.*;
import java.awt.*;

public class PrivateChatDetailView extends JPanel {
    private ChatController controller;
    private Person person;

    public PrivateChatDetailView(ChatController controller, Person person) {
        this.controller = controller;
        this.person = person;

        createView();
    }

    private void createView(){
        setBackground(new Color(23,42, 142));
        setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
        add(new JButton(person.getName()), BorderLayout.CENTER);
    }

}
