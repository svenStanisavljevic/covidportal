package main.java.sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.niti.DohvatiSveBolestiNit;
import main.java.hr.java.covidportal.niti.DohvatiSveOsobeNit;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static main.java.sample.BazaPodataka.aktivnaVezaSaBazom;
import static main.java.sample.BazaPodataka.dohvatiSveOsobe;


public class PretragaOsobaController implements Initializable {

    @FXML
    private TextField fieldPretraga;
    @FXML
    private TableColumn<Osoba, String> imeStupac;
    @FXML
    private TableColumn<Osoba, String> prezimeStupac;
    @FXML
    private TableColumn<Osoba, Integer> dobStupac;
    @FXML
    private TableColumn<Osoba, String> zupanijaStupac;
    @FXML
    private TableColumn<Osoba, String> zarazenStupac;
    @FXML
    private TableView<Osoba> tablicaOsoba;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dohvacanjeOsoba();
        } catch (SQLException | IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        imeStupac.setCellValueFactory(new PropertyValueFactory<Osoba, String>("ime"));
        prezimeStupac.setCellValueFactory(new PropertyValueFactory<Osoba, String>("prezime"));
        dobStupac.setCellValueFactory(new PropertyValueFactory<Osoba, Integer>("dob"));
        zupanijaStupac.setCellValueFactory(new PropertyValueFactory<Osoba, String>("zupanija"));
        zarazenStupac.setCellValueFactory(new PropertyValueFactory<Osoba, String>("zarazenBolescu"));
    }

    @FXML
    public synchronized void dohvacanjeOsoba() throws SQLException, IOException, ExecutionException, InterruptedException {
        DohvatiSveOsobeNit dohvatiSveOsobeNit = new DohvatiSveOsobeNit();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<List<Osoba>> buduceOsobe = executorService.submit(dohvatiSveOsobeNit);
        List<Osoba> osobe = buduceOsobe.get();
        List<Osoba> filtriraneOsobe = osobe.stream().filter(z -> (z.getIme().toLowerCase()
                .contains(fieldPretraga.getText().toLowerCase()) || z.getPrezime().toLowerCase()
                .contains(fieldPretraga.getText().toLowerCase()))).collect(Collectors.toList());
        ObservableList<Osoba> observableOsoba = FXCollections.observableArrayList(filtriraneOsobe);
        tablicaOsoba.setItems(observableOsoba);
        executorService.shutdown();
    }
}
