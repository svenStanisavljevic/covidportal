package main.java.hr.java.covidportal.model;

import java.io.Serializable;

/**
 * Predstavlja simptome koje bolesti mogu imati i njihovu ucestalost
 */

public class Simptom extends ImenovaniEntitet implements Serializable {

    private String vrijednost;

    /**
     * Kreira novi simptom
     *
     * @param naziv - naziv simptoma
     * @param vrijednost - ucestalost simptoma
     */

    public Simptom(Long id, String naziv, String vrijednost) {
        super(naziv, id);
        this.vrijednost = vrijednost;
    }

    public String getNaziv() {
        return super.naziv;
    }
    public String getVrijednost() {
        return vrijednost;
    }
    public void setNaziv(String naziv) {
        super.naziv = naziv;
    }
    public void setVrijednost(String vrijednost) {
        this.vrijednost = vrijednost;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return naziv;
    }
}
