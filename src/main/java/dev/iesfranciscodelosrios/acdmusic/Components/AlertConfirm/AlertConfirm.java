package dev.iesfranciscodelosrios.acdmusic.Components.AlertConfirm;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iArrowFunctions;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iAlert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class AlertConfirm  implements iAlert{
    public TextArea txt_description;

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
     * @param accept funcion que se ejecutara cuando el usuario clicke sobre el boton accept
     * @param cancel funcion que se ejecutara cuando el usuario clicke sobre el boton cancel
     */
    public void setData(String description, iArrowFunctions accept, iArrowFunctions cancel){
        txt_description.setText(description);
        btn_accept(accept);
        btn_cancel(cancel);
    }
}
