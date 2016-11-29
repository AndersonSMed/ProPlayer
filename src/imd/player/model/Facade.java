package imd.player.model;

public class Facade{
    private static Facade singleton;
    private MusicDAO musicDao;
    
    private Facade(){
        musicDao = new MusicDAO();
    }
    
    public static Facade getInstance(){
        if(Facade.singleton == null){
            Facade.singleton = new Facade();
        }
        return Facade.singleton;
    }
    
}
