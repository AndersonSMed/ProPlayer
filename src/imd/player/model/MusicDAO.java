package imd.player.model;

import imd.player.control.Playlist;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;

public class MusicDAO{
    
    public void playMusic(File music) throws InterruptedException, FileNotFoundException, JavaLayerException{
        Mp3Player.getInstance().start(music);
    }
    
    public void playMusics(Playlist playlist) throws InterruptedException{
        
    }
}
