package views;

import controllers.ChatController;
import util.ChatLogger;
import views.util.ContactCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ContactListView extends JPanel {
    private final int WIDTH = 240;

    private ChatController controller;
    private DefaultListModel listModel;

    public ContactListView(ChatController controller) {
        this.controller = controller;

        setPreferredSize(new Dimension(WIDTH, 600));
        setLayout(new BorderLayout());

        createView();
        updateListData();
    }

    private void updateListData() {
        Object[][] dummy = {{true, "Remo"}, {false, "HSR"}};

        for (Object[] data : dummy) {
            listModel.addElement(data);
        }
    }

    private void createView() {
        setBackground(Color.white);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(74, 126, 187)));

        createTileView();
        createListView();
    }

    private void createTileView() {
        JPanel titlePanel = new JPanel(new BorderLayout(20, 20));
        titlePanel.setBackground(Color.white);
        titlePanel.setPreferredSize(new Dimension(WIDTH, 40));
        titlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                new Color(74, 126, 187)));

        JLabel title = new JLabel("Contact List");
        title.setFont(new Font(title.getName(), Font.PLAIN, 18));
        titlePanel.add(title, BorderLayout.WEST);

        ImageIcon friendIcon = new ImageIcon(getClass().getClassLoader().getResource("images/add-friend2.png"));
        JLabel lblFriendAdd = new JLabel(friendIcon);
        lblFriendAdd.setSize(16, 16);
        ImageIcon groupIcon = new ImageIcon(getClass().getClassLoader().getResource("images/add-group.png"));
        JLabel lblGroupAdd = new JLabel(groupIcon);

        Box controlPanel = Box.createHorizontalBox();
        controlPanel.add(lblFriendAdd);
        controlPanel.add(Box.createHorizontalStrut(3));
        controlPanel.add(lblGroupAdd);
        controlPanel.add(Box.createHorizontalStrut(3));
        titlePanel.add(controlPanel, BorderLayout.EAST);

        add(titlePanel, BorderLayout.NORTH);

        // register click events
        lblFriendAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addFriend();
            }
        });
    }

    private void createListView() {
        // todo add Generics
        listModel = new DefaultListModel();
        JList list = new JList(listModel);
        list.setPreferredSize(new Dimension(WIDTH - 1, 88));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new ContactCellRenderer());

        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setBorder(null);

        add(listScroll, BorderLayout.CENTER);
    }

    private void addFriend() {
        String username = JOptionPane.showInputDialog("Enter the username of your friend");

        try {
            controller.addFriend(username);
        } catch (IOException | ClassNotFoundException e) {
            ChatLogger.error("Adding friend failed " + e.getMessage());
        }
    }
}
