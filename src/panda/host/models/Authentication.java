package panda.host.models;

import panda.host.utils.Panda;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

public class Authentication {
    private int code; // 0 = Credentials don't match any user, 1 = User found
    private User user;
    private Timestamp date;
    private boolean valid;
    private String codeMeaning;

    public Authentication(){
    }

    public Authentication(int code) {
        this.code = code;
        this.user = new User(Panda.DEFAULT_GUEST_NAME);
        this.date = Timestamp.from(Instant.now());
    }

    public Authentication(boolean asGuest) {
        if(asGuest){
            this.code = 2;
        } else {
            this.code = 0;
        }
        this.user = new User(Panda.DEFAULT_GUEST_NAME);
        this.date = Timestamp.from(Instant.now());
    }

    public Authentication(int code, User user, Timestamp date) {
        this.code = code;
        this.user = user;
        this.date = date;
    }

    @Override
    public String toString() {
        if (user != null) {
            return "Authentication {" +
                    " code = " + code +
                    ", user = " + user.getUsername() +
                    ", date = " + date.toString() +
                    " }";
        } else {
            return getCodeMeaning();
        }
    }

    public String getCodeMeaning() {
        switch (code) {
            case 0 -> {
                return "Credentials don't match any user";
            }
            case 1 -> {
                return "User found";
            }
            case 2 -> {
                return "Guest session";
            }
            default -> {
                return "Unknown code";
            }
        }
    }

    public boolean isValid(){
        // An authentication is only valid when its code is equal to 1 (Guest sessions are not considered as valid)
        return code == 1;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
