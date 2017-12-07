package chat.server;

import chat.network.TCPConnectionListener;
import chat.network.TCPconnection;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Logger;

// принимает входящие соединения и может держать несколько активных соединений и сможет рассылать сообщения
public class ChatServer implements TCPConnectionListener{ // сам сервер сделаем слушателем
    public static void main(String[] args) {
        new ChatServer();
    }

    private final ArrayList<TCPconnection> connection = new ArrayList<>();

    private ChatServer(){ // приватный , потому что мы его все равно запускаем внутри класса
        System.out.println("Server running ...");
        try(ServerSocket serverSocket = new ServerSocket(8189)){ // try с ресурсами, синтаксический сахар
            while(true){
                try {
                    new TCPconnection(this, serverSocket.accept()); // ждет новое соединение
                }catch (IOException e){
                    Logger logger = Logger.getLogger(ChatServer.class.getName());
                    logger.info("Info about exception");
                    logger.warning("TCPConnection exception: " + e);
                }
            } // запустили сервер в вечном цикле
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void onConnectionReady(TCPconnection tcpConnection) { // synchronized - чтобы одновременно нельзя было из разных потоков к ним попасть
        connection.add(tcpConnection);
        sendToAllConnection("Client connected: " + tcpConnection); // возращается toString
    }

    @Override
    public synchronized void onReceiveString(TCPconnection tcpConnection, String value) {
        // если приняли строчку, то рассылаем ее
        sendToAllConnection(value);
    }

    @Override
    public synchronized void onDisconnect(TCPconnection tcpConnection) {
        connection.remove(tcpConnection);
        sendToAllConnection("Client disconnected: " + tcpConnection);
    }

    @Override
    public synchronized void onException(TCPconnection tcpConnection, Exception e) {
        System.out.println("TCPconnection exception: " + e);
    }

    // метод, который будет рассылать всем клиентам сообщения
    private void sendToAllConnection(String value){
        System.out.println(value);
        for (int i = 0; i < connection.size(); i++){
            connection.get(i).sendString(value);
        }
    }
}
