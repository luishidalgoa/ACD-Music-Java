package dev.iesfranciscodelosrios.acdmusic.Pages.ReproductionListView;

import dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card.ReproductionList_mediumCard;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Style;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;

public class ReproductionListViewController extends ReproductionList_mediumCard {
    @FXML
    private ImageView img_picture;
    @FXML
    private Label label_nameList;
    @FXML
    private TextArea description;
    @FXML
    private Label subs_counter;
    @FXML
    private Pane comment_open;
    @FXML
    private ScrollPane Scroll_comment;
    @FXML
    private VBox vbox_container;

    @FXML
    public void initialize() {
        description.setStyle("-fx-border-width: 0;-fx-background-color: transparent;");
        img_picture.setStyle(Style.Shadow.getStyle());
        Scroll_comment.setVisible(false);
    }

    public void setData(ReproductionList rl) {
        super.setData(rl);
        description.setText(rl.getDescription());
        subs_counter.setText(String.valueOf(ReproductionListDAO.getInstance().getAllSubcriptions(rl.getId())));

    }

    public void updateSongsContainer() {
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

    @FXML
    public void comment_open() {
        /*Scroll_comment.setVisible(true);
        comment_open.setVisible(false);
        CommentDAO.getInstance().searchAllByIdList(rl.getId()).forEach(comment -> {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/CommentCard/CommentCard.fxml"));
            try {
                Node node = fxmlLoader.load();
                CommentCardController controller = fxmlLoader.getController();
                controller.setData(comment);
                vbox_container.getChildren().add(node);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });*/
    }

    @FXML
    public void comment_close() {
        Scroll_comment.setVisible(false);
        comment_open.setVisible(true);
    }
}
