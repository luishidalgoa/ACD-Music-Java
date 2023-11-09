package dev.iesfranciscodelosrios.acdmusic.Components.ArtistCard;

import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ArtistDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.File;

public class ArtistCardController {
    @FXML
    private Label label_ArtistName;
    @FXML
    private ImageView img_picture;
    @FXML
    public void initialize() {
        Circle clip = new Circle();
        clip.setRadius(50);
        clip.setCenterX(54);
        clip.setCenterY(50);

        // Aplicar el recorte circular a la imageView
        img_picture.setClip(clip);
    }
    public void loadArtistView(){

    }
    public void setData(ArtistDTO artist) {
        label_ArtistName.setText(artist.getName());
        File img = new File(artist.getPicture());
        if (img.exists()) {
            img_picture.setImage(new javafx.scene.image.Image(img.toURI().toString()));
        }
    }
}
