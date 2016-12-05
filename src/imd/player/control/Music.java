package imd.player.control;

import java.io.File;

/**
 * Class used to create an Music instance.
 * @author Anderson Santos and Yuri Reinaldo
 */
public class Music {
    /**
     * Receives the music name, that will be useful for identifies the music.
     */
    private String name;
    
    /**
     * Receives the music {@link File}, that will be used for read and play the music.
     */
    private File musicFile;

    /**
     * Constructor that receives the name and music {@link File}.
     * @param name Receives the music name
     * @param musicFile Receives the music {@link File}
     */
    public Music(String name, File musicFile) {
        this.name = name;
        this.musicFile = musicFile;
    }

    /**
     * Constructor that receives only the music {@link File} and set the music name automatically.
     * @param musicFile Receives the music {@link File}
     */
    public Music(File musicFile) {
        this.name = musicFile.getName().split(".mp3")[0];
        this.musicFile = musicFile;
    }
    
    /**
     * Returns the music {@link File};
     * @return Music {@link File};
     */
    public File getMusicFile() {
        return musicFile;
    }

    /**
     * Returns the music name;
     * @return Music name
     */
    public String getName() {
        return name;
    }
}
