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
    exports dev.iesfranciscodelosrios.acdmusic.Pages.Home;
    opens dev.iesfranciscodelosrios.acdmusic.Pages.Home;
    exports dev.iesfranciscodelosrios.acdmusic.Components.ArtistCard;
    opens dev.iesfranciscodelosrios.acdmusic.Components.ArtistCard;
    exports dev.iesfranciscodelosrios.acdmusic.Components.AlbumCard;
    opens dev.iesfranciscodelosrios.acdmusic.Components.AlbumCard;
    exports dev.iesfranciscodelosrios.acdmusic.Components.GenericForm;
    opens dev.iesfranciscodelosrios.acdmusic.Components.GenericForm;
    exports dev.iesfranciscodelosrios.acdmusic.Pages.ReproductionListView;
    opens dev.iesfranciscodelosrios.acdmusic.Pages.ReproductionListView;

}