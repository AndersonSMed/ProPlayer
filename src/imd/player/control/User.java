/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imd.player.control;

/**
 *
 * @author Anderson Santos and Yuri Reinaldo
 */
public abstract class User {
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
        sb.append(this.login + "\n");
        sb.append(this.password + "\n");
        return sb.toString();
    }
    
}
