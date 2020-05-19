package lt.bit.java2.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

@WebServlet("/")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<html><body>");
        writer.println("<h1>Tegyvuoja Ąžuolai !!!</h1>");

        String name = req.getParameter("vardas");
        if (name == null) {
            writer.println("<h2>Niekas</h2>");
        } else {
            writer.println("<h2>Labas, " + name + "</h2>");
        }


        writer.println("</body></html>");
    }
}
