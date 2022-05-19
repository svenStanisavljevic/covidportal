package main.java.sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import main.java.hr.java.covidportal.model.Zupanija;
import main.java.hr.java.covidportal.niti.KreirajZupanijuNit;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KreiranjeZupanijaController implements Initializable {

    @FXML
    private TextField naziv;
    @FXML
    private TextField brojStanovnika;
    @FXML
    private TextField brojZarazenih;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void kreirajZupaniju() throws SQLException, IOException {
        Zupanija zupanija = new Zupanija
                (null, naziv.getText(),
                        Integer.parseInt(brojStanovnika.getText()), Integer.parseInt(brojZarazenih.getText()));

        ExecutorService executorService = Executors.newCachedThreadPool();
        KreirajZupanijuNit kreirajZupanijuNit = new KreirajZupanijuNit(zupanija);
        executorService.execute(kreirajZupanijuNit);
        executorService.shutdown();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Covid Portal");
        alert.setHeaderText("Dobra vijest!");
        alert.setContentText("Aplikacija se nije skršila i županija je uspješno dodana.");
        alert.showAndWait();
    }

}
