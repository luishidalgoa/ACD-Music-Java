package dev.iesfranciscodelosrios.acdmusic.Pages.ReproductionListView;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card.ReproductionList_mediumCard;
import dev.iesfranciscodelosrios.acdmusic.Components.SongCard.SongCardController;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Style;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class ReproductionListViewController extends ReproductionList_mediumCard {
    @FXML
    private ImageView img_picture;
    @FXML
    private Text label_listName;
    @FXML
    private TextArea description;
    @FXML
    private Pane follow;
    @FXML
    private Label subs_counter;
    @FXML
    private Pane comment_open;
    @FXML
    private ScrollPane Scroll_comment;
    @FXML
    private VBox vbox_container;
    private ReproductionList rl;
    @FXML
    public void initialize() {
        description.setStyle("-fx-border-width: 0;-fx-background-color: transparent;");
        img_picture.setStyle(Style.Shadow.getStyle());
    }
    public void setLoadData(ReproductionList rl){
        this.rl=rl;
        label_listName.setText(rl.getName());
        description.setText(rl.getDescription());
        subs_counter.setText(String.valueOf(ReproductionListDAO.getInstance().getAllSubcriptions(rl.getId())));

        handleClickSubcribe(ReproductionListDAO.getInstance().getSubcribeToListByUser(
                Login.getInstance().getCurrentUser().getId(), rl.getId()));

    }
    public void updateSongsContainer(){
        vbox_container.getChildren().clear();
        /*for (Song aux:this.rl.getSongs()) {
            FXMLLoader fxmlLoader= new FXMLLoader(App.class.getResource("Components/SongCard/SongCard.fxml"));
            try {
                Node node=fxmlLoader.load();
                SongCardController controller=fxmlLoader.getController();
                controller.setData(aux);
                vbox_container.getChildren().add(node);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }*/
    }

    @Override
    public void toggleSubcribe() {
        UserDTO currentUser = Login.getInstance().getCurrentUser();
        if (ReproductionListDAO.getInstance().getSubcribeToListByUser(currentUser.getId(), rl.getId())) {
            ReproductionListDAO.getInstance().unSubcribe(currentUser.getId(), rl);
            handleClickSubcribe(false);
        } else {
            ReproductionListDAO.getInstance().Subcribe(currentUser.getId(), rl.getId());
            handleClickSubcribe(true);
        }
    }
}
