package imd.player.control;

import java.io.File;
import java.util.Random;

public class User {
    protected int id;
    protected String login;
    protected String password;
    
    public User(String login, String password){
        Random rand = new Random();
        this.id = rand.nextInt(1000);
        this.login = login;
        this.password = password;
    }

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public int getId() {
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

    public void generateNewID(){
        Random rand = new Random();
        this.id = rand.nextInt(1000);
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
