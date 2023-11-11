package dev.iesfranciscodelosrios.acdmusic.Pages.ReproductionListView;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Components.CommentCard.CommentCardController;
import dev.iesfranciscodelosrios.acdmusic.Components.GenericForm.GenericFormController;
import dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card.ReproductionList_mediumCard;
import dev.iesfranciscodelosrios.acdmusic.Components.SongCard.SongCardController;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.CommentDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Comment;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Style;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import dev.iesfranciscodelosrios.acdmusic.TestViews;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

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
    private VBox comment_container;
    @FXML
    private VBox Comment;

    @FXML
    public void initialize() {
        description.setStyle("-fx-border-width: 0;-fx-background-color: transparent;");
        img_picture.setStyle(Style.Shadow.getStyle());
        Comment.setVisible(false);
    }

    public void setData(ReproductionList rl) {
        super.setData(rl);
        description.setText(rl.getDescription());
        subs_counter.setText(String.valueOf(ReproductionListDAO.getInstance().getAllSubcriptions(rl.getId())));
        updateSongsContainer();

    }

    public void updateSongsContainer() {
        vbox_container.getChildren().clear();
        for (Song aux:this.rl.getSongs()) {
            FXMLLoader fxmlLoader= new FXMLLoader(App.class.getResource("Components/SongCard/SongCard.fxml"));
            try {
                Node node=fxmlLoader.load();
                SongCardController controller=fxmlLoader.getController();
                controller.setData(aux);
                vbox_container.getChildren().add(node);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @FXML
    public void comment_open() {
        comment_container.getChildren().clear();
        comment_open.setVisible(false);
        Comment.setVisible(true);
        CommentDAO.getInstance().searchAllByIdList(rl.getId()).forEach(comment -> {
            if (comment!=null){
                loadCommentNode(comment);
            }
        });
    }

    @FXML
    public void comment_close() {
        Comment.setVisible(false);
        comment_open.setVisible(true);
    }
    @FXML
    public void newComment(){
        FXMLLoader fxmlLoader= new FXMLLoader(App.class.getResource("Components/GenericForm/GenericForm.fxml"));
        try {
            Scene stage=App.newStage(fxmlLoader.load());
            GenericFormController controller= fxmlLoader.getController();
            loadGenericForm(controller,stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadCommentNode(Comment comment){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/CommentCard/CommentCard.fxml"));
        try {
            Node node = fxmlLoader.load();
            CommentCardController controller = fxmlLoader.getController();
            controller.setData(comment);
            comment_container.getChildren().add(node);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void loadGenericForm(GenericFormController controller,Scene stage){
        controller.addInput("comment","","Write your comment");
        controller.eventBtnSend(()->{
            String comment=((TextField)controller.getNode("comment")).getText();
            if(comment.length()>0){
               Comment aux=CommentDAO.getInstance().add(new Comment(Login.getInstance().getCurrentUser(), rl.getId(), comment));
               if (aux!=null){
                   aux.setDate(LocalDateTime.now());
                   loadCommentNode(aux);
               }
            }
            stage.getWindow().hide();
        });
        controller.btn_cancel.setOnAction(e->{
            stage.getWindow().hide();
        });
    }
}
