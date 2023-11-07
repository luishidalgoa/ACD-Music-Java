package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionSQL;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Comment;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;

import java.sql.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    private Connection connection; // Debes inicializar la conexión con tu base de datos

    public CommentDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar un comentario en la base de datos
    public void insertComment(Comment comment) {
        String query = "INSERT INTO commentlistusers (id_user, id_reproductionList, date, description) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, comment.getUserId());
            preparedStatement.setInt(2, comment.getReproductionListId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(comment.getDate()));
            preparedStatement.setString(4, comment.getDescription());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un comentario por su ID
    public void deleteComment(int commentId) {
        String query = "DELETE FROM commentlistusers WHERE id_comment = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, commentId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar comentarios en una lista de reproducción específica
    public List<Comment> searchCommentsByReproductionList(int reproductionListId) {
        String query = "SELECT * FROM commentlistusers WHERE id_reproductionList = ?";
        List<Comment> comments = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reproductionListId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("id_comment"));
                comment.setUserId(resultSet.getInt("id_user"));
                comment.setReproductionListId(resultSet.getInt("id_reproductionList"));
                comment.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                comment.setDescription(resultSet.getString("description"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }
}