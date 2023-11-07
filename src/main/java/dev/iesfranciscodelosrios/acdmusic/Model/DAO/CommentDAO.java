package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iCommentDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO implements iCommentDAO {
    private static CommentDAO instance;
    String addCommentQuery="INSERT INTO commentlistusers (id_user, id_reproductionList, date, description) VALUES (?, ?, CURRENT_DATE, ?)";
    String searchCommentQuery="SELECT id_comment,id_user,id_reproductionList,date,description FROM commentlistusers WHERE id_comment = ?";
    String deleteCommentQuery="DELETE FROM commentlistusers WHERE id_comment = ?";
    String searchAllByIdListQuery="SELECT id_comment,id_user,id_reproductionList,date,description FROM commentlistusers WHERE id_reproductionList = ?";
    private CommentDAO(Connection connection) {
    }



    @Override
    public Comment add(Comment comment){
        Connection conn= ConnectionData.getConnection();
        try {
            PreparedStatement ps=conn.prepareStatement(addCommentQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,comment.getUser().getId());
            ps.setInt(2,comment.getReproductionListId());
            ps.setString(3,comment.getDescription());
            ps.executeUpdate();
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()){
                comment.setId(rs.getInt(1));
                return comment;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionData.close();
        }
        return null;
    }

    @Override
    public boolean delete(int commentId) {
        Connection conn= ConnectionData.getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteCommentQuery)) {
            preparedStatement.setInt(1, commentId);

            if(preparedStatement.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionData.close();
        }
        return false;
    }

    @Override
    public List<Comment> searchAllByIdList(int idList) {
        Connection conn= ConnectionData.getConnection();;
        List<Comment> comments = new ArrayList<>();

        try (PreparedStatement preparedStatement = conn.prepareStatement(searchAllByIdListQuery)) {
            preparedStatement.setInt(1, idList);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                comments.add(searchComment(resultSet.getInt("id_comment")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionData.close();
        }
        if(comments.isEmpty()){
            return null;
        }
        return comments;
    }

    public Comment searchComment(int idComment){
        Connection conn= ConnectionData.getConnection();
        try {
            PreparedStatement ps=conn.prepareStatement(searchCommentQuery);
            ps.setInt(1,idComment);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Comment result= new Comment();
                result.setId(rs.getInt("id_comment"));
                result.setUser(UserDAO.getInstance().searchUserById(rs.getInt("id_user")));
                result.setReproductionListId(rs.getInt("id_reproductionList"));
                result.setDate(Timestamp.valueOf(rs.getString("date")).toLocalDateTime());
                result.setDescription(rs.getString("description"));
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static CommentDAO getInstance() {
        if (instance == null) {
            instance = new CommentDAO(ConnectionData.getConnection());
        }
        return instance;
    }
}