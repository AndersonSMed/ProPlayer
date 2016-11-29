package imd.player.model;

import imd.player.control.Playlist;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;

public class MusicDAO implements Runnable {

    private Playlist playlist;

    public MusicDAO() {
        playlist = new Playlist("generic playlist");
    }

    public void playMusic(File music) throws InterruptedException, FileNotFoundException, JavaLayerException {
        Mp3Player.getInstance().start(music);
    }

    public void playMusics(Playlist playlist) throws InterruptedException {
        this.playlist = playlist;
        Thread t = new Thread(this, "playmusics");
        t.start();
    }

    @Override
    public void run() {
        for (File music : playlist.getMusics()) {
            if (Mp3Player.getInstance().getThread_t() != null) {
                while (Mp3Player.getInstance().getThread_t().isAlive()) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Mp3Player.getInstance().start(music);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Mp3Player.getInstance().start(music);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
