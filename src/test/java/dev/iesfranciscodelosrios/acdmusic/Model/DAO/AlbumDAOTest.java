package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Artist;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AlbumDAOTest {

    @Test
    @Order(0)
    void getInstance() {
        AlbumDAO albumDAO = AlbumDAO.getInstance();
        assertNotNull(albumDAO);
    }

    @Test
    @Order(1)
    void addAlbum() {
        AlbumDAO albumDAO = AlbumDAO.getInstance();
        String date = "2023-11-01";

        Album album = new Album(5, 1, "Album1", date, "album2.png", 123);
        boolean isAdded = albumDAO.addAlbum(album);
        assertTrue(isAdded);
        // Add specific assertions to validate the album addition
    }

    @Test
    @Order(2)
    void searchAlbumByName() {
        AlbumDAO albumDAO = AlbumDAO.getInstance();
        String filterWord = "Example"; // Replace this with the desired search term
        Set<Album> albums = albumDAO.searchAlbumByName(filterWord);
        assertNotNull(albums);
        // Add specific assertions to validate the retrieved albums by name
    }

    @Test
    @Order(3)
    void searchAllAlbumsByArtist() {
        AlbumDAO albumDAO = AlbumDAO.getInstance();
        // Assume there's an ArtistDTO named 'artist' for testing purposes
        ArtistDTO artist = new ArtistDTO(/*Provide necessary parameters */);
        Set<Album> albums = albumDAO.searchAllAlbumsByArtist(artist);
        assertNotNull(albums);
        // Add specific assertions to validate the retrieved albums by the artist
    }

    @Test
    @Order(4)
    void searchMoreRecent() {
        AlbumDAO albumDAO = AlbumDAO.getInstance();
        Set<Album> recentAlbums = albumDAO.searchMoreRecent();
        assertNotNull(recentAlbums);
        // Add specific assertions to validate the retrieved most recent albums
    }

    @Test
    @Order(5)
    void updateAlbum() {
        AlbumDAO albumDAO = AlbumDAO.getInstance();
        int albumIdToUpdate = 1; // Replace this with an existing album ID for testing purposes
        String newAlbumName = "New Album Name";
        Album updatedAlbum = albumDAO.updateAlbum(albumIdToUpdate, newAlbumName);
        assertNotNull(updatedAlbum);
        // Add specific assertions to validate the updated album details
    }


}
