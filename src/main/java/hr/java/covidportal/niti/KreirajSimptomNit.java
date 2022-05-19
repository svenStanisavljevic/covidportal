package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.model.Simptom;

import java.io.IOException;
import java.sql.SQLException;

import static main.java.sample.BazaPodataka.*;
import static main.java.sample.BazaPodataka.aktivnaVezaSaBazom;

public class KreirajSimptomNit implements Runnable{

    Simptom simptom;

    @Override
    public synchronized void run() {
        try {
            while (aktivnaVezaSaBazom) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            aktivnaVezaSaBazom = true;
            spremiNoviSimptom(simptom);
            aktivnaVezaSaBazom = false;
            notifyAll();
        } catch (SQLException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public KreirajSimptomNit(Simptom simptom) {
        this.simptom = simptom;
    }
}
