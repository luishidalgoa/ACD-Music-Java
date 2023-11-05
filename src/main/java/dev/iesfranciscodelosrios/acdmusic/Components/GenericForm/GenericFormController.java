package dev.iesfranciscodelosrios.acdmusic.Components.GenericForm;

import dev.iesfranciscodelosrios.acdmusic.Interfaces.iArrowFunctions;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Style;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class GenericFormController {
    @FXML
    public VBox container;
    @FXML
    public Button btn_send;
    @FXML
    public Button btn_cancel;
    public List<Node> nodes = new ArrayList<>();

    @FXML
    public void initialize() {
        //estilaremos el container para que quede mas bonito y tenga forma de formulario
        container.setStyle("-fx-background-radius: 20px;-fx-padding: 10px;-fx-spacing: 10px;");
        //vamos a definir que el background del boton sea circular con clip
        btn_send.setStyle("-fx-background-radius: 20px;-fx-background-color: #1fdf64;");
        btn_cancel.setStyle("-fx-background-radius: 20px;-fx-background-color: #4f4f4f;");
        //establecer efecto transicion de 0.5segundos
        setClickedAnimation(btn_send, "-fx-background-radius: 20px;-fx-background-color: #1fdf64; -fx-translate-y: 2px;", "-fx-background-radius: 20px;-fx-background-color: #1fdf64; -fx-translate-y: 0px;");
        setClickedAnimation(btn_cancel, "-fx-background-radius: 20px;-fx-background-color: #4f4f4f; -fx-translate-y: 2px;", "-fx-background-radius: 20px;-fx-background-color: #4f4f4f; -fx-translate-y: 0px;");
    }

    /**
     * Crearemos un Node con un id, descripcion, arial
     * por ejemplo si comprobara que el texto introducido es un email
     *
     * @param idNode      id por el que reconoceremos el input en el fxml
     * @param description descripcion que se mostrara en el input
     * @param gridStyle  estilo que se le aplicara al grid
     * @param function    funcion que se ejecutara cuando se pulse el boton
     */
    public void addNode(Node node, String idNode, String description, String gridStyle, iArrowFunctions function) {
        node.setId(idNode);
        GridPane nodeContainer = getGridPane(description);
        nodeContainer.setStyle(nodeContainer.getStyle() + "; " + gridStyle);
        nodeContainer.add(node, 0, 1);
        container.getChildren().add(nodeContainer);
        nodes.add(node);

        function.function();
    }

    /**
     * Crearemos un Node con un id, descripcion, arial
     * por ejemplo si comprobara que el texto introducido es un email
     *
     * @param idNode       id por el que reconoceremos el input en el fxml
     * @param description  descripcion que se mostrara en el input
     * @param gridStyle    estilo que se le aplicara al grid
     * @param gridPosition posicion en la que se colocara el nodo en la grid [0]= columnas [1]=filas
     * @param function     funcion que se ejecutara cuando se pulse el boton
     */
    public void addNode(Node node, String idNode, String description,String gridStyle ,int[] gridPosition, iArrowFunctions function) {
        node.setId(idNode);
        GridPane nodeContainer = getGridPane(description);

        nodeContainer.setStyle(nodeContainer.getStyle() + "; "+ gridStyle);

        nodeContainer = getDescriptionName(nodeContainer,description);
        nodeContainer.add(node, gridPosition[0], gridPosition[1]);
        container.getChildren().add(nodeContainer);
        nodes.add(node);

        function.function();
    }

    /**
     * Crearemos un input con un id, descripcion, arial y una funcion que definira el comportamiento del input
     * por ejemplo si comprobara que el texto introducido es un email
     *
     * @param idInput     id por el que reconoceremos el input en el fxml
     * @param description descripcion que se mostrara en el input
     */
    public void addInput(String idInput, String description, String arial) {
        GridPane inputContainer = getGridPane(description);

        TextField textField = new TextField();
        textField.setId(idInput);
        textField.setPromptText(arial);
        textField.setStyle("-fx-pref-height: 30px;"+Style.textField.getStyle());

        inputContainer.add(textField, 0, 1);


        nodes.add(textField);
        container.getChildren().add(inputContainer);
    }

    /**
     * Crearemos un input con un id, descripcion, arial y una funcion que definira el comportamiento del input
     * @param functions  funcion que se ejecutara cuando se pulse el boton
     */
    public void eventBtnSend(iArrowFunctions functions) {
        btn_send.setOnAction(event -> {
            functions.function();
        });
    }

    /**
     * Este metodo se encarga de devolver el nodo que se le pase por parametro
     *
     * @param idNode id del nodo que se quiere obtener
     * @return el nodo con el id que se le pasa por parametro
     */
    public Node getNode(String idNode) {
        return nodes.stream().filter(node -> node.getId().equals(idNode)).findFirst().get();
    }

    /**
     * Este metodo se encarga de devolver un gridPane con la descripcion que se le pasa por parametro
     * @param description descripcion que se le añadira al gridPane
     * @return el gridPane con la descripcion
     */
    private GridPane getGridPane(String description) {
        GridPane nodeContainer = new GridPane();
        //vamos a estilar el Hbox de modo que tenga forma de un campo para un formulario, ademas de tener decoradores
        nodeContainer.setStyle("-fx-padding: 10px;-fx-spacing: 20px;");

        return nodeContainer;
    }

    /**
     * Este metodo se encarga de devolver un gridPane con la descripcion que se le pasa por parametro
     * @param nodeContainer gridPane al que se le añadira la descripcion
     * @param description descripcion que se le añadira al gridPane
     * @return el gridPane con la descripcion
     */
    private GridPane getDescriptionName(GridPane nodeContainer, String description){
        Text descriptionText = new Text(description);
        descriptionText.setStyle("-fx-font-size: 15px;-fx-font-weight: 300;");
        nodeContainer.setHalignment(descriptionText, HPos.LEFT);
        nodeContainer.add(descriptionText, 0, 0);
        return nodeContainer;
    }

    /**
     * Este metodo se encarga de devolver un gridPane con la descripcion que se le pasa por parametro
     * @param node gridPane al que se le añadira la descripcion
     * @param hoverStyle estilo que se le aplicara al pasar el raton por encima
     * @param normalStyle estilo que se le aplicara al quitar el raton de encima
     * @return Nodo con el hover animado
     */
    public Node setHoverAnimation(Node node, String hoverStyle, String normalStyle) {
        node.setOnMouseEntered(e -> {
            node.setStyle(hoverStyle);
        });
        node.setOnMouseExited(e -> {
            node.setStyle(normalStyle);
        });
        return node;
    }

    /**
     * Este metodo se encarga de devolver un gridPane con la descripcion que se le pasa por parametro
     * @param node gridPane al que se le añadira la descripcion
     * @param effectStyle estilo que se le aplicara al pulsar el boton
     * @param normalStyle estilo que se le aplicara al soltar el boton
     * @return Nodo con el ClickedEvent animado
     */
    public Node setClickedAnimation(Node node, String effectStyle, String normalStyle) {
        node.setOnMousePressed(e -> {
            node.setStyle(effectStyle);
        });
        node.setOnMouseReleased(e -> {
            node.setStyle(normalStyle);
        });
        return node;
    }

    @FXML
    public void cancel(){
        System.exit(0);
    }
}
