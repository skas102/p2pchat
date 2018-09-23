package views;

import views.util.ContactCellRenderer;

import javax.swing.*;
import java.awt.*;

public class ContactListView extends JPanel {
    private final int WIDTH = 240;

    private DefaultListModel listModel;

    public ContactListView() {
        setPreferredSize(new Dimension(WIDTH, 600));
        setLayout(new BorderLayout());

        createView();
        updateListData();
    }

    private void updateListData() {
        String[] dummy = {"Remo", "HSR"};

        for(String s : dummy){
            listModel.addElement(new Object[] {"icon", s});
        }
    }

    private void createView() {
        setBackground(Color.white);
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
    }

    private void createListView() {
        // todo add Generics
        listModel = new DefaultListModel();
        JList list = new JList(listModel);
        list.setPreferredSize(new Dimension(WIDTH, 88));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new ContactCellRenderer());

        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setBorder(null);

        add(listScroll, BorderLayout.CENTER);
    }
}
