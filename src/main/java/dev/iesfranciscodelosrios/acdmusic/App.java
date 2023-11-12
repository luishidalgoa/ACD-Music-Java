package dev.iesfranciscodelosrios.acdmusic;
import dev.iesfranciscodelosrios.acdmusic.Components.MediaPlayer.MediaPlayerController;
import dev.iesfranciscodelosrios.acdmusic.Pages.Hub.HubController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.function.Function;

public class App<T> extends Application {
    private static Scene scene;
    public static HubController hubController;
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(new FXMLLoader(getUrlResource("Pages/Login/","Login")).load(),1400,920);//,1400, 920
        stage.setTitle("ACD Music");
        stage.getIcons().add(new javafx.scene.image.Image(App.class.getResource("assets/pictures/app/Logo.png").toString()));
        stage.setScene(scene);
        stage.show();
    }

    public static <T> T setRoot(String route,String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getUrlResource(route,fxml));
            scene.setRoot(fxmlLoader.load());
            return fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static URL getUrlResource(String route, String fxml) {
        return App.class.getResource(route+fxml+".fxml");
    }
    public static Scene newStage(Parent p) throws IOException {
        Stage stage2=new Stage();
        Scene scene2= new Scene(p);
        stage2.setScene(scene2);
        stage2.show();
        stage2.setOnCloseRequest((WindowEvent event) -> {
            // Evitar que la ventana se cierre directamente
            event.consume();
        });
        return scene2;
    }
    public static Scene getScene() {
        return scene;
    }
}
