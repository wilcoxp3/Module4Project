package wilcoxp3;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for the store login page.
 * @author Paul
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DataAccessObject<User> userDao = DataAccessObjectFactory.getUserDao();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean failed = true;

        if (username != null && password != null 
                && !username.equalsIgnoreCase("") && !password.equals("")) {
            for (User u : userDao.readAll()) {
                if (username.equalsIgnoreCase(u.getUsername())
                        && password.equals(u.getPassword())) {
                    req.getSession().setAttribute("currentUser", u);
                    resp.sendRedirect("inventory");
                    failed = false;
                }
            }
        } else if ("Logout".equals(req.getParameter("button"))) {
            req.getSession().setAttribute("currentUser", null);
            resp.sendRedirect("login.jsp?logout=true");
            failed = false;
        }
        if (failed == true) {
            resp.sendRedirect("login.jsp?failed=true");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.jsp");
    }

}
