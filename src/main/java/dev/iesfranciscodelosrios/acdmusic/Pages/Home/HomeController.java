package dev.iesfranciscodelosrios.acdmusic.Pages.Home;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Components.AlbumCard.AlbumCardController;
import dev.iesfranciscodelosrios.acdmusic.Components.ArtistCard.ArtistCardController;
import dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card.ReproductionList_mediumCard;
import dev.iesfranciscodelosrios.acdmusic.Components.SongCard.SongCardController;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ArtistDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.SongDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Style;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class HomeController {
    @FXML
    private VBox vbox_container;

    public void initialize() {
        //agregaremos una separacion entre los elementos del vbox con gap-y
        vbox_container.setSpacing(20);
        loadHome();
    }

    /**
     * Cargara la vista previa de la ventana. La primera q se le mostrara al usuario como inicio
     */
    public void loadHome(){
        Set<Album> albums = AlbumDAO.getInstance().searchMoreRecent();
        setAlbumes(albums);


        Set<ReproductionList>rl = ReproductionListDAO.getInstance().getUserSubcriptions(Login.getInstance().getCurrentUser().getId());
        setReproductionList(rl,"Yours reproduction list");

        Set<ArtistDTO>artist = ArtistDAO.getInstance().searchArtistByName("");
        setArtist(artist);

        Set<Song>latestSongs = SongDAO.getInstance().searchRecientSongs();
        setSong(latestSongs,"Latest songs");

        Set<Song>topSongs = SongDAO.getInstance().searchTopSongs();
        setSong(topSongs,"Top songs");
    }
    public void setAlbumes(Set<Album> albums){
        Label albumLabel=new Label("Top Albums");
        albumLabel.setStyle(Style.h1.getStyle());
        vbox_container.getChildren().add(albumLabel);
        if(!albums.isEmpty()){
            for (Album album : albums) {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/AlbumCard/AlbumCard.fxml"));
                try {
                    Node card = fxmlLoader.load();
                    AlbumCardController albumCardController = fxmlLoader.getController();
                    //albumCardController.setData(album);
                    vbox_container.getChildren().add(card);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
    public void setReproductionList(Set<ReproductionList> rl,String text){
        Label rlLabel=new Label(text);
        rlLabel.setStyle(Style.h1.getStyle());
        vbox_container.getChildren().add(rlLabel);
        if (rl!=null && !rl.isEmpty()){
            for (ReproductionList aux : rl) {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/ReproductionList_mediumCard/ReproductionList_mediumCard.fxml"));
                try {
                    Node card = fxmlLoader.load();
                    ReproductionList_mediumCard controller = fxmlLoader.getController();
                    controller.setData(aux);
                    vbox_container.getChildren().add(card);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
    public void setArtist(Set<ArtistDTO> artists){
        Label artistLabel=new Label("Artists Recommend");
        artistLabel.setStyle(Style.h1.getStyle());
        vbox_container.getChildren().add(artistLabel);
        if (artists!=null && !artists.isEmpty()){
            for (ArtistDTO aux : artists) {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/ArtistCard/ArtistCard.fxml"));
                try {
                    Node card = fxmlLoader.load();
                    ArtistCardController controller = fxmlLoader.getController();
                    controller.setData(aux);
                    vbox_container.getChildren().add(card);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
    public void setSong(Set<Song> songs,String text){
        Label artistLabel=new Label(text);
        artistLabel.setStyle(Style.h1.getStyle());
        vbox_container.getChildren().add(artistLabel);
        if (songs!=null && !songs.isEmpty()){
            for (Song aux : songs) {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/SongCard/SongCard.fxml"));
                try {
                    Node card = fxmlLoader.load();
                    SongCardController controller = fxmlLoader.getController();
                    controller.setData(aux);
                    vbox_container.getChildren().add(card);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
    public void clear(){
        vbox_container.getChildren().clear();
    }
    public VBox getVbox_container() {
        return vbox_container;
    }
}
