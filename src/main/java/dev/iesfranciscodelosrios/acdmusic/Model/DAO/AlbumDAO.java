package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Interfaces.iAlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;

import java.util.Set;

public class AlbumDAO implements iAlbumDAO {

    @Override
    public boolean addAlbum(Album album) {
        return false;
    }

    @Override
    public Set<Album> searchAlbumByName(String filterWord) {
        return null;
    }

    @Override
    public Set<Album> searchAllAlbumesByArtist(ArtistDTO artist) {
        return null;
    }

    @Override
    public Set<Album> searchMoreRecent() {
        return null;
    }

    @Override
    public Album updateAlbum(int id) {
        return null;
    }
}
