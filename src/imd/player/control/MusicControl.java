package imd.player.control;

import imd.player.control.Mp3Player;
import imd.player.control.Playlist;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javazoom.jl.decoder.JavaLayerException;

public class MusicControl implements Runnable {

    private ArrayList<File> playlist;
    private boolean playNextMusic = false;
    private boolean playBackMusic = false;
    ProgressBarControl progressBarControl;
    private javax.swing.JProgressBar progressBar;
    
    public MusicControl() {
        this.playlist = new ArrayList<>();
        this.progressBarControl = new ProgressBarControl();
    }

    public void playMusic(File music, javax.swing.JProgressBar progressBar) throws InterruptedException, FileNotFoundException, JavaLayerException {
        this.progressBar = progressBar;
        Mp3Player.getInstance().start(music);
        this.progressBarControl.calculateProgress(this.progressBar);
    }

    public void playMusics(ArrayList<File> playlist, javax.swing.JProgressBar progressBar) throws InterruptedException {
        this.progressBar = progressBar;
        this.playlist = playlist;
        Thread musics = new Thread(this, "playmusics");
        musics.start();
    }

    public void nextMusic() {
        this.playNextMusic = true;
    }

    public void backMusic() {
        this.playBackMusic = true;
    }

    public boolean pausePlayMusic() {
        Mp3Player.getInstance().playPause();
        return !Mp3Player.getInstance().isPlaying();
    }

    public boolean stopMusic() {
        if (Mp3Player.getInstance().getThread_t().isAlive()) {
            Mp3Player.getInstance().stop();
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        for (int i = 0; i <= this.playlist.size(); i++) {
            if (Mp3Player.getInstance().getThread_t() != null) {
                while (Mp3Player.getInstance().getThread_t().isAlive()) {
                    if (this.playNextMusic) {
                        this.playNextMusic = false;
                        break;
                    }
                    if (this.playBackMusic) {
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (this.playBackMusic) {
                    i = (i < 2) ? (this.playlist.size() - 1) : (i - 2);
                    try {
                        Mp3Player.getInstance().start(this.playlist.get(i));
                        this.progressBarControl.calculateProgress(this.progressBar);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    this.playBackMusic = false;
                    continue;
                }

                if (i == this.playlist.size() && this.playNextMusic) {
                    i = 0;
                } else if (i == this.playlist.size()) {
                    break;
                }
            }
            try {
                Mp3Player.getInstance().start(this.playlist.get(i));
                this.progressBarControl.calculateProgress(this.progressBar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
