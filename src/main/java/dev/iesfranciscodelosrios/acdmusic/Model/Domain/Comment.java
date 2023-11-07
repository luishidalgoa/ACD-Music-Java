package dev.iesfranciscodelosrios.acdmusic.Model.Domain;

import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;

import java.time.LocalDateTime;

public class Comment {
    private int id;
    private UserDTO user;
    private int reproductionListId;
    private LocalDateTime date;
    private String description;

    public Comment(int id, UserDTO userId, int reproductionListId, LocalDateTime date, String description) {
        this.id = id;
        this.user = userId;
        this.reproductionListId = reproductionListId;
        this.date = date;
        this.description = description;
    }

    public Comment() {
    }

    public Comment(UserDTO userId, int reproductionListId, String description) {
        this.user = userId;
        this.reproductionListId = reproductionListId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public int getReproductionListId() {
        return reproductionListId;
    }

    public void setReproductionListId(int reproductionListId) {
        this.reproductionListId = reproductionListId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}