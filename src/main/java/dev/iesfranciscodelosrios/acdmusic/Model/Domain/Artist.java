package dev.iesfranciscodelosrios.acdmusic.Model.Domain;

import java.util.Objects;

public class Artist extends User{
    int id_artist;
    String nacionality;
    int id_user;

    public Artist(int id, String nickName, String name, String lastName, String picture, String email, String password, int id_artist, String nacionality, int id_user) {
        super(id, nickName, name, lastName, picture, email, password);
        this.id_artist = id_artist;
        this.nacionality = nacionality;
        this.id_user = id_user;
    }

    public Artist(String nickName, String name, String lastName, String picture, String email, String password, int id_artist, String nacionality, int id_user) {
        super(nickName, name, lastName, picture, email, password);
        this.id_artist = id_artist;
        this.nacionality = nacionality;
        this.id_user = id_user;
    }

    public Artist() {
    }

    public Artist(int id, String nickName, String name, String lastName, String picture, String email, int id_artist, String nacionality, int id_user) {
        super(id, nickName, name, lastName, picture, email);
        this.id_artist = id_artist;
        this.nacionality = nacionality;
        this.id_user = id_user;
    }

    public int getId_artist() {
        return id_artist;
    }

    public void setId_artist(int id_artist) {
        this.id_artist = id_artist;
    }

    public String getNacionality() {
        return nacionality;
    }

    public void setNacionality(String nacionality) {
        this.nacionality = nacionality;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return id_artist == artist.id_artist && id_user == artist.id_user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_artist, id_user);
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id_artist=" + id_artist +
                ", nacionality='" + nacionality + '\'' +
                ", id_user=" + id_user +
                ", id=" + id +
                ", nickName='" + nickName + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", picture='" + picture + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
