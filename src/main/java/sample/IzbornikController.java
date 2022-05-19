package main.java.sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class IzbornikController implements Initializable {

    @FXML
    private MenuItem pretragaZupanijaMenu;
    @FXML
    private MenuItem pretragaSimptomaMenu;
    @FXML
    private MenuItem pretragaBolestiMenu;
    @FXML
    private MenuItem pretragaVirusaMenu;
    @FXML
    private MenuItem pretragaOsobaMenu;
    @FXML
    private MenuItem kreiranjeZupanijaMenu;
    @FXML
    private MenuItem kreiranjeSimptomaMenu;
    @FXML
    private MenuItem kreiranjeBolestiMenu;
    @FXML
    private MenuItem kreiranjeVirusaMenu;
    @FXML
    private MenuItem kreiranjeOsobaMenu;
    @FXML
    public void prikaziEkranZaPretraguZupanija() throws IOException {

        Parent pretragaZupanijaFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "pretragaZupanija.fxml")));

        Scene pretragaZupanijaScene = new Scene(pretragaZupanijaFrame, 1920, 1080   );

        Main.getMainStage().setScene(pretragaZupanijaScene);

    }

    @FXML
    public void prikaziEkranZaPretraguSimptoma() throws IOException {


        Parent pretragaSimptomaFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "pretragaSimptoma.fxml")));

        Scene pretragaSimptomaScene = new Scene(pretragaSimptomaFrame, 1920, 1080   );

        Main.getMainStage().setScene(pretragaSimptomaScene);

    }

    @FXML
    public void prikaziEkranZaPretraguBolesti() throws IOException {

        Parent pretragaBolestiFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "pretragaBolesti.fxml"));

        Scene pretragaBolestiScene = new Scene(pretragaBolestiFrame, 1920, 1080   );

        Main.getMainStage().setScene(pretragaBolestiScene);

    }

    @FXML
    public void prikaziEkranZaPretraguVirusa() throws IOException {

        Parent pretragaVirusaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "pretragaVirusa.fxml"));

        Scene pretragaVirusaScene = new Scene(pretragaVirusaFrame, 1920, 1080   );

        Main.getMainStage().setScene(pretragaVirusaScene);

    }

    @FXML
    public void prikaziEkranZaPretraguOsoba() throws IOException {

        Parent pretragaVirusaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "pretragaOsoba.fxml"));

        Scene pretragaVirusaScene = new Scene(pretragaVirusaFrame, 1920, 1080   );

        Main.getMainStage().setScene(pretragaVirusaScene);

    }
    @FXML
    public void prikaziEkranZaKreiranjeZupanija() throws IOException {

        Parent pretragaZupanijaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "kreiranjeZupanija.fxml"));

        Scene pretragaZupanijaScene = new Scene(pretragaZupanijaFrame, 1920, 1080   );

        Main.getMainStage().setScene(pretragaZupanijaScene);

    }
    @FXML
    public void prikaziEkranZaKreiranjeSimptoma() throws IOException {

        Parent pretragaZupanijaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "kreiranjeSimptoma.fxml"));

        Scene pretragaZupanijaScene = new Scene(pretragaZupanijaFrame, 1920, 1080   );

        Main.getMainStage().setScene(pretragaZupanijaScene);

    }
    @FXML
    public void prikaziEkranZaKreiranjeBolesti() throws IOException {

        Parent pretragaZupanijaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "kreiranjeBolesti.fxml"));

        Scene pretragaZupanijaScene = new Scene(pretragaZupanijaFrame, 1920, 1080   );

        Main.getMainStage().setScene(pretragaZupanijaScene);

    }
    @FXML
    public void prikaziEkranZaKreiranjeVirusa() throws IOException {

        Parent pretragaZupanijaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "kreiranjeVirusa.fxml"));

        Scene pretragaZupanijaScene = new Scene(pretragaZupanijaFrame, 1920, 1080   );

        Main.getMainStage().setScene(pretragaZupanijaScene);

    }
    @FXML
    public void prikaziEkranZaKreiranjeOsoba() throws IOException {

        Parent pretragaZupanijaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource(
                        "kreiranjeOsoba.fxml"));

        Scene pretragaZupanijaScene = new Scene(pretragaZupanijaFrame, 1920, 1080   );

        Main.getMainStage().setScene(pretragaZupanijaScene);

    }



    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}