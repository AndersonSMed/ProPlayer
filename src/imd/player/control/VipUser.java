package imd.player.control;

/**
 * Extends {@link User} class by setting the differences of privilege of a Vip 
 * User, a Vip User can add other Users, create and use his own playlists and 
 * all things a Normal User can do.
 * @author Anderson Santos and Yuri Reinaldo
 */
public class VipUser extends User{
    
    /**
     * Creates a new VipUser in similar fashion to an {@link User}
     * @param login
     * @param password 
     */
    public VipUser(String login, String password) {
        super(login, password);
    }
    
    /**
     * Overrides the toString method of {@link User} and adds information that
     * sets a VipUser apart, Mostly used for saving data.
     * @return A string with this object's data to be saved.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("1\n");
        sb.append(super.toString());
        return sb.toString();
    }
    
}
