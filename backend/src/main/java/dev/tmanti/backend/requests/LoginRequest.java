package dev.tmanti.backend.requests;

public class LoginRequest {
    String username;
    String password;

    public LoginRequest(String uname, String pass){
        this.username = uname;
        this.password = pass;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String uname){
        this.username = uname;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String pass){
        this.password = pass;
    }
}
