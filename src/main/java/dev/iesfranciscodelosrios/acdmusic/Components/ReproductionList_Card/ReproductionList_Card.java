package dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card;

import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.ReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.UserDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import javafx.fxml.FXML;
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
    }

    public void loadReproductionListView(){

    }
    public void loadUserView(){

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
        if(findIdAlbumforFirstSong>-1){
            File img = new File(AlbumDAO.getInstance().getAlbumById(findIdAlbumforFirstSong).getPicture());
            if (img.exists()) {
                img_picture.setImage(new javafx.scene.image.Image(img.toURI().toString()));
            }
        }
        label_nameList.setText(rl.getName());
        label_username.setText(rl.getOwner().getNickName());
    }
}
