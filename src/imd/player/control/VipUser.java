package imd.player.control;

import java.util.ArrayList;

public class VipUser extends User{
    
    public VipUser(String login, String password) {
        super(login, password);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("1\n");
        sb.append(super.toString());
        return sb.toString();
    }
    
}
