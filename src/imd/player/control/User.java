package imd.player.control;

public class User {
    protected String id;
    protected String login;
    protected String password;
    
    public User(String login, String password){
        this.id = login.hashCode() + "";
        this.login = login;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("0\n");
        sb.append(this.id + "\n");
        sb.append(this.login + "\n");
        sb.append(this.password + "\n");
        
        return sb.toString();
    }
    
    
}
