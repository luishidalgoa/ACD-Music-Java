package dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Style;
import dev.iesfranciscodelosrios.acdmusic.Pages.ReproductionListView.ReproductionListViewController;
import dev.iesfranciscodelosrios.acdmusic.Pages.UserProfile.UserProfileController;
import dev.iesfranciscodelosrios.acdmusic.TestViews;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.File;

abstract public class ReproductionList_Card {

    @FXML
    protected ImageView img_picture;
    @FXML
    protected Label label_nameList;
    @FXML
    protected Label label_username;
    protected ReproductionList rl;

    @FXML
    public void initialize() {
        img_picture.setStyle(Style.Shadow.getStyle());
    }

    public void loadReproductionListView(){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Pages/ReproductionList/ReproductionList.fxml"));
        try {
            Node node = fxmlLoader.load();
            ReproductionListViewController controller = fxmlLoader.getController();
            controller.setData(ReproductionListDAO.getInstance().searchReproductionListById(rl.getId()));
            App.hubController.setViewsContainer(node);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void loadUserProfile(){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Pages/UserProfile/UserProfile.fxml"));
        try {
            Node node = fxmlLoader.load();
            UserProfileController controller = fxmlLoader.getController();
            controller.setData(rl.getOwner());
            App.hubController.setViewsContainer(node);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Le pasara por parametro la informacion necesaria para mostrar la Card de la lista de reproduccion
     *
     * @param obj Objeto de tipo ReproductionList que contiene la informacion necesaria para mostrar la Card
     */
    public void setData(ReproductionList obj){
        rl = obj;
        //apuntamos al archivo con la imagen del album de la primera cancion de la lista. Para asi mostrarla como foto principal de la lista
        int findIdAlbumforFirstSong = rl.getSongs()!=null?rl.getSongs().stream().findFirst().get().getId_album():-1;
        if(label_username!=null){
            label_username.setText(rl.getOwner().getNickName());
        }
        if(findIdAlbumforFirstSong>-1){
            File img = new File(AlbumDAO.getInstance().getAlbumById(findIdAlbumforFirstSong).getPicture());
            if (img.exists()) {
                img_picture.setImage(new javafx.scene.image.Image(img.toURI().toString()));
            }
        }
        label_nameList.setText(rl.getName());
    }
}
