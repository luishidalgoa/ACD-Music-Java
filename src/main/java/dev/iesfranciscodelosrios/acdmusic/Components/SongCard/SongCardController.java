package dev.iesfranciscodelosrios.acdmusic.Components.SongCard;

import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ArtistDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.SongDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Artist;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Style;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import dev.iesfranciscodelosrios.acdmusic.TestViews;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;


public class SongCardController {

    @FXML
    private ImageView song_view;

    @FXML
    private Text song_name;

    @FXML
    private Text artist_name;

    @FXML
    private Label total_view;

    @FXML
    private Label duration;

    @FXML
    private SplitMenuButton menu;
    private Song song;

    @FXML
    public void initialize() {
        song_view.setStyle(Style.Shadow.getStyle());
    }

    public void setData(Song song) {
        this.song = song;
        song_name.setText(song.getName());
        artist_name.setText(ArtistDAO.getInstance().searchArtistByIdSong(song.getId_song()).getName());
        total_view.setText(String.valueOf(song.getReproductions()));
        duration.setText(song.getTime().format(DateTimeFormatter.ofPattern("mm:ss")));

        File img = new File(AlbumDAO.getInstance().searchAlbumByIdSong(song.getId_song()).getPicture());
        if (img.exists()) {
            song_view.setImage(new javafx.scene.image.Image(img.toURI().toString()));
        }
    }

    @FXML
    private void handleTogglePlay(MouseEvent event) {
        Set<Song> songs = new HashSet<>();
        songs.add(song);
        TestViews.hubController.setData(songs, true);
    }

    @FXML
    private void showMenu(MouseEvent event) {
        if (song != null) {
            Set<ReproductionList> subscriptions = ReproductionListDAO.getInstance().getUserSubcriptions(Login.getInstance().getCurrentUser().getId());
            //creamos varios elementos hijos de menuItem para el menu con el mismo nombre que el de las listas de reproduccion
            for (ReproductionList rl : subscriptions) {
                if (Login.getInstance().getCurrentUser().equals(rl.getOwner())) {
                    MenuItem menuItem = new MenuItem(rl.getName());
                    menuItem.setOnAction(e -> {
                        boolean isEmpty = false;
                        try {
                            //si la cancion no esta en la lista de reproduccion, la añadimos
                            if (rl.getSongs() != null && rl.getSongs().stream().noneMatch(s -> s.getId_song() == song.getId_song())) {
                                ReproductionListDAO.getInstance().addSong(song.getId_song(), rl.getId());
                            } else if (rl.getSongs() != null) {
                                //si la cancion esta en la lista de reproduccion, la eliminamos
                                ReproductionListDAO.getInstance().removeSong(song.getId_song(), rl.getId(), Login.getInstance().getCurrentUser());
                            } else {
                                ReproductionListDAO.getInstance().addSong(song.getId_song(), rl.getId());
                                TestViews.hubController.updateReproductionLists();
                            }
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }
                    });
                    menu.getItems().add(menuItem);
                }
            }
            //activamos el desplegable del menu
            menu.show();
        }
    }

    @FXML
    private void handleTogglePlay() {
        Set<Song> songs = new HashSet<>();
        songs.add(song);
        TestViews.hubController.setData(songs, true);
    }
}
