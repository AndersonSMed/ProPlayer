package imd.player.control;

import imd.player.model.ModelFacade;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javazoom.jl.decoder.JavaLayerException;

/**
 * Class responsible for control of the player actions, like stop, pause and
 * play one or several musics
 *
 * @author Anderson Santos and Yuri Reinaldo
 */
public class MusicControl implements Runnable {

    /**
     * Makes an backup of the playlist that will be played
     */
    private Playlist playlist;
   
    /**
     * Saves if the user wants to play the next music
     */
    private boolean playNextMusic = false;
    
    /**
     * Saves if the user wants to play the back music
     */
    private boolean playBackMusic = false;
    
    /**
     * Saves if the user wants to stop the music reproduction
     */
    private boolean stop = false;
    
    /**
     * Object responsible for create an thread for control of the progressBar
     */
    private final ProgressBarControl progressBarControl;
    
    /**
     * Makes an backup of the {@link JProgressBar} that must receive the state of the music
     */
    private javax.swing.JProgressBar progressBar;
    
    /**
     * Makes an backup of the {@link JLabel} that must receive the name of the playing music
     */
    private javax.swing.JLabel musicLabel;
    
    /**
     * Thread responsible for start the MusicControl thread, that must execute an playlist in sequence
     */
    private Thread musics;

    /**
     * Initialize the {@link ProgressBarControl} that must control the progress of the {@link JProgressBar}
     */
    public MusicControl() {
        progressBarControl = new ProgressBarControl();
    }

    /**
     * Plays an music by passing the music name, the progress bar and the music label that must be saved in backup for be used inside the Thread
     * @param musicName Receives the music name
     * @param progressBar Receives the {@link JProgressBar} that will be saved in backup
     * @param musicLabel Receives the {@link JLabel} that will be saved in backup
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws JavaLayerException 
     */
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

    /**
     * Returns an array containing the musics names from a playlist from the passed user
     * @param user Receives an {@link VipUser} who will be used to search the playlist
     * @param playlistName Receives the playlist name
     * @return Returns an {@link ArrayList} of {@link String} containing the musics names of the searched playlist
     */
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

    /**
     * Returns an arraylist containing the names of all playlists of the {@link VipUser}
     * @param user Receives an {@link VipUser} who will be used to get the playlists
     * @return Returns an {@link ArrayList} of {@link String} containing all the names of the User playlists
     */
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

    /**
     * Add a new music in the system
     * @param musicFile Receives the music file that will be added on the system
     * @return Returns true if the music was successfully added, return false if the music already are in the system
     */
    public boolean addNewMusic(File musicFile) {
        Music music = new Music(musicFile);
        try {
            return ModelFacade.getInstance().addMusic(music);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 
     * @param user
     * @param musicsNames
     * @param playlistName
     * @return 
     */
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

    /**
     * 
     * @param directory 
     */
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

    /**
     * 
     * @param playlistName
     * @param user
     * @param progressBar
     * @param musicLabel
     * @throws InterruptedException 
     */
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
                if(this.musics.isAlive()) this.stop = true;
            }
            if (Mp3Player.getInstance().getThread_t().isAlive()) {
                Mp3Player.getInstance().stop();
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @return 
     */
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
                        
                    }
                }
                
                if(this.stop){
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
                    this.playNextMusic = false;
                    i = 0;
                } else if (i == this.playlist.getMusics().size()) {
                    break;
                }
                
                this.playNextMusic = false;
                
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
