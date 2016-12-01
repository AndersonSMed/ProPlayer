
package imd.player.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MusicDao {
    
    private File musicFile;
    private ArrayList<File> musics;
    
    public MusicDao(File musicFile){
        this.musicFile = musicFile;
        
        try {
            readFile();
        } catch (IOException ex) {
            Logger.getLogger(MusicDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void readFile() throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(musicFile));
        while(reader.ready()){
            musics.add(new File(reader.readLine()));
        }
        reader.close();
        
    }
    
    public void addToFile (File file){
        try {
            FileWriter writer = new FileWriter(musicFile);
        } catch (IOException ex) {
            Logger.getLogger(MusicDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveBackup(ArrayList<File> musics){
        
    }
}
