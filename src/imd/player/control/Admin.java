package imd.player.control;

import java.io.File;
import java.util.ArrayList;

public class Admin extends User{
    private ArrayList<Playlist> playlists;

    public Admin(String login, String password) {
        super(login, password);
        playlists = new ArrayList<Playlist>();
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void deletePlaylist(String name) {
        for(Playlist p : this.playlists){
            if(p.getName().equals(name)){
                playlists.remove(p);
                break;
            }
        }
    }
    
    public void addToPlaylists(String name, ArrayList<File> fileList){
        playlists.add(new Playlist(name, fileList));
    }
    
    public void addToPlayLists (String name, File musicFile, String playlistName){
        boolean isInserted = false;
        boolean playlistInserted = false;
        
        for(Playlist pl : playlists){
            if(pl.getName() == playlistName){
                playlistInserted = true;
                
                for(File music : pl.getMusics()){
                    
                    if(music == musicFile){
                        isInserted = true;
                        return;
                    }
                    pl.addMusic(musicFile);
                    return;
                }
            }
        }
        
        if(!playlistInserted){
            Playlist toInsert = new Playlist(playlistName);
            toInsert.addMusic(musicFile);
            playlists.add(toInsert);
        }
    }
            
    public boolean deleteAllPlaylists(String password){
        if(password.equals(this.getPassword())){
            playlists.clear();
            return true;
        }
        else
            return false;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("1\n");
        sb.append(this.id + "\n");
        sb.append(this.login + "\n");
        sb.append(this.password + "\n");
        
        return sb.toString();
    }
    
}
