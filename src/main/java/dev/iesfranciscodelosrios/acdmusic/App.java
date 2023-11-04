package dev.iesfranciscodelosrios.acdmusic;
import dev.iesfranciscodelosrios.acdmusic.Components.GenericForm.GenericFormController;
import dev.iesfranciscodelosrios.acdmusic.Utils.Style;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Function;

public class App extends Application {
    private static Scene scene;
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("GenericForm"));//,1400, 920
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Pages/Login" + fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        return root;
    }*/
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Components/GenericForm/" + fxml + ".fxml"));
        Parent root = fxmlLoader.load();


        GenericFormController controller=fxmlLoader.getController();

        controller.addInput("Prueba","Inserte dato","Escribe aqui");


        controller.addNode(new Button(),"fileUpload","Selecciona un archivo",()->{
            FileChooser fileChooser = new FileChooser();
            ((Button)controller.getNode("fileUpload")).setText("File");
            ((Button)controller.getNode("fileUpload")).setStyle(Style.btn_primary);
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
        });


        controller.eventBtnSend(()->{
            System.out.println(((TextField)controller.getNode("Prueba")).getText());
        });


        return root;
    }
    public static void newStage(Parent p) throws IOException {
        Stage stage2=new Stage();
        Scene scene2= new Scene(p);
        stage2.setScene(scene2);
        stage2.show();
    }
}
