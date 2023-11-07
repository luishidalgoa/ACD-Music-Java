package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Comment;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentDAOTest {
    private int example=1;
    private int idList=1;
    @Test
    @Order(0)
    void initialize(){
        Login.getInstance().setCurrentUser(new UserDTO(new User(3, "RaulNapias", "Raul", "Test", "test", "test", "1234")));
        ReproductionListDAO.getInstance().add(new ReproductionList("testName", "testDescription", Login.getInstance().getCurrentUser(), null, null)).getId();
    }
    @Test
    @Order(1)
    void add() {
        CommentDAO dao = CommentDAO.getInstance();
        dao.add(new Comment(Login.getInstance().getCurrentUser(), idList, "test"));
        assertNotNull(example);
    }

    @Test
    @Order(4)
    void delete() {
        CommentDAO dao = CommentDAO.getInstance();
        assertTrue(dao.delete(example));
    }

    @Test
    @Order(3)
    void searchAllByIdList() {
        CommentDAO dao = CommentDAO.getInstance();
        assertNotNull(dao.searchAllByIdList(this.idList));
    }

    @Test
    @Order(2)
    void searchComment() {
        CommentDAO dao = CommentDAO.getInstance();
        assertNotNull(dao.searchComment(example));
    }
    @Test
    @Order(5)
    void reset() {
        ReproductionListDAO.getInstance().removeReproductionList(idList);
        Connection conn = ConnectionData.getConnection();
        try {
            conn.createStatement().executeUpdate("ALTER TABLE rythm.reproductionlist AUTO_INCREMENT = 1;");
            conn.createStatement().executeUpdate("ALTER TABLE rythm.commentlistusers AUTO_INCREMENT = 1;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}