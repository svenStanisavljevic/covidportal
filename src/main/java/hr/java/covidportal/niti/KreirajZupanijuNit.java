package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.model.Virus;
import main.java.hr.java.covidportal.model.Zupanija;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static main.java.sample.BazaPodataka.*;
import static main.java.sample.BazaPodataka.aktivnaVezaSaBazom;

public class KreirajZupanijuNit implements Runnable{

    private Zupanija zupanija;

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
            spremiNovuZupaniju(zupanija);
            aktivnaVezaSaBazom = false;
            notifyAll();
        } catch (SQLException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public KreirajZupanijuNit(Zupanija zupanija) {
        this.zupanija = zupanija;
    }
}
