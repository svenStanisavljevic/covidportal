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
import main.java.hr.java.covidportal.model.Virus;
import main.java.hr.java.covidportal.niti.DohvatiSveBolestiNit;
import main.java.hr.java.covidportal.niti.DohvatiSveViruseNit;

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

import static main.java.sample.BazaPodataka.dohvatiSveViruse;


public class PretragaVirusaController implements Initializable {

    @FXML
    private TextField fieldPretraga;
    @FXML
    private TableColumn<Virus, String> nazivVirusaStupac;
    @FXML
    private TableColumn<Virus, String> simptomiStupac;
    @FXML
    private TableView<Virus> tablicaVirusa;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dohvacanjeVirusa();
        } catch (SQLException | IOException | ExecutionException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
        nazivVirusaStupac.setCellValueFactory(new PropertyValueFactory<Virus, String>("naziv"));
        simptomiStupac.setCellValueFactory(new PropertyValueFactory<Virus, String>("simptomi"));
    }

    @FXML
    public void dohvacanjeVirusa() throws SQLException, IOException, ExecutionException, InterruptedException {
        DohvatiSveViruseNit dohvatiSveViruseNit = new DohvatiSveViruseNit();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<List<Virus>> buduciVirusi = executorService.submit(dohvatiSveViruseNit);
        List<Virus> bolesti = buduciVirusi.get();
        List<Virus> filtriraneBolesti = bolesti.stream().filter(z -> z.getNaziv().toLowerCase().contains(fieldPretraga.getText().toLowerCase())).collect(Collectors.toList());
        ObservableList<Virus> observableVirus = FXCollections.observableArrayList(filtriraneBolesti);
        tablicaVirusa.setItems(observableVirus);
        executorService.shutdown();
    }
}
