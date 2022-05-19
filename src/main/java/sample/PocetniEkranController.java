package main.java.sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import main.java.hr.java.covidportal.niti.BrojVirusaNit;
import main.java.hr.java.covidportal.niti.NajviseZarazenihNit;
import main.java.hr.java.covidportal.niti.UneseniVirusNit;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static main.java.hr.java.covidportal.niti.BrojVirusaNit.brojVirusaGlobal;
import static main.java.hr.java.covidportal.niti.NajviseZarazenihNit.najveciNaziv;
import static main.java.sample.BazaPodataka.dohvatiBrojVirusa;


public class PocetniEkranController implements Initializable {

    @FXML
    public Label prviLabel;
    @FXML
    public Label drugiLabel;

    private static Boolean pocetak = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline brojVirusaTimeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            BrojVirusaNit brojVirusaNit = new BrojVirusaNit();
            ExecutorService executorService1 = Executors.newFixedThreadPool(1);
            Future<String> brojVirusaBuduci = executorService1.submit(brojVirusaNit);
            try {
                prviLabel.setText(brojVirusaBuduci.get());
            } catch (InterruptedException | ExecutionException interruptedException) {
                interruptedException.printStackTrace();
            }
            executorService1.shutdown();
        }), new KeyFrame(Duration.seconds(2)));
        brojVirusaTimeline.setCycleCount(Animation.INDEFINITE);
        brojVirusaTimeline.play();

        /*
        Timeline uneseniVirusTimeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            UneseniVirusNit uneseniVirusNit = new UneseniVirusNit();
            ExecutorService executorService2 = Executors.newFixedThreadPool(1);
            Future<String> u = executorService2.submit(uneseniVirusNit);
            try {
                prviLabel.setText(u.get());
            } catch (InterruptedException | ExecutionException interruptedException) {
                interruptedException.printStackTrace();
            }
            executorService2.shutdown();
        }), new KeyFrame(Duration.seconds(5)));
        uneseniVirusTimeline.setCycleCount(Animation.INDEFINITE);
        uneseniVirusTimeline.play();
*/
    }


}
