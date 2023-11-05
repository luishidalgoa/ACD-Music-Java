package dev.iesfranciscodelosrios.acdmusic.Pages.Test;

import dev.iesfranciscodelosrios.acdmusic.TestViews;
import javafx.fxml.FXML;

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
}
