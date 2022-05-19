package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Zupanija;

import java.util.List;
import java.util.concurrent.Callable;

import static main.java.sample.BazaPodataka.*;
import static main.java.sample.BazaPodataka.aktivnaVezaSaBazom;

public class DohvatiSveZupanijeNit implements Callable<List<Zupanija>> {
    @Override
    public synchronized List<Zupanija> call() throws Exception {
        while (aktivnaVezaSaBazom) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        aktivnaVezaSaBazom = true;
        List <Zupanija> zupanije = dohvatiSveZupanije();
        aktivnaVezaSaBazom = false;
        notifyAll();
        return zupanije;
    }
}
