package dev.iesfranciscodelosrios.acdmusic.Pages.AlbumView;

import dev.iesfranciscodelosrios.acdmusic.Components.SongCard.SongCardController;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.SongDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.security.cert.PolicyNode;
import java.util.List;

public class AlbumViewController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView album_image;
    @FXML
    private Text album_name;
    @FXML
    private Text date;
    @FXML
    private Text reproduction;
    @FXML
    private VBox songCardsContainer;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button add_Song;

    private Album album;
    private SongDAO songDAO = SongDAO.getInstance();
    private AlbumDAO albumDAO = AlbumDAO.getInstance();
    private ReproductionListDAO reproductionListDAO= ReproductionListDAO.getInstance();

    @FXML
    public void initialize() {
        loadAlbum();
        loadSongs();
    }

    private void loadAlbum(){
        //me falta de donde saco el album
    }

    private void addSong(){
        //método para subir canción dentro del album
    }

    private void loadSongs() {
        // Obtener la lista de canciones asociadas al álbum
        List<Song> songs = songDAO.searchByAlbumId(album.getId());

        // Cargar cada SongCard en el contenedor
        for (Song song : songs) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Components/Song_Card/SongCard.fxml"));
                Node node = fxmlLoader.load();
                SongCardController controller = fxmlLoader.getController();
                controller.initializeSong(song);
                songCardsContainer.getChildren().add(node);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
