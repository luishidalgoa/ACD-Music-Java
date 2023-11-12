package dev.iesfranciscodelosrios.acdmusic.Pages.Login;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.UserDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Style;
import dev.iesfranciscodelosrios.acdmusic.Services.FilesS;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class RegisterController {
    private Login sl= Login.getInstance();
    private UserDAO udao;

    @FXML
    private TextField NameText;
    @FXML
    private Label text_url;
    @FXML
    private Button upload;
    @FXML
    private TextField LastnameText;
    @FXML
    private TextField EmailText;
    @FXML
    private TextField NicknameText;
    @FXML
    private PasswordField PasswordText;
    @FXML
    private PasswordField ConfirmPasswordText;
    @FXML
    private Button RegisterBtn;
    @FXML
    private Button BackBtn;
    @FXML
    public void initialize() {
        upload.setStyle(Style.btn_primary.getStyle());
        upload.setOnMouseEntered(e -> upload.setStyle(Style.btn_primary_hover.getStyle()));
        upload.setOnMouseExited(e -> upload.setStyle(Style.btn_primary.getStyle()));
        uploadPicture();
    }
    @FXML
    private void BtnRegister(){
        User newUser = new User();
        newUser.setName(NameText.getText());
        newUser.setLastName(LastnameText.getText());
        newUser.setEmail(EmailText.getText());
        newUser.setNickName(NicknameText.getText());
        if(!PasswordText.getText().equals(ConfirmPasswordText.getText()))
            return;
        newUser.setPassword(PasswordText.getText());
        {
            FilesS fileService = new FilesS();
            File selectedFile = text_url.getText().isEmpty() || text_url.getText() == "" ? null : new File(text_url.getText());
            try {
                fileService.CopyFile(selectedFile.getAbsolutePath(), "./src/main/resources/dev/iesfranciscodelosrios/acdmusic/assets/pictures/user/" + selectedFile.getName().hashCode());
                newUser.setPicture("./src/main/resources/dev/iesfranciscodelosrios/acdmusic/assets/pictures/user/" + selectedFile.getName().hashCode() + "." + fileService.getExtension(selectedFile.getAbsolutePath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        UserDTO result= sl.Register(newUser);
        if (result != null) {
            sl.setCurrentUser(result);
            App.setRoot("Pages/Hub/","Hub");
        }
    }

    @FXML
    private void BtnBack(){
        App.setRoot("Pages/Login/","Login");
    }

    private void uploadPicture(){
        FileChooser fileChooser = new FileChooser();
        upload.setOnAction(e -> {
            FilesS fileService = new FilesS();
            // Mostrar el diálogo de selección de archivo
            fileChooser.setTitle("Seleccionar Archivo");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg"));

            // Obtener el archivo seleccionado
            java.io.File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                text_url.setText(selectedFile.getAbsolutePath());
                text_url.setStyle(text_url.getStyle() + "-fx-text-fill: green;");
            }
        });
    }

}
