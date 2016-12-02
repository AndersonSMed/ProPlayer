package imd.player.model;

import imd.player.control.Admin;
import imd.player.control.Music;
import imd.player.control.Playlist;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PlayListDao {

    private static HashMap<String, ArrayList<Playlist>> playlists;
    private File playlistPath;

    public PlayListDao(File playlistPath) throws FileNotFoundException, IOException {
        if (this.playlists == null) {
            this.playlists = new HashMap<>();
        }
        this.playlistPath = playlistPath;
        this.readPath();
    }

    private void readPath() throws FileNotFoundException, IOException {
        String playlistId;
        String playlistName;
        BufferedReader reader;
        Playlist innerPlaylist;

        for (File playlist : this.playlistPath.listFiles()) {

            reader = new BufferedReader(new FileReader(playlist));
            playlistName = reader.readLine();
            playlistId = reader.readLine();
            this.playlists.put(playlistId, new ArrayList<>());
            innerPlaylist = new Playlist(playlistName);
            while (reader.ready()) {
                innerPlaylist.addMusic(new Music(new File(reader.readLine())));
            }
            this.playlists.get(playlistId).add(innerPlaylist);

            reader.close();
        }
    }

    public ArrayList<Playlist> getPlaylistByUser(String id) {
        if (this.playlists.containsKey(id)) {
            return this.playlists.get(id);
        } else {
            return null;
        }
    }

    public void saveBackup() {
        Set<String> adminIds = this.playlists.keySet();
        for (File toBeDeleted : this.playlistPath.listFiles()) {
            toBeDeleted.delete();
        }
        int fileIdentificator = 1;
        for (String id : adminIds) {
            for(Playlist toBeSaved : this.playlists.get(id)){
                
            }
        }

    }

    public boolean addPlaylist(Admin user, Playlist playlist) {
        if (this.playlists.containsKey(user.getId())) {
            for (Playlist toBeAdded : this.playlists.get(user.getId())) {
                if (toBeAdded.getName().equals(playlist.getName())) {
                    toBeAdded = playlist;
                    return true;
                }
            }
            this.playlists.get(user.getId()).add(playlist);
            return true;
        }
        return false;
    }

    public boolean removePlaylist(Admin user, String playlistName) {
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
