package main.java.sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Virus;
import main.java.hr.java.covidportal.model.Zupanija;
import main.java.hr.java.covidportal.niti.KreirajOsobuNit;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static main.java.sample.BazaPodataka.*;

public class KreiranjeOsobaController implements Initializable {

    @FXML
    private TextField ime;
    @FXML
    private TextField prezime;
    @FXML
    private DatePicker datumRodenja;
    @FXML
    private ChoiceBox zupanija;
    @FXML
    private ChoiceBox bolest;
    @FXML
    private ComboBox kontakti;
    @FXML
    private TextField id;

    private List<Osoba> izlazneOsobe = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            for (Zupanija z : dohvatiSveZupanije())
                zupanija.getItems().add(z.getNaziv());
            for (Bolest b : dohvatiSveBolesti())
                bolest.getItems().add(b.getNaziv());
            for (Virus v : dohvatiSveViruse())
                bolest.getItems().add(v.getNaziv());
            for (Osoba o : dohvatiSveOsobe())
                kontakti.getItems().add(o.getIme() + " " + o.getPrezime());
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }

    }

    public void dodajKontakt() throws SQLException, IOException, InterruptedException {
        for (Osoba o : dohvatiSveOsobe())
            if (kontakti.getValue().toString().toLowerCase().contains(o.getIme().toLowerCase()) &&
                    kontakti.getValue().toString().toLowerCase().contains(o.getPrezime().toLowerCase())) {
                izlazneOsobe.add(o);
            }
    }

    public void dodajOsobu() throws SQLException, IOException, InterruptedException {
        Long zupanijaId = null;
        Long bolestId = null;
        for (Zupanija z : dohvatiSveZupanije()) {
            if(zupanija.getValue().toString().equals(z.getNaziv())) {
                zupanijaId = z.getId();
            }
        }
        for (Bolest b : dohvatiSveBolesti()) {
            if(bolest.getValue().toString().equals(b.getNaziv())) {
                bolestId = b.getId();
            }
        }
        for (Virus v : dohvatiSveViruse()) {
            if(bolest.getValue().toString().equals(v.getNaziv())) {
                bolestId = v.getId();
            }
        }


        LocalDate odabraniDatum = datumRodenja.getValue();
        Osoba osoba = new Osoba.Builder(null).withIme(ime.getText()).withPrezime(prezime.getText()).withKontakti(izlazneOsobe).build();
        ExecutorService executorService = Executors.newCachedThreadPool();
        KreirajOsobuNit kreirajOsobuNit = new KreirajOsobuNit(osoba, zupanijaId, bolestId, odabraniDatum, izlazneOsobe);
        executorService.execute(kreirajOsobuNit);
        executorService.shutdown();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Covid Portal");
        alert.setHeaderText("Dobra vijest!");
        alert.setContentText("Aplikacija se nije skršila i osoba je uspješno dodana.");
        alert.showAndWait();
    }
}
