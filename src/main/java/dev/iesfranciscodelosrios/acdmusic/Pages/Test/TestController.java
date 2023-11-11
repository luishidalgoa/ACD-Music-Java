package dev.iesfranciscodelosrios.acdmusic.Pages.Test;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Components.ArtistCard.ArtistCardController;
import dev.iesfranciscodelosrios.acdmusic.Components.GenericForm.GenericFormController;
import dev.iesfranciscodelosrios.acdmusic.Components.MediaPlayer.MediaPlayerController;
import dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card.ReproductionList_mediumCard;
import dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card.ReproductionList_minCard;
import dev.iesfranciscodelosrios.acdmusic.Components.SongCard.SongCardController;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ArtistDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.SongDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.UserDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Genre;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Style;
import dev.iesfranciscodelosrios.acdmusic.Pages.ReproductionListView.ReproductionListViewController;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import dev.iesfranciscodelosrios.acdmusic.TestViews;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class TestController extends TestViews{

    @FXML
    public void initialize() {
        Login.getInstance().setCurrentUser(UserDAO.getInstance().searchById(5));
    }
    @FXML
    public void ReproductionList_mediumCard() {

        try {
            FXMLLoader fxmlLoader = TestViews.getFXML("Components/ReproductionList_mediumCard/","ReproductionList_mediumCard");
            TestViews.newStage(fxmlLoader.load());
            ReproductionList_mediumCard controller = fxmlLoader.getController();
            controller.setData(ReproductionListDAO.getInstance().searchReproductionListById(1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void ReproductionList_minCard() {
        try {
            FXMLLoader fxmlLoader = TestViews.getFXML("Components/ReproductionList_minCard/","ReproductionList_minCard");
            TestViews.newStage(fxmlLoader.load());
            ReproductionList_minCard controller = fxmlLoader.getController();
            controller.setData(ReproductionListDAO.getInstance().searchReproductionListById(1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void loadMediaPlayer() {
        try {
            FXMLLoader fxmlLoader= TestViews.getFXML("Components/MediaPlayer/","MediaPlayer");
            TestViews.newStage(fxmlLoader.load());
            MediaPlayerController mediaPlayerController=fxmlLoader.getController();
            mediaPlayerController.setData(ReproductionListDAO.getInstance().searchSongsById(1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void loadSearch() {

        try {
            TestViews.newStage(TestViews.getFXML("Components/Search/","Search").load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void loadArtistCard() {
        try {
            FXMLLoader fxmlLoader= TestViews.getFXML("Components/ArtistCard/","ArtistCard");
            TestViews.newStage(fxmlLoader.load());
            ArtistCardController artistCardController=fxmlLoader.getController();
            artistCardController.setData(ArtistDAO.getInstance().searchArtistByIdArtist(2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void loadHome() {

        try {
            TestViews.newStage(TestViews.getFXML("Pages/Home/","Home").load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void loadHub() {
        try {
            FXMLLoader fxmlLoader= TestViews.getFXML("Pages/Hub/","Hub");
            Stage stage=TestViews.newStage(fxmlLoader.load());
            stage.setTitle("ACD Music");
            stage.getIcons().add(new javafx.scene.image.Image(App.class.getResource("assets/pictures/app/Logo.png").toString()));
            hubController=fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    public void loadGenericForm(){
        GenericFormController controller;
        try {
            FXMLLoader fxmlLoader=TestViews.getFXML("Components/GenericForm/","GenericForm");
            TestViews.newStage(fxmlLoader.load());
            controller=fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        {
            System.out.println(controller==null);
            controller.addInput("Prueba","","Name for the song");

            controller.addNode(new Button(),"fileUpload","File music", Style.gap_2.getStyle() ,new int[]{1, 0},()->{
                FileChooser fileChooser = new FileChooser();
                ((Button)controller.getNode("fileUpload")).setText("Choose file");
                ((Button)controller.getNode("fileUpload")).setStyle(Style.btn_primary.getStyle());
                ((Button)controller.getNode("fileUpload")).setOnAction(e -> {
                    // Mostrar el diálogo de selección de archivo
                    fileChooser.setTitle("Seleccionar Archivo");
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("Archivos de musica", "*.mp3")
                    );

                    // Obtener el archivo seleccionado
                    java.io.File selectedFile = fileChooser.showOpenDialog(null);

                    if (selectedFile != null) {
                        // Aquí puedes realizar operaciones con el archivo seleccionado.
                        System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath());
                    }
                });

                controller.setHoverAnimation(controller.getNode("fileUpload"), Style.btn_primary_hover.getStyle(),Style.btn_primary.getStyle());
            });
            //agregaremos un nuevo nodo que sera un desplegable con los generos musicales
            controller.addNode(new ComboBox<>(),"genre","Select to Genre","",new int[]{0,1},()->{
                //le agregaremos los generos musicales del enum
                ComboBox nodo=((ComboBox<String>)controller.getNode("genre"));
                for(Genre aux:Genre.values()){
                    nodo.getItems().add(aux.toString());
                }
            });

            controller.eventBtnSend(()->{
                System.out.println(((TextField)controller.getNode("Prueba")).getText());
                System.out.println(((ComboBox<String>)controller.getNode("genre")).getValue());
            });
        }

    }

    @FXML
    public void loadRL(){
        try {
            FXMLLoader fxmlLoader= TestViews.getFXML("Pages/ReproductionList/","ReproductionList");
            TestViews.newStage(fxmlLoader.load());
            ReproductionListViewController controller = fxmlLoader.getController();
            controller.setData(ReproductionListDAO.getInstance().searchReproductionListById(1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void loadSong(){
        try {
            FXMLLoader fxmlLoader= TestViews.getFXML("Components/SongCard/","SongCard");
            TestViews.newStage(fxmlLoader.load());
            SongCardController controller=fxmlLoader.getController();
            controller.setData(SongDAO.getInstance().searchById(3));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
