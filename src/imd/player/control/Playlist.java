package imd.player.control;

import java.util.ArrayList;

/**
 * Class used to create an Playlist instance. 
 * @author Anderson Santos and Yuri Reinaldo
 */
public class Playlist {
    
    /**
     * Receives the playlist name, that will be useful for playlist identification.
     */
    private String name;
    
    /**
     * Receives an {@link ArrayList} of {@link Music}, that will storage all the playlist musics.
     */
    private ArrayList<Music> musics;

    /**
     * Constructor used to create a new playlist without musics. 
     * @param name Receives the playlist name.
     */
    public Playlist(String name) {
        this.name = name;
        this.musics = new ArrayList<>();
    }

    /**
     * Constructor used to create a new playlist with musics.
     * @param name Receives the playlist name.
     * @param musics Receives the {@link ArrayList} of {@link Music}.
     */
    public Playlist(String name, ArrayList<Music> musics) {
        this.name = name;
        this.musics = musics;
    }
    
    /**
     * Returns the playlist name;
     * @return Playlist name;
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the playlist name.
     * @param name New playlist name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns all the musics from playlist.
     * @return {@ArrayList} of {@link Music}.
     */
    public ArrayList<Music> getMusics() {
        return musics;
    }

    /**
     * Adds a new music on the playlist;
     * @param music Receives the new {@link Music}.
     */
    public void addMusic(Music music) {
        this.musics.add(music);
    }

}
