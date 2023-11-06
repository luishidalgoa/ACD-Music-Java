package dev.iesfranciscodelosrios.acdmusic.Model.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Album {
    private int idAlbum;
    private int idArtist;
    private String name;
    private String date;
    private String picture;
    private Integer reproductions;
    private List<Song> songs;

    public Album(int idAlbum, int idArtist, String name, String date, String picture, Integer reproductions) {
        this.idAlbum = idAlbum;
        this.idArtist = idArtist;
        this.name = name;
        this.date = date;
        this.picture = picture;
        this.reproductions = reproductions;
        this.songs = new ArrayList<>();
    }

    public Album() {
        this.songs = new ArrayList<>();
    }

    public Album(int idArtist, String name, String date, String picture, Integer reproductions, List<Song> songs) {
        this.idArtist = idArtist;
        this.name = name;
        this.date = date;
        this.picture = picture;
        this.reproductions = reproductions;
        this.songs = songs;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public int getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(int idArtist) {
        this.idArtist = idArtist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getReproductions() {
        return reproductions;
    }

    public void setReproductions(Integer reproductions) {
        this.reproductions = reproductions;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return idAlbum == album.idAlbum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAlbum);
    }

    @Override
    public String toString() {
        return "Album{" +
                "idAlbum=" + idAlbum +
                ", idArtist=" + idArtist +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", picture='" + picture + '\'' +
                ", reproductions=" + reproductions +
                ", songs=" + songs +
                '}';
    }
}
