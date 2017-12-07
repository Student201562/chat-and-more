package chat.client;

import chat.network.TCPConnectionListener;
import chat.network.TCPconnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Logger;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionListener {

    private static final String IP_ADRESS = "127.0.0.1";
    private static final int PORT = 8189;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    public static void main(String[] args) {
        // во всех графических интерфейсах есть ограничение по работе с многопоточностью
        // нельзя работать с разных потоков
        // надо работать с главного потока
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        });
    }

    private final JTextArea log = new JTextArea();
    private final JTextField fieldName = new JTextField("Gylever");
    private final JTextField fieldInput = new JTextField();

    private TCPconnection connection;

    private ClientWindow(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null); // по центру
        setAlwaysOnTop(true); // сделать его сверху

        log.setEditable(false);// запрещаем редактирование
        log.setLineWrap(true); // перенос слов
        add(log, BorderLayout.CENTER);

        fieldInput.addActionListener(this); // добавляем себя
        add(fieldInput, BorderLayout.SOUTH);
        add(fieldName, BorderLayout.NORTH);

        setVisible(true);

        try {
            connection = new TCPconnection(this, IP_ADRESS, PORT);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(ClientWindow.class.getName());
            logger.info("Info about exception");
            logger.warning("Exception connecting server: " + e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = fieldInput.getText();
        if(msg.equals("")) return;
        fieldInput.setText(null);
        connection.sendString(fieldName.getText() + ": " +msg);
    }


    @Override
    public void onConnectionReady(TCPconnection tcpConnection) {
        printMassage("Connection ready...");
    }

    @Override
    public void onReceiveString(TCPconnection tcpConnection, String value) {
        printMassage(value);
    }

    @Override
    public void onDisconnect(TCPconnection tcpConnection) {
        printMassage("Connection close");
    }

    @Override
    public void onException(TCPconnection tcpConnection, Exception e) {
        printMassage("Connection exception: " + e);
    }

    private synchronized void printMassage(String msg){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength()); // коретка в самый конец документа
            }
        });
    }

}
