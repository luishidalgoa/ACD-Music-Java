package dev.iesfranciscodelosrios.acdmusic.Model.DTO;

import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Artist;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;

import java.util.Objects;

public class ArtistDTO extends User {
    private int id_artist;
    String nacionality;
    int id_user;

    public ArtistDTO(Artist artist){
        this.id=artist.getId();
        this.nickName=artist.getNickName();
        this.name=artist.getName();
        this.lastName=artist.getLastName();
        this.picture=artist.getPicture();
        this.email=artist.getEmail();
        this.id_artist = artist.getId();
        this.nacionality = artist.getNacionality();
        this.id_user = artist.getId_user();
    }

    public ArtistDTO() {
        this.id_artist = -1;
        this.nacionality = "";
        this.id_user = -1;
    }

    public ArtistDTO(int id, String nickName, String name, String lastName, String picture, String email, int id_artist, String nacionality, int id_user) {
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
        ArtistDTO artistDTO = (ArtistDTO) o;
        return id_artist == artistDTO.id_artist && id_user == artistDTO.id_user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_artist, id_user);
    }

    @Override
    public String toString() {
        return "ArtistDTO{" +
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
