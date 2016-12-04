/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imd.player.model;

import imd.AndersonYuri.dataStruct.Node;
import imd.AndersonYuri.dataStruct.Tree;
import imd.player.control.VipUser;
import imd.player.control.NormalUser;
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
public class UserDao implements DaoInterface {

    private final File userFile;
    private static Tree<User, String> userTree;

    public UserDao(File userFile) throws IOException {
        if (UserDao.userTree == null) {
            UserDao.userTree = new Tree<>();
        }
        this.userFile = userFile;
        this.readFile();
    }

    @Override
    public void readFile() {
        String level;
        String login;
        String password;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.userFile));
            while (reader.ready()) {

                level = reader.readLine();
                login = reader.readLine();
                password = reader.readLine();

                if (level.equals("1")) {
                    UserDao.userTree.insertNode(login.hashCode() + "", new VipUser(login, password));
                } else if (level.equals("0")) {
                    UserDao.userTree.insertNode(login.hashCode() + "", new NormalUser(login, password));
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveBackup() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            FileWriter writer = new FileWriter(this.userFile, false);
            userTree.enlistTreeElements(userList);
            for (User user : userList) {
                writer.write(user.toString());
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser(String login) {
        Node<User, String> node = UserDao.userTree.binarySearch(login.hashCode() + "");
        if(node == null) return null;
        return node.getData();
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<>();
        this.userTree.enlistTreeElements(list);
        return list;
    }

    public boolean addUser(User user) {
        if (this.userTree.binarySearch(user.getId()) == null) {
            this.userTree.insertNode(user.getId(), user);
            return true;
        }
        return false;
    }

    public boolean removeUser(String login) {
        String hashCode = login.hashCode() + "";
        if (this.userTree.binarySearch(hashCode) != null) {
            this.userTree.removeNode(hashCode);
            return true;
        }
        return false;
    }
    
}
