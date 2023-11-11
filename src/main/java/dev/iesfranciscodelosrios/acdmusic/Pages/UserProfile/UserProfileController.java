package dev.iesfranciscodelosrios.acdmusic.Pages.UserProfile;

import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.File;

public class UserProfileController {
    @FXML
    private Label label_name;
    @FXML
    private ImageView img_picture;
    @FXML
    private VBox vbox_container;
    @FXML
    public void initialize() {
        Circle clip = new Circle();
        clip.setRadius(70);
        clip.setCenterX(70);
        clip.setCenterY(70);
        img_picture.setClip(clip);
    }
    public void setData(UserDTO user){
        label_name.setText(user.getName());
        File img = new File(user.getPicture());
        if (img.exists()) {
            img_picture.setImage(new javafx.scene.image.Image(img.toURI().toString()));
        }
    }
}
