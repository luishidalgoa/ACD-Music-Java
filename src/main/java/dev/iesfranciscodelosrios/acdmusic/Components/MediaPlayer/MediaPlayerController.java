package dev.iesfranciscodelosrios.acdmusic.Components.MediaPlayer;

import dev.iesfranciscodelosrios.acdmusic.App;
import dev.iesfranciscodelosrios.acdmusic.Components.ReproductionList_Card.ReproductionList_minCard;
import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iArrowFunctions;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.SongDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.SVGPath;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class MediaPlayerController {
    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar songProgressBar;
    @FXML
    private ImageView SongImage;
    @FXML
    private Label SongName;
    @FXML
    private Label NextSongName;
    @FXML
    private Label label_next;
    @FXML
    private SVGPath svg_reproduction_1;
    @FXML
    private SVGPath svg_reproduction_2;

    private List<Song> Songs = new ArrayList<>();
    private int indexSong = 0;
    protected MediaPlayer mediaPlayer;
    private Media currentMedia;
    // Timer es una clase que permite programar tareas para que se ejecuten en un hilo en un momento específico o después de un retraso.
    private Timer timer;
    // TimerTask es una clase para crear tareas que se ejecutan a intervalos regulares de tiempo. eso quiere decir que se ejecuta cada x tiempo
    private TimerTask interval;
    private int currentIndex;

    private boolean running;

    @FXML
    public void initialize() {
        volumeSlider.setValue(100);
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100);
            }
        });
    }

    public void handleTogglePlay() {
        if (mediaPlayer!=null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            handlePause();
        } else if (mediaPlayer==null){
            if(mediaPlayer==null){
                Alert alert = new Alert(Alert.AlertType.ERROR,"No song selected");
                alert.show();
            }
        }else {
            handlePlay();
        }
    }

    private void handlePause() {
        pause();
        mediaPlayer.pause();
    }

    public void handlePlay() {
        play();
        mediaPlayer.play();
    }

    @FXML
    public void handleNext() {
        if (Songs.size() > currentIndex + 1) {
            mediaPlayer.stop();
            mediaPlayer = null;
            currentIndex++;
            setSong(Songs.get(currentIndex));
            handlePlay();
        }
    }

    @FXML
    public void handlePrevious() {
        if (currentIndex > 0) {
            cancelTimer();
            mediaPlayer.stop();
            mediaPlayer = null;
            currentIndex--;
            setSong(Songs.get(currentIndex));
            handlePlay();
        }
    }

    public void progressTimer() {
        timer = new Timer();
        interval = new TimerTask() {
            boolean reproducido = false;

            public void run() {
                //vamos a comprobar si se han reproducido los 5 primeros segundos de la cancion
                if (running && mediaPlayer.getCurrentTime().toSeconds() > 5 && !reproducido) {
                    reproducido = true;
                    SongDAO.getInstance().updateReproductions(Songs.get(currentIndex).getId_song());
                }

                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = currentMedia.getDuration().toSeconds();
                songProgressBar.setProgress(current / end);

                if (current / end == 1) {
                    Platform.runLater(() -> {
                        handleNext();
                    });
                }
            }
        };

        timer.scheduleAtFixedRate(interval, 0, 1000);
    }

    public void cancelTimer() {
        running = false;
        timer.cancel();
    }

    public void setData(Set<Song> songs) {
        if (!songs.isEmpty()) {
            Songs.clear();
        }
        for (Song aux : songs) {
            Songs.add(aux);
        }
        //Apuntamos a la primera cancion de la lista
        Song firstSong = Songs.stream().findFirst().get();
        currentIndex = 0;
        setSong(firstSong);
    }

    public void setSong(Song song) {
        currentMedia = new Media(new File(song.getUrl()).toURI().toString());
        mediaPlayer = new MediaPlayer(currentMedia);
        progressTimer();
        UpdateMediaPlayer(song);
    }
    public void setData(Set<Song> songs, boolean autoPlay){
        if(mediaPlayer!=null){
            cancelTimer();
            mediaPlayer.stop();
        }
        mediaPlayer=null;
        setData(songs);
        mediaPlayer.setAutoPlay(autoPlay);
        play();
    }
    public void play(){
        svg_reproduction_1.setContent("M48 64C21.5 64 0 85.5 0 112V400c0 26.5 21.5 48 48 48H80c26.5 0 48-21.5 48-48V112c0-26.5-21.5-48-48-48H48zm192 0c-26.5 0-48 21.5-48 48V400c0 26.5 21.5 48 48 48h32c26.5 0 48-21.5 48-48V112c0-26.5-21.5-48-48-48H240z");
        svg_reproduction_1.setScaleX(.09);
        svg_reproduction_1.setScaleY(.1);
        svg_reproduction_1.setLayoutX(-117);
        svg_reproduction_1.setLayoutY(-213);
        svg_reproduction_2.setContent("");
    }
    public void pause(){
        svg_reproduction_1.setContent("M8 1c3.9 0 7 3.1 7 7s-3.1 7-7 7-7-3.1-7-7 3.1-7 7-7zM8 0c-4.4 0-8 3.6-8 8s3.6 8 8 8 8-3.6 8-8-3.6-8-8-8v0z");
        svg_reproduction_1.setScaleX(4);
        svg_reproduction_1.setScaleY(4);
        svg_reproduction_1.setLayoutX(35);
        svg_reproduction_1.setLayoutY(35);
        svg_reproduction_2.setContent("M6 4v8l6-4z");
    }
    private void UpdateMediaPlayer(Song song) {
        SongName.setText(song.getName());
        if (Songs.size() > currentIndex + 1) {
            label_next.setText("Next: ");
            NextSongName.setText(Songs.get(currentIndex + 1).getName());
        }
        File img = new File(AlbumDAO.getInstance().searchAlbumByIdSong(song.getId_song()).getPicture());
        if (img.exists()) {
            SongImage.setImage(new javafx.scene.image.Image(img.toURI().toString()));
        }
    }

}
