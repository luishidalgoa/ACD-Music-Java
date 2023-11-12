package dev.iesfranciscodelosrios.acdmusic.Pages.Hub;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Components.GenericForm.GenericFormController;
import dev.iesfranciscodelosrios.acdmusic.Components.MediaPlayer.MediaPlayerController;
import dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card.ReproductionList_minCard;
import dev.iesfranciscodelosrios.acdmusic.Components.Search.SearchController;
import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ArtistDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.ArtistDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Style;
import dev.iesfranciscodelosrios.acdmusic.Pages.ArtistProfile.ArtistProfileController;
import dev.iesfranciscodelosrios.acdmusic.Pages.UserProfile.UserProfileController;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import dev.iesfranciscodelosrios.acdmusic.TestViews;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class HubController extends MediaPlayerController {
    @FXML
    private ImageView img_Profile;
    @FXML
    private VBox VBox_views_container;
    @FXML
    private VBox vbox_reproductionLists;
    @FXML
    private Label label_userName;
    @FXML
    private Pane searchContainer;
    private TimerTask intervalUpdateUserReproductionLists;
    private Set<ReproductionList> userReproductionList;
    Scene newList;
    private SearchController searchController;

    @FXML
    public void initialize() {
        super.initialize();

        {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/Search/Search.fxml"));
            try {
                Node node = fxmlLoader.load();
                node.setStyle(node.getStyle() + "-fx-translate-y: 35px;-fx-translate-x: 200px;");
                searchContainer.getChildren().add(node);
                searchController = fxmlLoader.getController();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        userReproductionList = ReproductionListDAO.getInstance().getUserSubcriptions(Login.getInstance().getCurrentUser().getId());
        if (userReproductionList==null){
            userReproductionList = new HashSet<>();
        }
        {
            File img = new File(Login.getInstance().getCurrentUser().getPicture());
            if (img.exists()) {
                img_Profile.setImage(new javafx.scene.image.Image(img.toURI().toString()));
                img_Profile.setStyle(Style.Shadow.getStyle());
            }
            Circle clip = new Circle();
            clip.setRadius(50);
            clip.setCenterX(50);
            clip.setCenterY(50);
            img_Profile.setClip(clip);
        }
        label_userName.setText(Login.getInstance().getCurrentUser().getName());
        intervalUpdateUserReproductionLists();
        updateReproductionLists();
        loadPreview();
    }

    /**
     * Estara a la escucha continua cada x tiempo para que las listas de reproduccion a la que el usuario esta suscrito
     * sean las mismas que las que tiene cargadas en el hub si no lo hara una llamada el metodo updateReproductionLists
     * para actualizarlas
     */
    private void intervalUpdateUserReproductionLists() {
        Timer timer = new Timer();
        intervalUpdateUserReproductionLists = new TimerTask() {
            @Override
            public void run() {
                try {
                    Set<ReproductionList> rl = ReproductionListDAO.getInstance().getUserSubcriptions(Login.getInstance().getCurrentUser().getId());
                    if (rl!=null && !userReproductionList.equals(rl)) {
                        userReproductionList = rl;
                        updateReproductionListsThread();
                    }else if (rl==null){
                        userReproductionList= new HashSet<>();
                        updateReproductionListsThread();
                    }
                }catch (Exception e) {
                    ConnectionData.getConnection();
                    // Captura la excepción y realiza las acciones necesarias
                    e.printStackTrace();

                    // Cancela la tarea actual para evitar que se siga ejecutando
                    intervalUpdateUserReproductionLists.cancel();

                    // Reinicia el hilo creando uno nuevo
                    intervalUpdateUserReproductionLists = new TimerTask() {
                        @Override
                        public void run() {
                            intervalUpdateUserReproductionLists(); // Llama al método recursivamente
                        }
                    };
                    // Vuelve a programar el hilo con un intervalo de 1 segundo
                    timer.schedule(intervalUpdateUserReproductionLists, 0, 1000);
                }
            }
        };
        timer.schedule(intervalUpdateUserReproductionLists, 0, 1000);
    }




    /**
     * Recargara completamente las listas de reproduccion del usuario a las que esta suscrito
     * en el Vbox de reproduccionLists
     * Este metodo es exclusivo para el Hilo de JavaFX encargado de la lista de reproducciones del usuario
     */
    public void updateReproductionListsThread() {
        Platform.runLater(() -> {
            vbox_reproductionLists.getChildren().clear();
            if(userReproductionList!=null || !userReproductionList.isEmpty()){
                for (ReproductionList aux : userReproductionList) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/ReproductionList_minCard/ReproductionList_minCard.fxml"));
                        Node card = fxmlLoader.load();
                        ReproductionList_minCard controller = fxmlLoader.getController();
                        controller.setData(aux);
                        vbox_reproductionLists.getChildren().add(card);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void updateReproductionLists() {
        userReproductionList= ReproductionListDAO.getInstance().getUserSubcriptions(Login.getInstance().getCurrentUser().getId());
        if (userReproductionList!=null){
            vbox_reproductionLists.getChildren().clear();
            for (ReproductionList aux : userReproductionList) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/ReproductionList_minCard/ReproductionList_minCard.fxml"));
                    Node card = fxmlLoader.load();
                    ReproductionList_minCard controller = fxmlLoader.getController();
                    controller.setData(aux);
                    vbox_reproductionLists.getChildren().add(card);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void newReproductionList() {
        if (newList == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/GenericForm/GenericForm.fxml"));
            try {
                newList = App.newStage(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            GenericFormController controller = fxmlLoader.getController();

            {
                Label node = new Label("Complete this form to create a new list");
                node.setStyle(Style.h3.getStyle());
                //Construimos la nueva lista de reproduccion
                controller.addNode(node, "TextField", "", "", () -> {
                });
                controller.addInput("name", "new list name");
                controller.addInput("description", "new list description");
                controller.eventBtnSend(() -> {
                    String name = ((TextField) controller.getNode("name")).getText();
                    String description = ((TextField) controller.getNode("description")).getText();
                    if (name.length() > 0) { //validamos que el nombre no este vacio
                        ReproductionList obj = new ReproductionList(name, description, Login.getInstance().getCurrentUser(), null, null);
                        if (ReproductionListDAO.getInstance().add(obj) != null) {
                            newList.getWindow().hide();
                            newList = null;
                        }
                    } else {
                        //mostramos un mensaje de error
                        controller.getNode("name").setStyle(Style.textField.getStyle() + "-fx-border-color: red;");
                    }
                });
                controller.eventBtnCancel(() -> {
                    //cerramos la ventana
                    newList.getWindow().hide();
                    newList = null;
                });
            }
        }
    }

    public void setViewsContainer(Node viewsContainer) {
        VBox_views_container.getChildren().clear();
        VBox_views_container.getChildren().add(viewsContainer);
    }

    @FXML
    public void logout() {
        Login.getInstance().setCurrentUser(null);
        App.hubController.intervalUpdateUserReproductionLists.cancel();
        this.mediaPlayer.stop();
        this.mediaPlayer=null;
        App.setRoot("Pages/Login/", "Login");
    }

    @FXML
    public void loadPreview() {
        try {
            FXMLLoader fxmlLoader = TestViews.getFXML("Pages/Home/", "Home");
            setViewsContainer(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void loadProfile(){
        FXMLLoader fxmlLoader;
        ArtistDTO isArtist= ArtistDAO.getInstance().searchArtistByIdUser(Login.getInstance().getCurrentUser().getId());
        if(isArtist==null){
            fxmlLoader = new FXMLLoader(App.class.getResource("Pages/UserProfile/UserProfile.fxml"));
        }else{
            fxmlLoader = new FXMLLoader(App.class.getResource("Pages/ArtistProfile/ArtistProfile.fxml"));
        }
        try {
            Node node=fxmlLoader.load();
            if (isArtist!=null){
                ArtistProfileController controller = fxmlLoader.getController();
                controller.setData(isArtist);
            }else{
                UserProfileController controller = fxmlLoader.getController();
                controller.setData(Login.getInstance().getCurrentUser());
            }
            setViewsContainer(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
