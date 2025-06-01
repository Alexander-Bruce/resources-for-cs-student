package experiment3.backend;

import experiment3.backend.beans.DetailedInfoBean;
import experiment3.backend.beans.LoginBean;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetLogin
 */
@WebServlet("/GetLogin")
public class GetLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");

        if(username.equals("abc") && pwd.equals("abc")) {
        	response.sendRedirect("admin.jsp");
        	return;
        }

        DetailedInfoBean dbean = null;
        String forwardUrl = null;
        System.out.println(username + " " + pwd);
        //如果用户用户名和密码正确
        //则从detailinfo表中读出详细信息，产生用户详细信息的bean
        if (LoginBean.isExistsInLogintable(username, pwd)){//判断是否存在于用户表中或者用户名和密码是否正确
            dbean = DetailedInfoBean.getDetailedInfoBean(username);
            if(dbean == null){//说明用户只是注册了用户名和密码，没有注册详细信息,需要请用户输入详细注册信息
                forwardUrl = "detailedregister.jsp";
            }
            else{//设置bean共享，供视图层展现
                request.setAttribute("readdetailedinfobean", dbean);
                forwardUrl = "showdetailedinfo.jsp";
            }
        }
        else{//请用户重新登录
            forwardUrl = "loginagain.jsp";
        }
        RequestDispatcher dispatcher =
                request.getRequestDispatcher(forwardUrl);
        dispatcher.forward(request, response);
    }

}

