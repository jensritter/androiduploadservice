package com.google.code.androiduploadservice;

import java.util.Date;

public class Status {
    String file;
    Date changed = new Date(0);
    int id;
    

    public String getFile() {
        return file;
    }
    public void setFile(String file) {
        this.file = file;
    }
    public Date getChanged() {
        return changed;
    }
    public void setChanged(Date changed) {
        this.changed = changed;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}
