package wilcoxp3;

import java.io.Serializable;
import java.util.Set;

/**
 * Object for users of the Store application.
 * @author Paul
 */
public class User implements Serializable {

    public static String ADMINISTRATOR = "ADMIN";
    public static String INVENTORY_MANAGER = "INV_MAN";

    private String username;
    private String password;
    private Set<String> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public boolean isAdministrator() {
        return this.getRoles().contains(ADMINISTRATOR);
    }

    public boolean isInventoryManager() {
        return this.getRoles().contains(INVENTORY_MANAGER)
                || this.isAdministrator();
    }
}
