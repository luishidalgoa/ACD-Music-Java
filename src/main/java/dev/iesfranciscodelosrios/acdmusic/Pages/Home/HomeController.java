package dev.iesfranciscodelosrios.acdmusic.Pages.Home;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Components.AlbumCard.AlbumCardController;
import dev.iesfranciscodelosrios.acdmusic.Components.ArtistCard.ArtistCardController;
import dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card.ReproductionList_mediumCard;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
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
        Label albumLabel=new Label("Top Albums");
        albumLabel.setStyle(titleStyle);
        vbox_container.getChildren().add(albumLabel);
        Set<Album> albums = AlbumDAO.getInstance().searchMoreRecent();
        for (Album album : albums) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/AlbumCard/AlbumCardController.fxml"));
            try {
                Node card = fxmlLoader.load();
                AlbumCardController albumCardController = fxmlLoader.getController();
                //albumCardController.setData(album);
                vbox_container.getChildren().add(card);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        Set<ReproductionList>rl = ReproductionListDAO.getInstance().getUserSubcriptions(Login.getInstance().getCurrentUser().getId());
        Label rlLabel=new Label("Yours Reproduction Lists");
        rlLabel.setStyle(titleStyle);
        vbox_container.getChildren().add(rlLabel);
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

        Set<ReproductionList>artist = ReproductionListDAO.getInstance().getUserSubcriptions(Login.getInstance().getCurrentUser().getId());
        Label artistLabel=new Label("Artists Recommend");
        artistLabel.setStyle(titleStyle);
        vbox_container.getChildren().add(artistLabel);
        for (ReproductionList aux : rl) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/ReproductionList_Card/ReproductionList_mediumCard.fxml"));
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
    public void setAlbumes(Set<Album> albums){

    }
    public void setReproductionList(Set<ReproductionList> rl){

    }
    public void setArtist(Set<Album> albums){

    }
}
