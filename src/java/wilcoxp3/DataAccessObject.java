package wilcoxp3;

import java.util.List;

/**
 * Defines methods for accessing and editing data for the store application.
 * @author Paul
 * @param <E>
 */
public interface DataAccessObject<E> {

    /**
     * Returns a List containing all objects.
     * @return 
     */
    public List<E> readAll();

    /**
     * Returns a single object by its primary key/unique identifier.
     * @param id
     * @return 
     */
    public E read(Object id);

    /**
     * Creates a new entity of the specified type.
     * @param entity 
     */
    public void create(E entity);

    /**
     * Edits and existing entity the specified type.
     * @param entity 
     */
    public void update(E entity);
    
    /**
     * Deletes the object with the specified primary key/unique identifier.
     * @param id 
     */
    public void delete(Object id);
}
