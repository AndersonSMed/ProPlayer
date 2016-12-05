package imd.player.control;

/**
 * Extends {@link User} class by setting the differences of privilege of a Normal 
 * User, a Normal User can play musics from a folder.
 * @author Anderson Santos and Yuri Reinaldo
 */
public class NormalUser extends User{

    /**
     * Creates a new NormalUser in similar fashion to an {@link User}
     * @param login this normal user's login
     * @param password the password that will be set to this normal user
     */
    public NormalUser(String login, String password){
        super(login, password);
    }

    /**
     * Overrides the toString method of {@link User} and adds information that
     * sets a NormalUser apart, Mostly used for saving data.
     * @return A string with this object's data to be saved.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("0\n");
        sb.append(super.toString());
        return sb.toString();
    }
    
    
}
