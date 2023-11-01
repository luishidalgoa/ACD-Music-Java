package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Interfaces.iCommentDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Comment;

public class CommentDAO implements iCommentDAO {
    @Override
    public Comment add(Comment comment) {
        return null;
    }

    @Override
    public boolean delete(int idList) {
        return false;
    }

    @Override
    public Comment searchByIdList(int idList) {
        return null;
    }
}
