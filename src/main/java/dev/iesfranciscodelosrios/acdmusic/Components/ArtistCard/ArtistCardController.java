package dev.iesfranciscodelosrios.acdmusic.Components.ArtistCard;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ArtistDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import dev.iesfranciscodelosrios.acdmusic.Pages.ArtistProfile.ArtistProfileController;
import dev.iesfranciscodelosrios.acdmusic.TestViews;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;

public class ArtistCardController {
    @FXML
    private Label label_ArtistName;
    @FXML
    private ImageView img_picture;
    private ArtistDTO artist;
    @FXML
    public void initialize() {
        Circle clip = new Circle();
        clip.setRadius(50);
        clip.setCenterX(50);
        clip.setCenterY(50);

        // Aplicar el recorte circular a la imageView
        img_picture.setClip(clip);
    }
    public void loadArtistView(){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Pages/ArtistProfile/ArtistProfile.fxml"));
        try {
            Node node=fxmlLoader.load();
            ArtistProfileController controller = fxmlLoader.getController();
            controller.setData(this.artist);
            App.hubController.setViewsContainer(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setData(ArtistDTO artist) {
        this.artist=artist;
        label_ArtistName.setText(artist.getName());
        File img = new File(artist.getPicture());
        if (img.exists()) {
            img_picture.setImage(new javafx.scene.image.Image(img.toURI().toString()));
        }
    }
}
