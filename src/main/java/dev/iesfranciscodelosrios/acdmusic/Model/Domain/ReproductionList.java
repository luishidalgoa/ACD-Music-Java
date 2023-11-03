package dev.iesfranciscodelosrios.acdmusic.Model.Domain;

import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;

import java.util.ArrayList;
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
}
