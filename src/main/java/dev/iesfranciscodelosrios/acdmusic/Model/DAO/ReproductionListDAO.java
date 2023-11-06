package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Interfaces.iReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Comment;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;

import java.util.Set;

public class ReproductionListDAO implements iReproductionListDAO{

    @Override
    public ReproductionList add(Album album) {
        return null;
    }

    @Override
    public boolean removeReproductionList(int id) {
        return false;
    }

    @Override
    public boolean Subcribe(UserDTO user, int idList) {
        return false;
    }

    @Override
    public boolean unSubcribe(UserDTO user, ReproductionList reproductionList) {
        return false;
    }

    @Override
    public boolean removeSong(int idSong, int idReproductionList, UserDTO user) {
        return false;
    }

    @Override
    public Set<ReproductionList> getUserSubcription(int idUser) {
        return null;
    }

    @Override
    public boolean getSubcribeToListByUser(int idUser, int idList) {
        return false;
    }

    @Override
    public Set<Comment> getAllComments(int idList) {
        return null;
    }

    @Override
    public boolean addSong(int idSong, int idReproductionList) {
        return false;
    }

    @Override
    public Song searchSongById(int idSong, int idReproductionList) {
        return null;
    }
}
