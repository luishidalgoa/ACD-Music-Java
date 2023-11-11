package dev.iesfranciscodelosrios.acdmusic.Components.Search;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.*;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import dev.iesfranciscodelosrios.acdmusic.Pages.Home.HomeController;
import dev.iesfranciscodelosrios.acdmusic.Pages.Test.TestController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
        input_filter.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searched();
            }
        });
    }
    @FXML
    private void searched(){
        String filterWord=input_filter.getText();

        Set<ArtistDTO>artists=ArtistDAO.getInstance().searchArtistByName(filterWord);
        Set<Album> albumes=AlbumDAO.getInstance().searchAlbumByName(filterWord);
        Set<ReproductionList> rls=ReproductionListDAO.getInstance().searchByName(filterWord);
        Set<Song> songs= SongDAO.getInstance().searchByNombre(filterWord);

        FXMLLoader fxmlLoader= new FXMLLoader(App.class.getResource("Pages/Home/Home.fxml"));
        try {
            Node node = fxmlLoader.load();
            HomeController homeController = fxmlLoader.getController();
            homeController.clear();
            if (artists.size()>0)
                homeController.setArtist(artists);
            if (albumes.size()>0)
                homeController.setAlbumes(albumes);
            if (rls.size()>0)
                homeController.setReproductionList(rls, "Reproduction list");
            if (songs.size()>0)
                homeController.setSong(songs, "Songs");

            TestController.hubController.setViewsContainer(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
