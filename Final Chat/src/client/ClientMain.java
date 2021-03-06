package client;

import javax.swing.*;

public class ClientMain {
    JFrame window;

    public ClientMain(){
        window = new JFrame("Welcome!!!");
        window.setSize(800, 650);
        window.add(new ClientGUI());
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientMain();
            }
        });
    }
}
