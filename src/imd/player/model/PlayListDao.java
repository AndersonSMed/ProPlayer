package imd.player.model;

import imd.player.control.VipUser;
import imd.player.control.Music;
import imd.player.control.Playlist;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * A class that is responsible for reading the Playlist folder and manages all 
 * playlist related data.
 * 
 * @author Anderson Santos and Yuri Reinaldo
 */
public class PlayListDao implements DaoInterface {

    /**
     * A Map of ArrayLists that keep all Playlists sorted by the creator's id.
     */
    private static HashMap<String, ArrayList<Playlist>> playlists;
    /**
     * A {@link File} instance that keeps the path to the folder which contains
     * all playlists previously saved.
     */
    private File playlistPath;

    /**
     * The construtor receives the folder path which it should read and get all 
     * data from the playlist files inside.
     * @param playlistPath a File instance with the path to the folder.
     * @throws IOException 
     */
    public PlayListDao(File playlistPath) throws FileNotFoundException, IOException {
        if (this.playlists == null) {
            this.playlists = new HashMap<>();
        }
        this.playlistPath = playlistPath;
        this.readFile();
    }

    /**
     * Reads all files contained in the set folder path and adds the data to the
     * playlists,This method should be called whenever this class is 
     * instantiated. 
     */
    @Override
    public void readFile() {
        String playlistId;
        String playlistName;
        BufferedReader reader;
        Playlist innerPlaylist;

        for (File playlist : this.playlistPath.listFiles()) {
            try {
                reader = new BufferedReader(new FileReader(playlist));
                playlistName = reader.readLine();
                playlistId = reader.readLine();
                
                if(!PlayListDao.playlists.containsKey(playlistId)){
                    PlayListDao.playlists.put(playlistId, new ArrayList<>());
                }
                
                innerPlaylist = new Playlist(playlistName);
                while (reader.ready()) {
                    innerPlaylist.addMusic(new Music(new File(reader.readLine())));
                }
                PlayListDao.playlists.get(playlistId).add(innerPlaylist);

                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns an array with all playlists that are set with the user id.
     * @param id The user id that created the playlists
     * @return An ArrayList if there contains a playlist created by the user, a 
     * null pointer otherwise.  
     */
    public ArrayList<Playlist> getPlaylistByUser(String id) {
        if (this.playlists.containsKey(id)) {
            return this.playlists.get(id);
        }
        return null;
    }

    /**
     * This method saves all data contained in the playlist files in the set 
     * folder, This method should always be called before this object is destroyed.
     */
    @Override
    public void saveBackup() {
        Set<String> adminIds = this.playlists.keySet();
        for (File toBeDeleted : this.playlistPath.listFiles()) {
            toBeDeleted.delete();
        }
        int fileIdentificator = 1;
        File savedFile;
        FileWriter writer;
        for (String id : adminIds) {
            for (Playlist toBeSaved : this.playlists.get(id)) {
                try {
                    savedFile = new File(this.playlistPath.getAbsolutePath() + "/playlist_" + fileIdentificator++ + ".txt");
                    savedFile.createNewFile();
                    writer = new FileWriter(savedFile, false);
                    writer.write(toBeSaved.getName() + "\n");
                    writer.write(id + "\n");
                    for (Music toBeSavedMusic : toBeSaved.getMusics()) {
                        
                        writer.write(toBeSavedMusic.getMusicFile().getAbsolutePath() + "\n");

                    }
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Adds a new playlist given a user and a Playlist object.
     * @param user The user who created the playlist
     * @param playlist  the playlist object already completed.
     */
    public void addPlaylist(VipUser user, Playlist playlist) {
        if (!this.playlists.containsKey(user.getId())) {
            playlists.put(user.getId(), new ArrayList<>());
        }
        this.playlists.get(user.getId()).add(playlist);
    }

    /**
     * Removes a playlist from the database given it's creator id and it's name.
     * @param user the playlist creator.
     * @param playlistName the name of the playlist to be removed. 
     * @return True if the playlist was inside the database and if it was 
     * removed, False otherwise.
     */
    public boolean removePlaylist(VipUser user, String playlistName) {
        if (this.playlists.containsKey(user.getId())) {
            for (Playlist toBeRemoved : this.playlists.get(user.getId())) {
                if (toBeRemoved.getName().equals(playlistName)) {
                    this.playlists.get(user.getId()).remove(toBeRemoved);
                    return true;
                }
            }
        }
        return false;
    }

}
