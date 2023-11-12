package dev.iesfranciscodelosrios.acdmusic.Pages.ArtistProfile;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Components.AlbumCard.AlbumCardController;
import dev.iesfranciscodelosrios.acdmusic.Components.GenericForm.GenericFormController;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.UserDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Style;
import dev.iesfranciscodelosrios.acdmusic.Services.FilesS;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class ArtistProfileController {
    @FXML
    private Label label_name;
    @FXML
    private ImageView img_picture;
    @FXML
    private VBox vbox_container;
    @FXML
    private Label nacionality;
    @FXML
    private Pane upload;
    private ArtistDTO artist;
    private Scene uploadScene;

    @FXML
    public void initialize() {
        upload.setVisible(false);
        upload.setDisable(true);
        Circle clip = new Circle();
        clip.setRadius(60);
        clip.setCenterX(65);
        clip.setCenterY(65);
        img_picture.setClip(clip);
    }

    public void setData(ArtistDTO artist) {
        this.artist = artist;
        label_name.setText(artist.getNickName());
        File img = new File(artist.getPicture());
        if (img.exists()) {
            img_picture.setImage(new javafx.scene.image.Image(img.toURI().toString()));
        }
        nacionality.setText(artist.getNacionality());
        updateSongsContainer();

        if (Login.getInstance().getCurrentUser().equals(UserDAO.getInstance().searchById(artist.getId_user()))) {
            upload.setVisible(true);
            upload.setDisable(false);
        }
    }

    @FXML
    public void newAlbum() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/GenericForm/GenericForm.fxml"));
        GenericFormController controller = null;
        try {
            uploadScene = App.newStage(fxmlLoader.load());
            controller = fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        controller.addNode(new Label(), "title", "", Style.gap_2.getStyle(), new int[]{1, 0}, () -> {

        });
        Label albumName = (Label) controller.getNode("title");
        albumName.setStyle(Style.h3.getStyle());
        albumName.setText("New Album");
        controller.addInput("albumName", "Name for the album");
        controller.addNode(new Label(), "picture", "", Style.gap_2.getStyle(), new int[]{1, 0}, () -> {

        });
        Label picture = (Label) controller.getNode("picture");
        picture.setStyle(Style.h3.getStyle());
        picture.setText("Select a picture");
        InputUpload(controller);

        GenericFormController finalController = controller;
        controller.eventBtnSend(() -> {
            Album album = new Album();
            album.setIdArtist(this.artist.getId_artist());
            album.setName(((TextField) finalController.getNode("albumName")).getText());
            {
                FilesS fileService = new FilesS();
                String text = ((Label) finalController.getNode("FileSelected")).getText();
                File selectedFile = text.isEmpty() || text == "" ? null : new File(text);
                try {
                    fileService.CopyFile(selectedFile.getAbsolutePath(), "./src/main/resources/dev/iesfranciscodelosrios/acdmusic/assets/pictures/album/" + selectedFile.getName().hashCode());
                    album.setPicture("./src/main/resources/dev/iesfranciscodelosrios/acdmusic/assets/pictures/album/" + selectedFile.getName().hashCode() + "." + fileService.getExtension(selectedFile.getAbsolutePath()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            AlbumDAO.getInstance().addAlbum(album);
            updateSongsContainer();

            uploadScene.getWindow().hide();
            uploadScene = null;
        });
        controller.eventBtnCancel(() -> {
            uploadScene.getWindow().hide();
            uploadScene = null;
        });
    }

    private void InputUpload(GenericFormController controller) {
        controller.addNode(new Button(), "fileUpload", "Add file image", Style.gap_2.getStyle(),
                new int[]{1, 0}, () -> {
                    controller.addNode(new Label(), "textFile", "File selected: ", "", new int[]{1, 0}, () -> {
                    });
                    controller.getNode("textFile").setStyle("-fx-font-size: 10px;");
                    controller.addNode(new Label(), "FileSelected", "", "", new int[]{0, 0}, () -> {

                    });
                    controller.getNode("FileSelected").setStyle("-fx-font-size: 10px;");
                    FileChooser fileChooser = new FileChooser();
                    Button btn = ((Button) controller.getNode("fileUpload"));
                    btn.setText("Choose file");
                    btn.setStyle(Style.btn_primary.getStyle());
                    btn.setOnAction(e -> {
                        FilesS fileService = new FilesS();
                        // Mostrar el diálogo de selección de archivo
                        fileChooser.setTitle("Seleccionar Archivo");
                        fileChooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg"));

                        // Obtener el archivo seleccionado
                        java.io.File selectedFile = fileChooser.showOpenDialog(null);

                        if (selectedFile != null) {
                            // Aquí puedes realizar operaciones con el archivo seleccionado.
                            Label label = (Label) controller.getNode("FileSelected");
                            label.setText(selectedFile.getAbsolutePath());
                            label.setStyle(label.getStyle() + "-fx-text-fill: green;");
                        }
                        fileService = null;
                    });

                    controller.setHoverAnimation(controller.getNode("fileUpload"),
                            Style.btn_primary_hover.getStyle(), Style.btn_primary.getStyle());
                });
    }

    public void updateSongsContainer() {
        vbox_container.getChildren().clear();
        Set<Album> albumes = AlbumDAO.getInstance().searchAllAlbumsByArtist(artist);
        if (albumes != null && !albumes.isEmpty()) {
            for (Album album : albumes) {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/AlbumCard/AlbumCard.fxml"));
                try {
                    Node node = fxmlLoader.load();
                    AlbumCardController controller = fxmlLoader.getController();
                    controller.setData(album);
                    vbox_container.getChildren().add(node);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
