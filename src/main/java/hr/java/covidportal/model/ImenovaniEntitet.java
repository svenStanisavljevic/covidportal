package main.java.hr.java.covidportal.model;

import java.io.Serializable;

/**
 * Koristi se da bi ostale klase nasljedivale naziv
 */

abstract class ImenovaniEntitet implements Serializable {

    String naziv;
    Long id;

    public ImenovaniEntitet(String naziv, Long id) {
        this.naziv = naziv;
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
