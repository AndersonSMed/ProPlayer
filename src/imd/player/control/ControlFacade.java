package imd.player.control;

import imd.player.model.ModelFacade;

/**
 * 
 * @author Anderson Santos and Yuri Reinaldo
 */
public class ControlFacade {
    private User user;
    private final MusicControl musicControl;
    private static ControlFacade singleton;
            
    private ControlFacade(){
        musicControl = new MusicControl();
    }
    
    public static ControlFacade getInstance(){
        if(ControlFacade.singleton == null){
            ControlFacade.singleton = new ControlFacade();
        }
        return ControlFacade.singleton;
    }
    
    public User getUserByLogin(String login){
        return ModelFacade.getInstance().getUser(login);
    }
}
