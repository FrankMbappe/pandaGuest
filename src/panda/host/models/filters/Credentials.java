package panda.host.models.filters;

import panda.host.models.User;

import java.util.ArrayList;

public class Credentials implements Filter {
    private String username;
    private String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public Credentials(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
