package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Interfaces.iArtistDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Artist;

import java.util.Set;

public class ArtistDAO implements iArtistDAO {
    @Override
    public ArtistDTO addArtist(Artist artist) {
        return null;
    }

    @Override
    public boolean removeArtist(int idUser) {
        return false;
    }

    @Override
    public ArtistDTO searchArtistByIdUser(int idUser) {
        return null;
    }

    @Override
    public Set<ArtistDTO> searchArtistByName(String filterWord) {
        return null;
    }
}
