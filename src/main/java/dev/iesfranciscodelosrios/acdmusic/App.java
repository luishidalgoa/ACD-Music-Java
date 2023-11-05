package dev.iesfranciscodelosrios.acdmusic;
import dev.iesfranciscodelosrios.acdmusic.Components.GenericForm.GenericFormController;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Genre;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Style;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxml=getFXML("Components/GenericForm/","GenericForm");
        //
        Parent root = fxml.load();;
        provisional(fxml.getController());
        //
        scene = new Scene(root);//,1400, 920
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String rute,String nameFile) {
        try {
            FXMLLoader loader=getFXML(rute,nameFile);
            scene.setRoot(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static FXMLLoader getFXML(String rute,String fileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(rute + fileName + ".fxml"));
        return fxmlLoader;
    }
    public static void newStage(Parent p) throws IOException {
        Stage stage2=new Stage();
        Scene scene2= new Scene(p);
        stage2.setScene(scene2);
        stage2.show();
    }

    public static void provisional(GenericFormController controller){
        System.out.println(controller==null);
        controller.addInput("Prueba","","Name for the song");

        controller.addNode(new Button(),"fileUpload","File music",Style.gap_2.getStyle() ,new int[]{1, 0},()->{
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
