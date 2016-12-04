package imd.player.control;

import imd.player.model.ModelFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anderson Santos and Yuri Reinaldo
 */
public class UserControl {

    private User loggedUser;

    public UserControl() {

    }

    public boolean loginAttempt(String login, String password) {
        User user;
        try {
            user = ModelFacade.getInstance().getUser(login);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (user == null) {
            return false;
        }

        if (user.getPassword().equals(password)) {
            this.loggedUser = user;
            return true;
        }
        return false;
    }

    public boolean addNormalUser(String login, String password) {
        if (this.isAdmin()) {
            NormalUser user = new NormalUser(login, password);
            try {
                return ModelFacade.getInstance().addUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean addVipUser(String login, String password) {
        if (this.isAdmin()) {
            VipUser user = new VipUser(login, password);
            try {
                return ModelFacade.getInstance().addUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ArrayList<String> getAllUsersLogins() {
        ArrayList<String> usersLogin = new ArrayList<>();
        if (this.isAdmin()) {
            try {
                for (User user : ModelFacade.getInstance().getAllUsers()) {
                    usersLogin.add(user.getLogin());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return usersLogin;
    }

    public boolean removeUser(String login) {
        if (this.isAdmin()) {
            try {
                return ModelFacade.getInstance().removeUser(login);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void addPlaylist(String name, ArrayList<Music> musics) {
        if (this.isAdmin()) {
            try {
                ModelFacade.getInstance().addPlaylist((VipUser) this.loggedUser, new Playlist(name, musics));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean removePlaylist(String name) {
        if (this.isAdmin()) {
            try {
                return ModelFacade.getInstance().removePlayList((VipUser) this.loggedUser, name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean isAdmin() {
        return (this.loggedUser instanceof VipUser);
    }

    public User getLoggedUser() {
        return this.loggedUser;
    }
}
