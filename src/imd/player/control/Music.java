/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imd.player.control;

import java.io.File;

/**
 *
 * @author yuri-wrlk
 */
public class Music {
    private String name;
    private File musicFile;

    public Music(String name, File musicFile) {
        this.name = name;
        this.musicFile = musicFile;
    }

    public File getMusicFile() {
        return musicFile;
    }

    public String getName() {
        return name;
    }

    public void setMusicFile(File musicFile) {
        this.musicFile = musicFile;
    }

    public void setName(String name) {
        this.name = name;
    }
}
