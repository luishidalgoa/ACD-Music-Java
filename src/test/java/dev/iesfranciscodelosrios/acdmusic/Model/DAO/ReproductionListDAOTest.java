package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Service.Login;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReproductionListDAOTest {

    @Test
    @Order(1)
    void add() {
        ReproductionListDAO dao = ReproductionListDAO.getInstance();

        ReproductionList list = new ReproductionList("testName", "testDescription", Login.getInstance().getCurrentUser(), null, null);
        ReproductionList result = dao.add(list);

        assertNotNull(result);
    }

    @Test
    @Order(2)
    void searchReproductionListById() {
        assertNotNull(ReproductionListDAO.getInstance().searchReproductionListById(1));
    }
    /*@Test
    @Order(3)
    void Subcribe() {
        ReproductionListDAO dao = ReproductionListDAO.getInstance();
        assertTrue(dao.Subcribe(UserDAO.getInstance().searchUserById(1), 1));
    }*/

    @Test
    @Order(4)
    void getUserSubcriptions() {
        ReproductionListDAO dao = ReproductionListDAO.getInstance();
        Set<ReproductionList> result= dao.getUserSubcriptions(3);
        // Comprobamos que el usuario tiene al menos una lista de reproduccion a la que esta subscrito
        assertTrue(result!=null && !result.isEmpty());
    }

    @Test
    @Order(5)
    void getSubcribeToListByUser() {
    }

    @Test
    @Order(6)
    void unSubcribe() {
    }

    @Test
    @Order(7)
    void getAllComments() {
    }

    @Test
    @Order(8)
    void addSong() {
        ReproductionListDAO dao = ReproductionListDAO.getInstance();
        assertTrue(dao.addSong(1,1));
    }

    @Test
    @Order(9)
    void searchSongById() {

    }

    @Test
    @Order(10)
    void removeSong() {
    }
    @Test
    @Order(11)
    void removeReproductionList() {
        assertTrue(ReproductionListDAO.getInstance().removeReproductionList(1));
    }

    @Test
    @Order(12)
    /**
     * Vamos a resetear el autoincremental de la tabla reproductionlist
     */
    void reset(){
        Connection conn = ConnectionData.getConnection();
        try {
            conn.createStatement().executeUpdate("ALTER TABLE rythm.reproductionlist AUTO_INCREMENT = 1;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}