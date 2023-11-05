module dev.iesfranciscodelosrios.acdmusic {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens dev.iesfranciscodelosrios.acdmusic to javafx.fxml;
    exports dev.iesfranciscodelosrios.acdmusic;
    exports dev.iesfranciscodelosrios.acdmusic.Components.GenericForm;
    opens dev.iesfranciscodelosrios.acdmusic.Components.GenericForm;

}