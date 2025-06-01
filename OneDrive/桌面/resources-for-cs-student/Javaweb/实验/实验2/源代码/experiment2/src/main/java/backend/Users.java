package backend;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@WebServlet(name = "UserServlet", urlPatterns = "/user/*")
public class Users extends HttpServlet {

    private String name = null;
    private String date = null;
    private String school = null;
    private String desc = null;
    private String lastLoginTime = null;
    private String lastLogOutTime = null;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        String path = request.getPathInfo();

        try {
            if ("/login".equals(path)) {
                Cookie[] cookies = request.getCookies();
                RequestDispatcher dispatcher = null;

                if (cookies == null || cookies.length <= 3) {
                    dispatcher = request.getRequestDispatcher("/register.jsp");
                    dispatcher.forward(request, response);
                } else {

                    for (Cookie cookie: cookies) {
                        String cookiesName = cookie.getName();
                        String cookiesVal = cookie.getValue();

                        if (cookiesName.equals("name")) name = cookiesVal;
                        if (cookiesName.equals("date")) date = cookiesVal;
                        if (cookiesName.equals("school")) school = cookiesVal;
                        if (cookiesName.equals("description")) desc = cookiesVal;
                        if (cookiesName.equals("lastLoginTime")) lastLoginTime = cookiesVal;
                        if (cookiesName.equals("lastLogoutTime")) lastLogOutTime = cookiesVal;
                    }

                    request.setAttribute("name", name);
                    request.setAttribute("date", date);
                    request.setAttribute("school", school);
                    request.setAttribute("description", desc);
                    request.setAttribute("lastLogin", lastLoginTime);
                    request.setAttribute("lastLogOut", lastLogOutTime);

                    request.getRequestDispatcher("/info.jsp").forward(request, response);
                }
            } else if ("/logout".equals(path)) {
                response.addCookie(new Cookie("lastLogoutTime", LocalDateTime.now().toString()));
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else if ("/register".equals(path)) {
                Cookie[] preCookies = request.getCookies();
                for (Cookie cookie: preCookies) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }

                name = request.getParameter("name");
                date = request.getParameter("date");
                school = request.getParameter("school");
                desc = request.getParameter("description");

                List<Cookie> cookies = new LinkedList<>();
                cookies.add(new Cookie("name", name));
                cookies.add(new Cookie("date", date));
                cookies.add(new Cookie("school", school));
                cookies.add(new Cookie("description", desc));
                cookies.forEach(cookie -> cookie.setMaxAge(60));
                cookies.add(new Cookie("lastLoginTime", LocalDateTime.now().toString()));

                for(Cookie cookie: cookies) {
                    response.addCookie(cookie);
                }

                response.sendRedirect(request.getContextPath() + "/user/login");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println("error occurs: " + e.getMessage());
        }

    }

}
