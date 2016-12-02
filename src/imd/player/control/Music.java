package imd.player.control;

import java.io.File;

public class Music {
    private String name;
    private File musicFile;

    public Music(String name, File musicFile) {
        this.name = name;
        this.musicFile = musicFile;
    }

    public Music(File musicFile) {
        this.name = musicFile.getName().split(".mp3")[0];
        this.musicFile = musicFile;
    }
    
    public File getMusicFile() {
        return musicFile;
    }

    public String getName() {
        return name;
    }
    
    public void setMusicFile(File musicFile) {
        this.musicFile = musicFile;
    }

    public void setName(String name) {
        this.name = name;
    }
}
