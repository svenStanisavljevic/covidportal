package main.java.sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.niti.DohvatiSveSimptomeNit;

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



public class PretragaSimptomaController implements Initializable {

    @FXML
    private TextField fieldPretraga;
    @FXML
    private TableColumn<Simptom, String> nazivSimptomaStupac;
    @FXML
    private TableColumn<Simptom, String> vrijednostStupac;
    @FXML
    private TableView<Simptom> tablicaSimptoma;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dohvacanjeSimptoma();
        } catch (SQLException | IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        nazivSimptomaStupac.setCellValueFactory(new PropertyValueFactory<Simptom, String>("naziv"));
        vrijednostStupac.setCellValueFactory(new PropertyValueFactory<Simptom, String>("vrijednost"));


        tablicaSimptoma.setRowFactory(r -> {
            TableRow<Simptom> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Simptom simptom = row.getItem();
                    try {
                        clicked(simptom);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

    }

    @FXML
    public void dohvacanjeSimptoma() throws SQLException, IOException, ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        DohvatiSveSimptomeNit dohvatiSveSimptomeNit = new DohvatiSveSimptomeNit();
        Future<List<Simptom>> buduciSimptomi = executorService.submit(dohvatiSveSimptomeNit);
        List<Simptom> simptomi = buduciSimptomi.get();
        List<Simptom> filtriraneSimptoma = simptomi.stream().filter(z -> z.getNaziv().toLowerCase()
                .contains(fieldPretraga.getText().toLowerCase())).collect(Collectors.toList());
        ObservableList<Simptom> observableSimptom = FXCollections.observableArrayList(filtriraneSimptoma);
        tablicaSimptoma.setItems(observableSimptom);
        executorService.shutdown();
    }

    @FXML
    public void clicked(Simptom simptom) throws IOException {
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource(("detaljiSimptoma.fxml")));

            Stage stage = new Stage();
            stage.setTitle("Prikazi simptom");
            stage.setScene(new Scene(loader.load(), 1920, 1080));
            stage.show();

            DetaljiSimptomaController detaljiSimptomaController=loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
