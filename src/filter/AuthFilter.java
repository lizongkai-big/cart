package filter;

import bean.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class AuthFilter implements Filter {
    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取来路用户的ip地址
        String ip = request.getRemoteAddr();
        // 获取用户访问的页面地址
        String url = request.getRequestURL().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String date = sdf.format(d);

        System.out.printf("%s %s 访问了 %s%n", date, ip, url);

        // 浏览器发出请求的资源名部分
        String uri = request.getRequestURI();
        System.out.println("uri: " + uri);
        String pattern = ".*\\.(js|jpg|png|css)";
        // 首先判断是否是访问的login.html和login，因为这两个页面就是在还没有登陆之前就需要访问的
        if (uri.endsWith("login.jsp") || uri.endsWith("login") || Pattern.matches(pattern, uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 从Session中获取userName，如果没有，就表示不曾登陆过，跳转到登陆页面。
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            // 相对路径，但是如果当前目录不和login.html同一个目录呢？
            response.sendRedirect("login.jsp");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
