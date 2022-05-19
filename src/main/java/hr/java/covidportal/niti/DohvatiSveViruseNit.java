package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Virus;

import java.util.List;
import java.util.concurrent.Callable;

import static main.java.sample.BazaPodataka.*;
import static main.java.sample.BazaPodataka.aktivnaVezaSaBazom;

public class DohvatiSveViruseNit implements Callable<List<Virus>> {
    @Override
    public synchronized List<Virus> call() throws Exception {
        while (aktivnaVezaSaBazom) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        aktivnaVezaSaBazom = true;
        List <Virus> bolesti = dohvatiSveViruse();
        aktivnaVezaSaBazom = false;
        notifyAll();
        return bolesti;

    }
}
