package dev.iesfranciscodelosrios.acdmusic.Model.Domain;

import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Genre;

public class Song {
    private int id;
    private int id_album;
    private String picture;
    private Genre genre;

    public Song(int id, int id_album, String picture, Genre genre) {
        this.id = id;
        this.id_album = id_album;
        this.picture = picture;
        this.genre = genre;
    }
    public Song(int id_album, String picture, Genre genre) {
        this.id_album = id_album;
        this.picture = picture;
        this.genre = genre;
    }
    public Song() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_album() {
        return id_album;
    }

    public void setId_album(int id_album) {
        this.id_album = id_album;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
