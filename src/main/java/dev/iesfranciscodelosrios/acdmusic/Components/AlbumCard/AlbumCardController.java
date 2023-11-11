package dev.iesfranciscodelosrios.acdmusic.Components.AlbumCard;

import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ArtistDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.SongDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Artist;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import javafx.fxml.FXML;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class AlbumCardController {
    @FXML
    private ImageView album_view;
    @FXML
    private Text album_name;

    @FXML
    private Text artist_name;
    @FXML
    private SplitMenuButton add_to_repro;

    private Album album;
    private SongDAO songDAO = SongDAO.getInstance();
    private AlbumDAO albumDAO = AlbumDAO.getInstance();
    private ArtistDAO artistDAO = ArtistDAO.getInstance();
    private ReproductionListDAO reproductionListDAO= ReproductionListDAO.getInstance();

    public void initializeSong(Song song) {
        this.album = album;
        updateUI();
    }

    private void updateUI() {
        if (album != null) {
            Album updatedAlbum = (Album) albumDAO.searchAlbumByName(album.getName());

            if (updatedAlbum != null) {
                album_name.setText(updatedAlbum.getName());

                Album album = albumDAO.getAlbumById(updatedAlbum.getIdAlbum());

                if (album != null) {
                    Artist artist = artistDAO.searchArtistByIdUser(album.getId_artist());

                    if (artist != null) {
                        artist_name.setText(artist.getName());
                    } else {
                        artist_name.setText(" ");
                    }
                }

                String imageName = updatedAlbum.getName().toLowerCase().replaceAll("\\s", "") + ".png";
                String imagePath = "/dev/iesfranciscodelosrios/acdmusic/resources/" + imageName;
                album_view.setImage(new javafx.scene.image.Image(getClass().getResource(imagePath).toString()));
            }
        }
    }

    @FXML
    private void addSongAlbumToReproductionList(MouseEvent event) {
        //método que cargue todas las canciones de un album a una lista de reproducción.
    }

    @FXML
    private void addSongAlbumToNewReproductionList(MouseEvent event) {
        //método que cargue todas las canciones de un album a una lista de reproducción nueva.
    }
}
