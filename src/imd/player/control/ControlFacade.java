package imd.player.control;

import imd.player.model.ModelFacade;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javazoom.jl.decoder.JavaLayerException;

/**
 * Class responsible for the Control package's Facade
 * @author Anderson Santos and Yuri Reinaldo
 */
public class ControlFacade {

    /**
     * Manage the control for the user, like add new user
     */
    private UserControl userControl;
    
    /**
     * Manage the music control, like play or pause the music
     */
    private final MusicControl musicControl;
    
    /**
     * Singleton object, this shall be returned by the method getInstance()
     */
    private static ControlFacade singleton;

    /**
     * Private constructor, responsible for initializate the inner controls
     */
    private ControlFacade() {
        musicControl = new MusicControl();
        userControl = new UserControl();
    }

    /**
     * Singleton method, this must be called to return the singleton of Control Facade
     * @return Returns the instance of ControlFacade, in other words, the singleton object
     */
    public static ControlFacade getInstance() {
        if (ControlFacade.singleton == null) {
            ControlFacade.singleton = new ControlFacade();
        }
        return ControlFacade.singleton;
    }

    /**
     * Tries to login in the system by passing a login and password
     * @param login The login of the user who tries to enter in the system
     * @param password The password of the user who tries to enter in the system
     * @return Returns true if the user was successfully found in the system and the passwords match, return false otherwise 
     */
    public boolean loginAttempt(String login, String password) {
        return this.userControl.loginAttempt(login, password);
    }

    /**
     * Verifies if the logged user is a vip user
     * @return Returns true if the logged user is a vip user, return false otherwise
     */
    public boolean loggedUserIsAdmin() {
        return this.userControl.isAdmin();
    }

    /**
     * This method must be called before the system exit call so the data can be stored in the files
     * @throws IOException Exception returned if data can't be stored
     */
    public void exit() throws IOException {
        ModelFacade.getInstance().saveFinalData();
    }

    /**
     * Pause or return to playing the music
     * @return Returns true if the music is paused, return false otherwise
     */
    public boolean pauseOrPlayMusic() {
        return this.musicControl.pausePlayMusic();
    }

    /**
     * Stops the actual music or playlist
     * @return Returns true if the music or playlist was successful stopped, return false otherwise
     */
    public boolean stopMusic() {
        return this.musicControl.stopMusic();
    }

    /**
     * Plays the next music of the playlist
     */
    public void playNextMusic() {
        this.musicControl.nextMusic();
    }

    /**
     * Plays the back music of the playlist
     */
    public void playBackMusic() {
        this.musicControl.backMusic();
    }

    /**
     * Plays an music by passing it's name
     * @param musicName Receives the music name
     * @param progressBar Receives the progress bar that must show the music progress
     * @param musicLabel Receives the label that must show the music name during the execution
     * @throws InterruptedException 
     * @throws FileNotFoundException
     * @throws JavaLayerException 
     */
    public void playMusic(String musicName, JProgressBar progressBar, JLabel musicLabel) throws InterruptedException, FileNotFoundException, JavaLayerException {
        this.musicControl.playMusic(musicName, progressBar, musicLabel);
    }

    /**
     * Plays an playlist in sequence by passing it's name
     * @param playlistName Receives the playlist name
     * @param progressBar Receives the progress bar that must show the music progress
     * @param musicLabel Receives the label that must show the music name during the execution
     * @throws InterruptedException 
     */
    public void playPlaylist(String playlistName, JProgressBar progressBar, JLabel musicLabel) throws InterruptedException {
        this.musicControl.playMusics(playlistName, (VipUser) this.userControl.getLoggedUser(), progressBar, musicLabel);
    }

    /**
     * Returns the login of the logged user
     * @return Returns the login of the logged user
     */
    public String getLoggedUserLogin() {
        return this.userControl.getLoggedUser().getLogin();
    }

    /**
     * Add a new music on the list of musics
     * @param musicFile Receives the music file that must be added on the musics list
     * @return Returns true if the music file was successfully added, return false otherwise
     */
    public boolean addNewMusic(File musicFile) {
        return this.musicControl.addNewMusic(musicFile);
    }

    /**
     * Add a new directory of musics, for the musics in this directory will be added in the musics list
     * @param directory Receives the directory path, passed as {@link File} 
     */
    public void addNewDirectoryOfMusics(File directory) {
        this.musicControl.addDirectory(directory);
    }

    /**
     * Returns an array containing all the musics names
     * @return Returns an {@link ArrayList} of {@link String} containing all the musics names
     */
    public ArrayList<String> getAllMusicNames() {
        return this.musicControl.getMusicNames();
    }

    /**
     * Returns an array containing all the playlists names of the logged user
     * @return Returns an {@link ArrayList} of {@link String} containing all the playlists names
     */
    public ArrayList<String> getAllPLaylistsNames() {
        return this.musicControl.getPlaylists((VipUser) this.userControl.getLoggedUser());
    }

    /**
     * Returns an array with all the music names from a specific playlist of the logged user
     * @param playlistName Receives the playlist name
     * @return Return an {@link ArrayList} of {@link String} cointaining all the musics names from the specific playlist
     */
    public ArrayList<String> getMusicsNamesFromPlaylist(String playlistName) {
        return this.musicControl.getMusicsFromPlaylist((VipUser) this.userControl.getLoggedUser(), playlistName);
    }

    /**
     * Add a new normal User, passing the user login and password, but just if the logged user is an vip User
     * @param login Receives the login of the new User
     * @param password Receives the password of the new User
     * @return Returns true if the User was successfully added, return false if the User already exist on the system 
     */
    public boolean addNewNormalUser(String login, String password) {
        return this.userControl.addNormalUser(login, password);
    }

    /**
     * Add a new vip User, passing the user login and password, but just if the logged user is an vip User
     * @param login Receives the login of the new User
     * @param password Receives the password of the new User
     * @return Returns true if the User was successfully added, return false if the User already exist on the system
     */
    public boolean addNewVipUser(String login, String password) {
        return this.userControl.addVipUser(login, password);
    }

    /**
     * Create a new playlist of music for the logged user, but just if the logged User is an vip User
     * @param musicsNames Receives an {@link ArrayList} of {@Link String} with the musics names
     * @param playlistName Receives the name of the new playlist
     * @return Returns true if the playlist was successfully added, return false if already exist a playlist with the passed name
     */
    public boolean createPlaylist(ArrayList<String> musicsNames, String playlistName){
        return this.musicControl.createPlaylist((VipUser) this.userControl.getLoggedUser(), musicsNames, playlistName);
    }
}
