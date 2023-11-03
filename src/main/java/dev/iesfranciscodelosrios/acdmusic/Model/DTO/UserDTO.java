package dev.iesfranciscodelosrios.acdmusic.Model.DTO;

import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;

import java.util.Objects;

public class UserDTO {
    private int id;
    private String nickName;
    private String name;
    private String lastName;
    private String picture;
    private String email;
    public UserDTO(User user){
        this.id = user.getId();
        this.nickName = user.getNickName();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.picture = user.getPicture();
        this.email = user.getEmail();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id && Objects.equals(nickName, userDTO.nickName) && Objects.equals(email, userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickName, email);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", picture='" + picture + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
