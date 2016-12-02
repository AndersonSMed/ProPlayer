package imd.player.model;

import imd.player.control.MusicControl;
import imd.player.control.User;

public class ModelFacade {

    private static ModelFacade singleton;
    private MusicControl musicDao;
    private UserDAO userDAO;

    private ModelFacade() {
        this.musicDao = new MusicControl();
    }

    public static ModelFacade getInstance() {
        if (ModelFacade.singleton == null) {
            ModelFacade.singleton = new ModelFacade();
        }
        return ModelFacade.singleton;
    }
    
    public void playMusic(String musicName){
        
    }
    
    public void addInTree(User user){
        
    }

    public User getUser(String login) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
