package ru.nsu.fit.modao.model;

public class UserIn {
    private String login;

    public UserIn(String login, String password) {
        this.login = login;
        this.password = password;
    }

    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
