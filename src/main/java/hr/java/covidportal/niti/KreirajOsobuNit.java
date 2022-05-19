package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.model.Osoba;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.List;

import static main.java.sample.BazaPodataka.*;
import static main.java.sample.BazaPodataka.aktivnaVezaSaBazom;

public class KreirajOsobuNit implements Runnable{

    private Osoba osoba;
    private Long zupanijaId;
    private Long bolestId;
    private LocalDate odabraniDatum;
    private List<Osoba> osobe;

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
            spremiNovuOsobu(osoba, zupanijaId, bolestId, odabraniDatum, osobe);
            aktivnaVezaSaBazom = false;
            notifyAll();
        } catch (SQLException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public KreirajOsobuNit(Osoba osoba, Long zupanijaId, Long bolestId, LocalDate odabraniDatum, List<Osoba> osobe) {
        this.osoba = osoba;
        this.zupanijaId = zupanijaId;
        this.bolestId = bolestId;
        this.odabraniDatum = odabraniDatum;
        this.osobe = osobe;
    }
}
