package main.java.sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.niti.KreirajBolestiNit;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static main.java.sample.BazaPodataka.dohvatiSveSimptome;
import static main.java.sample.BazaPodataka.spremiNovuBolest;

public class KreiranjeVirusaController implements Initializable {

    @FXML
    private TextField naziv;
    @FXML
    ComboBox simptomi = new ComboBox();

    List<Simptom> izlazniSimptomi = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            for (Simptom s : dohvatiSveSimptome())
                simptomi.getItems().add(s.getNaziv());
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dodajSimptom() throws SQLException, IOException, InterruptedException {
        for (Simptom s : dohvatiSveSimptome())
            if (s.getNaziv().toLowerCase().equals(simptomi.getValue().toString().toLowerCase()))
                izlazniSimptomi.add(s);
    }

    public void dodajBolest() throws SQLException, IOException {
        Bolest bolest = new Bolest(null, naziv.getText(), izlazniSimptomi);
        ExecutorService executorService = Executors.newCachedThreadPool();
        KreirajBolestiNit kreirajBolestiNit = new KreirajBolestiNit(bolest, true);
        executorService.execute(kreirajBolestiNit);
        executorService.shutdown();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Covid Portal");
        alert.setHeaderText("Dobra vijest!");
        alert.setContentText("Aplikacija se nije skršila i virus je uspješno dodan!.");
        alert.showAndWait();
    }
}
