package dev.iesfranciscodelosrios.acdmusic.Components.Search;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.*;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Pages.Home.HomeController;
import dev.iesfranciscodelosrios.acdmusic.Pages.Test.TestController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Set;

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

        Set<ArtistDTO>artists=ArtistDAO.getInstance().searchArtistByName(filterWord);
        Set<Album> albumes=AlbumDAO.getInstance().searchAlbumByName(filterWord);
        Set<ReproductionList> rls=ReproductionListDAO.getInstance().searchByName(filterWord);
        System.out.println(rls.size());

        FXMLLoader fxmlLoader= new FXMLLoader(App.class.getResource("Pages/Home/Home.fxml"));
        try {
            Node node = fxmlLoader.load();
            HomeController homeController = fxmlLoader.getController();
            homeController.clear();
            homeController.setArtist(artists);
            homeController.setAlbumes(albumes);
            homeController.setReproductionList(rls);

            TestController.hubController.setViewsContainer(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
