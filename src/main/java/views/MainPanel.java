package views;

import controllers.ChatController;
import models.Contact;
import models.Group;
import models.Person;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel implements MainPanelCallback {
    private EmptyDetailView emptyDetailView;

    public MainPanel(ChatController controller) {
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        emptyDetailView = new EmptyDetailView();
        add(new ContactListView(controller, this), BorderLayout.LINE_START);
        add(emptyDetailView, BorderLayout.CENTER);
    }

    @Override
    public void ShowContactDetail(Contact contact) {
        System.out.println(contact + " selected");
    }
}
