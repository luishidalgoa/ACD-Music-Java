package dev.iesfranciscodelosrios.acdmusic.Pages.Test;

import dev.iesfranciscodelosrios.acdmusic.Components.ArtistCard.ArtistCardController;
import dev.iesfranciscodelosrios.acdmusic.Components.MediaPlayer.MediaPlayerController;
import dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card.ReproductionList_mediumCard;
import dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card.ReproductionList_minCard;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ArtistDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.UserDAO;
import dev.iesfranciscodelosrios.acdmusic.Pages.Hub.HubController;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import dev.iesfranciscodelosrios.acdmusic.TestViews;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class TestController {
    @FXML
    public void initialize() {
        Login.getInstance().setCurrentUser(UserDAO.getInstance().searchById(1));
    }
    @FXML
    public void ReproductionList_mediumCard() {

        try {
            FXMLLoader fxmlLoader = TestViews.getFXML("Components/ReproductionList_mediumCard/","ReproductionList_mediumCard");
            TestViews.newStage(fxmlLoader.load());
            ReproductionList_mediumCard controller = fxmlLoader.getController();
            controller.setData(ReproductionListDAO.getInstance().searchReproductionListById(1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void ReproductionList_minCard() {
        try {
            FXMLLoader fxmlLoader = TestViews.getFXML("Components/ReproductionList_minCard/","ReproductionList_minCard");
            TestViews.newStage(fxmlLoader.load());
            ReproductionList_minCard controller = fxmlLoader.getController();
            controller.setData(ReproductionListDAO.getInstance().searchReproductionListById(1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void loadMediaPlayer() {
        try {
            FXMLLoader fxmlLoader= TestViews.getFXML("Components/MediaPlayer/","MediaPlayer");
            TestViews.newStage(fxmlLoader.load());
            MediaPlayerController mediaPlayerController=fxmlLoader.getController();
            mediaPlayerController.setData(ReproductionListDAO.getInstance().searchSongsById(1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void loadSearch() {

        try {
            TestViews.newStage(TestViews.getFXML("Components/Search/","Search").load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void loadArtistCard() {
        try {
            FXMLLoader fxmlLoader= TestViews.getFXML("Components/ArtistCard/","ArtistCard");
            TestViews.newStage(fxmlLoader.load());
            ArtistCardController artistCardController=fxmlLoader.getController();
            artistCardController.setData(ArtistDAO.getInstance().searchArtistByIdUser(2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void loadHome() {

        try {
            TestViews.newStage(TestViews.getFXML("Pages/Home/","Home").load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void loadHub() {
        try {
            FXMLLoader fxmlLoader= TestViews.getFXML("Pages/Hub/","Hub");
            TestViews.newStage(fxmlLoader.load());
            HubController hubController=fxmlLoader.getController();
            hubController.setData(ReproductionListDAO.getInstance().searchSongsById(1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
