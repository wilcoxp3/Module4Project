package wilcoxp3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * DataAccessObject implementation for User objects stored in a Derby database.
 *
 * @author Paul
 */
public class DatabaseUserDao implements DataAccessObject<User> {

    Connection con;

    DatabaseUserDao() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/store;create=true");
            if (!con.getMetaData().getTables(null, null, "USER", null).next()) {
                String createDml = "CREATE TABLE \"USER\" (USERNAME VARCHAR(25), "
                        + "PASSWORD VARCHAR(500), ROLES VARCHAR(5000))";
                PreparedStatement createStatement = con.prepareStatement(createDml);
                createStatement.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<User> readAll() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement readAllStatement = con.prepareStatement(
                    "SELECT USERNAME, PASSWORD, ROLES FROM \"USER\"");
            ResultSet results = readAllStatement.executeQuery();
            while (results.next()) {
                User user = new User();
                user.setUsername(results.getString("USERNAME"));
                user.setPassword(results.getString("PASSWORD"));
                String[] roles = results.getString("ROLES").split(",");
                user.setRoles(Arrays.stream(roles).collect(Collectors.toSet()));
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    @Override
    public User read(Object id) {
        User user = null;
        String username = (String) id;
        try {
            PreparedStatement readStatement = con.prepareStatement(
                    "SELECT USERNAME, PASSWORD, ROLES FROM \"USER\" WHERE USERNAME = ?");
            readStatement.setString(1, username);
            ResultSet results = readStatement.executeQuery();
            if (results.next()) {
                user = new User();
                user.setUsername(results.getString("USERNAME"));
                user.setPassword(results.getString("PASSWORD"));
                String[] roles = results.getString("ROLES").split(",");
                user.setRoles(Arrays.stream(roles).collect(Collectors.toSet()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    @Override
    public void create(User user) {
        if (user != null && read(user.getUsername()) == null) {
            try {
                PreparedStatement createStatement = con.prepareStatement(
                        "INSERT INTO \"USER\" (USERNAME, PASSWORD, ROLES) VALUES (?,?,?)");
                createStatement.setString(1, user.getUsername());
                createStatement.setString(2, user.getPassword());
                String roles = "";
                if (user.isAdministrator()) {
                    roles = roles + "," + User.ADMINISTRATOR;
                }
                if (user.isInventoryManager()) {
                    roles = roles + "," + User.INVENTORY_MANAGER;
                }
                createStatement.setString(3, roles);
                createStatement.execute();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(User user) {
        if (user != null && read(user.getUsername()) != null) {
            try {
                PreparedStatement updateStatement = con.prepareStatement(
                        "UPDATE \"USER\" SET PASSWORD=?, ROLES=? WHERE USERNAME = ?");
                updateStatement.setString(1, user.getPassword());
                String roles = "";
                if (user.isAdministrator()) {
                    roles = roles + "," + User.ADMINISTRATOR;
                }
                if (user.isInventoryManager()) {
                    roles = roles + "," + User.INVENTORY_MANAGER;
                }
                updateStatement.setString(2, roles);
                updateStatement.setString(3, user.getUsername());
                updateStatement.execute();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(Object id) {
        String username = (String) id;
        if (read(username) != null) {
            try {
                PreparedStatement deleteStatement = con.prepareStatement(
                        "DELETE FROM \"USER\" WHERE USERNAME = ?");
                deleteStatement.setString(1, username);
                deleteStatement.execute();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
