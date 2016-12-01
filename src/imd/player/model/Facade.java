package imd.player.model;

import imd.player.control.MusicControl;
import java.io.File;
import java.util.ArrayList;

public class Facade {

    private static Facade singleton;
    private MusicControl musicDao;
    private UserDAO userDAO;

    private Facade() {
        this.musicDao = new MusicControl();
    }

    public static Facade getInstance() {
        if (Facade.singleton == null) {
            Facade.singleton = new Facade();
        }
        return Facade.singleton;
    }

    public void playMusic(File music) {
        try {
            this.musicDao.playMusic(music);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playPlaylist(ArrayList playlist) {
        try {
            this.musicDao.playMusics(playlist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playNextMusic() {
        this.musicDao.nextMusic();
    }

    public void playBackMusic() {
        this.musicDao.backMusic();
    }

    public boolean pauseOrPlayMusic() {
        return this.musicDao.pausePlayMusic();
    }

    public void stopMusic() {
        this.musicDao.stopMusic();
    }
}
