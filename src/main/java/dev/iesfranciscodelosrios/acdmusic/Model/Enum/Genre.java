package dev.iesfranciscodelosrios.acdmusic.Model.Enum;

import java.util.Arrays;
import java.util.Set;

public enum Genre {
    POP,
    ROCK,
    METAL,
    RAP,
    REGGAE,
    JAZZ,
    BLUES,
    COUNTRY,
    ELECTRONIC,
    CLASSIC,
    FOLK,
    FLAMENCO,
    INDIE,
    PUNK,
    SOUL,
    FUNK,
    RNB,
    HIPHOP,
    TRAP,
    DANCE,
    HOUSE,
    TECHNO,
    DISCO,
    ALTERNATIVE,
    GRUNGE,
    SKA,
    GOSPEL,
    OPERA,
    NEWAGE,
    AMBIENT,
    CHILL,
    DUBSTEP,
    TRANCE,
    PSYCHEDELIC,
    INSTRUMENTAL,
    ACOUSTIC,
    OTHER;
    /**
     * CREAMOS UN METODO que a partir del valor de un enum devuleva su value en formato string
     */
    public static String getGenreValue(Genre genre) {
        return genre.name();
    }
    public static Set<Genre> getAllGenres(){
        return Set.of(Genre.values());
    }
}
