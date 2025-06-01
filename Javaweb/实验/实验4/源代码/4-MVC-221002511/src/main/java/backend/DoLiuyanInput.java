package backend;



import backend.domain.LiuyanBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DoLiuyanInput", value = "/submit")
public class DoLiuyanInput extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String content = request.getParameter("content");

        // 创建 LiuyanBean 实例并设置属性
        LiuyanBean liuyanBean = new LiuyanBean();
        liuyanBean.setUsername(username);
        liuyanBean.setContent(content);

        // 将 bean 存储到会话中
        request.getSession().setAttribute("message", liuyanBean);

        if (username == null || username.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            request.getRequestDispatcher("inputAgain.jsp").forward(request, response);
            return;
        }

        if (content.length() < 5) {
            request.setAttribute("username", username);
            request.setAttribute("errorMessage", "留言内容必须至少包含5个字符。");
            request.getRequestDispatcher("inputError.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("inputOk.jsp");
    }
}
