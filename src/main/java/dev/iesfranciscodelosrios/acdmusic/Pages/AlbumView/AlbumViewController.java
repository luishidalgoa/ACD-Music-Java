package dev.iesfranciscodelosrios.acdmusic.Pages.AlbumView;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Components.GenericForm.GenericFormController;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Genre;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Style;
import dev.iesfranciscodelosrios.acdmusic.Services.FilesS;
import dev.iesfranciscodelosrios.acdmusic.TestViews;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlbumViewController {
    private List<Integer> index;
    private Scene uploadScene;
    @FXML
    public void uploadSong(){
        index=new ArrayList<>();
        GenericFormController controller;
        if (uploadScene == null) {
            try {
                FXMLLoader fxmlLoader = TestViews.getFXML("Components/GenericForm/", "GenericForm");
                uploadScene = App.newStage(fxmlLoader.load());
                controller = fxmlLoader.getController();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            {
                controller.addInput("songName", "Name for the song");

                // agregaremos un nuevo nodo que sera un desplegable con los generos musicales
                controller.addNode(new ComboBox<>(), "genre", "Select to Genre", "", new int[] { 0, 1 }, () -> {
                    // le agregaremos los generos musicales del enum
                    ComboBox nodo = ((ComboBox<String>) controller.getNode("genre"));
                    for (Genre aux : Genre.values()) {
                        nodo.getItems().add(aux.toString());
                    }
                });
                controller.addNode(new Button(), "addSong", "", Style.gap_2.getStyle(), new int[] { 0, 0 }, () -> {
                    Button btn = (Button) controller.getNode("addSong");
                    btn.setText("Add song");
                    btn.setOnAction(e -> {
                        // le pasaremos un random del 1-1000
                        int random = (int) (Math.random() * 1000 + 1);
                        index.add(random);
                        InputUpload(controller,random );
                    });
                    btn.setStyle(Style.btn_primary.getStyle());
                });
                index.add(0);
                InputUpload(controller, 0);
            }
            controller.eventBtnSend(() -> {
                FilesS fileService = new FilesS();
                for (int i : index) {
                    String text =((Label)controller.getNode("FileSelected" + i)).getText();
                    File selectedFile = text.isEmpty() || text=="" ? null : new File(text);
                    try {
                        fileService.CopyFile(selectedFile.getAbsolutePath(), "./src/main/resources/dev/iesfranciscodelosrios/acdmusic/assets/music/" + selectedFile.getName().hashCode());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
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
        controller.addNode(new Button(), "fileUpload" + index, "Add file music", Style.gap_2.getStyle(),
                new int[] { 1, 0 }, () -> {
                    controller.addNode(new Label(), "textFile" + index, "File selected: ", "", new int[] { 1, 1 }, () -> {
                    });
                    controller.getNode("textFile" + index).setStyle("-fx-font-size: 10px;");
                    controller.addNode(new Label(), "FileSelected" + index, "", "", new int[] { 1, 2 }, () -> {

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
                            // Aquí puedes realizar operaciones con el archivo seleccionado.
                            System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath());
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
