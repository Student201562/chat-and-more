import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Connection {
    public static void main(String[] args) {
        int port = 8189;

        Server server = new Server(port);
        //
        ServletContextHandler context = new ServletContextHandler( ServletContextHandler.SESSIONS );
        context.setContextPath( "/" );

        context.addServlet(new ServletHolder(new Maker()), "/car_info");

        // Указываем серверу обработчик запросов.
        HandlerList handlers = new HandlerList(); // Список обработчиков событий, сохраненных за одно событие
        handlers.setHandlers(new Handler[] {context}); //Handler - это механизм, который позволяет работать с очередью сообщений. Он привязан к конкретному потоку (thread) и работает с его очередью. Handler умеет помещать сообщения в очередь.
        server.setHandler(handlers);

        StartServer(server);
    }
    private static void StartServer(Server server){
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
