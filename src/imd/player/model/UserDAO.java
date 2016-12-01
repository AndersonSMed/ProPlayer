/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imd.player.model;

import imd.player.control.Admin;
import imd.player.control.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yuri-wrlk
 */
public class UserDAO {

    private static HashMap< String, User > userMap;
    private File userFile;
    
    public UserDAO(File userFile) {
        this.userFile = userFile;
        
    }
    
    public void readFile () throws IOException
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(userFile));
            while(reader.ready()){
                
            }
                
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
}
