package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.model.Simptom;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import static main.java.sample.BazaPodataka.aktivnaVezaSaBazom;
import static main.java.sample.BazaPodataka.dohvatiSveSimptome;

public class DohvatiSveSimptomeNit implements Callable <List<Simptom>> {
    @Override
    public synchronized List<Simptom> call() throws InterruptedException, IOException, SQLException {
        while (aktivnaVezaSaBazom) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        aktivnaVezaSaBazom = true;
        List <Simptom> simptomi = dohvatiSveSimptome();
        aktivnaVezaSaBazom = false;
        notifyAll();
        return simptomi;

    }
}
