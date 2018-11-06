package views;

import controllers.ChatController;
import models.Contact;
import models.ContactType;
import models.Group;
import models.Person;
import repositories.ContactListener;
import repositories.ContactRepository;
import util.ChatLogger;
import views.util.ContactCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ContactListView extends JPanel implements ContactListener {
    private final int WIDTH = 240;

    private ChatController controller;
    private ContactRepository repo;
    private MainPanelCallback callback;

    private DefaultListModel<Contact> contactListModel;
    private JList<Contact> contactList;
    private DefaultListModel<Person> myFriendRequests;
    private DefaultListModel<Person> incomingFriendRequests;

    public ContactListView(ChatController controller, MainPanelCallback callback) {
        this.controller = controller;
        this.repo = controller.getContactRepository();
        this.repo.registerListener(this);
        this.callback = callback;

        setPreferredSize(new Dimension(WIDTH, 600));
        setLayout(new BorderLayout());

        createView();
        updateContactList();
        updateMyRequestList();
        updateIncomingFriendRequestList();
    }

    private void updateContactList() {
        contactListModel.removeAllElements();
        for (Contact c : repo.getFriends().values()) {
            contactListModel.addElement(c);
        }

        for (Contact c : repo.getGroups().values()) {
            contactListModel.addElement(c);
        }
    }

    private void updateMyRequestList() {
        for (Person p : repo.getMyRequests()) {
            myFriendRequests.addElement(p);
        }
    }

    private void updateIncomingFriendRequestList() {
        for (Person p : repo.getIncomingRequests()) {
            incomingFriendRequests.addElement(p);
        }
    }

    private void createView() {
        setBackground(Color.white);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(74, 126, 187)));

        createTileView();
        createListView();
        createFriendRequestsTab();
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
        lblGroupAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createGroup();
            }
        });
    }

    private void createListView() {
        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);
        contactList.setPreferredSize(new Dimension(WIDTH - 1, 50));
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.setCellRenderer(new ContactCellRenderer());

        JScrollPane listScroll = new JScrollPane(contactList);
        listScroll.setBorder(null);

        add(listScroll, BorderLayout.CENTER);

        contactList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Contact selectedContact = (Contact) contactList.getSelectedValue();
                if (e.getButton() == MouseEvent.BUTTON1) { // left click
                    callback.ShowContactDetail(selectedContact);
                } else if (e.getButton() == MouseEvent.BUTTON3) { // right click
                    getContactPopupMenu(selectedContact, contactList.getSelectedIndex())
                            .show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private void addFriend() {
        String username = JOptionPane.showInputDialog("Enter the username of your friend");

        try {
            if (username != null) {
                Person p = controller.addFriend(username);
                myFriendRequests.addElement(p);
            }
        } catch (IOException | ClassNotFoundException e) {
            ChatLogger.error("Adding friend failed " + e.getMessage());
        }
    }

    private void createGroup() {
        ArrayList<JComponent> inputs = new ArrayList<>();
        JTextField groupName = new JTextField();

        inputs.add(new JLabel("Group Name"));
        inputs.add(groupName);

        ArrayList<JCheckBox> cbFriends = new ArrayList<>();
        Map<String, Person> friends = repo.getFriends();
        for (Person p : friends.values()) {
            cbFriends.add(new JCheckBox(p.getName()));
        }
        inputs.addAll(cbFriends);

        int result = JOptionPane.showConfirmDialog(null, inputs.toArray(), "Group Creation",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                java.util.List<Person> members = new ArrayList<>();
                for (JCheckBox cb : cbFriends) {
                    if (cb.isSelected()) {
                        members.add(friends.get(cb.getText()));
                    }
                }

                controller.createGroup(groupName.getText(), members);
            } catch (Exception e) {
                ChatLogger.error("Create a new group failed " + e.getMessage());
            }
        }
    }

    private void confirmFriend(Person requester, int index) {
        controller.confirmFriend(requester);
        incomingFriendRequests.remove(index);
    }

    private void rejectFriend(Person requester, int index) {
        controller.rejectFriend(requester);
        incomingFriendRequests.remove(index);
    }

    private void removeFriend(Person friend) {
        controller.removeFriend(friend);
    }

    private void addMember(Group group) {

    }

    private void createFriendRequestsTab() {
        JTabbedPane tabbedPane = new JTabbedPane();

        incomingFriendRequests = new DefaultListModel<>();
        JList listIncomingRequests = new JList(incomingFriendRequests);
        listIncomingRequests.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabbedPane.addTab("Incoming Requests", null, listIncomingRequests);

        myFriendRequests = new DefaultListModel<>();
        JList listMyRequests = new JList(myFriendRequests);
        listMyRequests.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabbedPane.addTab("My Requests", null, listMyRequests);

        add(tabbedPane, BorderLayout.SOUTH);

        listIncomingRequests.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = listIncomingRequests.getSelectedIndex();
                Person requester = (Person) listIncomingRequests.getSelectedValue();
                getRequestPopupMenu(requester, index).show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }

    @Override
    public void onIncomingFriendRequest(Person p) {
        incomingFriendRequests.addElement(p);
    }

    @Override
    public void onMyFriendRequestRemoved(Person p) {
        myFriendRequests.removeElement(p);
    }

    @Override
    public void onFriendRemoved(Person friend) {
        if (contactList.getSelectedValue().equals(friend)) {
            callback.RemoveDetail();
        }

        updateContactList();
    }

    @Override
    public void onContactListUpdated() {
        updateContactList();
    }

    private JPopupMenu getRequestPopupMenu(Person requester, int index) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem acceptItem = new JMenuItem("Accept Request");
        popup.add(acceptItem);
        JMenuItem rejectItem = new JMenuItem("Reject Request");
        popup.add(rejectItem);

        acceptItem.addActionListener(e -> confirmFriend(requester, index));
        rejectItem.addActionListener(e -> rejectFriend(requester, index));

        return popup;
    }

    private JPopupMenu getContactPopupMenu(Contact contact, int index) {
        JPopupMenu popup = new JPopupMenu();

        if (contact.getType() == ContactType.PERSON) {
            JMenuItem removeFriendItem = new JMenuItem("Remove");
            popup.add(removeFriendItem);
            removeFriendItem.addActionListener(e -> removeFriend((Person) contact));
        } else if (contact.getType() == ContactType.GROUP) {
            JMenuItem addMemberItem = new JMenuItem("Add member");
            popup.add(addMemberItem);
            addMemberItem.addActionListener(e -> addMember((Group) contact));
        }
        return popup;
    }
}
