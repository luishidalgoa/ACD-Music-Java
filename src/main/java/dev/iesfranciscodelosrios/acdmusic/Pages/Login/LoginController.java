package dev.iesfranciscodelosrios.acdmusic.Pages.Login;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.UserDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;
import dev.iesfranciscodelosrios.acdmusic.Pages.Hub.HubController;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LoginController {

    private Login sl=Login.getInstance();
    private UserDAO udao;

    @FXML
    private TextField UserText;
    @FXML
    private PasswordField PasswordText;
    @FXML
    private Button RegisterBtn;
    @FXML
    private Button LoginBtn;
    @FXML
    private Text TextError;

    @FXML
    private void BtnLogin(){
        User loginUser = new User(UserText.getText(),"","","","",PasswordText.getText());
        if (sl.Auth(loginUser) != null) {
            App.hubController=App.setRoot("Pages/Hub/","Hub");
        } else {
            TextError.setText("Usuario o contraseña invalidos");
            TextError.setFill(Color.RED);
        }
    }

    @FXML
    private void BtnRegister(){
        App.setRoot("Pages/Login/","Register");
    }
}
