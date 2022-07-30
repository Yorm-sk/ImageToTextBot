package com.Yorm_sk.image_to_text_bot.models;

import java.util.Objects;

public class User {
    private long id;
    private String nickname;
    private String firstName;
    private String lastName;
    private String telegramLang;
    private long telegramId;

    public User() {
    }

    public User(String nickname, String firstName, String lastName, String telegramLang, long telegramId) {
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telegramLang = telegramLang;
        this.telegramId = telegramId;
    }

    public User(long id, String nickname, String firstName, String lastName, String telegramLang, long telegramId) {
        this.id = id;
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telegramLang = telegramLang;
        this.telegramId = telegramId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelegramLang() {
        return telegramLang;
    }

    public void setTelegramLang(String telegramLang) {
        this.telegramLang = telegramLang;
    }

    public long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(long telegramId) {
        this.telegramId = telegramId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && telegramId == user.telegramId
                && Objects.equals(nickname, user.nickname)
                && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName)
                && Objects.equals(telegramLang, user.telegramLang);
    }

    @Override
    public int hashCode() {
        int result = 31;
        result += id;
        result += telegramId;
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", telegramLang='" + telegramLang + '\'' +
                ", telegramId=" + telegramId +
                '}';
    }
}
