package imd.player.control;

import java.io.File;
import java.util.ArrayList;

public class Playlist {

    private String name;
    private ArrayList<Music> musics;

    public Playlist(String name) {
        this.name = name;
        this.musics = new ArrayList<>();
    }

    public Playlist(String name, ArrayList<Music> musics) {
        this.name = name;
        this.musics = musics;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Music> getMusics() {
        return musics;
    }

    public void addMusic(Music music) {
        this.musics.add(music);
    }

}
