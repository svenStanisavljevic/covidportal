package main.java.sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Zupanija;
import main.java.hr.java.covidportal.niti.DohvatiSveSimptomeNit;
import main.java.hr.java.covidportal.niti.KreirajSimptomNit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static main.java.sample.BazaPodataka.spremiNoviSimptom;

public class KreiranjeSimptomaController implements Initializable {

    @FXML
    private TextField naziv;
    @FXML
    ChoiceBox vrijednost = new ChoiceBox<>();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vrijednost.setValue("Intenzivno");
    }



    public void dodajSimptom() throws SQLException, IOException, InterruptedException {
        Simptom simptom = new Simptom
                (null, naziv.getText(),
                        vrijednost.getValue().toString());
        ExecutorService executorService = Executors.newCachedThreadPool();
        KreirajSimptomNit kreirajSimptomNit = new KreirajSimptomNit(simptom);
        executorService.execute(kreirajSimptomNit);
        spremiNoviSimptom(simptom);
        executorService.shutdown();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Covid Portal");
        alert.setHeaderText("Dobra vijest!");
        alert.setContentText("Aplikacija se nije skršila i simptom je uspješno dodan.");
        alert.showAndWait();
    }



}
