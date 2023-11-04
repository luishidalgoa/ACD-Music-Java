package dev.iesfranciscodelosrios.acdmusic.Components.GenericForm;

import dev.iesfranciscodelosrios.acdmusic.Interfaces.iArrowFunctions;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.util.ArrayList;
import java.util.List;

public class GenericFormController  {
    @FXML
    public VBox container;
    @FXML
    public Button btn_send;

    public List<Node> nodes=new ArrayList<>();

    @FXML
    public void initialize(){
        //estilaremos el container para que quede mas bonito y tenga forma de formulario
        container.setStyle("-fx-background-color: #f2f2f2;-fx-background-radius: 20px;-fx-padding: 10px;-fx-spacing: 10px");
        //vamos a definir que el background del boton sea circular con clip
        btn_send.setStyle("-fx-background-radius: 20px;-fx-background-color: #1fdf64" );
    }

    /**
     * Crearemos un Node con un id, descripcion, arial
     * por ejemplo si comprobara que el texto introducido es un email
     * @param idNode id por el que reconoceremos el input en el fxml
     * @param description descripcion que se mostrara en el input
     */
    public void addNode(Node node,String idNode, String description, iArrowFunctions function){
        HBox nodeContainer = getHbox(description);

        node.setId(idNode);

        nodeContainer.getChildren().addAll(node);
        container.getChildren().add(nodeContainer);

        nodes.add(node);
        function.function();
    }

    /**
     * Crearemos un input con un id, descripcion, arial y una funcion que definira el comportamiento del input
     * por ejemplo si comprobara que el texto introducido es un email
     * @param idInput id por el que reconoceremos el input en el fxml
     * @param description descripcion que se mostrara en el input
     */
    public void addInput(String idInput, String description,String arial){
        HBox inputContainer = getHbox(description);

        TextField textField = new TextField();
        textField.setId(idInput);
        textField.setPromptText(arial);


        inputContainer.getChildren().addAll(textField);
        container.getChildren().add(inputContainer);

        nodes.add(textField);
    }


    public void eventBtnSend(iArrowFunctions functions) {
        btn_send.setOnAction(event -> {
            functions.function();
        });
    }

    /**
     * Este metodo se encarga de devolver el nodo que se le pase por parametro
     * @param idNode id del nodo que se quiere obtener
     * @return el nodo con el id que se le pasa por parametro
     */
    public Node getNode(String idNode){
        return nodes.stream().filter(node -> node.getId().equals(idNode)).findFirst().get();
    }
    private HBox getHbox(String description){
        HBox nodeContainer = new HBox();

        //vamos a estilar el Hbox de modo que tenga forma de un campo para un formulario, ademas de tener decoradores
        nodeContainer.setStyle("-fx-background-color: #f2f2f2;-fx-background-radius: 20px;-fx-padding: 10px;-fx-spacing: 10px");

        Text descriptionText = new Text(description);
        descriptionText.setStyle("-fx-font-size: 20px;-fx-font-weight: bold");

        nodeContainer.getChildren().add(descriptionText);
        return nodeContainer;
    }
}
