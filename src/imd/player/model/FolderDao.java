/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imd.player.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FolderDao {

    private final File folders = new File("/home/anderson/Documents/Diretorios.txt");
    private File musicFile;
    private File playlistFolder;
    private File userFile;

    public FolderDao() throws FileNotFoundException, IOException {
        try (BufferedReader bufferReader = new BufferedReader(new FileReader(folders))) {
            this.musicFile = new File(bufferReader.readLine());
            this.playlistFolder = new File (bufferReader.readLine());
            this.userFile = new File(bufferReader.readLine());
        }
    }

    public File getMusicFile() {
        return musicFile;
    }

    public void setMusicFile(File musicFile) {
        this.musicFile = musicFile;
    }

    public File getPlaylistFolder() {
        return playlistFolder;
    }

    public void setPlaylistFolder(File playlistFolder) {
        this.playlistFolder = playlistFolder;
    }

    public File getUserFile() {
        return userFile;
    }

    public void setUserFile(File userFile) {
        this.userFile = userFile;
    }

}
