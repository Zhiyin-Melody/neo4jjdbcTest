package servlet;
/**
 * @Author:lizhiyin
 * @Description:servletTest
 * @Date: Created in 20180426
 * @Pram:
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class servletTest extends HttpServlet {
    public servletTest() {
        super();
    }

    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=gb2312");
        PrintWriter out = response.getWriter();
        request.getParameter("schemaparam");

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        out.flush();
        out.close();
    }

    public void init() throws ServletException {
        // Put your code here
    }
}
