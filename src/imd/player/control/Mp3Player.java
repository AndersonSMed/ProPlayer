package imd.player.control;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 * Implements a Mp3 Player using parallel programming, to do so we use the JLayer library and it's classes
 * 
 * @author Anderson e Yuri Reinaldo
 */
public final class Mp3Player implements Runnable {

    /**
     * {@link AdvancedPlayer} that will be used to play the music.
     */
    private AdvancedPlayer player;
    
    /**
     * {@link Thread} that will run the {@link Mp3Player} Thread.
     */
    private Thread thread_t;
    
    /**
     * Singleton responsible for guarantee an single instance of {@link Mp3Player}.
     */
    private static Mp3Player singleton;
    
    /**
     * Return whether the Thread is running.
     */
    private boolean playing = false;
    
    /**
     * {@link BufferedInputStream} that must be used for set the {@link AdvancedPlayer} and play a music.
     */
    private BufferedInputStream bis;

    /**
     * Singleton private constructor.
     */
    private Mp3Player() {

    }

    /**
     * Singleton getInstance method;
     * @return Returns an instance of {@link Mp3Player};
     */
    public static Mp3Player getInstance() {
        if (Mp3Player.singleton == null) {
            Mp3Player.singleton = new Mp3Player();
        }
        return Mp3Player.singleton;
    }

    /**
     * Thread responsible for play an music.
     */
    @Override
    public void run() {
        try {
            this.player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method called for create an new {@link Mp3Player} {@link Thread} and start it.
     * @param music The music file that will be played.
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws JavaLayerException 
     */
    public void start(File music) throws InterruptedException, FileNotFoundException, JavaLayerException {
        bis = new BufferedInputStream(new FileInputStream(music));
        if (this.thread_t != null) {
            if (this.thread_t.isAlive()) {
                this.player.close();
            }
        }
        this.player = new AdvancedPlayer(bis);
        this.thread_t = new Thread(this, "mp3Player");
        this.thread_t.start();
        this.playing = true;
    }

    /**
     * Tries to close the {@link Thread}, stopping the music playing.
     */
    public void stop() {
        if (this.player != null) {
            this.player.close();
        }
    }

    /**
     * Play or pause the {@link Thread}, setting the token playing true if the music is playing or false otherwise.
     */
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

    /**
     * Returns the {@link Thread}.
     * @return Returns the local {@link Thread}.
     */
    public Thread getThread_t() {
        return this.thread_t;
    }

    /**
     * Returns the playing token;
     * @return Returns true if the music is playing and false otherwise.
     */
    public boolean isPlaying() {
        return this.playing;
    }

    /**
     * Returns the {@link BufferedInputStream} local.
     * @return Returns the {@link BufferedInputStream} local.
     */
    public BufferedInputStream getStream() {
        return this.bis;
    }

    /**
     * Returns the local {@link AdvancedPlayer} instance.
     * @return Returns the local {@link AdvancedPlayer} instance
     */
    public AdvancedPlayer getPlayer() {
        return this.player;
    }
}
