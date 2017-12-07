package chat.network;

import sun.rmi.transport.tcp.TCPConnection;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class TCPconnection {

    private final Socket socket;
    private final Thread rxThread;
    private final TCPConnectionListener eventListener;
    private final BufferedReader in;
    private final BufferedWriter out;


    // сокет будет создоваться внутри
    public TCPconnection(TCPConnectionListener eventListener, String ipAdress, int port) throws IOException{
        this(eventListener, new Socket(ipAdress, port)); // вызываем первый конструктор
    }

    // первый конструктор расчитан, что кто - то снаружи сделает соединение (создаст сокет)
    public TCPconnection(TCPConnectionListener eventListener, Socket socket) throws IOException{
        this.eventListener = eventListener;
        this.socket = socket;
        // получаем входящий и исходящий поток
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    eventListener.onConnectionReady(TCPconnection.this); // TCPconnection.this я сделал так, потому что, если написать просто
                                                                                        // this без класса, то он возмет данные класса потока.
                    while (!rxThread.isInterrupted()){
                        String msg = in.readLine();
                        eventListener.onReceiveString(TCPconnection.this, msg);
                    }
                }catch (IOException e){
                    eventListener.onException(TCPconnection.this, e);
                }finally {
                    eventListener.onDisconnect(TCPconnection.this);
                }
            }
        });
        rxThread.start();
    }


    public synchronized void sendString(String value){ // synchronized для того чтобы можно было обращаться с разных потоков
        try {
            out.write(value + "\r\n"); //"\r\n" - чтобы сообщения выглядели нормально
            out.flush(); // Збрасывает буфферы и отправляет сообщение
        }catch (IOException e){
            eventListener.onException(TCPconnection.this, e);
            disconnect(); // у нас не получилось получить строчку вылетела ошибка, поэтому закрываем соединение
        }
    }

    public synchronized void disconnect(){
        rxThread.isInterrupted();
        try {
            socket.close();
        }catch (IOException e){
            eventListener.onException(TCPconnection.this, e);
        }
    }

    @Override
    public String toString(){
        return "TCPConnection " + socket.getInetAddress() + ": " + socket.getPort(); // формируем красивую строчку
    }
}
