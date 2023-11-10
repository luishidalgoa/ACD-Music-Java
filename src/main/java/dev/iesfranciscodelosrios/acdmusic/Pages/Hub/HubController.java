package dev.iesfranciscodelosrios.acdmusic.Pages.Hub;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Components.MediaPlayer.MediaPlayerController;
import dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card.ReproductionList_minCard;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import dev.iesfranciscodelosrios.acdmusic.TestViews;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.File;
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
    private TimerTask intervalUpdateUserReproductionLists;
    private Set<ReproductionList> userReproductionList;

    @FXML
    public void initialize() {
        super.initialize();
        userReproductionList = ReproductionListDAO.getInstance().getUserSubcriptions(Login.getInstance().getCurrentUser().getId());
        {
            File img = new File(Login.getInstance().getCurrentUser().getPicture());
            if (img.exists()) {
                img_Profile.setImage(new javafx.scene.image.Image(img.toURI().toString()));
            }
            Circle clip = new Circle();
            clip.setRadius(50);
            clip.setCenterX(50);
            clip.setCenterY(50);
            img_Profile.setClip(clip);
        }
        intervalUpdateUserReproductionLists();
        updateReproductionLists();
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
                Set<ReproductionList> rl = ReproductionListDAO.getInstance().getUserSubcriptions(Login.getInstance().getCurrentUser().getId());
                if (!userReproductionList.equals(rl)) {
                    userReproductionList = rl;
                    updateReproductionLists();
                }
            }
        };
        timer.schedule(intervalUpdateUserReproductionLists, 0, 1000);
    }

    /**
     * Recargara completamente las listas de reproduccion del usuario a las que esta suscrito
     * en el Vbox de reproduccionLists
     */
    public void updateReproductionLists() {
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

    @FXML
    public void newReproductionList() {

    }

    public void setViewsContainer(VBox viewsContainer) {
        VBox_views_container.getChildren().clear();
        VBox_views_container.getChildren().add(viewsContainer);
    }
}
