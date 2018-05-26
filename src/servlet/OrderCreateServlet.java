package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import bean.Order;
import bean.OrderItem;
import bean.User;
import dao.OrderDAO;
import dao.OrderItemDAO;

public class OrderCreateServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User u = (User) request.getSession().getAttribute("user");

        Order o = new Order();
        o.setUser(u);

        List<OrderItem> ois = (List<OrderItem>) request.getSession().getAttribute("ois");
        if (ois != null){
            // 同时Order的id会被确定
            new OrderDAO().insert(o);
        }
        for (OrderItem oi : ois) {
            oi.setOrder(o);
            new OrderItemDAO().insert(oi);
        }

        ois.clear();

        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().println("订单创建成功");

    }
}
