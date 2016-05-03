package wilcoxp3;

/**
 * Factory class for data access objects.
 * @author Paul
 */
public class DataAccessObjectFactory {

    DataAccessObjectFactory() {
    }

    public static DataAccessObject<User> getUserDao() {
        return new DatabaseUserDao();
    }

    public static DataAccessObject<Product> getProductDao() {
        return new DatabaseProductDao();
    }

}
