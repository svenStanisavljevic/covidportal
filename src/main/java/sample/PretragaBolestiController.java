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
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.niti.DohvatiSveBolestiNit;

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

import static main.java.sample.BazaPodataka.dohvatiSveBolesti;


public class PretragaBolestiController implements Initializable {

    @FXML
    private TextField fieldPretraga;
    @FXML
    private TableColumn<Bolest, String> nazivBolestiStupac;
    @FXML
    private TableColumn<Simptom, String> simptomiStupac;
    @FXML
    private TableView<Bolest> tablicaBolesti;

    @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dohvacanjeBolesti();
        } catch (SQLException | IOException | ExecutionException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
        nazivBolestiStupac.setCellValueFactory(new PropertyValueFactory<Bolest, String>("naziv"));
        simptomiStupac.setCellValueFactory(new PropertyValueFactory<Simptom, String>("simptomi"));
    }

    @FXML
    public void dohvacanjeBolesti() throws SQLException, IOException, ExecutionException, InterruptedException {
        DohvatiSveBolestiNit dohvatiSveBolestiNit = new DohvatiSveBolestiNit();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<List<Bolest>> buduceBolesti = executorService.submit(dohvatiSveBolestiNit);
        List<Bolest> bolesti = buduceBolesti.get();
        List<Bolest> filtriraneBolesti = bolesti.stream().filter(z -> z.getNaziv().toLowerCase().contains(fieldPretraga.getText().toLowerCase())).collect(Collectors.toList());
        ObservableList<Bolest> observableBolest = FXCollections.observableArrayList(filtriraneBolesti);
        tablicaBolesti.setItems(observableBolest);
        executorService.shutdown();
    }
}
