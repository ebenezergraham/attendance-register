package repositories;

import daos.DAOImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import model.Swipe;


public class Repository implements RepositoryInterface {
    private List<Swipe> items;
    private DAOImpl dao = new DAOImpl();
    
    public Repository() {
        this.items = new ArrayList<>();     
    }
    
    public Repository(List<Swipe> items) {        
        this.items = items;
    }
    
    public Repository(String filename) {
        this();
        this.items = dao.load(filename).getItems(); 
                
    }
    /**
     * Returns the items in the repository
     * @return 
     */
    @Override
    public List<Swipe> getItems() {        
        return this.items;
    }
    
    /**
     * assigns a new swipe list to the items
     * @param items 
     * @Override
     */
    public void setItems(List<Swipe> items) {        
        this.items = items;
    }
    
    /**
     * Adds a swipe to the list
     * @param items 
     */
    @Override
    public void add(Swipe item) {
        this.items.add(item);
    }
    
    /**
     * Removes a swipe if it matches the specified Id
     * @param id 
     */
    @Override
    public void remove(int id) {
        Predicate<Swipe> predicate = e->e.getId() == id;       
        this.items.removeIf(predicate);
    }
    
    /**
     * Returns the swipes in the repository's item list
     * @param id
     * @return Swipe
     */
    @Override
    public Swipe getItem(int id) {
        for (Swipe item:this.items) {
            if (item.getId() == id)
                return item;
        }
        return null;
    }
    
    /**
     * Returns the items in the repository
     * @return String
     */
    @Override
    public String toString() {
        return "\nItems: " + this.items;
    }
    
    /**
     * Returns a String of concatenated the values from a swipe object 
     * @param DELIMITER
     * @return String
     */
    public String toString(char DELIMITER){
        String output = "";
        final String EOLN = "\n";
        for(Swipe swipe : items){
            output = output.concat(swipe.toString(DELIMITER) + EOLN);
        }
        return output;
    }
    
    @Override
    public void store(String filename) {       
        dao.store(filename, this);
    }        
}

