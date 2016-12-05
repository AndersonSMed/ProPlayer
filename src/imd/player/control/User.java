package imd.player.control;

/**
 * The abstract representation of what parameters and responsibilities a user
 * should have.
 * @author Anderson Santos and Yuri Reinaldo
 */
public abstract class User {
    /**
     * A {@link String} that keeps the user id, this data should be hidden and
     * the user must not access it easily.
     */
    protected String id;
    /**
     * A {@link String} that keeps the user login, this should always be set when
     * creating a new User, since it is it's main key to access the data.
     */
    protected String login;
    /**
     * A {@link String} that keeps the user password, it must be set when creating
     * objects of this class and should not be easily accessed.
     */
    protected String password;
    
    /**
     * A simple constructor who sets the variables and creates a new id for the 
     * user.
     * @param login The login to be set.
     * @param password The password to be set.
     */
    public User(String login, String password){
        this.id = login.hashCode() + "";
        this.login = login;
        this.password = password;
    }

    /**
     * Returns the id of this User object, it is used in order to access data
     * related to this User.
     * @return a {@link String} representing this User's id. 
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the login of this User, this is mostly used to access other user's
     * objects.
     * @return a {@link String} with this user login. 
     */
    public String getLogin() {
        return login;
    }

    /**
     * Returns this users password, should be used only for user verification
     * should be used sparingly.
     * @return 
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets a new password to this user, used mostly for updating user data.
     * @param password this user's new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Turns this user data in a {@link String} in order to be saved in a backup.
     * @return A {@link String} that contains this user's login and password in
     * separate lines.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.login + "\n");
        sb.append(this.password + "\n");
        return sb.toString();
    }
    
}
