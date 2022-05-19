package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Osoba;

import java.util.List;
import java.util.concurrent.Callable;

import static main.java.sample.BazaPodataka.*;
import static main.java.sample.BazaPodataka.aktivnaVezaSaBazom;

public class DohvatiSveBolestiNit implements Callable<List<Bolest>> {
    @Override
    public synchronized List<Bolest> call() throws Exception {
        while (aktivnaVezaSaBazom) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        aktivnaVezaSaBazom = true;
        List <Bolest> bolesti = dohvatiSveBolesti();
        aktivnaVezaSaBazom = false;
        notifyAll();
        return bolesti;
    }

}
