package ru.nsu.fit.modao.model;

public class NewUser {
    private String username;
    private String login;
    private String password;
    private String phone_number;
    private String bank;
    private Integer idPicture;

    public NewUser(String username, String login, String password, String phone_number, String bank, Integer idPicture) {
        this.username = username;
        this.login = login;
        this.password = password;
        this.phone_number = phone_number;
        this.bank = bank;
        this.idPicture = idPicture;
    }


}
