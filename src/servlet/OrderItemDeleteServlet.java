package servlet;

import bean.OrderItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class OrderItemDeleteServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("oid"));
        List<OrderItem> ois = (List<OrderItem>) request.getSession().getAttribute("ois");

        for (int i = 0; i < ois.size(); i++) {
            if (ois.get(i).getProduct().getId() == id) {
                ois.remove(ois.get(i));
                break;
            }
        }

        response.sendRedirect("listOrderItem.jsp");
    }
}
