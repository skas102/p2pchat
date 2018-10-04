package views;

import controllers.ChatController;
import models.Contact;
import models.ContactType;
import models.Person;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel implements MainPanelCallback {
    private EmptyDetailView emptyDetailView;
    private PrivateChatDetailView privateChatView;

    private ChatController controller;

    public MainPanel(ChatController controller) {
        this.controller = controller;
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        emptyDetailView = new EmptyDetailView();
        add(new ContactListView(controller, this), BorderLayout.LINE_START);
        add(emptyDetailView, BorderLayout.CENTER);
    }

    @Override
    public void ShowContactDetail(Contact contact) {
        removeCurrentDetailView();

        if(contact.getType() == ContactType.PERSON){
            privateChatView = new PrivateChatDetailView(controller, (Person) contact);
            add(privateChatView, BorderLayout.CENTER);
        }else if(contact.getType() == ContactType.GROUP){
            // todo
        }
        revalidate();
        repaint();
    }

    private void removeCurrentDetailView(){
        BorderLayout lm = (BorderLayout) getLayout();
        remove(lm.getLayoutComponent(BorderLayout.CENTER));
    }
}
