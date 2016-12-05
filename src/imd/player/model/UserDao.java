package imd.player.model;

import imd.AndersonYuri.dataStruct.Node;
import imd.AndersonYuri.dataStruct.Tree;
import imd.player.control.VipUser;
import imd.player.control.NormalUser;
import imd.player.control.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is responsible for reading the data in Users file and manages all 
 * User related data.
 * @author Anderson Santos and Yuri Reinaldo
 */
public class UserDao implements DaoInterface {

    /**
     * The {@link File} instance that represents the file that contains all user 
     * data previously saved.
     */
    private final File userFile;
    /**
     * A tree that saves all user objects being used by the system.
     */
    private static Tree<User, String> userTree;

    /**
     * The constructor receives the path to the file which contains all the
     * previous user data and calls the readFile method.
     * @param userFile The file that contains previous user data.
     * @throws IOException 
     */
    public UserDao(File userFile) throws IOException {
        if (UserDao.userTree == null) {
            UserDao.userTree = new Tree<>();
        }
        this.userFile = userFile;
        this.readFile();
    }

    /**
     * Reads the userFile and saves it's data in User objects inserted in a 
     * Binary Search Tree, This method is always called upon the creation of this
     * object.
     */
    @Override
    public void readFile() {
        String level;
        String login;
        String password;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.userFile));
            while (reader.ready()) {

                level = reader.readLine();
                login = reader.readLine();
                password = reader.readLine();

                if (level.equals("1")) {
                    UserDao.userTree.insertNode(login.hashCode() + "", new VipUser(login, password));
                } else if (level.equals("0")) {
                    UserDao.userTree.insertNode(login.hashCode() + "", new NormalUser(login, password));
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all data related to the users who are saved in this object into the
     * userFile, this method should always be called before destructing this object.
     */
    @Override
    public void saveBackup() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            FileWriter writer = new FileWriter(this.userFile, false);
            userTree.enlistTreeElements(userList);
            for (User user : userList) {
                writer.write(user.toString());
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds a user object given it's login.
     * @param login the user's name.
     * @return the object found, or null if it wasn't inserted previously.
     */
    public User getUser(String login) {
        Node<User, String> node = UserDao.userTree.binarySearch(login.hashCode() + "");
        if(node == null) return null;
        return node.getData();
    }

    /**
     * Returns all user contained in the tree for managing purposes.
     * @return An ArrayList with all users that were previously inserted.
     */
    public ArrayList<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<>();
        this.userTree.enlistTreeElements(list);
        return list;
    }

    /**
     * Adds a new user to the database.
     * @param user A user to be inserted.
     * @return True if the user could be inserted, False if it was already inside
     * the database.
     */
    public boolean addUser(User user) {
        if (this.userTree.binarySearch(user.getId()) == null) {
            this.userTree.insertNode(user.getId(), user);
            return true;
        }
        return false;
    }
    
}
