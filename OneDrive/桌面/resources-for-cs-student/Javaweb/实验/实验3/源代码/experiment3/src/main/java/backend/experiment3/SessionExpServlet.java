package backend.experiment3;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "sessionExpServlet", value = "/hello-servlet")
public class SessionExpServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{


    }

    public void destroy() {

    }
}