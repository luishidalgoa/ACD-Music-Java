package dev.iesfranciscodelosrios.acdmusic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TestViews extends Application {
    private static Scene scene;
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxml=getFXML("Test/","Home");
        scene = new Scene(fxml.load());//,1400, 920
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
}
