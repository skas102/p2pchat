package views;

import javax.swing.*;
import java.awt.*;

public class EmptyDetailView extends JPanel {
    public EmptyDetailView(){
        setBackground(new Color(242, 242, 242));

        JLabel lbl = new JLabel("Please select a contact to see the detail view");
        lbl.setHorizontalAlignment(JLabel.CENTER);
        setLayout(new BorderLayout(10, 10));
        this.add(lbl, BorderLayout.CENTER);
    }
}