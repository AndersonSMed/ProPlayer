package imd.player.model;

import imd.player.control.VipUser;
import imd.player.control.Music;
import imd.player.control.Playlist;
import imd.player.control.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ModelFacade {

    private static ModelFacade singleton;
    private final UserDao userDao;
    private final MusicDao musicDao;
    private final PlayListDao playlistDao;
    private final FolderDao dao;
    
    private ModelFacade() throws IOException {
        this.dao = new FolderDao();
        this.musicDao = new MusicDao(this.dao.getMusicFile());
        this.userDao = new UserDao(this.dao.getUserFile());
        this.playlistDao = new PlayListDao(this.dao.getPlaylistFolder());
    }

    public static ModelFacade getInstance() throws IOException {
        if (ModelFacade.singleton == null) {
            ModelFacade.singleton = new ModelFacade();
        }
        return ModelFacade.singleton;
    }
    
    public boolean addUser(User user){
        return this.userDao.addUser(user);
    }

    public User getUser(String login) {
        return this.userDao.getUser(login);
    }
    
    public ArrayList<User> getAllUsers(){
        return this.userDao.getAllUsers();
    }
   
    
    public boolean removeUser(String login){
        return this.userDao.removeUser(login);
    }
    
    public boolean addMusic(Music music){
        return this.musicDao.addMusic(music);
    }
    
    public Music getMusic(String musicName){
        return this.musicDao.getMusic(musicName);
    }
    
    public void addPlaylist(VipUser user, Playlist playlist){
        this.playlistDao.addPlaylist(user, playlist);
    }
    
    public boolean removePlayList(VipUser user, String playlistName){
        return this.playlistDao.removePlaylist(user, playlistName);
    }
    
    public ArrayList<Playlist> getPlaylistsByUserId(String id){
        return this.playlistDao.getPlaylistByUser(id);
    }
    
    public void saveFinalData() throws IOException{
        this.musicDao.saveBackup();
        this.userDao.saveBackup();
        this.playlistDao.saveBackup();
    }
    
    public Collection<Music> getAllMusics(){
        return this.musicDao.getAllMusics();
    }
}
