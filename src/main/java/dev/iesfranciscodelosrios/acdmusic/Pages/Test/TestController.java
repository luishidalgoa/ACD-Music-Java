package dev.iesfranciscodelosrios.acdmusic.Pages.Test;

import dev.iesfranciscodelosrios.acdmusic.Components.MediaPlayer.MediaPlayerController;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.TestViews;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class TestController {

    @FXML
    public void ReproductionList_mediumCard() {

        try {
            TestViews.newStage(TestViews.getFXML("Components/ReproductionList_mediumCard/","ReproductionList_mediumCard").load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void ReproductionList_minCard() {

        try {
            TestViews.newStage(TestViews.getFXML("Components/ReproductionList_minCard/","ReproductionList_minCard").load());
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
}
