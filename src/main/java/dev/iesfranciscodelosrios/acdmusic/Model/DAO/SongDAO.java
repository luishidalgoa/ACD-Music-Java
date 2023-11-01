package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Interfaces.iSongDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Genre;

import java.util.Set;

public class SongDAO implements iSongDAO{
    @Override
    public Song addSong(Song song) {
        return null;
    }

    @Override
    public boolean removeSong(int idSong) {
        return false;
    }

    @Override
    public Set<Song> searchByGenre(Genre genre) {
        return null;
    }

    @Override
    public Song searchById(int idSong) {
        return null;
    }

    @Override
    public Set<Song> searchByAlbumId(int idAlbum) {
        return null;
    }

    @Override
    public Set<Song> searchTopSongs() {
        return null;
    }

    @Override
    public Set<Song> searchRecientSongs() {
        return null;
    }

    @Override
    public Set<Song> searchByNombre(String filterWord) {
        return null;
    }
}
