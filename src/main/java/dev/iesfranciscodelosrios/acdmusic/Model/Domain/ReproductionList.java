package dev.iesfranciscodelosrios.acdmusic.Model.Domain;

import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class ReproductionList {
    int id;
    String name;
    String description;
    UserDTO owner;
    Set<Song> Songs;
    ArrayList<Comment> comments;

    public ReproductionList(int id, String name, String description, UserDTO owner, Set<Song> songs, ArrayList<Comment> comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.Songs = songs;
        this.comments = comments;
    }

    public ReproductionList(String name, String description, UserDTO owner, Set<Song> songs, ArrayList<Comment> comments) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.Songs = songs;
        this.comments = comments;
    }

    public ReproductionList() {
        this.id = -1;
        this.name = "";
        this.description = "";
        this.owner = null;
        this.Songs = null;
        this.comments = null;
    }

    @Override
    public String toString() {
        return "ReproductionList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner +
                ", Songs=" + Songs +
                ", comments=" + comments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReproductionList that = (ReproductionList) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public Set<Song> getSongs() {
        return Songs;
    }

    public void setSongs(Set<Song> songs) {
        Songs = songs;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
