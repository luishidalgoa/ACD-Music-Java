package dev.iesfranciscodelosrios.acdmusic.Model.Domain;

import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Genre;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Objects;

public class Song {
    private int id_song;
    private int id_album;
    private String name;
    private String url;
    private LocalTime length;
    private Genre genre;
    int reproductions;

    public Song(int id_song, int id_album, String name, String url, LocalTime length, Genre genre, int reproductions) {
        this.id_song = id_song;
        this.id_album = id_album;
        this.name = name;
        this.url = url;
        this.length = length;
        this.genre = genre;
        this.reproductions = reproductions;
    }

    public Song(int id_album, String name, String url, LocalTime length, Genre genre, int reproductions) {
        this.id_album = id_album;
        this.name = name;
        this.url = url;
        this.length = length;
        this.genre = genre;
        this.reproductions = reproductions;
    }

    public Song(int id_song) {
        this.id_song = id_song;
    }

    public Song() {
    }

    public int getId_song() {
        return id_song;
    }

    public void setId_song(int id_song) {
        this.id_song = id_song;
    }

    public int getId_album() {
        return id_album;
    }

    public void setId_album(int id_album) {
        this.id_album = id_album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalTime getTime() {
        return length;
    }

    public void setTime(LocalTime length) {
        this.length = length;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getReproductions() {
        return reproductions;
    }

    public void setReproductions(int reproductions) {
        this.reproductions = reproductions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id_song == song.id_song;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_song);
    }

    @Override
    public String toString() {
        return "Song{" +
                "id_song=" + id_song +
                ", id_album=" + id_album +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", time=" + length +
                ", genre=" + genre +
                ", reproductions=" + reproductions +
                '}';
    }
}
