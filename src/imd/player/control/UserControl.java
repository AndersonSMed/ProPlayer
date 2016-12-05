package imd.player.control;

import imd.player.model.ModelFacade;
import java.util.ArrayList;

/**
 * Manages most of {@link User} related methods, including {@link VipUser} and
 * {@link NormalUser} implementations.
 * @author Anderson Santos and Yuri Reinaldo
 */
public class UserControl {

    /**
     * The {@link User} instance who is set to be the main User of this program 
     * usage.
     */
    private User loggedUser;

    /** 
     * Simple constructor, not much is done here.
     */
    public UserControl() {

    }

    /**
     * Tries to make a login attempt given the passed login and password.
     * @param login The login which is used to identify the user.
     * @param password The password used to verify this user identity.
     * @return True if both fields match a User registered, False otherwise.
     */
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

    /**
     * Adds a new {@link User} with {@link NormalUser} privileges. 
     * @param login the new user login, cannot be set to another value later.
     * @param password the new user's first password.
     * @return True if the user is a new one and could be saved in the database,
     * False if a user with the same login already exists.
     */
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

    /**
     * Adds a new {@link User} with {@link VipUser} privileges. 
     * @param login the new user login, cannot be set to another value later.
     * @param password the new user's first password.
     * @return True if the user is a new one and could be saved in the database,
     * False if a user with the same login already exists.
     */
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

    /**
     * Adds a new {@link Playlist} to the passed User's collection
     * @param name The user who owns this playlist
     * @param musics The array of musics who will be inserted into the playlist
     */
    public void addPlaylist(String name, ArrayList<Music> musics) {
        if (this.isAdmin()) {
            try {
                ModelFacade.getInstance().addPlaylist((VipUser) this.loggedUser, new Playlist(name, musics));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Removes a {@link Playlist} from the logged user's collection
     * @param name the name of the string which should be removed
     * @return True if the playlist was found and was removed, False otherwise.
     */
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

    /**
     * Returns whether the logged user has {@link VipUser} privileges
     * @return True if the logged user is a {@link VipUser}, False otherwise
     */
    public boolean isAdmin() {
        return (this.loggedUser instanceof VipUser);
    }

    /**
     * Returns the instance of the logged user, used for many verifications.
     * @return the instance of the actual user
     */
    public User getLoggedUser() {
        return this.loggedUser;
    }
}
