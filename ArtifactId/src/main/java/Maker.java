import com.sun.net.httpserver.HttpServer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Maker")
public class Maker extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String car = req.getParameter("car");
        String numberCar = req.getParameter("numberCar");
        String seatsNumber = req.getParameter("seatsNumber");

        String joinStr = "Ваша машина: " + car + "\nEе номер: " + numberCar + "\nКоличество мест: " + seatsNumber;
        printRes(resp, joinStr);
    }

    private void printRes(HttpServletResponse response, String result) throws IOException { // response - ответ
        String reply = "{\"error\":0,\"result\":" + result + "}"; // в формате json что нет ошибок
        response.getOutputStream().write(reply.getBytes("UTF-8"));
        //response.setContentType("application/json; charset=UTF-8"); // если это не написать будет кракозябра
        //response.setHeader("Result", "*");
        response.setStatus(HttpServletResponse.SC_OK); // получить статус (200)
    }
}
