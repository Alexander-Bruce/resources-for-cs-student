package exp.experiment1;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import org.apache.commons.text.StringEscapeUtils;

@WebServlet("/GetResume")
public class GetResume extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String dob = request.getParameter("dob");
        String school = request.getParameter("school");
        String summary = request.getParameter("summary");

        name = StringEscapeUtils.escapeHtml4(name != null ? name : "N/A");
        dob = StringEscapeUtils.escapeHtml4(dob != null ? dob : "N/A");
        school = StringEscapeUtils.escapeHtml4(school != null ? school : "N/A");
        summary = StringEscapeUtils.escapeHtml4(summary != null ? summary : "N/A");

        out.println("<html><head><title>简历</title>");
        out.println("<meta charset='UTF-8'>");
        out.println("<style>");
        out.println("body { display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f4f4f4; flex-direction: column; }");
        out.println("h2 { text-align: center; }");
        out.println("table { border-collapse: collapse; width: 50%; margin-top: 20px; }");
        out.println("th, td { padding: 10px; text-align: left; }");
        out.println("th { color: black; }");
        out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<h2>简历信息</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>姓名</th><td>" + name + "</td></tr>");
        out.println("<tr><th>生日</th><td>" + dob + "</td></tr>");
        out.println("<tr><th>毕业院校</th><td>" + school + "</td></tr>");
        out.println("<tr><th>个人简介</th><td>" + summary + "</td></tr>");
        out.println("</table>");
        out.println("</body></html>");
    }
}
