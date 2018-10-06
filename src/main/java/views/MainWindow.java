package views;

import controllers.ChatController;

import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow(ChatController controller) {
        add(new MainPanel(controller));
        setTitle("P2PChat " + controller.getContactRepository().getSelf().getName());
        showFrame();
    }

    private void showFrame() {
        pack();
        setResizable(false);
        setVisible(true);
        setLocation(200, 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
