package main.java.hr.java.covidportal.model;

import java.io.Serializable;
import java.util.List;

/**
 * Predstavlja posebnu vrstu bolesti koja je zarazna
 */

public class Virus extends Bolest implements Zarazno, Serializable {

    /**
     * Kreira novu instancu virusa
     *
     * @param naziv - naziv virusa
     * @param simptomi - niz simptoma tog virusa
     */

    public Virus(Long id, String naziv, List<Simptom> simptomi) {
        super(id, naziv, simptomi);
    }

    @Override

    /**
     * Implementira sucelje zarazno
     */

    public void prelazakZarazeNaOsobu(Osoba osoba) {
        osoba.setZarazenBolescu(new Virus(this.id, this.naziv, this.simptomi));
    }
}
