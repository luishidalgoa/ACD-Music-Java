package dev.iesfranciscodelosrios.acdmusic.Pages.Home;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Components.AlbumCard.AlbumCardController;
import dev.iesfranciscodelosrios.acdmusic.Components.ArtistCard.ArtistCardController;
import dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card.ReproductionList_mediumCard;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ArtistDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class HomeController {
    @FXML
    private VBox vbox_container;
    private String titleStyle="-fx-font-size: 24px;fx-font-weight: bold;-fx-font-family: 'SansSerif'";

    public void initialize() {
        loadHome();
    }

    /**
     * Cargara la vista previa de la ventana. La primera q se le mostrara al usuario como inicio
     */
    public void loadHome(){
        Label albumLabel=new Label("Top Albums");
        albumLabel.setStyle(titleStyle);
        vbox_container.getChildren().add(albumLabel);
        Set<Album> albums = AlbumDAO.getInstance().searchMoreRecent();
        setAlbumes(albums);


        Set<ReproductionList>rl = ReproductionListDAO.getInstance().getUserSubcriptions(Login.getInstance().getCurrentUser().getId());
        Label rlLabel=new Label("Yours Reproduction Lists");
        rlLabel.setStyle(titleStyle);
        vbox_container.getChildren().add(rlLabel);
        setReproductionList(rl);

        Set<ArtistDTO>artist = ArtistDAO.getInstance().searchArtistByName("");
        Label artistLabel=new Label("Artists Recommend");
        artistLabel.setStyle(titleStyle);
        vbox_container.getChildren().add(artistLabel);
        setArtist(artist);
    }
    public void setAlbumes(Set<Album> albums){
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
    public void setReproductionList(Set<ReproductionList> rl){
        if (!rl.isEmpty()){
            for (ReproductionList aux : rl) {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/ReproductionList_Card/ReproductionList_mediumCard.fxml"));
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
        if (!artists.isEmpty()){
            for (ArtistDTO aux : artists) {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/ArtistCard/ArtistCard.fxml"));
                try {
                    Node card = fxmlLoader.load();
                    ArtistCardController controller = fxmlLoader.getController();
                    //controller.setData(aux);
                    vbox_container.getChildren().add(card);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
    public VBox getVbox_container() {
        return vbox_container;
    }
}
