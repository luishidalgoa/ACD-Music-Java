package dev.iesfranciscodelosrios.acdmusic.Components.CommentCard;

import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Comment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class CommentCardController {
    @FXML
    private TextArea description;
    @FXML
    private Label labe_Date;
    @FXML
    private Label label_userName;
    @FXML
    public void initialize() {

    }
    public void setData(Comment comment){
        description.setText(comment.getDescription());
        labe_Date.setText(comment.getDate().toString());
        label_userName.setText(comment.getUser().getNickName());
    }
}
