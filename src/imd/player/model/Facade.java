package imd.player.model;

import imd.player.control.MusicControl;
import java.io.File;
import java.util.ArrayList;

public class Facade {

    private static Facade singleton;
    private MusicControl musicDao;
    private UserDAO userDAO;

    private Facade() {
        this.musicDao = new MusicControl();
    }

    public static Facade getInstance() {
        if (Facade.singleton == null) {
            Facade.singleton = new Facade();
        }
        return Facade.singleton;
    }
}
