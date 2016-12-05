package imd.player.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * A class that is responsible for reading the Directory file which contain the
 * path to the other files responsible for data persistence.
 * 
 * @author Anderson Santos and Yuri Reinaldo
 */
public class FolderDao {
    /**
     * The {@link File} instance that keeps the Directory file path.
     */
    private final File folders = new File("/home/anderson/Documents/Diretorios.txt");
    /**
     * The {@link File} instance that keeps the path to the file containing all 
     * musics previously opened in the player.
     */
    private File musicFile;
    /**
     * The {@link File} instance that keeps the path to the folder which 
     * contains all the playlist files within itself.
     */
    private File playlistFolder;
    /**
     * The {@link File} instance that keeps the path to the file which contains
     * all users registered in the system.
     */
    private File userFile;

    /**
     * Constructor that reads the data containing inside of {@link folders} and 
     * distributes it's contents to the class parameters, It should be noted 
     * that if the file is somehow empty then there will be some errors, so to
     * not let this happen make the Directory file contain a File, a Path and a 
     * File in first three different lines.
     * 
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public FolderDao() throws FileNotFoundException, IOException {
        try (BufferedReader bufferReader = new BufferedReader(new FileReader(folders))) {
            this.musicFile = new File(bufferReader.readLine());
            this.playlistFolder = new File (bufferReader.readLine());
            this.userFile = new File(bufferReader.readLine());
        }
    }

    /**
     * Returns a File object that can be read in order to get all musics in
     * the system.
     * @return the file containing all musics previously opened
     */
    public File getMusicFile() {
        return musicFile;
    }

    /**
     * Sets the musicFile to another one.
     * @param musicFile 
     */
    public void setMusicFile(File musicFile) {
        this.musicFile = musicFile;
    }

    /**
     * Returns a File object that represents the folder that contains all 
     * playlists that were saved in the system.
     * @return the folder containing a number of playlist files.
     */
    public File getPlaylistFolder() {
        return playlistFolder;
    }

    /**
     * Sets the parameter of playlistFolder, which must be a path to a folder
     * containing only a number of files with the playlist format.
     * @param playlistFolder 
     */
    public void setPlaylistFolder(File playlistFolder) {
        this.playlistFolder = playlistFolder;
    }

    /**
     * Returns a file which contains a number of user data, it should be written
     * in user formatting, with each line representing it's level, it's login and 
     * it's password, in that order.
     * 
     * @return a File with a number of user data
     */
    public File getUserFile() {
        return userFile;
    }

    /**
     * Sets the File instance to another one, it must be noted that it must 
     * contain a number of user data written using User formatting.
     * @param userFile 
     */
    public void setUserFile(File userFile) {
        this.userFile = userFile;
    }

}
