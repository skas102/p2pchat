package views.util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ContactCellRenderer implements ListCellRenderer {

    private JLabel lbCell = new JLabel(" ", JLabel.LEFT);
    private Border lineBorder = BorderFactory.createLineBorder(Color.black,1);
    private Border emptyBorder = BorderFactory.createEmptyBorder(2,2,2,2);
    private ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("images/friend.png"));

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Object[] pair = (Object[]) value;

        lbCell.setOpaque(true);
        lbCell.setIcon(icon);
        lbCell.setText(pair[1].toString());

        if(isSelected){
            lbCell.setForeground(list.getSelectionForeground());
            lbCell.setBackground(list.getSelectionBackground());
        }
        else{
            lbCell.setForeground(list.getForeground());
            lbCell.setBackground(list.getBackground());
        }

        lbCell.setBorder(cellHasFocus ? lineBorder : emptyBorder);

        return lbCell;
    }
}
