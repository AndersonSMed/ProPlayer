package imd.player.control;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 *
 * @author Anderson e Yuri Reinaldo
 *
 * Implements a Mp3 Player using parallel programming, to do so we use the
 * JLayer library and it's classes
 */
public final class Mp3Player implements Runnable {

    private AdvancedPlayer ap;
    private Thread thread_t;
    private static Mp3Player singleton;
    private boolean playing = false;

    private Mp3Player() {

    }

    public static Mp3Player getInstance() {
        if (Mp3Player.singleton == null) {
            Mp3Player.singleton = new Mp3Player();
        }
        return Mp3Player.singleton;
    }

    @Override
    public void run() {
        try {
            this.ap.play();
        } catch (JavaLayerException ex) {
            Logger.getLogger(Mp3Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start(File music) throws InterruptedException, FileNotFoundException, JavaLayerException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(music));
        if (thread_t != null) {
            if (thread_t.isAlive()) {
                this.ap.close();
            }
        }
        this.ap = new AdvancedPlayer(bis);
        thread_t = new Thread(this, "mp3Player");
        thread_t.start();
        this.playing = true;
    }

    public void stop() {

        this.ap.close();

    }

    public void playPause() {
        if (this.thread_t != null && this.thread_t.isAlive()) {
            if (this.playing) {
                this.thread_t.suspend();
            } else {
                this.thread_t.resume();
            }
            this.playing = !this.playing;
        }
    }

    public Thread getThread_t() {
        return thread_t;
    }

    public boolean isPlaying() {
        return playing;
    }

}
