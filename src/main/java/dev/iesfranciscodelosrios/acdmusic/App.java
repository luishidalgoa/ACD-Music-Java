package dev.iesfranciscodelosrios.acdmusic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        scene = new Scene(loadFXML("Login"),1400,920);//,1400, 920
        stage.setTitle("ACD Music");
        stage.getIcons().add(new javafx.scene.image.Image("https://i.imgur.com/4i1JX2p.png"));
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

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Pages/Login" + fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        return root;
    }
    public static Scene newStage(Parent p) throws IOException {
        Stage stage2=new Stage();
        Scene scene2= new Scene(p);
        stage2.setScene(scene2);
        stage2.show();
        return scene2;
    }
    public static Scene getScene() {
        return scene;
    }
}
