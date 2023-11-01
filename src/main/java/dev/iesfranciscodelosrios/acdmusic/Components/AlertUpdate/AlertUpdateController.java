package dev.iesfranciscodelosrios.acdmusic.Components.AlertUpdate;


import dev.iesfranciscodelosrios.acdmusic.Interfaces.iArrowFunctions;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iAlert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AlertUpdateController implements iAlert {
    public TextArea txt_description;
    public TextField textField_input;
    public Button btn_accept;
    public Button btn_cancel;
    @Override
    public void btn_accept(iArrowFunctions funcion) {
        funcion.function();
    }
    @Override
    public void btn_cancel(iArrowFunctions funcion) {
        funcion.function();
    }

    /**
     * Este metodo se encarga de establecer los datos que se mostraran en la alerta
     * @param description campo de texto que se mostrara en la alerta
     * @param prompt campo de texto que se mostrara en el textfield
     * @param accept funcion que se ejecutara cuando el usuario clicke sobre el boton accept
     * @param cancel funcion que se ejecutara cuando el usuario clicke sobre el boton cancel
     */
    public void setData(String description, String prompt , iArrowFunctions accept, iArrowFunctions cancel){
        txt_description.setText(description);
        textField_input.setPromptText(prompt);
        btn_accept(accept);
        btn_cancel(cancel);
    }
}
