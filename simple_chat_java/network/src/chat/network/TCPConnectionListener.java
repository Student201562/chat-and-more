package chat.network;
// Этот интерфейс нужен, чтобы дважды не реализовывать одно и тоже на сервере и клиенте, там всего лишь меняется код, а суть одна
public interface TCPConnectionListener {
// это по сути события, аналогия с кнопками
    void onConnectionReady(TCPconnection tcpConnection); //когда запустили соединение
    void onReceiveString(TCPconnection tcpConnection, String value); // когда приняла строчку
    void onDisconnect(TCPconnection tcpConnection); // когда соединение порвалось
    void onException(TCPconnection tcpConnection, Exception e); // когда где-то случилось исключение
}
