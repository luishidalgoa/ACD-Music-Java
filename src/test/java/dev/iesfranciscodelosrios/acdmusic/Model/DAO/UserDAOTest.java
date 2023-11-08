package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDAOTest {

    @Test
    @Order(1)
    void addUser() {
        UserDAO udao = new UserDAO();
        User user = new User("Nick", "Nick", "kol", "test", "nick@gmail.com", "1234");
        UserDTO userDTO = udao.searchByNickname(user.getNickName());

        assertNotNull(udao.addUser(user));
        assertNotNull(udao.searchById(userDTO.getId()));
    }

    @Test
    @Order(2)
    void getById() {

    }
    @Test
    @Order(3)
    void searchByEmail() {
    }

    @Test
    @Order(4)
    void searchByNickname() {
    }

    @Test
    @Order(5)
    void searchByName() {
    }

    @Test
    @Order(6)
    void searchById() {
    }
    @Test
    @Order(7)
    void delete() {
    }
}