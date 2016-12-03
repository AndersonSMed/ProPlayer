package imd.player.model;

import imd.player.control.Music;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MusicDao {

    private File musicFile;
    private static HashMap<String, Music> musics;

    public MusicDao(File musicFile) throws IOException {
        this.musicFile = musicFile;
        if (this.musics == null) {
            this.musics = new HashMap<>();
        }
        this.readFile();
    }

    private void readFile() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.musicFile));
        File fileMusic;
        String name;
        
        while (reader.ready()) {
            fileMusic = new File(reader.readLine());
            name = fileMusic.getName().split(".mp3")[0];
            musics.put(name, new Music(name, fileMusic));
        }
        reader.close();

    }

    public boolean addMusic(Music music) {
        if (this.musics.containsKey(music.getName())) {
            this.musics.put(music.getName(), music);
            return true;
        }
        return false;
    }

    public Music getMusic(String musicName){
        return this.musics.get(musicName);
    }
    
    public void saveBackup(ArrayList<File> musics) throws IOException {
        FileWriter writer = new FileWriter(this.musicFile, false);
        for(Music music : this.musics.values()){
            writer.write(music.getMusicFile().getAbsolutePath());
        }
        writer.close();
    }
}
