package ru.nsu.fit.modao.model;

import java.util.List;

public class User {
    private Integer id;
    private String username;
    private String login;
    private String password;
    private String phone_number;
    private String bank;
    private Integer idPicture;
    private List<Group> groupCustomPairIdNameList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Integer getIdPicture() {
        return idPicture;
    }

    public void setIdPicture(Integer idPicture) {
        this.idPicture = idPicture;
    }

    public List<Group> getGroups() {
        return groupCustomPairIdNameList;
    }

    public void setGroups(List<Group> groups) {
        this.groupCustomPairIdNameList = groups;
    }
}
