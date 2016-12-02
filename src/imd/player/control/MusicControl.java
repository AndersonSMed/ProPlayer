package imd.player.control;

import java.io.File;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;

/**
 * Class responsible for control of the player actions, like stop, pause and play one or several musics
 * @author Anderson Santos and Yuri Reinaldo
 */
public class MusicControl implements Runnable {

    
    private Playlist playlist;
    private boolean playNextMusic = false;
    private boolean playBackMusic = false;
    private final ProgressBarControl progressBarControl;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel musicLabel;
    
    public MusicControl() {
        progressBarControl = new ProgressBarControl();
    }

    /**
     * 
     * @param music Música a ser tocada
     * @param progressBar Barra de progresso que mostrará o progresso da música
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws JavaLayerException 
     */
    public void playMusic(File music, javax.swing.JProgressBar progressBar) throws InterruptedException, FileNotFoundException, JavaLayerException {
        this.progressBar = progressBar;
        Mp3Player.getInstance().start(music);
        this.progressBarControl.calculateProgress(this.progressBar);
    }

    /**
     * 
     * @param music Música a ser tocada
     * @param progressBar Barra de progresso que mostrará o progresso da música
     * @param musicLabel Label que deverá ser atualizada com o nome da música atual 
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws JavaLayerException 
     */
    public void playMusic(Music music, javax.swing.JProgressBar progressBar, javax.swing.JLabel musicLabel) throws InterruptedException, FileNotFoundException, JavaLayerException {
        this.progressBar = progressBar;
        this.musicLabel = musicLabel;
        Mp3Player.getInstance().start(music.getMusicFile());
        this.progressBarControl.calculateProgress(this.progressBar);
        this.musicLabel.setText(music.getName());
    }
    
    /**
     * 
     * @param playlist A playlist contendo as músicas que serão tocadas em ordem
     * @param progressBar Barra de progresso que mostrará o progresso da música
     * @throws InterruptedException 
     */
    public void playMusics(Playlist playlist, javax.swing.JProgressBar progressBar) throws InterruptedException {
        this.progressBar = progressBar;
        this.playlist = playlist;
        Thread musics = new Thread(this, "playmusics");
        musics.start();
    }

    /**
     * 
     * @param playlist A playlist contendo as músicas que serão tocadas em ordem
     * @param progressBar Barra de progresso que mostrará o progresso da música
     * @param musicLabel Recebe o label que mostrará o nome da música que está sendo executada atualmente
     * @throws InterruptedException 
     */
    public void playMusics(Playlist playlist, javax.swing.JProgressBar progressBar, javax.swing.JLabel musicLabel) throws InterruptedException {
        this.progressBar = progressBar;
        this.playlist = playlist;
        this.musicLabel = musicLabel;
        Thread musics = new Thread(this, "playmusics");
        
        musics.start();
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
     * @return return true is the music is paused and false otherwise 
     */
    public boolean pausePlayMusic() {
        Mp3Player.getInstance().playPause();
        return !Mp3Player.getInstance().isPlaying();
    }

    /**
     * Stops the playing music
     * @return return true if the music was successful stopped and false otherwise
     */
    public boolean stopMusic() {
        if (Mp3Player.getInstance().getThread_t().isAlive()) {
            Mp3Player.getInstance().stop();
            return true;
        }
        return false;
    }
    
    /**
     * Method responsible for the thread action of play several musics
     */
    @Override
    public void run() {
        for (int i = 0; i <= this.playlist.getMusics().size(); i++) {
            if (Mp3Player.getInstance().getThread_t() != null) {
                while (Mp3Player.getInstance().getThread_t().isAlive()) {
                    if (this.playNextMusic) {
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
                    i = (i < 2) ? (this.playlist.getMusics().size() - 1) : (i - 2);
                    try {
                        Mp3Player.getInstance().start(this.playlist.getMusics().get(i).getMusicFile());
                        this.musicLabel.setText(this.playlist.getMusics().get(i).getName());
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
                this.musicLabel.setText(this.playlist.getMusics().get(i).getName());
                this.progressBarControl.calculateProgress(this.progressBar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
