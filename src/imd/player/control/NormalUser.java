package imd.player.control;

public class NormalUser extends User{

    public NormalUser(String login, String password){
        super(login, password);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("0\n");
        sb.append(super.toString());
        return sb.toString();
    }
    
    
}
