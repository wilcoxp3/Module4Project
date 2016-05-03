package wilcoxp3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DataAccessObject implementation for Product objects stored in a Derby
 * database.
 *
 * @author Paul
 */
public class DatabaseProductDao implements DataAccessObject<Product> {

    Connection con;

    DatabaseProductDao() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/store;create=true");
            if (!con.getMetaData().getTables(null, null, "PRODUCT", null).next()) {
                String createDml = "CREATE TABLE PRODUCT (UPC VARCHAR(25), "
                        + "SHORT_DETAILS VARCHAR(50), LONG_DETAILS VARCHAR(5000), "
                        + "PRICE DECIMAL(10,2), STOCK INTEGER, PRIMARY KEY (UPC))";
                PreparedStatement createStatement = con.prepareStatement(createDml);
                createStatement.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<Product> readAll() {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement readAllStatement = con.prepareStatement("SELECT UPC, "
                    + "SHORT_DETAILS, LONG_DETAILS, PRICE, STOCK FROM PRODUCT");
            ResultSet results = readAllStatement.executeQuery();
            while (results.next()) {
                Product product = new Product();
                product.setUpc(results.getString("UPC"));
                product.setShortDetails(results.getString("SHORT_DETAILS"));
                product.setLongDetails(results.getString("LONG_DETAILS"));
                product.setPrice(results.getBigDecimal("PRICE"));
                product.setStock(results.getInt("STOCK"));
                products.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    @Override
    public Product read(Object id) {
        Product product = null;
        String upc = (String) id;
        try {
            PreparedStatement readStatement = con.prepareStatement("SELECT UPC, "
                    + "SHORT_DETAILS, LONG_DETAILS, PRICE, STOCK FROM PRODUCT "
                    + "WHERE UPC = ?");
            readStatement.setString(1, upc);
            ResultSet results = readStatement.executeQuery();
            if (results.next()) {
                product = new Product();
                product.setUpc(results.getString("UPC"));
                product.setShortDetails(results.getString("SHORT_DETAILS"));
                product.setLongDetails(results.getString("LONG_DETAILS"));
                product.setPrice(results.getBigDecimal("PRICE"));
                product.setStock(results.getInt("STOCK"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return product;
    }

    @Override
    public void create(Product product) {
        if (product != null && read(product.getUpc()) == null) {
            try {
                PreparedStatement createStatement = con.prepareStatement(
                        "INSERT INTO PRODUCT (UPC, SHORT_DETAILS, LONG_DETAILS, "
                        + "PRICE, STOCK) VALUES (?,?,?,?,?)");
                createStatement.setString(1, product.getUpc());
                createStatement.setString(2, product.getShortDetails());
                createStatement.setString(3, product.getLongDetails());
                createStatement.setBigDecimal(4, product.getPrice());
                createStatement.setInt(5, product.getStock());
                createStatement.execute();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Product product) {
        if (product != null && read(product.getUpc()) != null) {
            try {
                PreparedStatement updateStatement = con.prepareStatement(
                        "UPDATE PRODUCT SET SHORT_DETAILS=?, LONG_DETAILS=?, "
                        + "PRICE=?, STOCK=? WHERE UPC = ?");
                updateStatement.setString(1, product.getShortDetails());
                updateStatement.setString(2, product.getLongDetails());
                updateStatement.setBigDecimal(3, product.getPrice());
                updateStatement.setInt(4, product.getStock());
                updateStatement.setString(5, product.getUpc());
                updateStatement.execute();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(Object id) {
        String upc = (String) id;
        if (read(upc) != null) {
            try {
                PreparedStatement deleteStatement = con.prepareStatement(
                        "DELETE FROM PRODUCT WHERE UPC = ?");
                deleteStatement.setString(1, upc);
                deleteStatement.execute();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
