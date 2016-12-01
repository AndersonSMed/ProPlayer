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
    
    
    public MusicControl() {
        playlist = new ArrayList<>();
    }

    public void playMusic(File music) throws InterruptedException, FileNotFoundException, JavaLayerException {
        Mp3Player.getInstance().start(music);
    }

    public void playMusics(ArrayList<File> playlist) throws InterruptedException {
        this.playlist = playlist;
        Thread t = new Thread(this, "playmusics");
        t.start();
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

    public void stopMusic() {
        Mp3Player.getInstance().stop();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    this.playBackMusic = false;
                    continue;
                }

                if (i == this.playlist.size() && this.playNextMusic) {
                    i = 0;
                }
                else if (i == this.playlist.size()){
                    break;
                }
            }
            try {
                Mp3Player.getInstance().start(this.playlist.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
