package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Simptom;

import java.util.List;
import java.util.concurrent.Callable;

import static main.java.sample.BazaPodataka.*;
import static main.java.sample.BazaPodataka.aktivnaVezaSaBazom;

public class DohvatiSveOsobeNit implements Callable<List<Osoba>> {
    @Override
    public synchronized List<Osoba> call() throws Exception {
        while (aktivnaVezaSaBazom) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        aktivnaVezaSaBazom = true;
        List <Osoba> osobe = dohvatiSveOsobe();
        aktivnaVezaSaBazom = false;
        notifyAll();
        return osobe;
    }
}
