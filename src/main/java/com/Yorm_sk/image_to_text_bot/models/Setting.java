package com.Yorm_sk.image_to_text_bot.models;

import java.util.Objects;

public class Setting {
    private long id;
    private String interfaceLang;
    private String imageLang;
    private User user;

    public Setting() {
    }

    public Setting(String interfaceLang, String imageLang, User user) {
        this.interfaceLang = interfaceLang;
        this.imageLang = imageLang;
        this.user = user;
    }

    public Setting(long id, String interfaceLang, String imageLang, User user) {
        this.id = id;
        this.interfaceLang = interfaceLang;
        this.imageLang = imageLang;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInterfaceLang() {
        return interfaceLang;
    }

    public void setInterfaceLang(String interfaceLang) {
        this.interfaceLang = interfaceLang;
    }

    public String getImageLang() {
        return imageLang;
    }

    public void setImageLang(String imageLang) {
        this.imageLang = imageLang;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Setting setting = (Setting) o;
        return id == setting.id
                && Objects.equals(interfaceLang, setting.interfaceLang)
                && Objects.equals(imageLang, setting.imageLang) && Objects.equals(user, setting.user);
    }

    @Override
    public int hashCode() {
        int result = 29;
        result += id;
        result += user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "id=" + id +
                ", interfaceLang='" + interfaceLang + '\'' +
                ", imageLang='" + imageLang + '\'' +
                ", user=" + user +
                '}';
    }
}
