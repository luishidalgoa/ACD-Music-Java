package dev.iesfranciscodelosrios.acdmusic.Components.AlbumCard;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.*;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import dev.iesfranciscodelosrios.acdmusic.Pages.AlbumView.AlbumViewController;
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
import java.util.Set;

public class AlbumCardController {
    @FXML
    private ImageView img_picture;
    @FXML
    private Label album_name;

    @FXML
    private Label artist_name;
    @FXML
    private SplitMenuButton menu;

    private Album album;

    public void setData(Album album) {
        this.album = album;
        album_name.setText(album.getName());
        this.album_name.setText(album.getName());
        this.artist_name.setText(ArtistDAO.getInstance().searchArtistByIdAlbum(album.getIdAlbum()).getNickName());
        File img = new File(album.getPicture());
        if (img.exists()) {
            img_picture.setImage(new javafx.scene.image.Image(img.toURI().toString()));
        }
    }

    @FXML
    private void showMenu(MouseEvent event) {
        //limpiamos los elementos del menu
        menu.getItems().clear();
        if (this.album != null) {
            Set<ReproductionList> subscriptions = ReproductionListDAO.getInstance().getUserSubcriptions(Login.getInstance().getCurrentUser().getId());
            //creamos varios elementos hijos de menuItem para el menu con el mismo nombre que el de las listas de reproduccion
            for (ReproductionList rl : subscriptions) {
                if (Login.getInstance().getCurrentUser().equals(rl.getOwner())) {
                    MenuItem menuItem = new MenuItem(rl.getName());
                    //Evento que se ejecuta al pulsar sobre el elemento del menu
                    menuItem.setOnAction(e -> {
                        try {
                            if (rl.getSongs()!=null){
                                //extraemos todas las canciones del album y las añadimos a la lista de reproduccion
                                for (Song song: SongDAO.getInstance().searchByAlbumId(album.getIdAlbum())) {
                                    if (rl.getSongs().stream().noneMatch(s -> s.getId_song() == song.getId_song())) {
                                        ReproductionListDAO.getInstance().addSong(song.getId_song(), rl.getId());
                                    }
                                }
                            }else{
                                //extraemos todas las canciones del album y las añadimos a la lista de reproduccion
                                for (Song song: SongDAO.getInstance().searchByAlbumId(album.getIdAlbum())) {
                                    ReproductionListDAO.getInstance().addSong(song.getId_song(), rl.getId());
                                }
                                App.hubController.updateReproductionLists();
                            }
                        }catch (NullPointerException ex){
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
    public void loadArtistView(){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Pages/ArtistProfile/ArtistProfile.fxml"));
        try {
            Node node=fxmlLoader.load();
            ArtistProfileController controller = fxmlLoader.getController();
            controller.setData(ArtistDAO.getInstance().searchArtistByIdAlbum(album.getIdAlbum()));
            App.hubController.setViewsContainer(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void loadAlbumView(){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Pages/Album/Album.fxml"));
        try {
            Node node=fxmlLoader.load();
            AlbumViewController controller = fxmlLoader.getController();
            controller.setData(album);
            App.hubController.setViewsContainer(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
