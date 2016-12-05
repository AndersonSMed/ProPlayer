package imd.player.model;

import imd.player.control.VipUser;
import imd.player.control.Music;
import imd.player.control.Playlist;
import imd.player.control.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
/**
 * A class designed using facade pattern, it encapsulates methods from other 
 * objects in the modeling in order to make them much easier to be used together.
 * @author Anderson Santos and Yuri Reinaldo
 */
public class ModelFacade {
    /**
     * A singleton instance of this object.
     */
    private static ModelFacade singleton;
    /**
     * An object used to manage User related data.
     */
    private final UserDao userDao;
    /**
     * An object used to manage Music related data.
     */
    private final MusicDao musicDao;
    /**
     * An object used to manage Playlist related data.
     */
    private final PlayListDao playlistDao;
    /**
     * An object used to supply the other Dao objects with the path of the 
     * data they need to function.
     */
    private final FolderDao dao;
    
    /**
     * The class constructor, kept private in order to guarantee it's single
     * instance, it initializes the parameters in order to facilitate their use.
     * @throws IOException 
     */
    private ModelFacade() throws IOException {
        this.dao = new FolderDao();
        this.musicDao = new MusicDao(this.dao.getMusicFile());
        this.userDao = new UserDao(this.dao.getUserFile());
        this.playlistDao = new PlayListDao(this.dao.getPlaylistFolder());
    }

    /**
     * This method returns a static instance of this class in order to be used 
     * to access the parameters' methods in a intuitive fashion, another method 
     * needed to guarantee this class singleton aspect.
     * @return an unique instance of this class
     * @throws IOException 
     */
    public static ModelFacade getInstance() throws IOException {
        if (ModelFacade.singleton == null) {
            ModelFacade.singleton = new ModelFacade();
        }
        return ModelFacade.singleton;
    }
    
    /**
     * Adds a new user to the model class that manages most user related data
     * @param user A user to be inserted in the system
     * @return a boolean set to True if the user was successfully inserted in 
     * the system and False if not.
     */
    public boolean addUser(User user){
        return this.userDao.addUser(user);
    }

    /**
     * Searches if there is a user in previous data, using his' or her's login
     * in order to find them.
     * @param login The users login which is set at registering
     * @return If the user is successfully found it returns a object pertaining
     * to him or her, it it is not found then a null pointer is sent instead.
     */
    public User getUser(String login) {
        return this.userDao.getUser(login);
    }
    
    /**
     * Returns an ArrayList of Users that were previously registered.
     * @return A list containing all the users
     */
    public ArrayList<User> getAllUsers(){
        return this.userDao.getAllUsers();
    }
    
    /**
     * Adds a music to the object that manages music related data and saves them
     * in a file
     * @param music A music that can be added
     * @return A boolean set to True if the music could be properly inserted, if
     * it could not be then False will be returned instead.
     */
    public boolean addMusic(Music music){
        return this.musicDao.addMusic(music);
    }
    
    /**
     * Returns a music with the passed name argument in the music manager object 
     * @param musicName A name of a music within the system
     * @return The music object associated with the name
     */
    public Music getMusic(String musicName){
        return this.musicDao.getMusic(musicName);
    }
    
    /**
     * Adds a playlist to the passed argument user.
     * @param user the user who will receive the playlist
     * @param playlist the playlist to be added to the user
     */
    public void addPlaylist(VipUser user, Playlist playlist){
        this.playlistDao.addPlaylist(user, playlist);
    }
    
    /**
     * Removes a playlist given the owner's username and the playlist's name    
     * @param user the user who owns the playlist
     * @param playlistName the signature of the playlist
     * @return True if the playlist could be properly removed, False otherwise.
     */
    public boolean removePlayList(VipUser user, String playlistName){
        return this.playlistDao.removePlaylist(user, playlistName);
    }
    
    /**
     * Finds all playlists who belong to a certain user and returns them in an 
     * ArrayList.
     * @param id the Id of the user to whom the playlists belong
     * @return An arraylist with all the playlists associated to that user's id
     */
    public ArrayList<Playlist> getPlaylistsByUserId(String id){
        return this.playlistDao.getPlaylistByUser(id);
    }
    
    /**
     * For all the Dao objects, call the saveBackup method which is responsible
     * to save all data altered within the system, this method should always be 
     * called at the end of the execution. 
     * @throws IOException 
     */
    public void saveFinalData() throws IOException{
        this.musicDao.saveBackup();
        this.userDao.saveBackup();
        this.playlistDao.saveBackup();
    }
    
    /**
     * Returns all musics that were previously saved within the music data manager
     * @return An ArrayList containing all musics that were saved before
     */
    public Collection<Music> getAllMusics(){
        return this.musicDao.getAllMusics();
    }
}
