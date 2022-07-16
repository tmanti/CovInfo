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

    public String getUsername() {
        return username;
    }

    public int getPrivilege() {
        return privilege;
    }

    public String getPasswordhash(){
        return this.passwordhash;
    }

    public boolean ValidatePassword(String password){
        throw new NotImplementedException();
    }
}
