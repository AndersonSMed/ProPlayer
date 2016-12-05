package imd.player.model;

import imd.player.control.Music;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * A class that is responsible for reading the Musics file and manages all Music
 * related data management
 * 
 * @author Anderson Santos and Yuri Reinaldo
 */
public class MusicDao implements DaoInterface {
    /**
     * A {@link File} instance that keeps the file which contains data from all
     * the musics used before.
     */
    private File musicFile;
    /**
     * A map containing all music data that are being used by the system.
     */
    private static HashMap<String, Music> musics;

    /**
     * The construtor receives the file which should read in order to get all the
     * music data then calls the ReadFile method in order to fill the musics map.
     * @param musicFile
     * @throws IOException 
     */
    public MusicDao(File musicFile) throws IOException {
        this.musicFile = musicFile;
        if (this.musics == null) {
            this.musics = new HashMap<>();
        }
        this.readFile();
    }

    /**
     * Reads the content in musicFile and fills the musics map with data, this 
     * method should be called only in the instatiation of this object.
     */
    @Override
    public void readFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.musicFile));
            File fileMusic;
            String name;
            Music music;
            
            while (reader.ready()) {
                fileMusic = new File(reader.readLine());
                music = new Music(fileMusic);
                musics.put(music.getName(), music);
            }
            
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tries to insert a music into the map,  if it isn't already inserted.
     * @param music Music to be inserted in file.
     * @return True if the music was successfully inserted, False if it's already
     * inside
     */
    public boolean addMusic(Music music) {
        if (!this.musics.containsKey(music.getName())) {
            this.musics.put(music.getName(), music);
            return true;
        }
        return false;
    }

    /**
     * Returns a music inside the map given it's name
     * @param musicName the music name
     * @return the music object of the passing name
     */
    public Music getMusic(String musicName) {
        return this.musics.get(musicName);
    }

    /**
     *  Saves all data contained within this object in a file so it can be
     *  accessed after a new execution. 
     */
    @Override
    public void saveBackup() {
        try {
            FileWriter writer = new FileWriter(this.musicFile, false);
            for (Music music : this.musics.values()) {
                writer.write(music.getMusicFile().getAbsolutePath() + "\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns all music objects contained within this instance
     * @return an array with all music objects 
     */
    public Collection<Music> getAllMusics(){
        return this.musics.values();
    }
}
