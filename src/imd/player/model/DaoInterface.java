package imd.player.model;

/**
 * An interface for the classes that deal directly with file access
 * 
 * @author Anderson Santos and Yuri Reinaldo
 */
public interface DaoInterface {
    /**
     * This is a method that reads from the file set in class upon being 
     * instantiated updating the parameters they should contain.
     * 
     */
    public void readFile();
        /**
     * This is a method that saves all data from the class parameters into the
     * set file for reading in a further future, it should be called whenever 
     * this class is about to be deconstructed.
     */
    public void saveBackup();
}
