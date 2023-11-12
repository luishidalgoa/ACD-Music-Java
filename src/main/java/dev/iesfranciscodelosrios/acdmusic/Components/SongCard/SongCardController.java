package dev.iesfranciscodelosrios.acdmusic.Components.SongCard;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ArtistDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Style;
import dev.iesfranciscodelosrios.acdmusic.Pages.ArtistProfile.ArtistProfileController;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;


public class SongCardController {

    @FXML
    private ImageView img_picture;

    @FXML
    private Label song_name;

    @FXML
    private Label artist_name;

    @FXML
    private Label total_view;

    @FXML
    private Label duration;

    @FXML
    private SplitMenuButton menu;
    private Song song;

    @FXML
    public void initialize() {
        img_picture.setStyle(Style.Shadow.getStyle());
    }

    public void setData(Song song) {
        this.song = song;
        song_name.setText(song.getName());
        artist_name.setText(ArtistDAO.getInstance().searchArtistByIdSong(song.getId_song()).getNickName());
        total_view.setText(String.valueOf(song.getReproductions()));
        duration.setText(song.getTime().format(DateTimeFormatter.ofPattern("mm:ss")));

        File img = new File(AlbumDAO.getInstance().searchAlbumByIdSong(song.getId_song()).getPicture());
        if (img.exists()) {
            img_picture.setImage(new javafx.scene.image.Image(img.toURI().toString()));
        }
    }

    @FXML
    private void handleTogglePlay(MouseEvent event) {
        Set<Song> songs = new HashSet<>();
        songs.add(song);
        App.hubController.setData(songs, true);
    }

    @FXML
    private void showMenu(MouseEvent event) {
        //limpiamos los elementos del menu
        menu.getItems().clear();
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
                                App.hubController.updateReproductionLists();
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
        App.hubController.setData(songs, true);
    }
    @FXML
    public void loadArtistView(){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Pages/ArtistProfile/ArtistProfile.fxml"));
        try {
            Node node=fxmlLoader.load();
            ArtistProfileController controller = fxmlLoader.getController();
            controller.setData(ArtistDAO.getInstance().searchArtistByIdSong(song.getId_song()));
            App.hubController.setViewsContainer(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
