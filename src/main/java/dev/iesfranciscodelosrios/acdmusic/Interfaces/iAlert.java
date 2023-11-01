package dev.iesfranciscodelosrios.acdmusic.Interfaces;

public interface iAlert {
    /**
     *  Funcion que se ejecutara cuando el usuario clicke sobre el boton
     * @param funcion funcion anonima que se le aplicara al boton apply
     */
    void btn_accept(iArrowFunctions funcion);
    /**
     * Funcion que se ejecutara cuando el usuario clicke sobre el boton
     * @param funcion funcion anonima que se le aplicara al boton cancel
     */
    void btn_cancel(iArrowFunctions funcion);
}
