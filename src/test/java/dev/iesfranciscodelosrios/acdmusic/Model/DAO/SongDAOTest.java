package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Genre;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SongDAOTest {

    @Test
    @Order(0)
    void getInstance() {
        // Write test logic to check the instance creation for SongDAO
        // For example:
        SongDAO songDAO = SongDAO.getInstance();
        assertNotNull(songDAO);
    }

    @Test
    @Order(1)
    void addSong() {
        SongDAO songDAO = SongDAO.getInstance();
        Song song = new Song('9','2',"Solo tu","Youtube", LocalTime.of(00,03,00),Genre.ROCK,100);
        LocalTime songTime = LocalTime.parse("00:03:33");
        Song addedSong = songDAO.addSong(song);
        assertNotNull(addedSong);
        // Add specific assertions to validate the added song details
    }

    @Test
    @Order(2)
    void removeSong() {
        SongDAO songDAO = SongDAO.getInstance();
        int songIdToRemove = 2;
        assertTrue(songDAO.removeSong(songIdToRemove));
    }

    @Test
    @Order(3)
    void searchByGenre() {
        SongDAO songDAO = SongDAO.getInstance();
        Set<Song> songsByGenre = songDAO.searchByGenre(Genre.JAZZ);
        for (Song aux:songsByGenre
             ) {
            System.out.println(aux);
        }
        assertNotNull(songsByGenre);
        // Add specific assertions to validate the retrieved songs by genre
    }

    @Test
    @Order(4)
    void searchById() {
        SongDAO songDAO = SongDAO.getInstance();
        int songIdToSearch = 1;
        Song foundSong = songDAO.searchById(songIdToSearch);
        assertNotNull(foundSong);
        // Add specific assertions to validate the details of the retrieved song
    }

    @Test
    @Order(5)
    void searchByAlbumId() {
        SongDAO songDAO = SongDAO.getInstance();
        int albumIdToSearch = 1;
        Set<Song> songsByAlbum = songDAO.searchByAlbumId(albumIdToSearch);
        assertNotNull(songsByAlbum);
        // Add specific assertions to validate the retrieved songs by album
    }

    @Test
    @Order(6)
    void searchTopSongs() {
        SongDAO songDAO = SongDAO.getInstance();
        Set<Song> topSongs = songDAO.searchTopSongs();
        assertNotNull(topSongs);
        // Add specific assertions to validate the retrieved top songs
    }

    @Test
    @Order(7)
    void searchRecientSongs() {
        SongDAO songDAO = SongDAO.getInstance();
        Set<Song> recentSongs = songDAO.searchRecientSongs();
        assertNotNull(recentSongs);
        // Add specific assertions to validate the retrieved recent songs
    }

    @Test
    @Order(8)
    void searchByNombre() {
        SongDAO songDAO = SongDAO.getInstance();
        String filterWord = "tu"; // Replace this with the desired search term
        Set<Song> songs = songDAO.searchByNombre(filterWord);
        assertNotNull(songs);
        // Add specific assertions to validate the retrieved songs by name
    }


}
