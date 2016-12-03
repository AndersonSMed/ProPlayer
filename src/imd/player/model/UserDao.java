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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Anderson Santos and Yuri Reinaldo
 */
public class UserDao {

    private File userFile;
    private static Tree<User, String> userTree;

    public UserDao(File userFile) throws IOException {
        if (this.userTree == null) {
            this.userTree = new Tree<>();
            this.readFile();
        }
        this.userFile = userFile;
    }

    private void readFile() throws IOException {
        String level;
        String id;
        String login;
        String password;
        BufferedReader reader = new BufferedReader(new FileReader(this.userFile));
        while (reader.ready()) {

            level = reader.readLine();
            id = reader.readLine();
            login = reader.readLine();
            password = reader.readLine();

            if (level.equals("1")) {
                this.userTree.insertNode(login, new Admin(login, password));
            } else if (level.equals("0")) {
                this.userTree.insertNode(login, new User(login, password));
            }
        }
        reader.close();

    }

    public void saveBackup() throws IOException {
        ArrayList<User> userList = new ArrayList<>();
        FileWriter writer = new FileWriter(this.userFile, false);
        userTree.enlistTreeElements(userList);
        for (User user : userList) {
            writer.write(user.toString());
        }
        writer.close();
    }

    public User getUser(String login) {
        return this.userTree.binarySearch(login.hashCode() + "").getData();
    }

    public boolean addUser(User user) {
        if (this.userTree.binarySearch(user.getId()) == null) {
            this.userTree.insertNode(user.getId(), user);
            return true;
        }
        return false;
    }

    public boolean removeUser(String login) {
        if (this.userTree.binarySearch(login.hashCode() + "") != null) {
            this.userTree.removeNode(login.hashCode() + "");
            return true;
        }
        return false;
    }

}
