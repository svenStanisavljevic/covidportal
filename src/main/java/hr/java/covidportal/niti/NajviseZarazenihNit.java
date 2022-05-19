package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.model.Zupanija;
import main.java.hr.java.covidportal.sort.CovidSorter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static main.java.sample.BazaPodataka.dohvatiSveZupanije;

public class NajviseZarazenihNit implements Runnable {

    public static String najveciNaziv;

    @Override
    public void run() {

        while (true) {
            List<Zupanija> sveZupanije = new ArrayList<>();
            try {
                sveZupanije = dohvatiSveZupanije();
            } catch (SQLException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
            najveciNaziv = sveZupanije.stream().max(new CovidSorter()).get().getNaziv();
            System.out.println("Zupanija s najvecom stopom zarazenih - " + najveciNaziv);


            try { Thread.sleep(10000); }
            catch (InterruptedException exception)
            {
                exception.printStackTrace();
                Thread.currentThread().interrupt();
            }

        }
    }
}
