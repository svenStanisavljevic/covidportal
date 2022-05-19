package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.model.Bolest;

import java.io.IOException;
import java.sql.SQLException;

import static main.java.sample.BazaPodataka.*;
import static main.java.sample.BazaPodataka.aktivnaVezaSaBazom;

public class KreirajBolestiNit implements Runnable {

    private Bolest bolest;
    private Boolean statusVirusa;

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
            spremiNovuBolest(bolest, statusVirusa);
            aktivnaVezaSaBazom = false;
            notifyAll();
        } catch (SQLException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public KreirajBolestiNit(Bolest bolest, Boolean statusVirusa) {
        this.bolest = bolest;
        this.statusVirusa = statusVirusa;
    }
}
