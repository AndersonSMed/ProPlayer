/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imd.player.model;

import imd.AndersonYuri.dataStruct.Tree;
import imd.player.control.Admin;
import imd.player.control.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yuri-wrlk
 */
public class UserDAO {

    private File userFile;
    private static Tree<User, String> userTree = new Tree <User,String>();
    
    public UserDAO(File userFile) {
        this.userFile = userFile;
        
    }
    
    public void readFile () throws IOException
    {
        String level;
        String id;
        String login;
        String password;
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(userFile));
            while(reader.ready()){
                level = reader.readLine();
                id = reader.readLine();
                login = reader.readLine();
                password = reader.readLine();
                
                if(level.equals("1")){
                    userTree.insertNode(login, new Admin(Integer.parseInt(id), login, password));
                }
                else if (level.equals("0")){
                    userTree.insertNode(login, new User(Integer.parseInt(id), login, password));
                }
            }
                
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void saveBackup (){
        ArrayList<User> userList = new ArrayList<User>();
        try {
            FileWriter writer = new FileWriter(userFile, false);
            userTree.enlistTreeElements(userList);
            for(User user : userList)
            {
                writer.write(user.toString());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
