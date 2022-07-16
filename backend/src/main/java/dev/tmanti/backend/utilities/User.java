package dev.tmanti.backend.utilities;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.UUID;

public class User {
    private UUID id;
    private String username;
    private String passwordhash;
    private int privilege;

    public User(UUID id, String username, String passwordhash, int privilege){
        this.id = id;
        this.username = username;
        this.passwordhash = passwordhash;
        this.privilege = privilege;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public String getPasswordhash(){
        return this.passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public boolean ValidatePassword(String password){
        throw new NotImplementedException();
    }
}
