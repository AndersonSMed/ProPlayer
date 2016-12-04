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
 *
 * @author Anderson Santos and Yuri Reinaldo
 */
public class ControlFacade {

    private UserControl userControl;
    private final MusicControl musicControl;
    private static ControlFacade singleton;

    private ControlFacade() {
        musicControl = new MusicControl();
        userControl = new UserControl();
    }

    public static ControlFacade getInstance() {
        if (ControlFacade.singleton == null) {
            ControlFacade.singleton = new ControlFacade();
        }
        return ControlFacade.singleton;
    }

    public boolean loginAttempt(String login, String password) {
        return this.userControl.loginAttempt(login, password);
    }

    public boolean loggedUserIsAdmin() {
        return this.userControl.isAdmin();
    }

    public ArrayList<String> getAllUsersLogins() {
        return this.userControl.getAllUsersLogins();
    }

    public void exit() throws IOException {
        ModelFacade.getInstance().saveFinalData();
    }

    public boolean pauseOrPlayMusic() {
        return this.musicControl.pausePlayMusic();
    }

    public boolean stopMusic() {
        return this.musicControl.stopMusic();
    }

    public void playNextMusic() {
        this.musicControl.nextMusic();
    }

    public void playBackMusic() {
        this.musicControl.backMusic();
    }

    public void playMusic(String musicName, JProgressBar progressBar, JLabel musicLabel) throws InterruptedException, FileNotFoundException, JavaLayerException {
        this.musicControl.playMusic(musicName, progressBar, musicLabel);
    }

    public void playPlaylist(String playlistName, JProgressBar progressBar, JLabel musicLabel) throws InterruptedException {
        this.musicControl.playMusics(playlistName, (VipUser) this.userControl.getLoggedUser(), progressBar, musicLabel);
    }

    public String getLoggedUserLogin() {
        return this.userControl.getLoggedUser().getLogin();
    }

    public boolean addNewMusic(File musicFile) {
        return this.musicControl.addNewMusic(musicFile);
    }

    public void addNewDirectoryOfMusics(File directory) {
        this.musicControl.addDirectory(directory);
    }

    public ArrayList<String> getAllMusicNames() {
        return this.musicControl.getMusicNames();
    }

    public ArrayList<String> getAllPLaylistsNames() {
        return this.musicControl.getPlaylists((VipUser) this.userControl.getLoggedUser());
    }

    public ArrayList<String> getMusicsNamesFromPlaylist(String playlistName) {
        return this.musicControl.getPlayListByUserId((VipUser) this.userControl.getLoggedUser(), playlistName
        );
    }

    public boolean addNewNormalUser(String login, String password) {
        return this.userControl.addNormalUser(login, password);
    }

    public boolean addNewVipUser(String login, String password) {
        return this.userControl.addVipUser(login, password);
    }

    public boolean createPlaylist(ArrayList<String> musicsNames, String playlistName){
        return this.musicControl.createPlaylist((VipUser) this.userControl.getLoggedUser(), musicsNames, playlistName);
    }
}
