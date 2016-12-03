package imd.player.control;

import imd.player.model.ModelFacade;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Anderson Santos and Yuri Reinaldo
 */
public class ControlFacade {
    private UserControl userControl;
    private final MusicControl musicControl;
    private static ControlFacade singleton;
            
    private ControlFacade(){
        musicControl = new MusicControl();
        userControl = new UserControl();
    }
    
    public static ControlFacade getInstance(){
        if(ControlFacade.singleton == null){
            ControlFacade.singleton = new ControlFacade();
        }
        return ControlFacade.singleton;
    }
    
    public boolean loginAttempt(String login, String password){
        return this.userControl.loginAttempt(login, password);
    }
    
    public boolean loggedUserIsAdmin(){
        return this.userControl.isAdmin();
    }
    
    public ArrayList<User> getNormalUsers(){
        return this.userControl.getNormalUsers();
    }
    
    public void exit() throws IOException {
        ModelFacade.getInstance().saveFinalData();
    }
   
}
