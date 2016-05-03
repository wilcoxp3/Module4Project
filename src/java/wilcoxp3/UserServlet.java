package wilcoxp3;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for updating users of the store application.
 * @author Paul
 */
@WebServlet("/users")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User currentUser = (User) req.getSession().getAttribute("currentUser");

        if (currentUser == null || !currentUser.isAdministrator()) {
            resp.sendRedirect("login.jsp?denied=true");
        } else {
            resp.sendRedirect("users.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User u = new User();
        DataAccessObject<User> userDao = DataAccessObjectFactory.getUserDao();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String admin = req.getParameter("isAdministrator");
        String invMan = req.getParameter("isInventoryManager");
        Set<String> roles = new HashSet<>();
        if (admin != null) {
            roles.add(User.ADMINISTRATOR);
        }
        if (invMan != null) {
            roles.add(User.INVENTORY_MANAGER);
        }

        switch (req.getParameter("button")) {
            case "Create":
                u.setUsername(username);
                u.setPassword(password);
                u.setRoles(roles);
                userDao.create(u);
                resp.sendRedirect("users");
                break;
            case "Edit":
                u.setUsername(username);
                u.setPassword(password);
                u.setRoles(roles);
                userDao.update(u);
                resp.sendRedirect("users");
                break;
            case "Delete":
                userDao.delete(username);
                resp.sendRedirect("users");
                break;
            case "Manage Inventory":
                resp.sendRedirect("inventory");
                break;
            case "Logout":
                resp.sendRedirect("login.jsp?logout=true");
                break;
        }
    }
}
