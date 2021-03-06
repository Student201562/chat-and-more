package client;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

public class ClientGUI extends JPanel{
    String UserName;
    private ClientObject clientObject;

    private static Logger log = Logger.getLogger(ClientGUI.class.getName());

    private JTextField userNameBox = new JTextField();
    private JButton signInButton = new JButton("Sign in");
    private JButton signOutButton = new JButton("Sign out");

    private JTextArea chatBox = new JTextArea(10, 10);
    private JTextArea messageBox = new JTextArea();
    private JButton sendButton = new JButton("Send");

    ClientGUI() {
        setLayout(null);
        setFocusable(true);
        grabFocus();

        JLabel jLabel = new JLabel("Enter your name: ");
        jLabel.setBounds(10, 10, 100, 30);
        add(jLabel);

        userNameBox.setBounds(120, 10, 270, 30);
        add(userNameBox);

        signInButton.setBounds(400, 10, 100, 30);
        signInButton.setEnabled(false); //Чтобы сделать кнопку активной и позволить
        // реагировать на действия пользователя или наоборот
        // деактивировать её используется метод setEnabled
        add(signInButton);

        signOutButton.setBounds(510, 10, 100, 30);
        signOutButton.setEnabled(false);
        add(signOutButton);

        chatBox.setEditable(false); // запретить пользователю вводить текст в неправильное текстовое поле
        chatBox.setVisible(true);
        chatBox.setLineWrap(true); // перенос строки

        DefaultCaret caret = (DefaultCaret) chatBox.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane scroll = new JScrollPane(chatBox);
        scroll.setBounds(10,50,600,300);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scroll);

        messageBox.setBounds(10,360,490,100);
        messageBox.setEnabled(false);
        add(messageBox);

        sendButton.setBounds(510,395,100,30);
        sendButton.setEnabled(false);
        add(sendButton);

        userNameBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER && userNameBox.getText().trim().length() > 0)
                {
                    signInButton_Click();
                }
                if (userNameBox.getText().trim().length() > 0) {
                    signInButton.setEnabled(true);
                } else {
                    signInButton.setEnabled(false);
                }
            }
        });
        signInButton.addActionListener(e -> signInButton_Click());
        signOutButton.addActionListener(e-> signOutButton_Click());

        messageBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER && userNameBox.getText().trim().length() > 0){
                    sendButton_Click();
                }
            }
        });

        sendButton.addActionListener(e->sendButton_Click());
        log.info("Chat GUI created");
    }

    private void signInButton_Click() {
        log.info("Sign in button pressed");

        if (userNameBox.getText().trim().length() > 0) {
            UserName = userNameBox.getText().trim();

            clientObject = new ClientObject(this);
            new Thread(clientObject).start();

        } else {
            showErrorMessage("Fill the name!");
            log.info("Field name is empty");
        }
    }

    private void signOutButton_Click() {
        log.info("Sign out button pressed");

        signInButton.setEnabled(true);
        signOutButton.setEnabled(false);
        userNameBox.setEnabled(true);
        messageBox.setText("");
        chatBox.setText("");
        messageBox.setEnabled(false);
        sendButton.setEnabled(false);

        clientObject.disconnect();
    }

    private void sendButton_Click() {
        log.info("Send button pressed");

        if (messageBox.getText().trim().length() > 0) {
            String message = messageBox.getText();
            clientObject.sendMessage(message);

            messageBox.requestFocus();
            messageBox.setText("");
        }
    }

    void printMessage(String message) {
        chatBox.append(message + "\n");
        //chatBox.scrollRectToVisible(chatBox.getVisibleRect());
        //chatBox.paint(chatBox.getGraphics()); ////////////////

        log.info("Chat history updated");
    }

    void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null,message);
        log.info(message);
    }

    void startGUI() {
        userNameBox.setEnabled(false);
        signInButton.setEnabled(false);
        signOutButton.setEnabled(true);
        messageBox.setEnabled(true);
        sendButton.setEnabled(true);
        messageBox.requestFocusInWindow();
    }
}
