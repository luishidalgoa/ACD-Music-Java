package dev.iesfranciscodelosrios.acdmusic.Components.SongCard;

import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ArtistDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.SongDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Artist;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

import java.time.format.DateTimeFormatter;


public class SongCardController {

    @FXML
    private ImageView song_view;

    @FXML
    private Text song_name;

    @FXML
    private Text artist_name;

    @FXML
    private Text total_view;

    @FXML
    private Label duration;

    @FXML
    private SplitMenuButton add_to_repro;

    @FXML
    private SVGPath play_song;

    private Song song;
    private SongDAO songDAO = SongDAO.getInstance();
    private AlbumDAO albumDAO = AlbumDAO.getInstance();
    private ArtistDAO artistDAO = ArtistDAO.getInstance();
    private ReproductionListDAO reproductionListDAO= ReproductionListDAO.getInstance();


    public void initializeSong(Song song) {
        this.song = song;
        updateUI();
    }

    private void updateUI() {
        if (song != null) {
            Song updatedSong = songDAO.searchById(song.getId_song());

            if (updatedSong != null) {
                song_name.setText(updatedSong.getName());

                Album album = albumDAO.getAlbumById(updatedSong.getId_album());

                if (album != null) {
                    Artist artist = artistDAO.searchArtistByIdUser(album.getId_artist());

                    if (artist != null) {
                        artist_name.setText(artist.getName());
                    } else {
                        artist_name.setText(" ");
                    }
                }

                total_view.setText("Reproducciones: " + updatedSong.getReproductions());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String formattedDuration = updatedSong.getTime().format(formatter);
                duration.setText(formattedDuration);

                String imageName = updatedSong.getName().toLowerCase().replaceAll("\\s", "") + ".png";
                String imagePath = "/dev/iesfranciscodelosrios/acdmusic/resources/" + imageName;
                song_view.setImage(new javafx.scene.image.Image(getClass().getResource(imagePath).toString()));
            }
        }
    }

    @FXML
    private void play_song(MouseEvent event) {
        //hay que llamar al método que toque la canción
    }

    @FXML
    private void addToReproductionList(MouseEvent event) {
        if (song != null) {
            //No se cual método utilizamos para ver las listas del usuario
            int idReproductionList = 1;

            boolean result = reproductionListDAO.addSong(song.getId_song(), idReproductionList);
        }
    }

    @FXML
    private void addToNewReproductionList(MouseEvent event) {
        if (song != null) {
            // Crear una nueva lista de reproducción, no se si llamar a la card de agregar lista o no
            ReproductionList newReproductionList = new ReproductionList();

            ReproductionList createdReproductionList = reproductionListDAO.add(newReproductionList);
        }
        if (createdReproductionList != null) {
            //Agregamos la canción a esta lista
            boolean result = reproductionListDAO.addSong(song.getId_song(), createdReproductionList.getId());
        }
    }
}
