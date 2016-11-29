package imd.player.control;

import java.util.ArrayList;

public class Admin extends User{
    private ArrayList<Playlist> playlists;

    public Admin(String login, String password) {
        super(login, password);
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
