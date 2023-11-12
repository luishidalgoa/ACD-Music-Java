package dev.iesfranciscodelosrios.acdmusic.Pages.UserProfile;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card.ReproductionList_mediumCard;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.File;
import java.util.Set;

public class UserProfileController {
    @FXML
    private Label label_name;
    @FXML
    private ImageView img_picture;
    @FXML
    private VBox vbox_container;
    private UserDTO user;
    @FXML
    public void initialize() {
        Circle clip = new Circle();
        clip.setRadius(70);
        clip.setCenterX(70);
        clip.setCenterY(70);
        img_picture.setClip(clip);
    }
    public void setData(UserDTO user){
        this.user = user;
        label_name.setText(user.getNickName());
        File img = new File(user.getPicture());
        if (img.exists()) {
            img_picture.setImage(new javafx.scene.image.Image(img.toURI().toString()));
        }

        loadReproductionLists();
    }
    public void loadReproductionLists(){
        Set<ReproductionList> reproductionLists = ReproductionListDAO.getInstance().getUserSubcriptions(user.getId());
        if(reproductionLists!=null && !reproductionLists.isEmpty()){
            for(ReproductionList rl: reproductionLists){
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(App.class.getResource("Components/ReproductionList_mediumCard/ReproductionList_mediumCard.fxml"));
                    Node node = loader.load();
                    ReproductionList_mediumCard controller = loader.getController();
                    controller.setData(rl);
                    vbox_container.getChildren().add(node);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
