package daos;

import repositories.Repository;

/**
 *
 * @author Ebenezer Graham
 */
public interface DAOInterface {

    public Repository load(String filename);

    public void store(String filename, Repository repository);
}
