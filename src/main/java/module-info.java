module dev.iesfranciscodelosrios.acdmusic {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens dev.iesfranciscodelosrios.acdmusic to javafx.fxml;
    exports dev.iesfranciscodelosrios.acdmusic;
    exports dev.iesfranciscodelosrios.acdmusic.Pages.Hub;
    opens dev.iesfranciscodelosrios.acdmusic.Pages.Hub to javafx.fxml;
    exports dev.iesfranciscodelosrios.acdmusic.Pages.Test;
    opens dev.iesfranciscodelosrios.acdmusic.Pages.Test to javafx.fxml;
    exports dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card to javafx.fxml;
    opens dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card to javafx.fxml;
    exports dev.iesfranciscodelosrios.acdmusic.Components.MediaPlayer;
    opens  dev.iesfranciscodelosrios.acdmusic.Components.MediaPlayer;
    exports dev.iesfranciscodelosrios.acdmusic.Components.Search;
    opens dev.iesfranciscodelosrios.acdmusic.Components.Search;

}