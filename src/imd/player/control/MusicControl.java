package imd.player.control;

import imd.player.model.ModelFacade;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;

/**
 * Class responsible for control of the player actions, like stop, pause and
 * play one or several musics
 *
 * @author Anderson Santos and Yuri Reinaldo
 */
public class MusicControl implements Runnable {

    private Playlist playlist;
    private boolean playNextMusic = false;
    private boolean playBackMusic = false;
    private boolean stop = false;
    private final ProgressBarControl progressBarControl;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel musicLabel;
    private Thread musics;

    public MusicControl() {
        progressBarControl = new ProgressBarControl();
    }

    public void playMusic(String musicName, javax.swing.JProgressBar progressBar, javax.swing.JLabel musicLabel) throws InterruptedException, FileNotFoundException, JavaLayerException {
        this.progressBar = progressBar;
        this.musicLabel = musicLabel;
        try {
            Music music = ModelFacade.getInstance().getMusic(musicName);
            if (music != null) {
                Mp3Player.getInstance().stop();
                Mp3Player.getInstance().start(music.getMusicFile());
                this.progressBarControl.calculateProgress(this.progressBar);
                String text = ModelFacade.getInstance().getMusic(musicName).getName().split(".mp3")[0];
                this.musicLabel.setText(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getMusicsFromPlaylist(VipUser user, String playlistName) {

        ArrayList<String> musicnames = new ArrayList<>();
        Playlist playlist = null;
        try {
            for (Playlist pl : ModelFacade.getInstance().getPlaylistsByUserId(user.getId())) {
                if (pl.getName().equals(playlistName)) {
                    for (Music music : pl.getMusics()) {
                        musicnames.add(music.getName());
                    }
                    return musicnames;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return musicnames;
    }

    public ArrayList<String> getPlaylists(VipUser user) {
        ArrayList<String> playlistNames = new ArrayList<>();
        try {
            if (ModelFacade.getInstance().getPlaylistsByUserId(user.getId()) == null) {
                return playlistNames;
            }
            for (Playlist pl : ModelFacade.getInstance().getPlaylistsByUserId(user.getId())) {
                playlistNames.add(pl.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playlistNames;
    }

    public ArrayList<String> getPlayListByUserId(VipUser user, String playlistName) {
        ArrayList<String> musicNames = new ArrayList<>();
        try {
            for (Playlist pl : ModelFacade.getInstance().getPlaylistsByUserId(user.getId())) {
                if (pl.getName().equals(playlistName)) {
                    for (Music music : pl.getMusics()) {
                        musicNames.add(music.getName());
                    }
                    return musicNames;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return musicNames;
    }

    public boolean addNewMusic(File musicFile) {
        String musicName = musicFile.getName().split(".mp3")[0];
        Music music = new Music(musicName, musicFile);
        try {
            return ModelFacade.getInstance().addMusic(music);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createPlaylist(VipUser user, ArrayList<String> musicsNames, String playlistName) {
        try {
            if (ModelFacade.getInstance().getPlaylistsByUserId(user.getId()) != null) {
                for (Playlist p : ModelFacade.getInstance().getPlaylistsByUserId(user.getId())) {
                    if (p.getName().equals(playlistName)) {
                        return false;
                    }
                }
            }
            Playlist playlist = new Playlist(playlistName);
            for (String musicName : musicsNames) {
                playlist.addMusic(ModelFacade.getInstance().getMusic(musicName));
            }
            ModelFacade.getInstance().addPlaylist(user, playlist);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addDirectory(File directory) {
        String musicName;
        for (File actualFile : directory.listFiles()) {
            if (actualFile.getName().contains(".mp3")) {
                musicName = actualFile.getName().split(".mp3")[0];
                try {
                    ModelFacade.getInstance().addMusic(new Music(musicName, actualFile));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void playMusics(VipUser user, String playlistName, javax.swing.JProgressBar progressBar) throws InterruptedException {
        this.progressBar = progressBar;
        try {
            for (Playlist pl : ModelFacade.getInstance().getPlaylistsByUserId(user.getId())) {
                if (pl.getName().equals(playlistName)) {
                    this.playlist = pl;
                    Mp3Player.getInstance().stop();
                    Thread musics = new Thread(this, "playmusics");
                    musics.start();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playMusics(String playlistName, VipUser user, javax.swing.JProgressBar progressBar, javax.swing.JLabel musicLabel) throws InterruptedException {
        this.progressBar = progressBar;
        try {
            for (Playlist pl : ModelFacade.getInstance().getPlaylistsByUserId(user.getId())) {
                if (pl.getName().equals(playlistName)) {
                    this.playlist = pl;
                    this.musicLabel = musicLabel;
                    this.musics = new Thread(this, "playmusics");
                    this.musics.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Play the next music
     */
    public void nextMusic() {
        this.playNextMusic = true;
    }

    /**
     * Play the back music
     */
    public void backMusic() {
        this.playBackMusic = true;
    }

    /**
     * Pause or play music
     *
     * @return return true is the music is paused and false otherwise
     */
    public boolean pausePlayMusic() {
        Mp3Player.getInstance().playPause();
        return !Mp3Player.getInstance().isPlaying();
    }

    /**
     * Stops the playing music
     *
     * @return return true if the music was successful stopped and false
     * otherwise
     */
    public boolean stopMusic() {
        if (Mp3Player.getInstance().getThread_t() != null) {
            if (this.musics != null) {
                this.stop = true;
            }
            if (Mp3Player.getInstance().getThread_t().isAlive()) {
                Mp3Player.getInstance().stop();
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getMusicNames() {
        ArrayList<String> musicNames = new ArrayList<>();
        try {
            for (Music music : ModelFacade.getInstance().getAllMusics()) {
                musicNames.add(music.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return musicNames;
    }

    /**
     * Method responsible for the thread action of play several musics
     */
    @Override
    public void run() {
        for (int i = 0; i <= this.playlist.getMusics().size(); i++) {
            if (Mp3Player.getInstance().getThread_t() != null) {
                while (Mp3Player.getInstance().getThread_t().isAlive()) {
                    if (this.playNextMusic || this.playBackMusic || this.stop) {
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (this.stop) {
                    this.stop = false;
                    break;
                }
                if (this.playBackMusic) {
                    i = (i < 2) ? (this.playlist.getMusics().size() - 1) : (i - 2);
                    try {
                        Mp3Player.getInstance().start(this.playlist.getMusics().get(i).getMusicFile());
                        String text = this.playlist.getMusics().get(i).getMusicFile().getName().split(".mp3")[0];
                        this.musicLabel.setText(text);
                        this.progressBarControl.calculateProgress(this.progressBar);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    this.playBackMusic = false;
                    continue;
                }

                if (i == this.playlist.getMusics().size() && this.playNextMusic) {
                    i = 0;
                } else if (i == this.playlist.getMusics().size()) {
                    break;
                }
                if (this.playNextMusic) {
                    this.playNextMusic = false;
                }
            }
            try {
                Mp3Player.getInstance().start(this.playlist.getMusics().get(i).getMusicFile());
                String text = this.playlist.getMusics().get(i).getMusicFile().getName().split(".mp3")[0];
                this.musicLabel.setText(text);
                this.progressBarControl.calculateProgress(this.progressBar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
