package dev.iesfranciscodelosrios.acdmusic.Components.Search;

import dev.iesfranciscodelosrios.acdmusic.Model.DAO.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class SearchController {
    @FXML
    private Pane container;
    @FXML
    private TextField input_filter;

    @FXML
    private void initialize() {
    }
    @FXML
    private void searched(){
        String filterWord=input_filter.getText();

        //ArtistDAO.getInstance().searchByName(filterWord);
        SongDAO.getInstance().searchByNombre(filterWord);
        AlbumDAO.getInstance().searchAlbumByName(filterWord);
        //ReproductionListDAO.getInstance().
    }
}
