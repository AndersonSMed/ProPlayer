/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imd.player.model;

import imd.player.control.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {
    private final File folders = new File("/home/anderson/Documents/Diretorios.txt");
    private File musicFile;
    private Path playlistFolder;
    private File userFile;
    
    public FileManager() throws FileNotFoundException, IOException{
        BufferedReader bufferReader = new BufferedReader(new FileReader(folders));
        this.musicFile = new File(bufferReader.readLine());
        this.playlistFolder = Paths.get(bufferReader.readLine());
        this.userFile = new File(bufferReader.readLine());
    }

    public File getMusicFile() {
        return musicFile;
    }

    public void setMusicFile(File musicFile) {
        this.musicFile = musicFile;
    }

    public Path getPlaylistFolder() {
        return playlistFolder;
    }

    public void setPlaylistFolder(Path playlistFolder) {
        this.playlistFolder = playlistFolder;
    }

    public File getUserFile() {
        return userFile;
    }

    public void setUserFile(File userFile) {
        this.userFile = userFile;
    }
    
    public void insertUser(User user) throws IOException{
        FileWriter writer = new FileWriter(userFile, true);
        
        
    }
     
}
