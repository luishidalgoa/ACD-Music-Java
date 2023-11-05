package dev.iesfranciscodelosrios.acdmusic.Model.Enum;

public enum Style {
    gap_2("-fx-hgap: 10;"),
    btn_primary("-fx-background-color: #3498db; " +
            "-fx-background-radius: 10; " +
            "-fx-text-fill: #ffffff; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0, 0, 1);"),
    btn_primary_hover("-fx-background-color: #2980b9; " +
            "-fx-background-radius: 10; " +
            "-fx-text-fill: #ffffff; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0, 0, 1);"),
    //eliminaremos el borde y dejaremos solo el borde inferior de color azul
    textField("-fx-background-color: transparent; -fx-border-color: transparent transparent #3498db transparent; -fx-border-width: 0 0 2 0; -fx-text-fill: black;-fx-padding:0px;");
    private String style;
    Style(String style) {
        this.style = style;
    }
    public String getStyle() {
        return style;
    }
}
