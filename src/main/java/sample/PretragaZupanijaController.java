package main.java.sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import main.java.hr.java.covidportal.model.Zupanija;
import main.java.hr.java.covidportal.niti.DohvatiSveZupanijeNit;
import main.java.hr.java.covidportal.niti.NajviseZarazenihNit;

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

import static main.java.hr.java.covidportal.niti.NajviseZarazenihNit.najveciNaziv;
import static main.java.sample.BazaPodataka.dohvatiSveZupanije;


public class PretragaZupanijaController implements Initializable {

    @FXML
    private TextField fieldPretraga;
    @FXML
    private TableColumn<Zupanija, String> nazivZupanijaStupac;
    @FXML
    private TableColumn<Zupanija, Integer> stanovniciStupac;
    @FXML
    private TableColumn<Zupanija, Integer> zarazeniStupac;
    @FXML
    private TableView<Zupanija> tablicaZupanija;

    private static Boolean pretragaStisnuta = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!pretragaStisnuta) {
            NajviseZarazenihNit najviseZarazenihNit = new NajviseZarazenihNit();
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute(najviseZarazenihNit);
            pretragaStisnuta = true;
        }
        try {
            dohvacanjeZupanija();
        } catch (SQLException | IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Timeline najviseZarazenihTimeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            Main.glavniStage.setTitle("Covid Portal - " + najveciNaziv);
        }), new KeyFrame(Duration.seconds(10)));
        najviseZarazenihTimeline.play();

        nazivZupanijaStupac.setCellValueFactory(new PropertyValueFactory<Zupanija, String>("naziv"));
        stanovniciStupac.setCellValueFactory(new PropertyValueFactory<Zupanija, Integer>("brojStanovnika"));
        zarazeniStupac.setCellValueFactory(new PropertyValueFactory<Zupanija, Integer>("brojZarazenih"));
    }

    @FXML
    public void dohvacanjeZupanija() throws SQLException, IOException, ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        DohvatiSveZupanijeNit sveZupanijeNit = new DohvatiSveZupanijeNit();
        Future<List<Zupanija>> buduceZupanije = executorService.submit(sveZupanijeNit);
        List<Zupanija> zupanije = buduceZupanije.get();
        List<Zupanija> filtriraneZupanije = zupanije.stream()
                .filter(z -> z.getNaziv().toLowerCase()
                        .contains(fieldPretraga.getText().toLowerCase()))
                        .collect(Collectors.toList());
        ObservableList<Zupanija> observableZupanija = FXCollections.observableArrayList(filtriraneZupanije);
        tablicaZupanija.setItems(observableZupanija);
        executorService.shutdown();
    }
}
