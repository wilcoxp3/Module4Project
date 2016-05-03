package wilcoxp3;

/*
 * Paul Wilcox 
 * Module 3 Project 
 * This application allows the user to manage an
 * inventory of products. The user may add view a product's information, add a
 * new product to the inventory, update information for an existing product, or
 * delete a product from the inventory.
 */
import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * InventoryServlet overrides doGet and doPost in order to add and save new
 * products to the product inventory, and to display a list of all saved
 * products.
 *
 * @author wilcoxp3
 */
@WebServlet("/inventory")
public class InventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User currentUser = (User) req.getSession().getAttribute("currentUser");

        if (currentUser == null || !currentUser.isInventoryManager()) {
            resp.sendRedirect("login.jsp?denied=true");
        } else {
            resp.sendRedirect("inventory.jsp");
        }
    }

    /**
     * This method adds a new product, edits an existing product, or deletes a
     * product from the inventory with the data taken from inventory.jsp. It
     * then calls the doGet method in order to display the saved products upon
     * form submittal.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Product p = new Product();
        DataAccessObject<Product> productDao = DataAccessObjectFactory.getProductDao();
        String upc = req.getParameter("upc");
        String shortDetails = req.getParameter("shortDetails");
        String longDetails = req.getParameter("longDetails");
        String priceInput = req.getParameter("price");
        String stockInput = req.getParameter("stock");

        BigDecimal price;
        Integer stock;
        try {
            price = new BigDecimal(priceInput);
        } catch (NumberFormatException | NullPointerException e) {
            priceInput = "-999";
            price = new BigDecimal(priceInput);
        }

        try {
            stock = new Integer(stockInput);
        } catch (NumberFormatException | NullPointerException e) {
            stockInput = "-999";
            stock = new Integer(stockInput);
        }

        switch (req.getParameter("button")) {
            case "Create":
                p.setUpc(upc);
                p.setShortDetails(shortDetails);
                p.setLongDetails(longDetails);
                p.setPrice(price);
                p.setStock(stock);
                productDao.create(p);
                resp.sendRedirect("inventory");
                break;
            case "Edit":
                p.setUpc(upc);
                p.setShortDetails(shortDetails);
                p.setLongDetails(longDetails);
                p.setPrice(price);
                p.setStock(stock);
                productDao.update(p);
                resp.sendRedirect("inventory");
                break;
            case "Delete":
                productDao.delete(upc);
                resp.sendRedirect("inventory");
                break;
            case "Manage Users":
                resp.sendRedirect("users");
                break;
            case "Logout":
                resp.sendRedirect("login.jsp?logout=true");
                break;
        }

    }
}
