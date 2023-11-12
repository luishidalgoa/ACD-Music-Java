package dev.iesfranciscodelosrios.acdmusic.Pages.AlbumView;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Components.GenericForm.GenericFormController;
import dev.iesfranciscodelosrios.acdmusic.Components.SongCard.SongCardController;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.*;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Genre;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Style;
import dev.iesfranciscodelosrios.acdmusic.Services.FilesS;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AlbumViewController {
    @FXML
    private ImageView album_image;
    @FXML
    private Label album_name;
    @FXML
    private Label date;
    @FXML
    private Label reproduction;
    @FXML
    private VBox songCardsContainer;
    @FXML
    private Pane upload;
    private Album album;
    private List<Integer> index;
    private Scene uploadScene;

    @FXML
    public void initialize() {
        upload.setVisible(false);
        upload.setDisable(true);
    }
    public void setData(Album album){
        if (Login.getInstance().getCurrentUser().getId()== ArtistDAO.getInstance().searchArtistByIdAlbum(album.getIdAlbum()).getId()) {
            upload.setVisible(true);
            upload.setDisable(false);
        }
        if (album != null) {
            this.album = album;
            loadAlbum();
            loadSongs();
        }
    }

    private void loadAlbum() {
        album_name.setText(album.getName());
        date.setText(album.getDate().formatted("%d/%m/%Y"));
        reproduction.setText(String.valueOf(album.getReproductions()));
        File img = new File(album.getPicture());
        if (img.exists()) {
            album_image.setImage(new javafx.scene.image.Image(img.toURI().toString()));
        }
    }

    private void addSong() {
        //método para subir canción dentro del album
    }

    private void loadSongs() {
        // Obtener la lista de canciones asociadas al álbum
        Set<Song> songs = SongDAO.getInstance().searchByAlbumId(album.getIdAlbum());

        // Cargar cada SongCard en el contenedor
        for (Song song : songs) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/SongCard/SongCard.fxml"));
                Node node = fxmlLoader.load();
                SongCardController controller = fxmlLoader.getController();
                controller.setData(song);
                songCardsContainer.getChildren().add(node);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void uploadSong() {
        index = new ArrayList<>();
        GenericFormController controller;
        if (uploadScene == null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.getUrlResource("Components/GenericForm/", "GenericForm"));
                uploadScene = App.newStage(fxmlLoader.load());
                controller = fxmlLoader.getController();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            {
                controller.addNode(new Button(), "addSong", "", Style.gap_2.getStyle(), new int[]{0, 0}, () -> {
                    Button btn = (Button) controller.getNode("addSong");
                    btn.setText("Add song");
                    btn.setOnAction(e -> {
                        // le pasaremos un random del 1-1000
                        int random = (int) (Math.random() * 1000 + 1);
                        index.add(random);
                        InputUpload(controller, random);
                    });
                    btn.setStyle(Style.btn_primary.getStyle());
                });
                index.add(0);
                InputUpload(controller, 0);
            }
            controller.eventBtnSend(() -> {
                FilesS fileService = new FilesS();
                for (int i : index) {
                    boolean isUpload;
                    Label fileUrl = ((Label) controller.getNode("FileSelected" + i));
                    TextField nameSong = ((TextField) controller.getNode("songName" + i));
                    ComboBox genre = ((ComboBox) controller.getNode("genre" + i));
                    Song song = new Song();
                    song.setId_album(album.getIdAlbum());
                    song.setName(nameSong.getText());
                    song.setGenre(Genre.valueOf(genre.getValue().toString()));
                    String text = fileUrl.getText();
                    File selectedFile = text.isEmpty() || text == "" ? null : new File(text);
                    try {
                        isUpload=fileService.CopyFile(selectedFile.getAbsolutePath(), "./src/main/resources/dev/iesfranciscodelosrios/acdmusic/assets/music/" + selectedFile.getName().hashCode());
                        song.setUrl("./src/main/resources/dev/iesfranciscodelosrios/acdmusic/assets/music/" + selectedFile.getName().hashCode() + "." + fileService.getExtension(selectedFile.getAbsolutePath()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if(isUpload){
                        SongDAO.getInstance().addSong(song);
                    }
                }
                uploadScene.getWindow().hide();
                uploadScene = null;
            });
            controller.btn_cancel.setOnAction(e -> {
                uploadScene.getWindow().hide();
                uploadScene = null;
            });
        }
    }

    private void InputUpload(GenericFormController controller, int index) {
        controller.addInput("songName"+index, "Name for the song");

        // agregaremos un nuevo nodo que sera un desplegable con los generos musicales
        controller.addNode(new ComboBox<>(), "genre"+index, "Select to Genre", "", new int[]{0, 1}, () -> {
            // le agregaremos los generos musicales del enum
            ComboBox nodo = ((ComboBox<String>) controller.getNode("genre"+index));
            for (Genre aux : Genre.values()) {
                nodo.getItems().add(aux.toString());
            }
        });
        controller.addNode(new Button(), "fileUpload" + index, "Add file music", Style.gap_2.getStyle(),
                new int[]{1, 0}, () -> {
                    controller.addNode(new Label(), "textFile" + index, "File selected: ", "", new int[]{0, 0}, () -> {
                    });
                    controller.getNode("textFile" + index).setStyle("-fx-font-size: 10px;");
                    controller.addNode(new Label(), "FileSelected" + index, "", "", new int[]{1,0}, () -> {

                    });
                    controller.getNode("FileSelected" + index).setStyle("-fx-font-size: 10px;");
                    FileChooser fileChooser = new FileChooser();
                    Button btn = ((Button) controller.getNode("fileUpload" + index));
                    btn.setText("Choose file");
                    btn.setStyle(Style.btn_primary.getStyle());
                    btn.setOnAction(e -> {
                        FilesS fileService = new FilesS();
                        // Mostrar el diálogo de selección de archivo
                        fileChooser.setTitle("Seleccionar Archivo");
                        fileChooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("Archivos de musica", "*.mp3"));

                        // Obtener el archivo seleccionado
                        java.io.File selectedFile = fileChooser.showOpenDialog(null);

                        if (selectedFile != null) {
                            Label label = (Label) controller.getNode("FileSelected" + index);
                            label.setText(selectedFile.getAbsolutePath());
                            label.setStyle(label.getStyle() + "-fx-text-fill: green;");
                        }
                        fileService = null;
                    });

                    controller.setHoverAnimation(controller.getNode("fileUpload" + index),
                            Style.btn_primary_hover.getStyle(), Style.btn_primary.getStyle());
                });
    }
}
