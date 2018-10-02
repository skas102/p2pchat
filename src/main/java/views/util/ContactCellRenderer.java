package views.util;

import models.Contact;
import models.ContactType;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ContactCellRenderer implements ListCellRenderer<Contact> {

    private JLabel lbCell = new JLabel(" ", JLabel.LEFT);
    private Border lineBorder = BorderFactory.createLineBorder(Color.black, 1);
    private Border emptyBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
    private ImageIcon friendChat = new ImageIcon(getClass().getClassLoader().getResource("images/friend.png"));
    private ImageIcon groupChat = new ImageIcon(getClass().getClassLoader().getResource("images/group.png"));

    @Override
    public Component getListCellRendererComponent(JList list, Contact contact, int index, boolean isSelected, boolean cellHasFocus) {
        lbCell.setOpaque(true);
        lbCell.setIcon(contact.getType().equals(ContactType.PERSON) ? friendChat : groupChat);
        lbCell.setText(contact.getName());

        if (isSelected) {
            lbCell.setForeground(list.getSelectionForeground());
            lbCell.setBackground(list.getSelectionBackground());
        } else {
            lbCell.setForeground(list.getForeground());
            lbCell.setBackground(list.getBackground());
        }

        lbCell.setBorder(cellHasFocus ? lineBorder : emptyBorder);

        return lbCell;
    }
}
