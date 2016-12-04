package imd.player.model;

import imd.player.control.Music;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class MusicDao implements DaoInterface {

    private File musicFile;
    private static HashMap<String, Music> musics;

    public MusicDao(File musicFile) throws IOException {
        this.musicFile = musicFile;
        if (this.musics == null) {
            this.musics = new HashMap<>();
        }
        this.readFile();
    }

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

    public boolean addMusic(Music music) {
        if (!this.musics.containsKey(music.getName())) {
            this.musics.put(music.getName(), music);
            return true;
        }
        return false;
    }

    public Music getMusic(String musicName) {
        return this.musics.get(musicName);
    }

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
    
    public Collection<Music> getAllMusics(){
        return this.musics.values();
    }
}
