package dev.iesfranciscodelosrios.acdmusic.Components.MediaPlayer;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.AlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.SongDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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

    private List<Song> Songs=new ArrayList<>();
    private int indexSong=0;
    private MediaPlayer mediaPlayer;
    private Media currentMedia;
    // Timer es una clase que permite programar tareas para que se ejecuten en un hilo en un momento específico o después de un retraso.
    private Timer timer;
    // TimerTask es una clase para crear tareas que se ejecutan a intervalos regulares de tiempo. eso quiere decir que se ejecuta cada x tiempo
    private TimerTask interval;
    private int currentIndex;

    private boolean running;
    @FXML
    public void initialize(){
        volumeSlider.setValue(100);
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volumeSlider.getValue()/100);
            }
        });
    }
    public void handleTogglePlay(){
        if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        }else{
            mediaPlayer.play();
        }
    }
    @FXML
    public void handleNext(){
        if(Songs.size()>currentIndex+1) {
            mediaPlayer.stop();
            mediaPlayer=null;
            currentIndex++;
            setSong(Songs.get(currentIndex));
        }
    }
    @FXML
    public void handlePrevious(){
        if(currentIndex>0) {
            cancelTimer();
            mediaPlayer.stop();
            mediaPlayer=null;
            currentIndex--;
            setSong(Songs.get(currentIndex));
        }
    }
    public void progressTimer(){
        timer = new Timer();

        interval = new TimerTask() {
        boolean reproducido=false;
            public void run() {
                //vamos a comprobar si se han reproducido los 5 primeros segundos de la cancion
                if(running && mediaPlayer.getCurrentTime().toSeconds()>5 && !reproducido){
                    reproducido=true;
                    SongDAO.getInstance().updateReproductions(Songs.get(currentIndex).getId_song());
                }

                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = currentMedia.getDuration().toSeconds();
                songProgressBar.setProgress(current/end);

                if(current/end == 1) {
                    handleNext();
                }
            }
        };

        timer.scheduleAtFixedRate(interval, 0, 1000);
    }
    public void cancelTimer(){
        running = false;
        timer.cancel();
    }
    public void handleVolume(){

    }
    public void setData(Set<Song> songs){
        if(!songs.isEmpty()){
            Songs.clear();
        }
        for (Song aux:songs) {
            Songs.add(aux);
        }
        //Apuntamos a la primera cancion de la lista
        Song firstSong=Songs.stream().findFirst().get();
        currentIndex=0;
        setSong(firstSong);
    }

    public void setSong(Song song){
        System.out.println(song);
        currentMedia = new Media(new File(song.getUrl()).toURI().toString());
        mediaPlayer= new MediaPlayer(currentMedia);
        progressTimer();
        UpdateMediaPlayer(song);
    }
    private void UpdateMediaPlayer(Song song){
        SongName.setText(song.getName());
        if(Songs.size()>currentIndex+1) {
            label_next.setText("Next: ");
            NextSongName.setText(Songs.get(currentIndex + 1).getName());
        }
        File img = new File(AlbumDAO.getInstance().getAlbumById(song.getId_song()).getPicture());
        if (img.exists()) {
            SongImage.setImage(new javafx.scene.image.Image(img.toURI().toString()));
        }
    }

}
